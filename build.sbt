ThisBuild/version := "0.0.7snap"
name := "OpenStrat"
ThisBuild/scalaVersion := "2.13.1"
ThisBuild/organization := "OpenStratOrg"
ThisBuild/autoAPIMappings := true

def commonSettings = List(
	scalacOptions ++= Seq("-feature", "-language:higherKinds,implicitConversions", "-deprecation", "-Ywarn-value-discard", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xlint"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
    libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.9" % "test",
    testFrameworks += new TestFramework("utest.runner.Framework"), 
)

def stdSettings = commonSettings ::: List(
  scalaSource := (baseDirectory).value / "src",
  Compile/scalaSource := baseDirectory.value / "src",
  Test/scalaSource := baseDirectory.value / "test/src",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value, baseDirectory.value / "examples/src"),
)

lazy val UtilMacros = (project in file("Util/Macros")).settings(stdSettings).settings(  
  Compile/unmanagedSourceDirectories := List(scalaSource.value),	
)

lazy val Util = project.dependsOn(UtilMacros).settings(stdSettings).settings(
  Compile/unmanagedSourceDirectories := List(scalaSource.value),	
)

lazy val Graphic = project.dependsOn(Util).settings(stdSettings).settings(
  Compile/unmanagedSourceDirectories := List("src", "jvm/src").map(baseDirectory.value / _),
)

lazy val World = project.dependsOn(Graphic).settings(stdSettings).settings(
  Compile/unmanagedSourceDirectories := List("src", "jvm/src").map(baseDirectory.value / _),
)

lazy val Strat = project.dependsOn(World).settings(stdSettings).settings(
  Compile/unmanagedSourceDirectories := List("src", "jvm/src").map(baseDirectory.value / _),
  assemblyJarName in assembly := "strat" + (ThisBuild/version).value + ".jar"
)

lazy val root = (project in file(".")).dependsOn(Strat).enablePlugins(ScalaUnidocPlugin).settings(commonSettings).settings(
  Compile/scalaSource := baseDirectory.value / "Dev/src",
  Test/scalaSource := baseDirectory.value / "Dev/test/src",
  Compile/unmanagedSourceDirectories := List("Dev/src", "Dev/jvm/src", "Graphic/examples/src").map(s => baseDirectory.value / s),
  Compile/unmanagedResourceDirectories := List(baseDirectory.value / "Dev/mine"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

lazy val DocProj = (project in file("target/DocProj")).dependsOn(UtilMacros).settings(commonSettings).settings(
  Compile/unmanagedSourceDirectories := List("Util/src", "Graphic/src", "Graphic/jvm/src", "Graphic/examples/src", "World/src", "Strat/src", "Dev/src").map(s => (ThisBuild/baseDirectory).value / s),
  version := "0.0.7snap",
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
)

def jsProj(name: String) = Project("Js" + name, file("Dev/SbtDir/Js" + name)).enablePlugins(ScalaJSPlugin).settings(commonSettings).settings(
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value, 
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.7",
  scalaSource := (ThisBuild/baseDirectory).value / name / "src",
)

lazy val JsUtilMacros = jsProj("UtilMacros").settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
)

lazy val JsUtil = jsProj("Util").dependsOn(JsUtilMacros).settings(  
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
)

lazy val JsGraphic = jsProj("Graphic").dependsOn(JsUtil).settings(  
  Compile/unmanagedSourceDirectories := List("Graphic/src", "Graphic/js/src").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val JsWorld = jsProj("World").dependsOn(JsGraphic).settings(  
  Compile/unmanagedSourceDirectories := List("World/src", "World/js/src").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val JsStrat = jsProj("Strat").dependsOn(JsWorld).settings(  
  Compile/unmanagedSourceDirectories := List("Strat/src", "Strat/js/src").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val JsDev = jsProj("Dev").dependsOn(JsWorld).settings(  
  Compile/unmanagedSourceDirectories := List("Dev/src", "Dev/js/src").map(s => (ThisBuild/baseDirectory).value / s)
)
