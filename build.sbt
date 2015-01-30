name := "PoliceSpark"

version := "1.0"

scalaVersion := "2.10.4"

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies += "io.spray" % "spray-client_2.10" % "1.3.2"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.8"

libraryDependencies += "com.typesafe.akka" %% "akka-http-experimental" % "0.11"

libraryDependencies += "org.specs2" % "specs2-core_2.10" % "2.4.15"

libraryDependencies += "org.scalamock" % "scalamock-specs2-support_2.10" % "3.2.1"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.2.3"


