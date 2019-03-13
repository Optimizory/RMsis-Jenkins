package io.jenkins.plugins.rmsis.clients.graphql.helper;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphTestCaseStatus;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * ${Copyright}
 */
public class GraphTestCaseStatusParser implements JsonObjectParser<GraphTestCaseStatus>
{
  @Override
  public GraphTestCaseStatus parse(JSONObject json) throws JSONException
  {
    Long id = json.getLong("id");
    String name = json.getString("name");

    return new GraphTestCaseStatus(id, name);
  }
}
