/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.3.1snap"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor = "3.2"
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
  scalaVersion := scalaMajor + "." + scalaMinor,
  scalacOptions ++= Seq("-feature", "-language:implicitConversions"/*, "-language:strictEquality"*/, "-noindent", "-deprecation", "-encoding", "UTF-8"),
)

def proj(srcsStr: String, nameStr: String) = Project(nameStr, file("Dev/SbtDir/" + nameStr)).settings(sett3).settings(
  moduleDir := baseDir.value / srcsStr,
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test" withSources(),
  testFrameworks += new TestFramework("utest.runner.Framework"),
  Test/scalaSource := moduleDir.value / "TestSrc",
  Test/resourceDirectory :=  moduleDir.value / "TestRes",
)

def mainProj(srcsStr: String, nameStr: String) = proj(srcsStr, nameStr).settings(
  scalaSource := moduleDir.value / "src",
  Compile/scalaSource := moduleDir.value / "src",
)

def mainJvmProj(srcsStr: String) = mainProj(srcsStr, srcsStr).settings(
  Compile/unmanagedSourceDirectories := List("src", "JvmSrc", "JvmFxSrc").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List(moduleDir.value / "ExsSrc", (Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List(moduleDir.value / "TestRes", (Test/resourceDirectory).value),
  resourceDirectory := moduleDir.value / "res",
)

def exsJvmProj(srcsStr: String) = proj(srcsStr, srcsStr + "Exs").settings(
  scalaSource := moduleDir.value / "ExsSrc",
  Compile/scalaSource := moduleDir.value / "ExsSrc",
  Compile/unmanagedSourceDirectories := List("ExsSrc", "ExsJvmSrc").map(moduleDir.value / _),
  resourceDirectory := moduleDir.value / "ExsRes",
  Test/unmanagedResourceDirectories := List(moduleDir.value / "ExsRes", (Test/resourceDirectory).value),
)

def jsProj(name: String) = mainProj(name, name + "Js").enablePlugins(ScalaJSPlugin).settings(
  Compile/unmanagedSourceDirectories := List("src", "JsSrc").map(moduleDir.value / _),
  libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "2.1.0") withSources(),
)

def natProj(name: String) = mainProj(name, name + "Nat").enablePlugins(ScalaNativePlugin).settings(
  Compile/unmanagedSourceDirectories := List("src", "NatSrc", "Exs/src").map(moduleDir.value / _),
  Compile/resourceDirectories := List("res", "NatRes").map(moduleDir.value / _),
)

lazy val Util = mainJvmProj("Util").settings(
  name := "RUtil",
  Compile/unmanagedSourceDirectories ++= List("srcRArr", "srcParse").map{ str => moduleDir.value / str },
)

lazy val UtilJs = jsProj("Util").settings(
  name := "RUtil",
  Compile/unmanagedSourceDirectories += moduleDir.value / "srcParse",

  Compile / sourceGenerators += Def.task {
    val str = scala.io.Source.fromFile("Util/srcRArr/RArr.scala").mkString
    val str2 = str.replaceAll("AnyVal with ", "")
    val arr = (Compile / sourceManaged).value / "Js" / "RArr.scala"
    IO.write(arr, str2)
    Seq(arr)
  }.taskValue,
)

lazy val UtilNat = natProj("Util").enablePlugins(ScalaNativePlugin).settings(
  Compile/unmanagedSourceDirectories ++= List("srcArr", "srcParse").map{ str => moduleDir.value / str },
)

def geomSett = List(
  Compile/unmanagedSourceDirectories ++= List("src3d", "srcWeb", "srcGui").map(s => (ThisBuild/baseDirectory).value / "Geom" / s),
)

lazy val Geom = mainJvmProj("Geom").dependsOn(Util).settings(geomSett).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1" withSources(),
)

lazy val GeomExs = exsJvmProj("Geom").dependsOn(Geom).settings(
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GeomJs = jsProj("Geom").dependsOn(UtilJs).settings(geomSett)
lazy val GeomNat = natProj("Geom").dependsOn(UtilNat).settings(geomSett).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Geom/srcNat",
)

lazy val Globe = mainJvmProj("Globe").dependsOn(Geom).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Globe/srcPts",
)
lazy val GlobeExs = exsJvmProj("Globe").dependsOn(Globe)

lazy val GlobeJs = jsProj("Globe").dependsOn(GeomJs).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Globe/srcPts",
)

lazy val GlobeNat = natProj("Globe").dependsOn(GeomNat).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcPts",
)

lazy val Tiling = mainJvmProj("Tiling").dependsOn(Globe).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcAncient",  
)

lazy val TilingExs = exsJvmProj("Tiling").dependsOn(Tiling)

lazy val TilingJs = jsProj("Tiling").dependsOn(GlobeJs).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcPts",
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcAncient",
)

lazy val TilingNat = natProj("Tiling").dependsOn(GlobeNat).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcAncient",
)

lazy val EarthAppJs = jsApp("EarthApp").settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/JsSrcApp",
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcPts",
)

lazy val Dev = mainJvmProj("Dev").dependsOn(GeomExs, GlobeExs, TilingExs).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcGrand", "JvmSrc", "JvmFxSrc").map(moduleDir.value / _) :::
    List("Geom", "Tiling").map((ThisBuild/baseDirectory).value / _ / "Test/src"),

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

def jsApp(name: String) = mainProj(name, name + "Js").enablePlugins(ScalaJSPlugin).dependsOn(TilingJs).settings(
  Compile/unmanagedSourceDirectories := List((ThisBuild/baseDirectory).value / "Dev/src", (ThisBuild/baseDirectory).value / "Dev/srcGrand") :::
    List("Geom", "Globe", "Tiling").map((ThisBuild/baseDirectory).value / _ / "ExsSrc"),
  libraryDependencies ++= Seq(
    "io.github.cquiroz" %%% "scala-java-time" % "2.4.0-M1",
    "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.4.0-M1"
  ),
)

lazy val WebGlJs = jsApp("WebGl").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/JsAppsSrc/GlApp")
lazy val ZugJs = jsApp("Zug").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/JsAppsSrc/ZugApp")
lazy val WW2Js = jsApp("WW2").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/JsAppsSrc/WW2App")
lazy val Y1783Js = jsApp("Y1783").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/JsAppsSrc/Y1783App")
lazy val Bc305Js = jsApp("Bc305").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/JsAppsSrc/Bc305App")
lazy val PlanetsJs = jsApp("Planets").settings(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Dev/JsAppsSrc/PlanetsApp")

val moduleDirs: List[String] = List("Util", "Geom", "Globe", "Tiling", "Dev")
val specDirs: List[String] = List("Util/srcParse", "Geom/src3d", "Geom/srcGui", "Geom/srcWeb", "Globe/srcPts", "Tiling/srcAncient")
val CommonDirs: List[String] = moduleDirs.flatMap(m => List(m + "/src", m + "/ExsSrc")) ::: specDirs

lazy val bothDoc = taskKey[Unit]("Aims to be a task to aid building ScalaDocs")
bothDoc :=
{ val t1 = (DocMain/Compile/doc).value
  val t2 = (DocJs/Compile/doc).value
  println("Main docs and Js docs built")
}

lazy val DocMain = (project in file("Dev/SbtDir/DocMain")).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := (CommonDirs ::: moduleDirs.flatMap(s =>
    List(s + "/JvmSrc")) ::: List("Util/srcRArr", "Geom/JvmFxSrc", "Dev/JvmFxSrc")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1",
  Compile/doc/scalacOptions ++= Seq("-groups"),
  publish/skip := true,
)

lazy val DocJs = (project in file("Dev/SbtDir/DocJs")).enablePlugins(ScalaJSPlugin).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := (CommonDirs ::: moduleDirs.map(_ + "/JsSrc") ::: List("Dev/JsAppSrc")).map(s => baseDir.value / s),

  Compile / sourceGenerators += Def.task {
    val str = scala.io.Source.fromFile("Util/srcRArr/RArr.scala").mkString
    val str2 = str.replaceAll("AnyVal with ", "")
    val arr = (Compile / sourceManaged).value / "Js" / "RArr.scala"
    IO.write(arr, str2)
    Seq(arr)
  }.taskValue,

  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.0.0" withSources(),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  Compile/doc/scalacOptions ++= Seq("-groups"),
)