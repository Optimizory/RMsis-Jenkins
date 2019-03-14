package io.jenkins.plugins.rmsis.clients.graphql.helper;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphTestCase;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * ${Copyright}
 */
public class GraphTestCaseParser implements JsonObjectParser<GraphTestCase>
{
  private Long customField;

  GraphTestCaseParser(Long customField)
  {
    this.customField = customField;
  }

  @Override
  public GraphTestCase parse(JSONObject json) throws JSONException
  {
    Long id = json.getLong("id");
    String name = json.getString("name");
    String customValue = null;

    JSONArray customFields = json.getJSONArray("customFields");
    for (int i = 0; i < customFields.length(); i++) {
      JSONObject customFieldObject = customFields.getJSONObject(i);
      Long cid = customFieldObject.getLong("id");
      if (customField.equals(cid)) {
        customValue = customFieldObject.getString("value");
      }
    }

    return new GraphTestCase(id, name, customValue);
  }
}
