package org.jms.consumer

import java.util.Properties

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}

@RunWith(classOf[JUnitRunner])
class TextConnectionPropertiesSuite extends FunSuite with Matchers {

  trait TestFixture {
    val brokerURL = "broker-url"
    val topicName = "topic-name"
  }

  test("Create connection properties") {
    new TestFixture {
      val connectionProperties: ConnectionProperties = new TextConnectionProperties(brokerURL, topicName)
      val props: Properties = connectionProperties.properties()

      props.size should be (2)
      props.get(TextConnectionProperties.BROKER_URL) should be (brokerURL)
      props.get(TextConnectionProperties.TOPIC_NAME) should be (topicName)
    }
  }
}
