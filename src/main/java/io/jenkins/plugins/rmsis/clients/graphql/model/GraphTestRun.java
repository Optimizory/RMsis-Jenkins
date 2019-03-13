package io.jenkins.plugins.rmsis.clients.graphql.model;

/**
 * ${Copyright}
 */
public class GraphTestRun
{
  private Long id;
  private String name;

  public GraphTestRun(Long id, String name)
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
