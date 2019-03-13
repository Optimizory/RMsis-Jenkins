package io.jenkins.plugins.rmsis.clients.graphql.helper;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphTestRun;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * ${Copyright}
 */
public class GraphTestRunParser implements JsonObjectParser<GraphTestRun>
{
  @Override
  public GraphTestRun parse(JSONObject json) throws JSONException
  {
    Long id = json.getLong("id");
    String name = json.getString("name");

    return new GraphTestRun(id, name);
  }
}
