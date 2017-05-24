name := "nhs-scraper"

version := "1.0"

scalaVersion := "2.12.2"

val circeVersion = "0.8.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "com.typesafe" % "config" % "1.3.1",
  "org.clapper" %% "grizzled-slf4j" % "1.3.1",
  "org.slf4j" % "slf4j-simple" % "1.7.25",
  "org.jsoup" % "jsoup" % "1.10.2",

  "org.scalatest" %% "scalatest" % "3.0.1" % "test")
