package io.jenkins.plugins.rmsis.model;

import java.util.Map;

/**
 * ${Copyright}
 */
public class InstanceConfig
{
  private Long project;
  private Long testRun;
  private Long customField;
  private Instance instance;
  private Map<String, Boolean> testCaseResultMap;

  public Long getProject()
  {
    return project;
  }

  public void setProject(Long project)
  {
    this.project = project;
  }

  public Long getTestRun()
  {
    return testRun;
  }

  public void setTestRun(Long testRun)
  {
    this.testRun = testRun;
  }

  public Long getCustomField()
  {
    return customField;
  }

  public void setCustomField(Long customField)
  {
    this.customField = customField;
  }

  public Instance getInstance()
  {
    return instance;
  }

  public void setInstance(Instance instance)
  {
    this.instance = instance;
  }

  public void setTestCaseResults(Map<String, Boolean> testCaseResultMap)
  {
    this.testCaseResultMap = testCaseResultMap;
  }

  public Map<String, Boolean> getTestCaseResultMap()
  {
    return testCaseResultMap;
  }
}
