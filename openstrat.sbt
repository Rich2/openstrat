/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.3.3snap"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor: String = "3.4"
val scalaMinor: String = "2"
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
ThisBuild/jsAppsDir := bbDir.value / "Apps/JsAppsSrc"

def sett3 = List(
  scalaVersion := scalaVersionStr,
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
)

def proj(srcsStr: String, nameStr: String) = Project(nameStr, file(nameStr)).settings(sett3).settings(
  moduleDir := bbDir.value / srcsStr,
  Test/scalaSource := moduleDir.value / "TestSrc",
  Test/resourceDirectory :=  moduleDir.value / "TestRes",
)

def mainProj(srcsStr: String, nameStr: String) = proj(srcsStr, nameStr).settings(
  artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
    val cl = artifact.classifier match {
      case Some(st) => "-" + st
      case _ => ""
    }
    artifact.name + "-" + module.revision + cl + "." + artifact.extension }
)

def jvmProj(srcsStr: String): Project = mainProj(srcsStr, srcsStr).settings(
  Compile/unmanagedSourceDirectories := List("src", "JvmSrc").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List(moduleDir.value / "TestRes", (Test/resourceDirectory).value),
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.8.4" % "test" withSources() withJavadoc(),
  testFrameworks += new TestFramework("utest.runner.Framework"),
)

def jsProj(name: String) = mainProj(name, name + "Js").enablePlugins(ScalaJSPlugin).settings(
  Compile/unmanagedSourceDirectories := List(moduleDir.value /"src", baseDirectory.value / "src"),
  libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "2.8.0")  withSources() withJavadoc(),
)

def natProj(name: String) = mainProj(name, name + "Nat").enablePlugins(ScalaNativePlugin).settings(
  Compile/unmanagedSourceDirectories := List("src").map(moduleDir.value / _),
  Compile/resourceDirectories := List("res", "NatRes").map(moduleDir.value / _),
)

def utilSett = List(
  Compile/unmanagedSourceDirectories ++= List("srcArr", "srcPersist", "srcParse").map(s => bbDir.value / "Util" / s),
)

lazy val Util = jvmProj("Util").settings(utilSett).settings(
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

lazy val Geom = jvmProj("Geom").dependsOn(Util).settings(geomSett)

lazy val GeomFx = jvmProj("GeomFx").dependsOn(Geom).settings(geomSett).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1" withSources() withJavadoc(),
)

lazy val GeomExs = jvmProj("GeomExs").dependsOn(Geom).settings(
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GeomJs = jsProj("Geom").dependsOn(UtilJs).settings(geomSett)

def tilingSett = List(
  Compile/unmanagedSourceDirectories ++= List("srcHex", "srcHLayer", "srcSq", "srcSqLayer").map(s => bbDir.value / "Tiling" / s),
)
lazy val Tiling = jvmProj("Tiling").dependsOn(Geom).settings(tilingSett).settings(Test/unmanagedSourceDirectories += bbDir.value / "Tiling/ExsSrc")
lazy val TilingExs = jvmProj("TilingExs").dependsOn(Tiling)
lazy val TilingJs = jsProj("Tiling").dependsOn(GeomJs).settings(tilingSett).dependsOn(GeomJs)

lazy val EGrid = jvmProj("EGrid").dependsOn(Tiling).settings(Compile/unmanagedSourceDirectories += bbDir.value / "EGrid/srcPts")
lazy val EGridExs = jvmProj("EGridExs").dependsOn(EGrid)
lazy val EGridJs = jsProj("EGrid").dependsOn(TilingJs).settings(Compile/unmanagedSourceDirectories += bbDir.value / "EGrid/srcPts")

lazy val EarthAppJs = jsProj("EarthApp").dependsOn(EGridJs).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "EGrid/JsSrcApp",
)

def appsSett = List(Compile/unmanagedSourceDirectories ++= List("srcStrat").map(s => bbDir.value / "Apps" / s))
lazy val Apps = jvmProj("Apps").dependsOn(EGrid).settings(appsSett)

lazy val Diceless = config("Diceless") extend(Compile)
lazy val Discov = config("Discov") extend(Compile)
lazy val IndRev = config("IndRev") extend(Compile)
lazy val WW1 = config("WW1") extend(Compile)
lazy val WW2 = config("WW2") extend(Compile)
lazy val BC305 = config("BC305") extend(Compile)

lazy val Planets = config("Planets") extend(Compile)

lazy val AppsJs = jsProj("Apps").dependsOn(EGridJs).settings(
  Compile/unmanagedSourceDirectories := List(bbDir.value / "Apps/src", bbDir.value / "Apps/srcStrat") :::
    List("Geom", "Earth", "Tiling", "EGrid").map(bbDir.value / _ / "ExsSrc"),

  libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.6.0",

  inConfig(Diceless)(Defaults.compileSettings),
  inConfig(Diceless)(ScalaJSPlugin.compileConfigSettings),
  Diceless/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "DicelessApp",
  Diceless/mainClass:= Some("ostrat.pSJs.DicelessAppJs"),

  inConfig(Discov)(Defaults.compileSettings),
  inConfig(Discov)(ScalaJSPlugin.compileConfigSettings),
  Discov/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "DiscovApp",
  Discov/mainClass:= Some("ostrat.pSJs.DiscovAppJs"),

  inConfig(IndRev)(Defaults.compileSettings),
  inConfig(IndRev)(ScalaJSPlugin.compileConfigSettings),
  IndRev/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "IndRevApp",
  IndRev/mainClass:= Some("ostrat.pSJs.IndRevAppJs"),

  inConfig(WW1)(Defaults.compileSettings),
  inConfig(WW1)(ScalaJSPlugin.compileConfigSettings),
  WW1/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "WW1App",
  WW1/mainClass:= Some("ostrat.pSJs.WW1AppJs"),

  inConfig(WW2)(Defaults.compileSettings),
  inConfig(WW2)(ScalaJSPlugin.compileConfigSettings),
  WW2/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "WW2App",
  WW2/mainClass:= Some("ostrat.pSJs.WW2AppJs"),

  inConfig(Planets)(Defaults.compileSettings),
  inConfig(Planets)(ScalaJSPlugin.compileConfigSettings),
  Planets/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ jsAppsDir.value / "PlanetsApp",
  Planets/mainClass:= Some("ostrat.pSJs.PlanetsAppJs"),
)

lazy val allJs = taskKey[Unit]("Task to build all Js assets.")
allJs :=
{
  import io.IO.copyFile
  (AppsJs / Diceless / fullLinkJS).value
  val scStr: String = "scala-" + scalaVersionStr + "/appsjs-"
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "Diceless-opt/main.js"), siteDir.value / "earthgames/dicelessapp.js")
  (AppsJs/Discov/fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "Discov-opt/main.js"), siteDir.value / "earthgames/discovapp.js")
  (AppsJs/IndRev/fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "IndRev-opt/main.js"), siteDir.value / "earthgames/indrevapp.js")
  (AppsJs/WW1/fullLinkJS).value
  copyFile(bbDir.value / "AppsJs/target" / (scStr + "WW1-opt/main.js"), siteDir.value / "earthgames/ww1app.js")
  println("Built 3 Js files.")
}

lazy val Dev = jvmProj("Dev").dependsOn(GeomExs, TilingExs, EGridExs, Apps).settings(
  Compile/unmanagedSourceDirectories := List("src", "JvmSrc").map(moduleDir.value / _) :::
    List("Util", "Tiling").map(bbDir.value / _ / "Test/src"),

  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/unmanagedResourceDirectories := List(baseDirectory.value / "res", bbDir.value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pDev.SiteHtmlWrite"),
  reStart/mainClass	:= Some("ostrat.pDev.ServRawOS"),

  libraryDependencies ++= Seq(
    "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % "provided" withSources() withJavadoc(),
    "io.github.cquiroz" %%% "scala-java-time" % "2.5.0" withSources() withJavadoc(),
    "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.5.0" withSources() withJavadoc(),
    ),
  )

lazy val DevFx = jvmProj("DevFx").dependsOn(Dev, GeomFx).settings(
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

lazy val ServZio = jvmProj("ServZio").dependsOn(Dev).settings(
  libraryDependencies += "dev.zio" %% "zio" % "2.1.7" withSources() withJavadoc(),
  libraryDependencies += "dev.zio" %% "zio-http" % "3.0.0-RC9" withSources() withJavadoc(),
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