package io.jenkins.plugins.rmsis.clients.graphql.helper;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphTestRun;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphTestRuns;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Collection;

/**
 * ${Copyright}
 */
public class GraphTestRunsParser implements JsonObjectParser<GraphTestRuns>
{
  @Override
  public GraphTestRuns parse(JSONObject json) throws JSONException
  {
    JSONObject data = json.getJSONObject("data");

    Collection<GraphTestRun> testRuns = JsonParseUtil
        .parseJsonArray(data.getJSONArray("getTestRuns"), new GraphTestRunParser());

    return new GraphTestRuns(testRuns);
  }
}