lazy val commonSettings = Seq(
  organization := "net.kgtkr",
  name := "twitter-tools",
  version := "0.1",
  scalaVersion := "2.13.1",
  scalacOptions ++= Seq(
    "-language:higherKinds"
  ),
  wartremoverErrors ++= Warts.all
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.0.0"
    ),
    mainClass in assembly := Some("net.kgtkr.twitter_tools.App"),
    assemblyJarName in assembly := "app.jar"
  )
