ThisBuild/version := "0.0.7snap"
name := "OpenStrat"
ThisBuild/scalaVersion := "2.13.3"
ThisBuild/organization := "OpenStratOrg"
ThisBuild/autoAPIMappings := true

def commonSettings = List(
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-Ywarn-value-discard", "-encoding", "UTF-8", "-unchecked", "-Xlint"),
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,  
  testFrameworks += new TestFramework("utest.runner.Framework"),
  
)

def jvmSettings = commonSettings ::: List(
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.4" % "test",
)

def stdSettings(name: String) = jvmSettings ::: List(
  scalaSource := (ThisBuild/baseDirectory).value / name / "/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / name / "/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value, (ThisBuild/baseDirectory).value / name / "srcEx"),
  resourceDirectory := (ThisBuild/baseDirectory).value / name / "/res",
  Test/scalaSource := (ThisBuild/baseDirectory).value / name / "test/src",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value, (ThisBuild/baseDirectory).value / name / "learn/src"),
  Test/resourceDirectory :=  (ThisBuild/baseDirectory).value / name / "test/res",
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  version := (ThisBuild/version).value
)

lazy val root = (project in file(".")).aggregate(Util, Graphic, Tiling, Strat, Dev, JsDev)

lazy val UtilMacros = Project("UtilMacros", file("target/JvmUtilMacros")).settings(jvmSettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Util/Macros/test/src",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

def stdJvmProj(name: String) = Project(name, file("target/Jvm" + name)).settings(stdSettings(name)).settings(
  
)

lazy val Util = stdJvmProj("Util").dependsOn(UtilMacros)
lazy val Graphic = stdJvmProj("Graphic").dependsOn(Util)
lazy val Tiling = stdJvmProj("Tiling").dependsOn(Graphic)
lazy val Strat = stdJvmProj("Strat").dependsOn(Tiling).settings(assemblyJarName in assembly := "strat" + (ThisBuild/version).value + ".jar")

lazy val Dev = stdJvmProj("Dev").dependsOn(Strat).enablePlugins(ScalaUnidocPlugin).settings(commonSettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Dev/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Dev/src",
  Test/scalaSource := (ThisBuild/baseDirectory).value / "Dev/test/src",
  Compile/unmanagedSourceDirectories := List("Dev/src", "Dev/jvm/src").map(s => (ThisBuild/baseDirectory).value / s),
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value, (ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "13",
)

val docDirs: List[String] = List("Util", "Graphic", "Tiling", "Strat", "Dev")

lazy val DocMain = (project in file("target/DocMain")).dependsOn(UtilMacros).settings(commonSettings).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/jvm/src", el + "/srcEx")).map(s => (ThisBuild/baseDirectory).value / s),
  version := "0.0.7snap",
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "14",
)

lazy val DocJs = (project in file("target/DocJs")).dependsOn(JsUtilMacros).settings(commonSettings).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/js/src", el + "/srcEx")).map(s => (ThisBuild/baseDirectory).value / s),
  version := "0.0.7snap",
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
)

def jsProj(name: String) = Project("Js" + name, file("target/Js" + name)).enablePlugins(ScalaJSPlugin).settings(commonSettings).settings(
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value, 
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.0.0",
  scalaSource := (ThisBuild/baseDirectory).value / name / "src",
  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.7.4" % "test",
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

lazy val JsTiling = jsProj("Tiling").dependsOn(JsGraphic).settings(  
  Compile/unmanagedSourceDirectories := List("Tiling/src", "Tiling/js/src").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val JsStrat = jsProj("Strat").dependsOn(JsTiling).settings(  
  Compile/unmanagedSourceDirectories := List("Strat/src", "Strat/js/src").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val JsDev = jsProj("Dev").dependsOn(JsStrat).settings(  
  Compile/unmanagedSourceDirectories := List("Dev/src", "Dev/js/src", "Graphic/srcEx", "Strat/srcEx").map(s => (ThisBuild/baseDirectory).value / s),
)

def dottySettings = List(
	scalaVersion := "0.25.0-RC2",
  resolvers += Resolver.url("typesafe", url("http://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns),
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8", "-unchecked"),
)

lazy val DotMacros = Project("DotModule", file("target/DotModule")).settings(dottySettings).settings(  
  scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/srcDot",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/srcDot",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Util/Macros/test/srcDot",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),  
)

lazy val DotUtil = Project("DotUtil", file("target/DotUtil")).dependsOn(DotMacros).settings(dottySettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Util/test/src",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

lazy val DotGraphic = Project("DotGraphic", file("target/Graphic")).dependsOn(DotUtil).settings(dottySettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Graphic/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Graphic/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Graphic/test/src",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)