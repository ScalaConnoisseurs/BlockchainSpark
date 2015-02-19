import NativePackagerKeys._

packageArchetype.java_application

name := "PoliceSpark"

version := "1.0"

scalaVersion := "2.11.2"

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies += "io.spray" % "spray-client_2.11" % "1.3.2"

libraryDependencies += "io.spray" % "spray-routing-shapeless2_2.11" % "1.3.2"

libraryDependencies += "io.spray" % "spray-testkit_2.11" % "1.3.2"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.1"

libraryDependencies += "com.wandoulabs.akka" %% "spray-websocket" % "0.1.4"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.scalamock" % "scalamock-specs2-support_2.11" % "3.2.1"

libraryDependencies += "org.threeten" % "threetenbp" % "1.2"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.2.1"
