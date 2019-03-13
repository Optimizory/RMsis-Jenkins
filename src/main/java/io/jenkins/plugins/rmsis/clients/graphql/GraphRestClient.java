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

  public Promise<GraphProjects> getProjects()
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "{getProjects{id externalId}}";

    return this.postAndParse(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator(),
        new GraphProjectsParser());
  }

  public Promise<GraphTestRuns> getTestRuns(Long project)
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "{getTestRuns(projectId:" + project + "){id name}}";

    return this.postAndParse(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator(),
        new GraphTestRunsParser());
  }

  public Promise<GraphCustomFields> getCustomFields(Long project)
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "{getTestCaseCustomFields(projectId:" + project + "){id name type}}";

    return this.postAndParse(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator(),
        new GraphCustomFieldsParser());
  }

  public Promise<GraphTestCases> getTestCases(Long project, Long testRun, Long customField)
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "{getTestCasesByTestRun(projectId:" + project + ", testRunId:" + testRun + "){id name customFields{id type value}}}";


    return this.postAndParse(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator(),
        new GraphTestCasesParser(customField));
  }

  public Promise<GraphTestCaseStatuses> getTestCaseStatuses()
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "{getTestCaseStatuses{id name}}";


    return this.postAndParse(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator(),
        new GraphTestCaseStatusesParser());
  }

  public void setTestCaseStatus(Long testRun, Long testCase, Long status)
  {
    UriBuilder uriBuilder = UriBuilder.fromUri(this.graphURI);
    String query = "mutation { updateTestCaseByTestRun(testRunId: " + testRun + ", testCaseId: " + testCase + ", testCaseStatusId: " + status + ") { id } }";

    this.post(uriBuilder.build(), new GraphInput(query), new GraphInputJsonGenerator()).claim();
  }
}
