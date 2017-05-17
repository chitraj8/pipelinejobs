package com.cisco.ci.nagios.apis

import com.cisco.ci.nagios.domain.Monitor
import com.cisco.ci.nagios.domain.Status
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import groovyx.net.http.RESTClient

class MonitorApi extends Api {

  MonitorApi(RESTClient restClient, ObjectMapper mapper) {
    super(restClient, mapper)
  }

  Map<String, Monitor> availability(servicegroup, start, end) {
    unmarshall(restClient
        .get(path: "v1/availability", query: [from: start.toEpochSecond(), to: end.toEpochSecond(), group: servicegroup, outages: 1])
        .responseData,
      new TypeReference<Map<String, Monitor>>() {})
  }

  List<Monitor> serviceAvailability(service, start, end) {
    unmarshall(restClient
        .get(path: "v1/availability", query: [from: start.toEpochSecond(), to: end.toEpochSecond(), service: service, outages: 1])
        .responseData,
      new TypeReference<List<Monitor>>() {})
  }

  List<Status> health(servicegroup) {
    unmarshall(restClient
        .get(path: "v1/query",
             query: ["get": "servicegroups", "filter[]": ["alias ~ " + servicegroup],
                     "columns[]": ["num_services", "num_services_ok", "num_services_warn", "num_services_crit", "num_services_pending", "num_services_unknown"]
                    ])
        .responseData,
      new TypeReference<List<Status>>() {})
  }
}
