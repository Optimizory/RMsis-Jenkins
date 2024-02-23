package io.jenkins.plugins.rmsis.model;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * ${Copyright}
 */
public class Instance {
  private String server;
  private String username;
  private String password;

  @DataBoundConstructor
  public Instance(String server, String username, String password) {
    this.server = server;
    this.username = username;
    this.password = password;
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
