package io.jenkins.plugins.rmsis.clients.graphql.helper;

/**
 * ${Copyright}
 */
public class GraphInput
{
  private String query;

  public GraphInput(String query)
  {
    this.query = query;
  }

  public String getQuery()
  {
    return query;
  }

  public void setQuery(String query)
  {
    this.query = query;
  }
}
