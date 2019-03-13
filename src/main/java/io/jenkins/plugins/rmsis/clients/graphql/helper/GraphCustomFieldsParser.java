package io.jenkins.plugins.rmsis.clients.graphql.helper;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphCustomField;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphCustomFields;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Collection;

/**
 * ${Copyright}
 */
public class GraphCustomFieldsParser implements JsonObjectParser<GraphCustomFields>
{
  @Override
  public GraphCustomFields parse(JSONObject json) throws JSONException
  {
    JSONObject data = json.getJSONObject("data");

    Collection<GraphCustomField> customFields = JsonParseUtil
        .parseJsonArray(data.getJSONArray("getTestCaseCustomFields"), new GraphCustomFieldParser());


    return new GraphCustomFields(customFields);
  }
}
