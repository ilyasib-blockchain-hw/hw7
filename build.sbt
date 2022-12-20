ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

libraryDependencies += "com.monovore" %% "decline-effect" % "2.4.1"
libraryDependencies += "com.google.guava" % "guava" % "31.1-jre"

lazy val root = (project in file("."))
  .settings(
    name := "hw7"
  )
