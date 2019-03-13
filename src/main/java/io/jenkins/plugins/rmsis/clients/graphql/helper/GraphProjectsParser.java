package io.jenkins.plugins.rmsis.clients.graphql.helper;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.atlassian.jira.rest.client.internal.json.JsonParseUtil;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphProject;
import io.jenkins.plugins.rmsis.clients.graphql.model.GraphProjects;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Collection;

/**
 * ${Copyright}
 */
public class GraphProjectsParser implements JsonObjectParser<GraphProjects>
{
  @Override
  public GraphProjects parse(JSONObject json) throws JSONException
  {
    JSONObject data = json.getJSONObject("data");

    Collection<GraphProject> projects = JsonParseUtil
        .parseJsonArray(data.getJSONArray("getProjects"), new GraphProjectParser());

    return new GraphProjects(projects);
  }
}
