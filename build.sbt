import NativePackagerKeys._

packageArchetype.java_application

name := "PoliceSpark"

version := "1.0"

scalaVersion := "2.11.2"

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies += "io.spray" % "spray-client_2.11" % "1.3.2"

libraryDependencies += "io.spray" % "spray-routing_2.11" % "1.3.2"

libraryDependencies += "io.spray" % "spray-testkit_2.11" % "1.3.2"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.6"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
