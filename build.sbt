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
  scalaSource := baseDirectory.value / "src",
	Compile/unmanagedSourceDirectories := List(scalaSource.value),	
)

lazy val Util = project.dependsOn(UtilMacros).settings(commonSettings).settings(
	scalaSource := baseDirectory.value / "src",
	Compile/unmanagedSourceDirectories := List(scalaSource.value),
)

lazy val Graphic = project.dependsOn(Util).settings(commonSettings).settings(
	scalaSource := baseDirectory.value / "src",
	Compile/unmanagedSourceDirectories := List("src", "jvm/src").map(baseDirectory.value / _),
)

lazy val Strat = project.dependsOn(Graphic).settings(commonSettings).settings(
  scalaSource := baseDirectory.value / "src",
	Compile/unmanagedSourceDirectories := List(scalaSource.value),	
)

lazy val root = (project in file(".")).dependsOn(Strat).settings(commonSettings).settings(
	scalaSource := baseDirectory.value / "DevModule/src",
	Compile/unmanagedSourceDirectories := List("src", "srcLearn", "jvm/src").map(s => baseDirectory.value / ("DevModule/" + s)),
	Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)
