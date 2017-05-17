package com.cisco.ci.nagios;

import com.cisco.ci.nagios.apis.MonitorApi;
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.TupleConstructor
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

class NagiosClientFactory {

  NagiosClient client(String url, String secret) {
    new NagiosClient(url, secret)
  }
}

@TupleConstructor
class NagiosClient {
  private static final String apiSuffix = '/api'

  protected final RESTClient restClient
  protected final ObjectMapper mapper

  public NagiosClient(String url, String secret) {
    url = url.endsWith('/') ? url.substring(0, url.length() - 1) : url
    url = url.endsWith(apiSuffix) ? url.substring(0, url.length() - apiSuffix.length()) : url
    restClient = new RESTClient(url + apiSuffix + '/')
    restClient.headers.'Content-Type' = ContentType.JSON
    restClient.headers.'X-Auth-Token' = secret
    mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
  }

  def MonitorApi monitorApi() {
    new MonitorApi(restClient, mapper)
  }
}
