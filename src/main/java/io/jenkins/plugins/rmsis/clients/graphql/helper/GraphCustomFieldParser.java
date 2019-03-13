package io.jenkins.plugins.rmsis.clients.graphql.helper;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphCustomField;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * ${Copyright}
 */
public class GraphCustomFieldParser implements JsonObjectParser<GraphCustomField>
{
  @Override
  public GraphCustomField parse(JSONObject json) throws JSONException
  {
    Long id = json.getLong("id");
    String name = json.getString("name");
    String type = json.getString("type");

    return new GraphCustomField(id, name, type);
  }
}