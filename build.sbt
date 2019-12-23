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

def stdSettings(name: String) = commonSettings ::: List(
  scalaSource := (ThisBuild/baseDirectory).value / name / "/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / name / "/src",
  Test/scalaSource := (ThisBuild/baseDirectory).value / name / "test/src",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value, (ThisBuild/baseDirectory).value / name / "learn/src"),
  version := (ThisBuild/version).value
)

lazy val UtilMacros = Project("UtilMacros", file("target/JvmUtilMacros")).settings(commonSettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Util/Macros/test/src",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

def stdJvmProj(name: String) = Project(name, file("target/Jvm" + name)).settings(stdSettings(name))

lazy val Util = stdJvmProj("Util").dependsOn(UtilMacros).settings(
  Compile/unmanagedSourceDirectories := List(scalaSource.value),	
)

lazy val Graphic = stdJvmProj("Graphic").dependsOn(Util).settings(
  Compile/unmanagedSourceDirectories := List("src", "jvm/src").map(str => (ThisBuild/baseDirectory).value / "Graphic" / str),
)

lazy val World = stdJvmProj("World").dependsOn(Graphic).settings(
  Compile/unmanagedSourceDirectories := List("src", "jvm/src").map(str => (ThisBuild/baseDirectory).value / "World" / str),
)

lazy val Strat = stdJvmProj("Strat").dependsOn(World).settings(
  Compile/unmanagedSourceDirectories := List("src", "jvm/src").map(str => (ThisBuild/baseDirectory).value / "Strat" / str),
  assemblyJarName in assembly := "strat" + (ThisBuild/version).value + ".jar"
)

lazy val root = (project in file(".")).dependsOn(Strat).enablePlugins(ScalaUnidocPlugin).settings(commonSettings).settings(
  scalaSource := baseDirectory.value / "Dev/src",
  Compile/scalaSource := baseDirectory.value / "Dev/src",
  Test/scalaSource := baseDirectory.value / "Dev/test/src",
  Compile/unmanagedSourceDirectories := List("Dev/src", "Dev/jvm/src", "Graphic/learn/src").map(s => baseDirectory.value / s),
  Compile/unmanagedResourceDirectories := List(baseDirectory.value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

val docDirs: List[String] = List("Util", "Graphic", "World", "Strat", "Dev")

lazy val DocMain = (project in file("target/DocMain")).dependsOn(UtilMacros).settings(commonSettings).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/jvm/src", el + "/learn/src")).map(s => (ThisBuild/baseDirectory).value / s),
  version := "0.0.7snap",
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
)

lazy val DocJs = (project in file("target/DocJs")).dependsOn(JsUtilMacros).settings(commonSettings).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/js/src", el + "/learn/src")).map(s => (ThisBuild/baseDirectory).value / s),
  version := "0.0.7snap",
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
)

def jsProj(name: String) = Project("Js" + name, file("target/Js" + name)).enablePlugins(ScalaJSPlugin).settings(commonSettings).settings(
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value, 
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.8",
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

lazy val JsDev = jsProj("Dev").dependsOn(JsStrat).settings(  
  Compile/unmanagedSourceDirectories := List("Dev/src", "Dev/js/src", "Graphic/learn/src").map(s => (ThisBuild/baseDirectory).value / s)
)