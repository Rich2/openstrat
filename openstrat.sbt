/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.3.3snap"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor: String = "3.5"
val scalaMinor: String = "1"
val scalaVersionStr: String = scalaMajor + "." + scalaMinor
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

lazy val root = (project in file(".")).aggregate(Util, Tiling, Dev).enablePlugins(ScalaUnidocPlugin).aggregate(Dev).settings(
  publish/skip := true,
  ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(Util),
  ScalaUnidoc/unidoc/scalacOptions += "-Ymacro-expand:none",
)

lazy val moduleDir = SettingKey[File]("moduleDir")
lazy val baseDir = SettingKey[File]("baseDir")
lazy val bbDir = SettingKey[File]("bbDir")
ThisBuild/bbDir := (ThisBuild/baseDirectory).value
lazy val tarDir = SettingKey[File]("tarDir")
ThisBuild/tarDir := (ThisBuild/baseDirectory).value / "target"
lazy val siteDir = SettingKey[File]("siteDir")
ThisBuild/siteDir := tarDir.value / "Site"
lazy val jsAppsDir = SettingKey[File]("jsAppsDir")
ThisBuild/jsAppsDir := bbDir.value / "Apps/AppsJs/AppSrcs"

def sett3 = List(
  scalaVersion := scalaVersionStr,
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
)

def proj(nameStr: String, locationStr: String) = Project(nameStr, file(locationStr)).settings(sett3)

def projSub(rootNameStr: String, extStr: String) = proj(rootNameStr + extStr, rootNameStr + "/" + rootNameStr + extStr).settings(
  Compile/unmanagedSourceDirectories := List(baseDirectory.value / "src", bbDir.value / rootNameStr / (extStr + "Src"))
)

def jvmProj(nameStr: String, srcsStr: String) = proj(nameStr, srcsStr).settings(
  moduleDir := bbDir.value / srcsStr,

  artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
    val cl = artifact.classifier match {
      case Some(st) => "-" + st
      case _ => ""
    }
    artifact.name + "-" + module.revision + cl + "." + artifact.extension }
)

def jvmMainProj(name: String): Project = jvmProj(name, name).settings(
  Compile/unmanagedSourceDirectories := List("src", "JvmSrc").map(moduleDir.value / _),
  Test/scalaSource := moduleDir.value / "TestSrc",
  Test/resourceDirectory :=  moduleDir.value / "TestRes",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value, moduleDir.value / "Test/src"),
  Test/unmanagedResourceDirectories := List(moduleDir.value / "TestRes", (Test/resourceDirectory).value),
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.8.4" % "test" withSources() withJavadoc(),
  testFrameworks += new TestFramework("utest.runner.Framework"),
)

def jsProj(name: String) = proj(name + "Js", name + "/" + name + "Js").enablePlugins(ScalaJSPlugin).settings(
  moduleDir := bbDir.value / name,
  Compile/unmanagedSourceDirectories := List(moduleDir.value /"src", moduleDir.value /"JsSrc"),
  libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "2.8.0")  withSources() withJavadoc(),
)

def natProj(name: String) = proj(name + "Nat", name + "/" + name + "Nat").enablePlugins(ScalaNativePlugin).settings(
  moduleDir := bbDir.value / name,
  Compile/unmanagedSourceDirectories := List("src").map(moduleDir.value / _),
  Compile/resourceDirectories := List("res", "NatRes").map(moduleDir.value / _),
)

def utilSett = List(
  Compile/unmanagedSourceDirectories ++= List("srcArr", "srcPersist", "srcParse").map(s => bbDir.value / "Util" / s),
)

lazy val Util = jvmMainProj("Util").settings(utilSett).settings(
  name := "rutil",
  Compile/unmanagedSourceDirectories += moduleDir.value / "srcRArr",
  libraryDependencies += "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" withSources() withJavadoc(),
)

lazy val UtilJs = jsProj("Util").settings(utilSett).settings(
  name := "rutiljs",
  Compile / sourceGenerators += Def.task {
    val str = scala.io.Source.fromFile("Util/srcRArr/RArr.scala").mkString
    val str2 = str.replaceAll("AnyVal with ", "")
    val arr = (Compile / sourceManaged).value / "Js" / "RArr.scala"
    IO.write(arr, str2)
    Seq(arr)
  }.taskValue,
)

lazy val UtilNat = natProj("Util").enablePlugins(ScalaNativePlugin).settings(utilSett).settings(
  Compile/unmanagedSourceDirectories += moduleDir.value / "srcRArr",
)

def geomSett = List(
  Compile/unmanagedSourceDirectories ++= List("srcLines", "srcShapes", "srcPoly", "srcUnits", "srcImperial", "srcTrans", "srcGraphic", "srcWeb", "srcGui",
    "srcEarth").map(s => bbDir.value / "Geom" / s),
)

lazy val Geom = jvmMainProj("Geom").dependsOn(Util).settings(geomSett)

lazy val GeomFx = projSub("Geom", "Fx").dependsOn(Geom).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1" withSources() withJavadoc(),
)

lazy val GeomExs = projSub("Geom", "Exs").dependsOn(Geom).settings(
  Compile/unmanagedSourceDirectories += baseDirectory.value / "srcLessons",
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GeomJs = jsProj("Geom").dependsOn(UtilJs).settings(geomSett).settings(
  Compile/unmanagedSourceDirectories += bbDir.value / "Geom/GeomJs/src",
)

def tilingSett = List(
  Compile/unmanagedSourceDirectories ++= List("srcHex", "srcHLayer", "srcSq", "srcSqLayer").map(s => bbDir.value / "Tiling" / s),
)
lazy val Tiling = jvmMainProj("Tiling").dependsOn(Geom).settings(tilingSett)
lazy val TilingExs = projSub("Tiling", "Exs").dependsOn(Tiling)
lazy val TilingJs = jsProj("Tiling").dependsOn(GeomJs).settings(tilingSett).dependsOn(GeomJs)

lazy val EGrid = jvmMainProj("EGrid").dependsOn(Tiling).settings(Compile/unmanagedSourceDirectories += bbDir.value / "EGrid/srcPts")

lazy val EarthIrr = config("EarthIrr") extend(Compile)
lazy val EG1300 = config("EG1300") extend(Compile)

lazy val EGridJs = jsProj("EGrid").dependsOn(TilingJs).settings(Compile/unmanagedSourceDirectories += bbDir.value / "EGrid/srcPts").settings(
  inConfig(EarthIrr)(Defaults.compileSettings),
  inConfig(EarthIrr)(ScalaJSPlugin.compileConfigSettings),
  EarthIrr/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ bbDir.value / "EGrid/JsAppsSrc/EarthApp",
  EarthIrr/mainClass:= Some("ostrat.pSJs.EarthAppJs"),

  inConfig(EG1300)(Defaults.compileSettings),
  inConfig(EG1300)(ScalaJSPlugin.compileConfigSettings),
  EG1300/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ bbDir.value / "EGrid/JsAppsSrc/EG1300App",
  EG1300/mainClass:= Some("ostrat.pSJs.EG1300AppJs"),
)

def appsSett = List(Compile/unmanagedSourceDirectories ++= List("srcStrat").map(s => bbDir.value / "Apps" / s))
lazy val Apps = jvmMainProj("Apps").dependsOn(EGrid).settings(appsSett)

lazy val Diceless = config("Diceless") extend(Compile)
lazy val Discov = config("Discov") extend(Compile)
lazy val IndRev = config("IndRev") extend(Compile)
lazy val Sors = config("Sors") extend(Compile)
lazy val WW1 = config("WW1") extend(Compile)
lazy val WW2 = config("WW2") extend(Compile)
lazy val BC305 = config("BC305") extend(Compile)
lazy val Dungeon = config("Dungeon") extend(Compile)
lazy val Planets = config("Planets") extend(Compile)
lazy val Chess = config("Chess") extend(Compile)

lazy val AppsJs = jsProj("Apps").dependsOn(EGridJs).settings(
  Compile/unmanagedSourceDirectories := List(bbDir.value / "Apps/src", bbDir.value / "Apps/srcStrat"),

  libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.6.0",

  inConfig(Diceless)(Defaults.compileSettings), inConfig(Diceless)(ScalaJSPlugin.compileConfigSettings), Diceless/mainClass:= Some("ostrat.pSJs.DicelessAppJs"),
  Diceless/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "DicelessApp",
  
  inConfig(Discov)(Defaults.compileSettings), inConfig(Discov)(ScalaJSPlugin.compileConfigSettings), Discov/mainClass:= Some("ostrat.pSJs.DiscovAppJs"),
  Discov/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "DiscovApp",

  inConfig(IndRev)(Defaults.compileSettings), inConfig(IndRev)(ScalaJSPlugin.compileConfigSettings), IndRev/mainClass:= Some("ostrat.pSJs.IndRevAppJs"),
  IndRev/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "IndRevApp",

  inConfig(Sors)(Defaults.compileSettings), inConfig(Sors)(ScalaJSPlugin.compileConfigSettings), Sors/mainClass:= Some("ostrat.pSJs.SorsAppJs"),
  Sors/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "SorsApp",

  inConfig(WW1)(Defaults.compileSettings), inConfig(WW1)(ScalaJSPlugin.compileConfigSettings), WW1/mainClass:= Some("ostrat.pSJs.WW1AppJs"),
  WW1/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "WW1App",

  inConfig(WW2)(Defaults.compileSettings), inConfig(WW2)(ScalaJSPlugin.compileConfigSettings), WW2/mainClass:= Some("ostrat.pSJs.WW2AppJs"),
  WW2/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "WW2App",

  inConfig(Dungeon)(Defaults.compileSettings), inConfig(Dungeon)(ScalaJSPlugin.compileConfigSettings),
  Dungeon/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "DungeonApp",
  Dungeon/mainClass:= Some("ostrat.pSJs.DungeonAppJs"),

  inConfig(Planets)(Defaults.compileSettings), inConfig(Planets)(ScalaJSPlugin.compileConfigSettings), Planets/mainClass:= Some("ostrat.pSJs.PlanetsAppJs"),
  Planets/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "PlanetsApp",

  inConfig(Chess)(Defaults.compileSettings), inConfig(Chess)(ScalaJSPlugin.compileConfigSettings), Chess/mainClass:= Some("ostrat.pSJs.ChessAppJs"),
  Chess/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "ChessApp",
)

lazy val allJs = taskKey[Unit]("Task to build all Js assets.")
allJs :=
{ import io.IO.copyFile
  val scStr: String = "scala-" + scalaVersionStr + "/appsjs-"
  (AppsJs / Diceless / fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "Diceless-opt/main.js"), siteDir.value / "earthgames/dicelessapp.js")
  (AppsJs/Discov/fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "Discov-opt/main.js"), siteDir.value / "earthgames/discovapp.js")
  (AppsJs/IndRev/fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "IndRev-opt/main.js"), siteDir.value / "earthgames/indrevapp.js")
  (AppsJs/Sors/fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "Sors-opt/main.js"), siteDir.value / "earthgames/sorsapp.js")
  (AppsJs/WW1/fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "WW1-opt/main.js"), siteDir.value / "earthgames/ww1app.js")
  (AppsJs/WW2/fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "WW2-opt/main.js"), siteDir.value / "earthgames/ww2app.js")
  (AppsJs/Planets/fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "Dungeon-opt/main.js"), siteDir.value / "otherapps/dungeonapp.js")
  (AppsJs/Dungeon/fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "Planets-opt/main.js"), siteDir.value / "otherapps/planetsapp.js")
  (AppsJs/Chess/fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "Chess-opt/main.js"), siteDir.value / "otherapps/chessapp.js")
  println("Built 8 Js files.")
}

lazy val Dev = jvmMainProj("Dev").dependsOn(GeomExs, TilingExs, EGrid, Apps).settings(
  Compile/unmanagedSourceDirectories := List("src", "JvmSrc").map(moduleDir.value / _) ::: List("Util", "Tiling").map(bbDir.value / _ / "Test/src"),

  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/unmanagedResourceDirectories := List(baseDirectory.value / "res", bbDir.value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pDev.SiteHtmlWrite"),
  reStart/mainClass	:= Some("ostrat.pDev.ServRawOS"),

  libraryDependencies ++= Seq(
    "io.github.cquiroz" %%% "scala-java-time" % "2.5.0" withSources() withJavadoc(),
    "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.5.0" withSources() withJavadoc(),
    ),
  )

lazy val DevFx =  projSub("Dev", "Fx").dependsOn(Dev, GeomFx).settings(
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

lazy val ServTom = jvmProj("ServTom", "Dev/ServTom").dependsOn(Dev).settings(
  Compile/unmanagedSourceDirectories := List(baseDirectory.value / "src"),
  libraryDependencies += "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % "provided" withSources() withJavadoc(),
)

lazy val ServZio = proj("ServZio", "Dev/ServZio").dependsOn(Dev).settings(
  Compile/unmanagedSourceDirectories := List(bbDir.value / "Dev/ServZio/src"),
  libraryDependencies += "dev.zio" %% "zio" % "2.1.9" withSources() withJavadoc(),
  libraryDependencies += "dev.zio" %% "zio-http" % "3.0.0" withSources() withJavadoc(),
)

val moduleDirs: List[String] = List("Util", "Geom", "Tiling", "EGrid", "Apps", "Dev")

val specDirs: List[String] =
  List("Util/srcArr", "Util/srcParse", "Util/srcPersist", "Geom/srcGraphic", "Geom/srcLines", "Geom/srcPoly", "Geom/srcShapes", "Geom/src3d", "Geom/srcGui",
    "Geom/srcWeb", "Geom/srcTrans", "Tiling/srcHex", "Tiling/srcHLayer", "Tiling/srcSq", "Tiling/srcSqLayer", "EGrid/srcPts", "Apps/srcStrat")

val CommonDirs: List[String] = moduleDirs.flatMap(m => List(m + "/src", m + "/ExsSrc")) ::: specDirs

lazy val bothDoc = taskKey[Unit]("Aims to be a task to aid building ScalaDocs")
bothDoc :=
{ val t1 = (DocMain/Compile/doc).value
  val t2 = (DocJs/Compile/doc).value
  println("Main docs and Js docs built")
}

lazy val DocMain = Project("DocMain", file("Dev/SbtDir/DocMain")).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := (CommonDirs ::: moduleDirs.flatMap(s => List(s + "/JvmSrc")) :::
    List("Util/srcRArr", "Geom/JvmFxSrc", "Dev/JvmFxSrc")).map(s => bbDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1",
  libraryDependencies += "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" withSources() withJavadoc(),
  Compile/doc/scalacOptions ++= Seq("-project-version", "0.3.2snap", "-groups"),
)

lazy val DocJs = (project in file("Dev/SbtDir/DocJs")).enablePlugins(ScalaJSPlugin).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := (CommonDirs ::: moduleDirs.map(_ + "/JsSrc") ::: List("Apps/JsAppSrc")).map(s => bbDir.value / s),

  Compile / sourceGenerators += Def.task {
    val str = scala.io.Source.fromFile("Util/srcRArr/RArr.scala").mkString
    val str2 = str.replaceAll("AnyVal with ", "")
    val arr = (Compile / sourceManaged).value / "Js" / "RArr.scala"
    IO.write(arr, str2)
    Seq(arr)
  }.taskValue,

  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.0" withSources() withJavadoc(),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  Compile/doc/scalacOptions ++= Seq("-groups"),
)