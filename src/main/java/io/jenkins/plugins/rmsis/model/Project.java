package io.jenkins.plugins.rmsis.model;

/**
 * ${Copyright}
 */
public class Project
{
  private Long id;
  private String key;
  private String name;

  public Project(Long id, String key, String name)
  {
    this.id = id;
    this.key = key;
    this.name = name;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getKey()
  {
    return key;
  }

  public void setKey(String key)
  {
    this.key = key;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }
}
