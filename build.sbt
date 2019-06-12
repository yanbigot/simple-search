name := "search-code-challenge"

version := "0.1.1"

scalaVersion := "2.12.6"

// https://mvnrepository.com/artifact/org.scalatest/scalatest
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-SNAP10" % Test

//generate html reports of tests
libraryDependencies += "org.pegdown" % "pegdown" % "1.6.0" % Test
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-html-reports")