name := "PoliceSpark"

version := "1.0"

scalaVersion := "2.11.2"

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies += "io.spray" % "spray-client_2.11" % "1.3.2"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.6"