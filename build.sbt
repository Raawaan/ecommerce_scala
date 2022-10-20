ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "ecommerce"
  )
val AkkaVersion = "2.6.20"
val AkkaHttpVersion = "10.2.10"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion,
  "org.scalatest" %% "scalatest" % "3.2.14" % "test",
  "com.typesafe.slick" %% "slick" % "3.4.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
  "org.slf4j" % "slf4j-nop" % "2.0.3",
  "mysql" % "mysql-connector-java" % "8.0.30",
  "com.typesafe.slick" %% "slick-codegen" % "3.4.1",
  "org.scalamock" %% "scalamock" % "5.2.0" % Test,
  "org.scalatest" %% "scalatest" % "3.2.14" % Test,
  "org.scalatest" %% "scalatest" % "3.1.1" % "test",
  "org.scalatestplus" %% "mockito-4-6" % "3.2.14.0" % "test",
  "com.h2database" % "h2" % "1.4.187" % "test",

)
