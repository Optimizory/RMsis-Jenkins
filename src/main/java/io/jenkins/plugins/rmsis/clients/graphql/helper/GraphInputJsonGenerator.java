package io.jenkins.plugins.rmsis.clients.graphql.helper;

import com.atlassian.jira.rest.client.internal.json.gen.JsonGenerator;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * ${Copyright}
 */
public class GraphInputJsonGenerator implements JsonGenerator<GraphInput>
{
  @Override
  public JSONObject generate(GraphInput graphInput) throws JSONException
  {
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("query", graphInput.getQuery());

    return jsonObject;
  }
}
