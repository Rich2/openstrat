/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.3.10snap"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor: String = "3.7"
val scalaMinor: String = "3"
val scalaVersionStr: String = scalaMajor + "." + scalaMinor
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

lazy val root = (project in file(".")).aggregate(Util, UtilDoc, Geom, GeomExs, Tiling, TilingExs, EGrid, Apps, Dev).enablePlugins(ScalaUnidocPlugin).settings(
  scalaVersion := scalaVersionStr,
  publish/skip := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(Util, Geom, GeomExs, GeomFx, Tiling, TilingExs, EGrid, Apps, Dev, DevFx, Servlet),
)

lazy val JsAgg = (project in file("Dev/JsAgg")).aggregate(UtilJs, GeomJs, TilingJs, EGridJs).enablePlugins(ScalaUnidocPlugin).settings(
  scalaVersion := scalaVersionStr,
  publish/skip := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(UtilJs, GeomJs, TilingJs, EGridJs),
)

lazy val moduleDir = SettingKey[File]("moduleDir")
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
  Test/scalaSource := moduleDir.value / "TestSrc",
  Test/resourceDirectory :=  moduleDir.value / "TestRes",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value, moduleDir.value / "Test/src"),
  Test/unmanagedResourceDirectories := List(moduleDir.value / "TestRes", (Test/resourceDirectory).value),
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.9.1" % "test" withSources() withJavadoc(),
  testFrameworks += new TestFramework("utest.runner.Framework"),

  artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
    val cl = artifact.classifier match {
      case Some(st) => "-" + st
      case _ => ""
    }
    artifact.name + "-" + module.revision + cl + "." + artifact.extension }
)

def jvmMainProj(name: String): Project = jvmProj(name, name).settings(
  Compile/unmanagedSourceDirectories := List("src", "JvmSrc").map(moduleDir.value / _),  
)

def jvmDocProj(name: String): Project = jvmProj(name + "Doc", name + "/" + name + "Doc").settings(
  Compile/unmanagedSourceDirectories := List("src", "JvmSrc").map(moduleDir.value / _),
)

def jvmExsProj(name: String): Project = jvmProj(name + "Exs", name + "/" + name + "Exs").settings(
  Compile/unmanagedSourceDirectories := List("src", "JvmSrc").map(moduleDir.value / _),
)

def jsProj(name: String, locationStr: String) = proj(name, locationStr).enablePlugins(ScalaJSPlugin).settings(
  libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "2.8.1")  withSources() withJavadoc(),
)

def jsMainProj(nameStem: String) = jsProj(nameStem + "Js", nameStem + "/" + nameStem + "Js").settings(
  moduleDir := bbDir.value / nameStem,
  Compile/unmanagedSourceDirectories := List("src", "JsSrc", nameStem + "Js/src").map(moduleDir.value / _),
)

def jsExsProj(nameStem: String) = jsProj(nameStem + "ExsJs", nameStem + "/" + nameStem + "Exs/" + nameStem + "ExsJs").settings(
  moduleDir := bbDir.value / nameStem / (nameStem + "Exs"),
  Compile/unmanagedSourceDirectories := List("src", nameStem + "ExsJs/src").map(moduleDir.value / _),
)

def jsDocProj(nameStem: String) = jsProj(nameStem + "DocJs", nameStem + "/" + nameStem + "Doc/" + nameStem + "DocJs").settings(
  moduleDir := bbDir.value / (nameStem + "Doc"),
  Compile/unmanagedSourceDirectories := List("src", "srcJs", nameStem + "DocJs/src").map(bbDir.value / nameStem / (nameStem + "Doc") / _),
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
)

lazy val UtilJs = jsMainProj("Util").settings(utilSett).settings(
  name := "rutiljs",
)

lazy val UtilDoc = jvmDocProj("Util").dependsOn(Geom)
lazy val UtilDocJs = jsDocProj("Util").dependsOn(GeomJs)

lazy val UtilNat = natProj("Util").enablePlugins(ScalaNativePlugin).settings(utilSett).settings(
  Compile/unmanagedSourceDirectories += moduleDir.value / "srcRArr",
)

def geomSett = List(
  Compile/unmanagedSourceDirectories ++=
   List("srcLines", "srcShapes", "srcPoly", "srcQuad", "srcUnits", "srcImperial", "srcTrans", "srcGraphic", "srcXml", "srcWeb", "srcWCode", "srcGui", "srcEarth").
   map(s => bbDir.value / "Geom" / s),
)

lazy val Geom = jvmMainProj("Geom").dependsOn(Util).settings(geomSett)

lazy val GeomFx = projSubName("Geom", "Fx").dependsOn(Geom).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1" withSources() withJavadoc(),
)

def geomExsSett = List(Compile/unmanagedSourceDirectories += bbDir.value / "Geom" / "GeomExs" / "srcLessons")

lazy val GeomExs = jvmExsProj("Geom").dependsOn(Geom).settings(geomExsSett).settings(
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GeomJs = jsMainProj("Geom").dependsOn(UtilJs).settings(geomSett)
lazy val GeomExsJs = jsExsProj("Geom").dependsOn(GeomJs).settings(geomExsSett)
lazy val GeomDoc = jvmDocProj("Geom").dependsOn(UtilDoc, GeomExs)
lazy val GeomDocJs = jsDocProj("Geom").dependsOn(UtilDocJs, GeomExsJs)

def tilingSett = List(
  Compile/unmanagedSourceDirectories ++= List("srcHex", "srcHLayer", "srcSq", "srcSqLayer").map(s => bbDir.value / "Tiling" / s),
)

lazy val Tiling = jvmMainProj("Tiling").dependsOn(Geom).settings(tilingSett)
lazy val TilingJs = jsMainProj("Tiling").dependsOn(GeomJs).settings(tilingSett).dependsOn(GeomJs)
lazy val TilingExs = jvmExsProj("Tiling").dependsOn(Tiling, GeomExs)
lazy val TilingDoc = jvmDocProj("Tiling").dependsOn(Tiling, UtilDoc)
lazy val TilingDocJs = jsDocProj("Tiling").dependsOn(TilingJs, UtilDocJs)

lazy val EGrid = jvmMainProj("EGrid").dependsOn(Tiling).settings(Compile/unmanagedSourceDirectories += bbDir.value / "EGrid/srcPts")
lazy val EGridDoc = jvmDocProj("EGrid").dependsOn(EGrid, UtilDoc)
lazy val EarthIrr = config("EarthIrr") extend(Compile)
lazy val EG1300 = config("EG1300") extend(Compile)

lazy val EGridJs = jsMainProj("EGrid").dependsOn(TilingJs).settings(Compile/unmanagedSourceDirectories += bbDir.value / "EGrid/srcPts").settings(
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
lazy val AppsDoc = jvmDocProj("Apps").dependsOn(UtilDoc)

lazy val AppsJs = jsMainProj("Apps").dependsOn(EGridJs).settings(
  Compile/unmanagedSourceDirectories := List(bbDir.value / "Apps/src", bbDir.value / "Apps/srcStrat", bbDir.value / "Apps/AppsJs/src"),
  libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.6.0",
  Compile/mainClass:= Some("ostrat.pSJs.DicelessAppJs"),
  Compile/scalaJSUseMainModuleInitializer := true,
)

lazy val DevDoc = jvmDocProj("Dev").dependsOn(GeomDoc, TilingExs, TilingDoc, EGridDoc, AppsDoc)

lazy val Dev = jvmMainProj("Dev").dependsOn(Apps, TilingExs, DevDoc).settings(
  Compile/unmanagedSourceDirectories += moduleDir.value / "srcDoc",
  Compile/unmanagedResourceDirectories := List(bbDir.value / "User"),
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
  assembly/mainClass := Some("ostrat.pFx.DevApp"),
  assemblyMergeStrategy := {case _ => MergeStrategy.first }
)

lazy val DevDocJs = jsDocProj("Dev").dependsOn(GeomJs).settings(
  //Compile/unmanagedSourceDirectories += bbDir.value / "Geom/GeomExs/GeomExsJs/src",
)

lazy val Servlet = projSub("Dev", "Servlet").dependsOn(Dev).settings(
  libraryDependencies += "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % "provided" withSources() withJavadoc(),
)

lazy val ServletExtras = projSub("Dev", "ServletExtras").dependsOn(Dev).settings(
  Compile/mainClass	:= Some("ostrat.pDev.StagingServlet"),
)

lazy val ServCask = projSub("Dev", "ServCask").dependsOn(Dev).settings(
  libraryDependencies += "com.lihaoyi" %% "cask" % "0.9.7" withSources() withJavadoc(),
)

lazy val ServZio = projSub("Dev", "ServZio").dependsOn(Dev).settings(
  libraryDependencies += "dev.zio" %% "zio" % "2.1.16" withSources() withJavadoc(),
  libraryDependencies += "dev.zio" %% "zio-http" % "3.1.0" withSources() withJavadoc(),
)

lazy val bothDoc = taskKey[Unit]("Aims to be a task to aid building ScalaDocs")
bothDoc :=
{ val t1 = (Compile/unidoc).value
  val t2 = (JsAgg/Compile/unidoc).value
  println("Main docs and Js docs built")
}
