/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.3.0snap"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor = "3.0"
val scalaMinor = "2"
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

lazy val root = (project in file(".")).aggregate(UtilJvm, GraphicsJvm, TilingJvm, EarthJvm, DevJvm).settings(
  publish/skip := true,
)

lazy val moduleDir = SettingKey[File]("moduleDir")
lazy val baseDir = SettingKey[File]("baseDir")
ThisBuild/baseDir := (ThisBuild/baseDirectory).value

def sett3 = List(
  scalaVersion := "3.0.2",
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
)

def baseProj(srcsStr: String, nameStr: String) = Project(nameStr, file("Dev/SbtDir/" + nameStr)).settings(sett3).settings(
  moduleDir := baseDir.value / srcsStr,
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test" withSources(),
  testFrameworks += new TestFramework("utest.runner.Framework"),
  scalaSource := moduleDir.value / "src",
  Compile/scalaSource := moduleDir.value / "src",
  resourceDirectory := moduleDir.value / "res",
  Test/scalaSource := moduleDir.value / "testSrc",
  Test/resourceDirectory :=  moduleDir.value / "testRes",
)

def jvmProj(srcsStr: String) = baseProj(srcsStr, srcsStr + "Jvm").settings(
  testFrameworks += new TestFramework("utest.runner.Framework"), 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test" withSources(),
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx", "srcExs", "srcExsJvm", "srcExsFx").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List(moduleDir.value / "testSrc", (Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List(moduleDir.value / "testRes", (Test/resourceDirectory).value),
)

def jsProj(name: String) = baseProj(name, name + "Js").enablePlugins(ScalaJSPlugin).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJs", "srcExs").map(moduleDir.value / _),
  libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "1.2.0").cross(CrossVersion.for3Use2_13) withSources(),
)

lazy val UtilJvm = jvmProj("Util").settings(
  name := "RUtil",
  Compile/unmanagedSourceDirectories ++= Seq((ThisBuild/baseDirectory).value / "Macros/src3", (ThisBuild/baseDirectory).value / "Util/srcAnyVal")
)
lazy val UtilJs = jsProj("Util").settings(
  name := "RUtil",
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Macros/src3",

  Compile / sourceGenerators += Def.task {
    val str = scala.io.Source.fromFile("Util/srcAnyVal/Arr.scala").mkString
    val str2 = str.replaceAll("AnyVal with ", "")
    val arr = (Compile / sourceManaged).value / "Js" / "Arr.scala"
    IO.write(arr, str2)
    Seq(arr)
  }.taskValue,
)

lazy val GraphicsJvm = jvmProj("Graphics").dependsOn(UtilJvm).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1" withSources(),
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GraphicsJs = jsProj("Graphics").dependsOn(UtilJs)
lazy val TilingJvm = jvmProj("Tiling").dependsOn(GraphicsJvm)
lazy val TilingJs = jsProj("Tiling").dependsOn(GraphicsJs)
lazy val EarthJvm = jvmProj("Earth").dependsOn(TilingJvm)
lazy val EarthJs = jsProj("Earth").dependsOn(TilingJs)
lazy val EarthAppJs = jsApp("EarthApp").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Earth/srcEarthApp")

lazy val DevJvm = jvmProj("Dev").dependsOn(EarthJvm).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value, (ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

def jsApp(name: String) = baseProj(name, name + "Js").enablePlugins(ScalaJSPlugin).dependsOn(EarthJs).settings(
  Compile/unmanagedSourceDirectories := List((ThisBuild/baseDirectory).value / "Dev/src"),
  libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "1.2.0").cross(CrossVersion.for3Use2_13) withSources(),
)

lazy val WebGlJs = jsApp("WebGl").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/GlApp")
lazy val ZugJs = jsApp("Zug").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/ZugApp")
lazy val WW2Js = jsApp("WW2").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/WW2App")
lazy val Y1783Js = jsApp("Y1783").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/Y1783App")
lazy val Bc305Js = jsApp("Bc305").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/Bc305App")
lazy val PlanetsJs = jsApp("Planets").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/PlanetsApp")

val docDirs: List[String] = List("Util", "Graphics", "Tiling", "Earth", "Dev")

lazy val bothDoc = taskKey[Unit]("Aims to be a task to aid building ScalaDocs")
bothDoc :=
{ val t1 = (DocMain/Compile/doc).value
  val t2 = (DocJs/Compile/doc).value
  println("Main docs and Js docs built")
}

lazy val DocMain = (project in file("Dev/SbtDir/DocMain")).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := ("Macros" :: docDirs).flatMap(el => List(el + "/src", el + "/srcJvm", el + "/srcExs", el + "srcFx")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1",
  Compile/doc/scalacOptions ++= Seq("-groups"),
  publish/skip := true,
)

lazy val DocJs = (project in file("Dev/SbtDir/DocJs")).enablePlugins(ScalaJSPlugin).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJs", el + "/srcExs")).map(s => baseDir.value / s),
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0" withSources(),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  Compile/doc/scalacOptions ++= Seq("-groups"),
)