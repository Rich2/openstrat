/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.3.1snap"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor = "3.1"
val scalaMinor = "1"
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

lazy val root = (project in file(".")).aggregate(Util, Geom, Tiling, Dev).enablePlugins(ScalaUnidocPlugin).settings(
  publish/skip := true,
  ScalaUnidoc/unidoc/scalacOptions += "-Ymacro-expand:none",
)

lazy val moduleDir = SettingKey[File]("moduleDir")
lazy val baseDir = SettingKey[File]("baseDir")
ThisBuild/baseDir := (ThisBuild/baseDirectory).value

def sett3 = List(
  scalaVersion := "3.1.1",
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

def jvmProj(srcsStr: String) = baseProj(srcsStr, srcsStr).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List(moduleDir.value / "testSrc", (Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List(moduleDir.value / "testRes", (Test/resourceDirectory).value),
)

def jsProj(name: String) = baseProj(name, name + "Js").enablePlugins(ScalaJSPlugin).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJs").map(moduleDir.value / _),
  libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "2.1.0") withSources(),
)

def natProj(name: String) = baseProj(name, name + "Nat").enablePlugins(ScalaNativePlugin).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcNat", "Exs/src").map(moduleDir.value / _),
  Compile/resourceDirectories := List("res", "resNat").map(moduleDir.value / _),
)

lazy val Util = jvmProj("Util").settings(
  name := "RUtil",
  Compile/unmanagedSourceDirectories ++= List("srcAnyVal", "srcParse").map{ str => (ThisBuild/baseDirectory).value / "Util" / str },
)
lazy val UtilExs = jvmProj("Util").dependsOn(Util)

lazy val UtilJs = jsProj("Util").settings(
  name := "RUtil",
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Util/srcParse",

  Compile / sourceGenerators += Def.task {
    val str = scala.io.Source.fromFile("Util/srcAnyVal/Arr.scala").mkString
    val str2 = str.replaceAll("AnyVal with ", "")
    val arr = (Compile / sourceManaged).value / "Js" / "Arr.scala"
    IO.write(arr, str2)
    Seq(arr)
  }.taskValue,
)

lazy val UtilNat = natProj("Util").enablePlugins(ScalaNativePlugin).settings(
  Compile/unmanagedSourceDirectories += moduleDir.value / "srcAnyVal",
)

def geomSett = List(
  Compile/unmanagedSourceDirectories ++= List("src3d", "srcWeb", "srcGui", "srcGlobe").map(s => (ThisBuild/baseDirectory).value / "Geom" / s),
  Test/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Geom/testSrcGlobe",
)

lazy val Geom = jvmProj("Geom").dependsOn(Util).settings(geomSett).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1" withSources(),
)

lazy val GeomExs = jvmProj("GeomExs").dependsOn(Geom).settings(
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GeomJs = jsProj("Geom").dependsOn(UtilJs).settings(geomSett)
lazy val GeomExsJs = jsProj("GeomExs").dependsOn(GeomJs).settings(geomSett)
lazy val GeomNat = natProj("Geom").dependsOn(UtilNat).settings(geomSett).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Geom/srcNat",
)

lazy val Tiling = jvmProj("Tiling").dependsOn(Geom).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcPts",
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcOld",
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcAncient",
  
  
)

lazy val TilingExs = jvmProj("TilingExs").dependsOn(Tiling)

lazy val TilingJs = jsProj("Tiling").dependsOn(GeomJs).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcPts",
)
lazy val TilingExsJs = jsProj("TilingExs").dependsOn(TilingJs)

lazy val TilingNat = natProj("Tiling").dependsOn(GeomNat).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcPts",
)

lazy val EarthAppJs = jsApp("EarthApp").settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcEarthApp",
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcPts",
)

lazy val Dev = jvmProj("Dev").dependsOn(GeomExs, TilingExs).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value, (ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
  libraryDependencies ++= Seq(
      "io.github.cquiroz" %%% "scala-java-time" % "2.4.0-M1",
      "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.4.0-M1"
    ),
)

lazy val DevNat = natProj("Dev").dependsOn(TilingNat)

def jsApp(name: String) = baseProj(name, name + "Js").enablePlugins(ScalaJSPlugin).dependsOn(TilingJs, GeomExsJs, TilingExsJs).settings(
  Compile/unmanagedSourceDirectories := List((ThisBuild/baseDirectory).value / "Dev/src"),
  libraryDependencies ++= Seq(
    "io.github.cquiroz" %%% "scala-java-time" % "2.4.0-M1",
    "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.4.0-M1"
  ),
)

lazy val WebGlJs = jsApp("WebGl").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/GlApp")
lazy val ZugJs = jsApp("Zug").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/ZugApp")
lazy val WW2Js = jsApp("WW2").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/WW2App")
lazy val Y1783Js = jsApp("Y1783").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/Y1783App")
lazy val Bc305Js = jsApp("Bc305").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/Bc305App")
lazy val PlanetsJs = jsApp("Planets").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/srcJsApps/PlanetsApp")

val moduleDirs: List[String] = List("Util", "UtilExs", "Geom", "GeomExs", "Tiling", "TilingExs", "Dev")
val specDirs: List[String] = List("Geom/src3d", "Geom/srcGlobe", "Geom/srcGui", "Geom/srcWeb", "Tiling/srcPts")
val CommonDirs: List[String] = moduleDirs.map(_ + "/src") ::: specDirs

lazy val bothDoc = taskKey[Unit]("Aims to be a task to aid building ScalaDocs")
bothDoc :=
{ val t1 = (DocMain/Compile/doc).value
  val t2 = (DocJs/Compile/doc).value
  println("Main docs and Js docs built")
}

lazy val DocMain = (project in file("Dev/SbtDir/DocMain")).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := (CommonDirs ::: moduleDirs.flatMap(s =>
    List(s + "/srcJvm")) ::: List("Util/srcAnyVal", "Geom/srcFx", "Dev/srcFx")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1",
  Compile/doc/scalacOptions ++= Seq("-groups"),
  publish/skip := true,
)

lazy val DocJs = (project in file("Dev/SbtDir/DocJs")).enablePlugins(ScalaJSPlugin).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := (CommonDirs ::: moduleDirs.map(_ + "/srcJs")).map(s => baseDir.value / s),

  Compile / sourceGenerators += Def.task {
    val str = scala.io.Source.fromFile("Util/srcAnyVal/Arr.scala").mkString
    val str2 = str.replaceAll("AnyVal with ", "")
    val arr = (Compile / sourceManaged).value / "Js" / "Arr.scala"
    IO.write(arr, str2)
    Seq(arr)
  }.taskValue,

  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.0.0" withSources(),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  Compile/doc/scalacOptions ++= Seq("-groups"),
)