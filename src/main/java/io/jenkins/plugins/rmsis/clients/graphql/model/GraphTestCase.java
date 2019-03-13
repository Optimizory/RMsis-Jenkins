package io.jenkins.plugins.rmsis.clients.graphql.model;

/**
 * ${Copyright}
 */
public class GraphTestCase
{
  private Long id;
  private String name;
  private String customValue;

  public GraphTestCase()
  {
  }

  public GraphTestCase(Long id, String name, String customValue)
  {
    this.id = id;
    this.name = name;
    this.customValue = customValue;
  }

  public Long getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getCustomValue()
  {
    return customValue;
  }
}
