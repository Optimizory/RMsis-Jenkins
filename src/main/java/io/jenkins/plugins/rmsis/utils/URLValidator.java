package io.jenkins.plugins.rmsis.utils;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * ${Copyright}
 */
public class URLValidator
{
  private static final String INVALID_URL = "This is not a valid URL";
  private static final String SSL_ERROR = "SSL Exception";
  private static final String CONNECTION_ERROR = "Could not establish the connection";

  public static String validate(String string)
  {
    String result;
    URL url;
    URLConnection conn;
    try {
      url = new URL(string);
      conn = url.openConnection();
      conn.connect();

      result = url.getProtocol();
      result += "://";
      result += url.getHost();

      int port = url.getPort();
      if (port > 0) {
        result += ":";
        result += port;

      }
    } catch (MalformedURLException e) {
      result = INVALID_URL;
    } catch (SSLException e) {
      result = SSL_ERROR;
    } catch (IOException e) {
      result = CONNECTION_ERROR;
    }

    return result;
  }

  public static String fetch(String server)
  {
    return validate(server);
  }
}
