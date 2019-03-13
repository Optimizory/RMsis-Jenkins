package io.jenkins.plugins.rmsis.clients.graphql.model;

/**
 * ${Copyright}
 */
public class GraphTestCaseStatus
{
  private Long id;
  private String name;

  public GraphTestCaseStatus(Long id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public Long getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }
}
