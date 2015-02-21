import NativePackagerKeys._

packageArchetype.java_application

name := "PoliceSpark"

version := "1.0"

scalaVersion := "2.11.2"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += Resolver.url("Kamon Releases", url("http://repo.kamon.io"))(Resolver.ivyStylePatterns)

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

val kamonVersion = "0.3.5"

libraryDependencies ++= Seq(
  "kamon" %%  "kamon-spray" % "0.0.11",
  "io.kamon" %% "kamon-core" % kamonVersion,
  "io.kamon" %% "kamon-statsd" % kamonVersion,
  "io.kamon" %% "kamon-log-reporter" % kamonVersion,
  "io.kamon" %% "kamon-system-metrics" % kamonVersion,
  "org.aspectj" % "aspectjweaver" % "1.8.1"
)


