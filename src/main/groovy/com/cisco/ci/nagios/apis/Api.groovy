package com.cisco.ci.nagios.apis

import com.fasterxml.jackson.databind.ObjectMapper
import groovyx.net.http.RESTClient

abstract class Api {
  protected final RESTClient restClient
  protected final ObjectMapper mapper

  Api(RESTClient restClient, ObjectMapper mapper) {
    this.restClient = restClient
    this.mapper = mapper
  }

  protected def toJson(o) {
    mapper.writeValueAsString(o)
  }

  protected def unmarshall(input, cls) {
    return mapper.convertValue(input, cls)
  }
}
