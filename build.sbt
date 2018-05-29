scalaVersion in ThisBuild := "2.12.6"
organization in ThisBuild := "OpenStrat"

val ante = Project("AnteCompono", file(name)).settings(
   scalaSource in Compile := baseDirectory.value / "src",
   scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint", "-Yno-adapted-args")
)

val common = Project("Common", file(name)).settings(
   scalaSource in Compile := baseDirectory.value / "src",
   scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint", "-Yno-adapted-args")
).dependsOn(ante)
