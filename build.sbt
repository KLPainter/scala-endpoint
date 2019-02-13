name := "ScalaEndpoint"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq (
  "com.nike.riposte" % "riposte-core" % "0.15.0",
  "com.nike.riposte" % "riposte-async-http-client" % "0.15.0",
  "org.slf4j" % "slf4j-jdk14" % "1.8.0-beta2",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.scalamock" %% "scalamock" % "4.1.0" % "test",
  "org.mockito" % "mockito-core" % "2.24.0" % "test",
  "org.mockito" %% "mockito-scala" % "1.1.1" % "test"
)



