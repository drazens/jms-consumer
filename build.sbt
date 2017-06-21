name := "jms-consumer"
organization := "org.jms.consumer"
version := "1.0.0"

scalaVersion := "2.12.2"
	
scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xfatal-warnings",
  "-Xlint:missing-interpolator",
  "-Ywarn-unused-import",
  //"-Ywarn-unused",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen")

libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter" % "1.5.4.RELEASE",
  "org.apache.activemq" % "activemq-broker" % "5.14.5",
  "org.scalaz" %% "scalaz-effect" % "7.2.13",
  "org.springframework.boot" % "spring-boot-starter-test" % "1.5.4.RELEASE" % "test",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.5" % "test",
  "junit" % "junit" % "4.12" % "test",
  "org.apache.activemq.tooling" % "activemq-junit" % "5.14.5" % "test")