package io.jenkins.plugins.rmsis.utils;

import com.atlassian.jira.rest.client.api.domain.BasicProject;
import io.jenkins.plugins.rmsis.clients.CustomJiraRestClient;
import io.jenkins.plugins.rmsis.clients.CustomJiraRestClientFactory;
import io.jenkins.plugins.rmsis.clients.graphql.model.*;
import io.jenkins.plugins.rmsis.model.*;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ${Copyright}
 */
public class RestClient
{
  private String server;
  private String username;
  private String password;
  private CustomJiraRestClient client;


  public RestClient(String server, String username, String password)
  {
    this.server = server;
    this.username = username;
    this.password = password;

    initialize();
  }

  public RestClient(Instance instance)
  {
    this(instance.getServer(), instance.getUsername(), instance.getPassword());
  }

  private void initialize()
  {
    CustomJiraRestClientFactory factory = new CustomJiraRestClientFactory();

    this.client = (CustomJiraRestClient) factory
        .createWithBasicHttpAuthentication(URI.create(server), username, password);
  }

  public void destroy()
  {
    if (null != this.client) {
      try {
        this.client.close();
      } catch (IOException e) {
        //ignore
      }
    }
  }

  public boolean isLogged()
  {
    try {
      this.client.getSessionClient().getCurrentSession().claim().getUsername();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public List<Project> getProjects()
  {
    GraphProjects graphProjects = client.getGraphRestClient().getProjects().claim();
    Iterable<BasicProject> jiraProjects = client.getProjectClient().getAllProjects().claim();
    List<Project> projects = new ArrayList<>();

    for (GraphProject project : graphProjects.getProjects()) {
      Long id = project.getId();
      Long external = Util.getLong(project.getExternalId());
      BasicProject jp = null;

      for (BasicProject p : jiraProjects) {
        if (Objects.equals(p.getId(), external)) {
          jp = p;
          break;
        }
      }

      if (jp != null) projects.add(new Project(id, jp.getKey(), jp.getName()));
    }

    return projects;
  }

  public List<TestRun> getTestRuns(Long project)
  {
    GraphTestRuns testRuns = client.getGraphRestClient().getTestRuns(project).claim();
    List<TestRun> testRunList = new ArrayList<>();

    for (GraphTestRun testRun : testRuns.getTestRuns()) {
      testRunList.add(new TestRun(testRun.getId(), testRun.getName()));
    }

    return testRunList;
  }

  public List<CustomField> getTestCaseCustomFields(Long project)
  {
    GraphCustomFields customFields = client.getGraphRestClient().getCustomFields(project).claim();
    List<CustomField> customFieldList = new ArrayList<>();

    for (GraphCustomField customField : customFields.getCustomFields()) {
      if ("TEXT".equals(customField.getType())) {
        customFieldList.add(new CustomField(customField.getId(), customField.getName()));
      }
    }

    return customFieldList;
  }

  List<TestCase> getTestCases(Long project, Long testRun, Long customField)
  {
    GraphTestCases testCases = client.getGraphRestClient().getTestCases(project, testRun, customField).claim();
    List<TestCase> testCaseList = new ArrayList<>();

    for (GraphTestCase testCase : testCases.getTestCases()) {
      testCaseList.add(new TestCase(testCase.getId(), testCase.getName(), testCase.getCustomValue()));
    }

    return testCaseList;
  }

  List<TestCaseStatus> getTestCaseStatuses()
  {
    GraphTestCaseStatuses testCaseStatuses = client.getGraphRestClient().getTestCaseStatuses().claim();
    List<TestCaseStatus> testCaseStatusList = new ArrayList<>();

    for (GraphTestCaseStatus testCaseStatus : testCaseStatuses.getTestCaseStatuses()) {
      testCaseStatusList.add(new TestCaseStatus(testCaseStatus.getId(), testCaseStatus.getName()));
    }

    return testCaseStatusList;
  }

  void setTestCaseStatus(Long testRun, Long testCase, Long status)
  {
    client.getGraphRestClient().setTestCaseStatus(testRun, testCase, status);
  }
}
