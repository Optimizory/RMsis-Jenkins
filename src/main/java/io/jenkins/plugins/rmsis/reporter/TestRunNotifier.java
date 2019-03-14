package io.jenkins.plugins.rmsis.reporter;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.junit.CaseResult;
import hudson.tasks.junit.SuiteResult;
import hudson.tasks.junit.TestResultAction;
import io.jenkins.plugins.rmsis.model.Instance;
import io.jenkins.plugins.rmsis.model.InstanceConfig;
import io.jenkins.plugins.rmsis.utils.ResultUploader;
import io.jenkins.plugins.rmsis.utils.Util;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ${Copyright}
 */
public class TestRunNotifier extends Notifier
{
  private final static Logger LOG = Logger.getLogger(TestRunNotifier.class.getName());

  private String server;
  private String project;
  private String testRun;
  private String methodCustomField;

  @DataBoundConstructor
  public TestRunNotifier(String server, String project, String testRun, String methodCustomField)
  {
    this.server = server;
    this.project = project;
    this.testRun = testRun;
    this.methodCustomField = methodCustomField;
  }

  public String getServer()
  {
    return server;
  }

  public void setServer(String server)
  {
    this.server = server;
  }

  public String getProject()
  {
    return project;
  }

  public void setProject(String project)
  {
    this.project = project;
  }

  @SuppressWarnings("unused")
  public String getTestRun()
  {
    return testRun;
  }

  @SuppressWarnings("unused")
  public void setTestRun(String testRun)
  {
    this.testRun = testRun;
  }

  @SuppressWarnings("unused")
  public String getMethodCustomField()
  {
    return methodCustomField;
  }

  @SuppressWarnings("unused")
  public void setMethodCustomField(String methodCustomField)
  {
    this.methodCustomField = methodCustomField;
  }

  @Override
  public boolean perform(final AbstractBuild build, final Launcher launcher, final BuildListener listener)
  {
    if (!validateConfig()) {
      LOG.log(Level.WARNING, "Invalid build configuration, please verify!");
      return false;
    }

    InstanceConfig config = initInstanceConfig();
    boolean tests = prepareTests(build, config);

    if (!tests) {
      LOG.log(Level.SEVERE, "Error parsing surefire reports.");
      LOG.log(Level.SEVERE, "Please ensure \"Publish JUnit test result report is added\" as a post build action");

      return false;
    }

    try {
      ResultUploader.upload(config);
    } catch (Exception e) {
      LOG.log(Level.SEVERE, "Error uploading");
    }

    LOG.log(Level.INFO, "Done uploading!!!");

    return true;
  }

  private boolean prepareTests(final AbstractBuild build, InstanceConfig config)
  {
    TestResultAction testResultAction = build.getAction(TestResultAction.class);
    Collection<SuiteResult> suites;
    try {
      suites = testResultAction.getResult().getSuites();
    } catch (Exception e) {
      LOG.log(Level.SEVERE, e.getLocalizedMessage());
      return false;
    }

    if (suites == null || suites.isEmpty()) {
      return false;
    }

    Map<String, Boolean> testCaseResultMap = prepareResults(suites);
    config.setTestCaseResults(testCaseResultMap);

    return true;
  }

  private Map<String, Boolean> prepareResults(Collection<SuiteResult> suites)
  {
    Map<String, Boolean> resultMap = new HashMap<>();

    for (SuiteResult suite : suites) {
      for (CaseResult caseResult : suite.getCases()) {
        resultMap.put(caseResult.getFullName(), caseResult.isPassed());
      }
    }

    return resultMap;
  }

  private InstanceConfig initInstanceConfig()
  {
    InstanceConfig config = new InstanceConfig();
    List<Instance> instanceList = getDescriptor().getInstances();
    config.setProject(Util.getLong(project));
    config.setTestRun(Util.getLong(testRun));
    config.setCustomField(Util.getLong(methodCustomField));
    for (Instance instance : instanceList) {
      if (instance.getServer().equals(server)) {
        config.setInstance(instance);
        break;
      }
    }

    return config;
  }

  private boolean validateConfig()
  {
    boolean isValid = true;
    if (StringUtils.isBlank(server)
        || StringUtils.isBlank(project)
        || StringUtils.isBlank(testRun)
        || StringUtils.isBlank(methodCustomField)) {

      isValid = false;

    }
    return isValid;
  }

  @Override
  public BuildStepMonitor getRequiredMonitorService()
  {
    return BuildStepMonitor.NONE;
  }

  @Override
  public TestRunDescriptor getDescriptor()
  {
    return (TestRunDescriptor) super.getDescriptor();
  }

  @Override
  public boolean needsToRunAfterFinalized()
  {
    return true;
  }
}
