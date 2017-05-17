package com.cisco.ci.nagios

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification

class BaseTest extends Specification implements GroovyInterceptable {

  @Shared
  def log = LoggerFactory.getLogger(this.class)

  /**
   * Intercept method invocations to log any @Check methods calls and
   * their results for debugging output
   */
  @Override
  def invokeMethod(String name, args) {
    def result = null,
        method = metaClass.getMetaMethod(name, args)
    if(method == null) {
      // this method is (hopefully) controlled via the metaclass dispatch
      result = metaClass.invokeMethod(this, name, args);
    } else {
      // treat this as a normal method call
      result = method.invoke(this, args)
    }
    return result
  }

  def <E> E readJson(String resource, Class<E> cls) {
    return new ObjectMapper().readValue(BaseTest.getResource(resource).openStream(), cls)
  }
}
