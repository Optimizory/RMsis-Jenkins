package io.jenkins.plugins.rmsis.clients.graphql;

import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.atlassian.util.concurrent.Promise;
import io.jenkins.plugins.rmsis.clients.graphql.helper.*;
import io.jenkins.plugins.rmsis.clients.graphql.model.*;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * ${Copyright}
 */
public class GraphRestClient extends AbstractAsynchronousRestClient
{
  private URI graphURI;

  public GraphRestClient(URI graphURI, DisposableHttpClient httpClient)
  {
    super(httpClient);
    this.graphURI = graphURI;
  }

  @SuppressWarnings("UnstableApiUsage")
  public Promise<GraphProjects> getProjects()
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "{getProjects{id externalId}}";

    return (Promise<GraphProjects>) this.postAndParse(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator(),
        new GraphProjectsParser());
  }

  @SuppressWarnings("UnstableApiUsage")
  public Promise<GraphTestRuns> getTestRuns(Long project)
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "{getTestRuns(projectId:" + project + "){id name}}";

    return (Promise<GraphTestRuns>)this.postAndParse(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator(),
        new GraphTestRunsParser());
  }

  @SuppressWarnings("UnstableApiUsage")
  public Promise<GraphCustomFields> getCustomFields(Long project)
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "{getTestCaseCustomFields(projectId:" + project + "){id name type}}";

    return (Promise<GraphCustomFields>)this.postAndParse(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator(),
        new GraphCustomFieldsParser());
  }

  @SuppressWarnings("UnstableApiUsage")
  public Promise<GraphTestCases> getTestCases(Long project, Long testRun, Long customField)
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "{getTestCasesByTestRun(projectId:" + project + ", testRunId:" + testRun + "){id name customFields{id type value}}}";


    return (Promise<GraphTestCases>) this.postAndParse(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator(),
        new GraphTestCasesParser(customField));
  }

  @SuppressWarnings("UnstableApiUsage")
  public Promise<GraphTestCaseStatuses> getTestCaseStatuses()
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "{getTestCaseStatuses{id name}}";


    return (Promise<GraphTestCaseStatuses>) this.postAndParse(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator(),
        new GraphTestCaseStatusesParser());
  }

  public void setTestCaseStatus(Long testRun, Long testCase, Long status)
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "mutation { updateTestCaseByTestRun(testRunId: " + testRun + ", testCaseId: " + testCase + ", testCaseStatusId: " + status + ") { id } }";

    this.post(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator()).claim();
  }
}
