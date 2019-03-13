package io.jenkins.plugins.rmsis.clients.graphql.helper;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphProject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * ${Copyright}
 */
public class GraphProjectParser implements JsonObjectParser<GraphProject>
{
  @Override
  public GraphProject parse(JSONObject json) throws JSONException
  {
    Long id = json.getLong("id");
    String externalId = json.getString("externalId");

    return new GraphProject(id, externalId);
  }
}
