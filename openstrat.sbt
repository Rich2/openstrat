/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.3.5snap"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor: String = "3.6"
val scalaMinor: String = "3"
val scalaVersionStr: String = scalaMajor + "." + scalaMinor
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

lazy val root = (project in file(".")).aggregate(Util, Geom, GeomExs, Tiling, TilingExs, EGrid, Apps).enablePlugins(ScalaUnidocPlugin).settings(
  scalaVersion := scalaVersionStr,
  publish/skip := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(Util, Geom, GeomExs, GeomFx, Tiling, TilingExs, EGrid),
)

lazy val JsAgg = (project in file("Dev/JsAgg")).aggregate(UtilJs, GeomJs, TilingJs, EGridJs).enablePlugins(ScalaUnidocPlugin).settings(
  scalaVersion := scalaVersionStr,
  publish/skip := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(UtilJs, GeomJs, TilingJs, EGridJs),
)

lazy val moduleDir = SettingKey[File]("moduleDir")
lazy val baseDir = SettingKey[File]("baseDir")
lazy val bbDir = SettingKey[File]("bbDir")
ThisBuild/bbDir := (ThisBuild/baseDirectory).value
lazy val tarDir = SettingKey[File]("tarDir")
ThisBuild/tarDir := (ThisBuild/baseDirectory).value / "target"
lazy val siteDir = SettingKey[File]("siteDir")
ThisBuild/siteDir := tarDir.value / "Site"

def sett3 = List(
  scalaVersion := scalaVersionStr,
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
)

def proj(nameStr: String, locationStr: String) = Project(nameStr, file(locationStr)).settings(sett3)

def projSub(rootName: String, subName: String) = proj(subName, rootName + "/" + subName).settings(
  Compile/unmanagedSourceDirectories := List(baseDirectory.value / "src")
)

def projSubName(rootNameStr: String, extStr: String) = projSub(rootNameStr, rootNameStr + extStr)

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
  Compile/unmanagedResourceDirectories := List(bbDir.value / "User"),
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
  Compile/unmanagedSourceDirectories ++=
   List("srcLines", "srcShapes", "srcPoly", "srcUnits", "srcImperial", "srcTrans", "srcGraphic", "srcWeb", "srcGui", "srcEarth").
   map(s => bbDir.value / "Geom" / s),
)

lazy val Geom = jvmMainProj("Geom").dependsOn(Util).settings(geomSett)

lazy val GeomFx = projSubName("Geom", "Fx").dependsOn(Geom).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1" withSources() withJavadoc(),
)

lazy val GeomExs = projSubName("Geom", "Exs").dependsOn(Geom).settings(
  Compile/unmanagedSourceDirectories ++= Seq(baseDirectory.value / "srcLessons", baseDirectory.value / "JvmSrc"),
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GeomJs = jsProj("Geom").dependsOn(UtilJs).settings(geomSett).settings(
  Compile/unmanagedSourceDirectories += bbDir.value / "Geom/GeomJs/src",
)

def tilingSett = List(
  Compile/unmanagedSourceDirectories ++= List("srcHex", "srcHLayer", "srcSq", "srcSqLayer").map(s => bbDir.value / "Tiling" / s),
)
lazy val Tiling = jvmMainProj("Tiling").dependsOn(Geom).settings(tilingSett)
lazy val TilingExs = projSubName("Tiling", "Exs").dependsOn(Tiling)
lazy val TilingJs = jsProj("Tiling").dependsOn(GeomJs).settings(tilingSett).dependsOn(GeomJs)

lazy val EGrid = jvmMainProj("EGrid").dependsOn(Tiling).settings(Compile/unmanagedSourceDirectories += bbDir.value / "EGrid/srcPts")

lazy val EarthIrr = config("EarthIrr") extend(Compile)
lazy val EG1300 = config("EG1300") extend(Compile)

lazy val EGridJs = jsProj("EGrid").dependsOn(TilingJs).settings(Compile/unmanagedSourceDirectories += bbDir.value / "EGrid/srcPts").settings(
  inConfig(EarthIrr)(Defaults.compileSettings),
  inConfig(EarthIrr)(ScalaJSPlugin.compileConfigSettings),
  //EarthIrr/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ bbDir.value / "EGrid/JsAppsSrc/EarthApp",
  EarthIrr/mainClass:= Some("ostrat.pSJs.EarthAppJs"),

  inConfig(EG1300)(Defaults.compileSettings),
  inConfig(EG1300)(ScalaJSPlugin.compileConfigSettings),
  EG1300/unmanagedSourceDirectories := (Compile/unmanagedSourceDirectories).value :+ bbDir.value / "EGrid/JsAppsSrc/EG1300App",
  EG1300/mainClass:= Some("ostrat.pSJs.EG1300AppJs"),
)

def appsSett = List(Compile/unmanagedSourceDirectories ++= List("srcStrat").map(s => bbDir.value / "Apps" / s))
lazy val Apps = jvmMainProj("Apps").dependsOn(EGrid).settings(appsSett)

lazy val AppsJs = jsProj("Apps").dependsOn(EGridJs).settings(
  Compile/unmanagedSourceDirectories := List(bbDir.value / "Apps/src", bbDir.value / "Apps/srcStrat", bbDir.value / "Apps/AppsJs/src"),
  libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.6.0",
  Compile/mainClass:= Some("ostrat.pSJs.DicelessAppJs"),
  Compile/scalaJSUseMainModuleInitializer := true,
)

lazy val Dev = jvmMainProj("Dev").dependsOn(GeomExs, TilingExs, EGrid, Apps).settings(
  Compile/unmanagedSourceDirectories := List("src", "JvmSrc").map(moduleDir.value / _) ::: List("Util", "Tiling").map(bbDir.value / _ / "Test/src"),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/mainClass	:= Some("ostrat.pDev.SiteHtmlWrite"),
  reStart/mainClass	:= Some("ostrat.pDev.ServRawOS"),

  libraryDependencies ++= Seq(
    "io.github.cquiroz" %%% "scala-java-time" % "2.6.0" withSources() withJavadoc(),
    "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.6.0" withSources() withJavadoc(),
    ),
  )

lazy val DevFx =  projSubName("Dev", "Fx").dependsOn(Dev, GeomFx).settings(
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

lazy val Servlet = projSub("Dev", "Servlet").dependsOn(Dev).settings(
  libraryDependencies += "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % "provided" withSources() withJavadoc(),
)

lazy val ServletExtras = projSub("Dev", "ServletExtras").dependsOn(Dev).settings(
  Compile/mainClass	:= Some("ostrat.pDev.StagingServlet"),
)

lazy val ServCask = projSub("Dev", "ServCask").dependsOn(Dev).settings(
  libraryDependencies += "com.lihaoyi" %% "cask" % "0.9.4" withSources() withJavadoc(),
)

lazy val ServZio = projSub("Dev", "ServZio").dependsOn(Dev).settings(
  libraryDependencies += "dev.zio" %% "zio" % "2.1.14" withSources() withJavadoc(),
  libraryDependencies += "dev.zio" %% "zio-http" % "3.0.1" withSources() withJavadoc(),
)

lazy val bothDoc = taskKey[Unit]("Aims to be a task to aid building ScalaDocs")
bothDoc :=
{ val t1 = (Compile/unidoc).value
  val t2 = (JsAgg/Compile/unidoc).value
  println("Main docs and Js docs built")
}