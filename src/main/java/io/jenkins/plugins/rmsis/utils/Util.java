package io.jenkins.plugins.rmsis.utils;

/**
 * ${Copyright}
 */
public class Util
{
  public static Long getLong(String string)
  {
    long parseLong;
    try {
      parseLong = Long.parseLong(string);
    } catch (NumberFormatException e) {
      return null;
    }

    return parseLong;
  }
}
