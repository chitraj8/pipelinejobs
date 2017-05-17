package com.cisco.ci.nagios

import groovy.json.JsonSlurper

import java.time.ZoneOffset
import java.time.ZonedDateTime

class NagiosClientTestIntegration extends BaseTest {

  static def uptimeApi, now = ZonedDateTime.now(ZoneOffset.UTC), weekAgo = ZonedDateTime.now(ZoneOffset.UTC).minusWeeks(1)

  def setupSpec() {
    if (System.getProperty('config') == null) {
      throw new IllegalStateException("Config file not specified, this can be done via the -Dconfig JVM option")
    }

    def cfg = new JsonSlurper().parse(new File(System.getProperty('config')))
    def client = new NagiosClient(cfg.server.url, cfg.server.username, cfg.server.password)

    uptimeApi = client.uptimeApi()
  }

  def "Get hosts uptime for given resource"() {
    when: "Getting hosts uptime for the resource given"
    def uptime = uptimeApi.hostsUptime("Jenkins Master VMs", weekAgo, now)

    then: "Valid list of host uptimes is expected"
    assert uptime != null && !uptime.isEmpty()
  }

  def "Get websites uptime for given resource"() {
    when: "Getting services uptime for the resource given"
    def uptime = uptimeApi.servicesUptime("Northstar", weekAgo, now)

    then: "Valid list of services uptimes is expected"
    assert uptime != null && !uptime.isEmpty()
  }
}
