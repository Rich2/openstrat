/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.3.0snap"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor = "3.0"
val scalaMinor = "1"
//lazy val jarVersion = "_" + scalaMajor + "-" + versionStr + ".jar"
//ThisBuild/scalaVersion := scalaMajor + "." + scalaMinor
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

lazy val root = (project in file(".")).aggregate(UtilJvm, GraphicsJvm, TilingJvm, EarthJvm, DevJvm).settings(
  publish/skip := true,
)

lazy val moduleDir = SettingKey[File]("moduleDir")
lazy val baseDir = SettingKey[File]("baseDir")
ThisBuild/baseDir := (ThisBuild/baseDirectory).value

def baseProj(srcsStr: String, nameStr: String) = Project(nameStr, file("Dev/SbtDir/" + nameStr)).settings(
  moduleDir := baseDir.value / srcsStr,  
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test" withSources(),
  testFrameworks += new TestFramework("utest.runner.Framework"),
  scalaSource := moduleDir.value / "src",
  Compile/scalaSource := moduleDir.value / "src",
  resourceDirectory := moduleDir.value / "res",
  Test/scalaSource := moduleDir.value / "testSrc",
  Test/resourceDirectory :=  moduleDir.value / "testRes",
)

def sett2 = List(
  scalaVersion := "2.13.6",
  scalacOptions ++= List("-feature", "-language:implicitConversions", "-deprecation", "-encoding", "UTF-8", "-Xsource:3"),// "-explaintypes", "-Ywarn-value-discard", "-Xlint"),
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value withSources(),
  publish/skip := true,
)

def sett3 = List(
  scalaVersion := "3.0.2-RC1",
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
)

def jvm3Proj(srcsStr: String) = baseProj(srcsStr, srcsStr + "Jvm").settings(sett3).settings(
  testFrameworks += new TestFramework("utest.runner.Framework"), 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test" withSources(),
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx", "src3", "srcExs", "srcExsJvm", "srcExsFx").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List(moduleDir.value / "testSrc", (Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List(moduleDir.value / "testRes", (Test/resourceDirectory).value),
)

def js2Proj(name: String) = baseProj(name, name + "Js").enablePlugins(ScalaJSPlugin).settings(sett2).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJs", "src2", "srcExs").map(moduleDir.value / _),
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.2.0" withSources(),
)

def js3Proj(name: String) = baseProj(name, name + "Js3").enablePlugins(ScalaJSPlugin).settings(sett3).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJs", "src3", "srcExs").map(moduleDir.value / _),
  libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "1.2.0").cross(CrossVersion.for3Use2_13) withSources(),
)

def nat2Proj(name: String) = baseProj(name, name + "Nat").enablePlugins(ScalaNativePlugin).settings(sett2).settings(
  Compile/unmanagedSourceDirectories := List("src", "src2", "srcNat", "srcExs").map(moduleDir.value / _),
)

//lazy val MacrosJvm = jvm3Proj("Macros")
lazy val MacrosJs = js2Proj("Macros")
lazy val MacrosNat = nat2Proj("Macros")
lazy val UtilJvm = jvm3Proj("Util")/*.dependsOn(MacrosJvm)*/.settings(
  name := "RUtil",
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Macros/src3"
)
lazy val UtilJs = js2Proj("Util").dependsOn(MacrosJs)
lazy val UtilNat = nat2Proj("Util").dependsOn(MacrosNat)

lazy val GraphicsJvm = jvm3Proj("Graphics").dependsOn(UtilJvm).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1" withSources(),
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GraphicsJs = js2Proj("Graphics").dependsOn(UtilJs)
lazy val GraphicsNat = nat2Proj("Graphics").dependsOn(UtilNat)
lazy val TilingJvm = jvm3Proj("Tiling").dependsOn(GraphicsJvm)
lazy val TilingJs = js2Proj("Tiling").dependsOn(GraphicsJs)
lazy val TilingNat = js2Proj("Tiling").dependsOn(GraphicsNat)
lazy val EarthJvm = jvm3Proj("Earth").dependsOn(TilingJvm)
lazy val EarthJs = js2Proj("Earth").dependsOn(TilingJs)
lazy val EarthAppJs = js2App("EarthApp").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Earth/srcEarthApp")
lazy val EarthNat = js2Proj("Earth").dependsOn(TilingNat)

lazy val DevJvm = jvm3Proj("Dev").dependsOn(EarthJvm).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value, (ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

def js2App(name: String) = baseProj(name, name + "Js").enablePlugins(ScalaJSPlugin).dependsOn(EarthJs).settings(sett2).settings(
  Compile/unmanagedSourceDirectories := List((ThisBuild/baseDirectory).value / "Dev/src"),
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0" withSources(),
)

lazy val WebGlJs = js2App("WebGl").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/GlApp")
lazy val ZugJs = js2App("Zug").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/ZugApp")
lazy val WW2Js = js2App("WW2").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/WW2App")
lazy val Y1783Js = js2App("Y1783").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/Y1783App")
lazy val Bc305Js = js2App("Bc305").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/Bc305App")
lazy val PlanetsJs = js2App("Planets").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/PlanetsApp")

lazy val DevNat = nat2Proj("Dev").dependsOn(EarthNat).settings(
  resourceDirectory := (ThisBuild/baseDirectory).value / "Dev/resNat",
  Compile/resourceDirectory := (ThisBuild/baseDirectory).value / "Dev/resNat",
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value),
)

val docDirs: List[String] = List("Util", "Graphics", "Tiling", "Earth", "Dev")

lazy val bothDoc = taskKey[Unit]("Aims to be a task to aid building ScalaDocs")
bothDoc :=
{ val t1 = (DocMain/Compile/doc).value
  val t2 = (DocJs/Compile/doc).value
  println("Main docs and Js docs built")
}

lazy val DocMain = (project in file("Dev/SbtDir/DocMain")).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := ("Macros" :: docDirs).flatMap(el => List(el + "/src", el + "/src3", el + "/srcJvm", el + "/srcExs", el + "srcFx")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1",
  Compile/doc/scalacOptions ++= Seq("-groups"),
  publish/skip := true,
)

lazy val DocJs = (project in file("Dev/SbtDir/DocJs")).enablePlugins(ScalaJSPlugin).dependsOn(MacrosJs).settings(sett2).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJs", el + "/srcExs")).map(s => baseDir.value / s),
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0" withSources(),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  Compile/doc/scalacOptions ++= Seq("-groups"),
)