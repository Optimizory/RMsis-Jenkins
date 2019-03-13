package io.jenkins.plugins.rmsis.clients.graphql.model;

import java.util.Collection;

/**
 * ${Copyright}
 */
public class GraphTestCaseStatuses
{
  private Collection<GraphTestCaseStatus> testCaseStatuses;

  public GraphTestCaseStatuses(Collection<GraphTestCaseStatus> testCaseStatuses)
  {
    this.testCaseStatuses = testCaseStatuses;
  }

  public Collection<GraphTestCaseStatus> getTestCaseStatuses()
  {
    return testCaseStatuses;
  }
}
