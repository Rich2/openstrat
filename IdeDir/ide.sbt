ThisBuild/version := "0.0.4snap"
name := "OpenStrat"
ThisBuild/scalaVersion := "2.12.8"
ThisBuild/organization := "OpenStratOrg"
ThisBuild/autoAPIMappings := true

val commonSettings = List(	
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xlint"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value, 
)

lazy val UtilMacros = project.settings(commonSettings).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "../UtilMacros/src",
)

lazy val Util = project.dependsOn(UtilMacros).settings(commonSettings).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "../Util/src",
)

lazy val Graphic = project.dependsOn(Util).settings(commonSettings).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "../Graphic/src",
)

lazy val Strat = project.dependsOn(Graphic).settings(commonSettings).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "../Strat/src",
)
