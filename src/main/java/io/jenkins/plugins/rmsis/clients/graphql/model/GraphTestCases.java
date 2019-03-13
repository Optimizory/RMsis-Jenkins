package io.jenkins.plugins.rmsis.clients.graphql.model;

import java.util.Collection;

/**
 * ${Copyright}
 */
public class GraphTestCases
{
  private Collection<GraphTestCase> testCases;

  public GraphTestCases(Collection<GraphTestCase> testCases)
  {
    this.testCases = testCases;
  }

  public Collection<GraphTestCase> getTestCases()
  {
    return testCases;
  }
}
