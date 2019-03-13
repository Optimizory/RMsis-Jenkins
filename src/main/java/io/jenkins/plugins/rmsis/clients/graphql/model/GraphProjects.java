package io.jenkins.plugins.rmsis.clients.graphql.model;

import java.util.Collection;

/**
 * ${Copyright}
 */
public class GraphProjects
{
  private Collection<GraphProject> projects;

  public GraphProjects(Collection<GraphProject> projects)
  {
    this.projects = projects;
  }


  public Collection<GraphProject> getProjects()
  {
    return projects;
  }
}
