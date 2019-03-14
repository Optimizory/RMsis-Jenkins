package io.jenkins.plugins.rmsis.clients;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClient;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import io.jenkins.plugins.rmsis.clients.graphql.GraphRestClient;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * ${Copyright}
 */
public class CustomJiraRestClient extends AsynchronousJiraRestClient implements JiraRestClient
{
  private final GraphRestClient graphRestClient;

  CustomJiraRestClient(URI serverUri, DisposableHttpClient httpClient)
  {
    super(serverUri, httpClient);

    URI graphURI = UriBuilder.fromUri(serverUri).path("/rest/service/latest/rmsis/graphql").build();

    graphRestClient = new GraphRestClient(graphURI, httpClient);
  }

  public GraphRestClient getGraphRestClient()
  {
    return graphRestClient;
  }
}
