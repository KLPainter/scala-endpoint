name := "ScalaEndpoint"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq (
  "com.nike.riposte" % "riposte-core" % "0.15.0",
  "com.nike.riposte" % "riposte-async-http-client" % "0.15.0",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)



