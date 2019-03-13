package io.jenkins.plugins.rmsis.clients.graphql.model;

/**
 * ${Copyright}
 */
public class GraphProject
{
  private Long id;
  private String externalId;

  public GraphProject(Long id, String externalId)
  {
    this.id = id;
    this.externalId = externalId;
  }

  public Long getId()
  {
    return id;
  }

  public String getExternalId()
  {
    return externalId;
  }
}
