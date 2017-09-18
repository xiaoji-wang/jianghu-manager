name := """jianghu-manager"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJpa,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.1.36",
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.8.5",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-hibernate5" % "2.8.5"
)

fork in run := false

PlayKeys.externalizeResources := false