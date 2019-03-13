package io.jenkins.plugins.rmsis.model;

/**
 * ${Copyright}
 */
public class TestCase
{
  private Long id;
  private String name;
  private String customValue;

  public TestCase(Long id, String name, String customValue)
  {
    this.id = id;
    this.name = name;
    this.customValue = customValue;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getCustomValue()
  {
    return customValue;
  }

  public void setCustomValue(String customValue)
  {
    this.customValue = customValue;
  }
}
