package io.jenkins.plugins.rmsis.reporter;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.rmsis.model.CustomField;
import io.jenkins.plugins.rmsis.model.Instance;
import io.jenkins.plugins.rmsis.model.Project;
import io.jenkins.plugins.rmsis.model.TestRun;
import io.jenkins.plugins.rmsis.utils.Constants;
import io.jenkins.plugins.rmsis.utils.RestClient;
import io.jenkins.plugins.rmsis.utils.URLValidator;
import io.jenkins.plugins.rmsis.utils.Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * ${Copyright}
 */

@Extension
public class TestRunDescriptor extends BuildStepDescriptor<Publisher>
{
  private List<Instance> instances;

  public TestRunDescriptor()
  {
    super(TestRunNotifier.class);
    load();
  }

  public List<Instance> getInstances()
  {
    return instances;
  }

  public void setInstances(List<Instance> instances)
  {
    this.instances = instances;
  }

  @Override
  public Publisher newInstance(@CheckForNull StaplerRequest req, @Nonnull JSONObject formData) throws FormException
  {
    return super.newInstance(req, formData);
  }

  @Nonnull
  @Override
  public String getDisplayName()
  {
    return Constants.DISPLAY_NAME;
  }

  @Override
  public boolean isApplicable(Class<? extends AbstractProject> aClass)
  {
    return true;
  }

  @Override
  public boolean configure(StaplerRequest req, JSONObject formData) throws FormException
  {
    req.bindParameters(this);
    this.instances = new ArrayList<>();

    Object object = formData.get("instances");

    if (object instanceof JSONArray) {
      JSONArray array = (JSONArray) object;

      array.forEach(this::accept);

    } else if (object instanceof JSONObject) {
      JSONObject jsonObject = formData.getJSONObject("instances");
      accept(jsonObject);
    }

    save();
    return super.configure(req, formData);
  }

  private void accept(Object o)
  {
    JSONObject jsonObject = (JSONObject) o;
    String server = URLValidator.validate(jsonObject.getString(Constants.GLOBAL_SERVER_URL).trim());
    String username = jsonObject.getString(Constants.GLOBAL_USERNAME).trim();
    String password = jsonObject.getString(Constants.GLOBAL_PASSWORD).trim();

    Instance instance = new Instance(server, username, password);
    RestClient client = null;
    boolean valid;
    try {
      client = new RestClient(instance);
      valid = client.isLogged();
    } finally {
      if (client != null) client.destroy();
    }

    if (valid) {
      instances.add(instance);
    }
  }

  @SuppressWarnings("unused")
  public FormValidation doTestConnection(@QueryParameter String server,
                                         @QueryParameter String username,
                                         @QueryParameter String password)
  {
    if (StringUtils.isBlank(server)) {
      return FormValidation.error("Please enter the server");
    }

    if (StringUtils.isBlank(username)) {
      return FormValidation.error("Please enter the username");
    }

    if (StringUtils.isBlank(password)) {
      return FormValidation.error("Please enter the password");
    }

    if (!(server.trim().startsWith("https://") || server.trim().startsWith("http://"))) {
      return FormValidation.error("Incorrect server address format");
    }

    URLValidator.validate(server);
    RestClient client = null;
    boolean valid;
    try {
      client = new RestClient(server, username, password);
      valid = client.isLogged();
    } finally {
      if (client != null) client.destroy();
    }

    if (!valid) {
      return FormValidation.error("Invalid url or login credentials");
    }

    return FormValidation.ok("Connection to Jira RMsis has been validated");
  }

  @SuppressWarnings("unused")
  public ListBoxModel doFillServerItems()
  {
    ListBoxModel model = new ListBoxModel();
    model.add(Constants.ADD_GLOBAL_SERVER_CONFIG);
    if (!this.instances.isEmpty()) {
      model.add(Constants.SELECT_SERVER, Constants.EMPTY);
      for (Instance instance : instances) {
        model.add(instance.getServer());
      }
    } else {
      model.add(Constants.ADD_GLOBAL_SERVER_CONFIG);
    }

    return model;
  }

  @SuppressWarnings("unused")
  public ListBoxModel doFillProjectItems(@QueryParameter String server)
  {
    ListBoxModel model = new ListBoxModel();
    if (StringUtils.isBlank(server)
        || server.trim().equals(Constants.ADD_GLOBAL_SERVER_CONFIG)
        || (this.instances.isEmpty())) {
      model.add(Constants.ADD_GLOBAL_SERVER_CONFIG);
      return model;
    }

    if (server.trim().equals(Constants.EMPTY)) {
      model.add(Constants.SELECT_SERVER);
      return model;
    }

    RestClient client = null;
    List<Project> projects;
    try {
      client = getByServer(server);
      projects = client.getProjects();
    } finally {
      if (client != null) client.destroy();
    }
    for (Project project : projects) {
      model.add(project.getName(), project.getId() + "");
    }

    return model;
  }

  @SuppressWarnings("unused")
  public ListBoxModel doFillTestRunItems(@QueryParameter String project,
                                         @QueryParameter String server)
  {
    ListBoxModel model = new ListBoxModel();

    if (server.trim().equals(Constants.EMPTY)) {
      model.add(Constants.SELECT_SERVER);
      return model;
    }

    if (StringUtils.isBlank(project)
        || project.trim().equals(Constants.ADD_GLOBAL_SERVER_CONFIG)
        || (this.instances.isEmpty())) {
      model.add(Constants.ADD_GLOBAL_SERVER_CONFIG);
      return model;
    }

    Long projectId = Util.getLong(project);
    if (null == projectId) return model;

    RestClient client = null;
    List<TestRun> testRuns;
    try {
      client = getByServer(server);
      testRuns = client.getTestRuns(projectId);
    } finally {
      if (client != null) client.destroy();
    }

    for (TestRun testRun : testRuns) {
      model.add(testRun.getName(), testRun.getId() + "");
    }

    return model;
  }

  @SuppressWarnings("unused")
  public ListBoxModel doFillMethodCustomFieldItems(@QueryParameter String project,
                                                   @QueryParameter String server)
  {
    ListBoxModel model = new ListBoxModel();
    if (StringUtils.isBlank(project)
        || project.trim().equals(Constants.ADD_GLOBAL_SERVER_CONFIG)
        || (this.instances.isEmpty())) {
      model.add(Constants.ADD_GLOBAL_SERVER_CONFIG);
      return model;
    }

    Long projectId = Util.getLong(project);
    if (null == projectId) return model;

    RestClient client = null;
    List<CustomField> customFields;
    try {
      client = getByServer(server);
      customFields = client.getTestCaseCustomFields(projectId);
    } finally {
      if (client != null) client.destroy();
    }

    for (CustomField customField : customFields) {
      model.add(customField.getName(), customField.getId() + "");
    }

    return model;
  }

  private RestClient getByServer(String server)
  {
    for (Instance instance : instances) {
      if (instance.getServer().equals(server)) {
        return new RestClient(instance);
      }
    }

    return null;
  }
}
