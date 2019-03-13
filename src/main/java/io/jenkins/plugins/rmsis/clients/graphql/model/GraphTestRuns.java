package io.jenkins.plugins.rmsis.clients.graphql.model;

import java.util.Collection;

/**
 * ${Copyright}
 */
public class GraphTestRuns
{
  private Collection<GraphTestRun> testRuns;

  public GraphTestRuns(Collection<GraphTestRun> testRuns)
  {
    this.testRuns = testRuns;
  }

  public Collection<GraphTestRun> getTestRuns()
  {
    return testRuns;
  }
}
