package io.jenkins.plugins.rmsis.clients;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;

import java.net.URI;

/**
 * ${Copyright}
 */
public class CustomJiraRestClientFactory extends AsynchronousJiraRestClientFactory
{
  @Override
  public JiraRestClient create(URI serverUri, AuthenticationHandler authenticationHandler)
  {
    DisposableHttpClient httpClient = (new AsynchronousHttpClientFactory())
        .createClient(serverUri, authenticationHandler);
    return new CustomJiraRestClient(serverUri, httpClient);
  }

  @Override
  public JiraRestClient createWithBasicHttpAuthentication(URI serverUri, String username, String password)
  {
    return this.create(serverUri, new BasicHttpAuthenticationHandler(username, password));
  }
}