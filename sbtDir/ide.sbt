ThisBuild/version := "0.0.4snap"
name := "OpenStrat"
ThisBuild/scalaVersion := "2.12.8"
ThisBuild/organization := "OpenStratOrg"
ThisBuild/autoAPIMappings := true

val commonSettings = List(	
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xlint"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
 //   watchSources += (ThisBuild/baseDirectory).value / "DevSettings"
)

def proj(name: String): Project = Project(name , file(name)).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / ".." / name / "src",
)

lazy val Util = proj("Util")
