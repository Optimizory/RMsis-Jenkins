package io.jenkins.plugins.rmsis.utils;

import io.jenkins.plugins.rmsis.model.InstanceConfig;
import io.jenkins.plugins.rmsis.model.StatusEnum;
import io.jenkins.plugins.rmsis.model.TestCase;
import io.jenkins.plugins.rmsis.model.TestCaseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${Copyright}
 */
public class ResultUploader
{
  public static void upload(InstanceConfig config)
  {
    RestClient client = null;
    try {
      client = new RestClient(config.getInstance());
      Map<StatusEnum, Long> statusMap = getStatusMap(client);
      List<TestCase> testCases = client.getTestCases(config.getProject(), config.getTestRun(), config.getCustomField());
      Map<String, Boolean> resultMap = config.getTestCaseResultMap();
      for (TestCase testCase : testCases) {
        String customField = testCase.getCustomValue();
        Boolean result = resultMap.get(customField);
        Long status;
        if (Boolean.TRUE.equals(result)) {
          status = statusMap.get(StatusEnum.PASS);
        } else if (Boolean.FALSE.equals(result)) {
          status = statusMap.get(StatusEnum.FAIL);
        } else {
          status = statusMap.get(StatusEnum.UNKNOWN);
        }

        client.setTestCaseStatus(config.getTestRun(), testCase.getId(), status);
      }
    } finally {
      if (null != client) client.destroy();
    }
  }

  private static Map<StatusEnum, Long> getStatusMap(RestClient client)
  {
    Map<StatusEnum, Long> map = new HashMap<>();
    List<TestCaseStatus> testCaseStatusList = client.getTestCaseStatuses();

    for (TestCaseStatus testCaseStatus : testCaseStatusList) {
      Long id = testCaseStatus.getId();
      String name = testCaseStatus.getName();
      if (Constants.TEST_CASE_STATUS_PASS.equals(name)) {
        map.put(StatusEnum.PASS, id);
      } else if (Constants.TEST_CASE_STATUS_FAIL.equals(name)) {
        map.put(StatusEnum.FAIL, id);
      } else if (Constants.TEST_CASE_STATUS_UNKNOWN.equals(name)) {
        map.put(StatusEnum.UNKNOWN, id);
      }
    }

    return map;
  }
}
