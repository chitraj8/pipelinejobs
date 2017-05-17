package com.cisco.ci.nagios.domain

import groovy.transform.TupleConstructor

@TupleConstructor
class Monitor {
  String host
  String service
  BigDecimal up
  long down_duration
  BigDecimal down
  int num_outages
  List<Outage> outages
  Map<String, Monitor> children
  long duration
  long from
  long to
}

@TupleConstructor
class Outage {
  long id
  long start
  long end
  long duration
}

@TupleConstructor
class Status {
  int num_services
  int num_services_ok
  int num_services_warn
  int num_services_crit
  int num_services_pending
  int num_services_unknown
}