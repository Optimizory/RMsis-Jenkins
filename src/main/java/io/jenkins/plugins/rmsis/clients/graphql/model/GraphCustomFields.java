package io.jenkins.plugins.rmsis.clients.graphql.model;

import java.util.Collection;

/**
 * ${Copyright}
 */
public class GraphCustomFields
{
  private Collection<GraphCustomField> customFields;

  public GraphCustomFields(Collection<GraphCustomField> customFields)
  {
    this.customFields = customFields;
  }

  public Collection<GraphCustomField> getCustomFields()
  {
    return customFields;
  }
}
