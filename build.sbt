name := "PoliceSpark"

version := "1.0"

scalaVersion := "2.10.4"

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies += "io.spray" % "spray-client_2.10" % "1.3.2"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.8"