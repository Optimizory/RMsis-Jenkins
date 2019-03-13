package io.jenkins.plugins.rmsis.clients.graphql.helper;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphTestCaseStatus;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphTestCaseStatuses;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Collection;

/**
 * ${Copyright}
 */
public class GraphTestCaseStatusesParser implements JsonObjectParser<GraphTestCaseStatuses>
{
  @Override
  public GraphTestCaseStatuses parse(JSONObject json) throws JSONException
  {
    JSONObject data = json.getJSONObject("data");

    Collection<GraphTestCaseStatus> testCaseStatuses = JsonParseUtil
        .parseJsonArray(data.getJSONArray("getTestCaseStatuses"), new GraphTestCaseStatusParser());

    return new GraphTestCaseStatuses(testCaseStatuses);
  }
}
