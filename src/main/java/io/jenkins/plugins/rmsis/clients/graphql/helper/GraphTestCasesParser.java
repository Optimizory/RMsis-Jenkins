package io.jenkins.plugins.rmsis.clients.graphql.helper;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphTestCase;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphTestCases;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Collection;

/**
 * ${Copyright}
 */
public class GraphTestCasesParser implements JsonObjectParser<GraphTestCases>
{
  private Long customField;

  public GraphTestCasesParser(Long customField)
  {

    this.customField = customField;
  }

  @Override
  public GraphTestCases parse(JSONObject json) throws JSONException
  {
    JSONObject data = json.getJSONObject("data");

    Collection<GraphTestCase> testCases = JsonParseUtil
        .parseJsonArray(data.getJSONArray("getTestCasesByTestRun"), new GraphTestCaseParser(customField));

    return new GraphTestCases(testCases);
  }
}
