 /* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.3.3snap"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor = "3.4"
val scalaMinor = "1"
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

lazy val root = (project in file(".")).aggregate(Util, Tiling, Dev).enablePlugins(ScalaUnidocPlugin).aggregate(Dev).settings(
  publish/skip := true,
  ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(Util),
  ScalaUnidoc/unidoc/scalacOptions += "-Ymacro-expand:none",
)

lazy val moduleDir = SettingKey[File]("moduleDir")
lazy val baseDir = SettingKey[File]("baseDir")
ThisBuild/baseDir := (ThisBuild/baseDirectory).value

def sett3 = List(
  scalaVersion := scalaMajor + "." + scalaMinor,
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
)

def proj(srcsStr: String, nameStr: String) = Project(nameStr, file("Dev/SbtDir/" + nameStr)).settings(sett3).settings(
  moduleDir := baseDir.value / srcsStr,
  Test/scalaSource := moduleDir.value / "TestSrc",
  Test/resourceDirectory :=  moduleDir.value / "TestRes",
)

def mainProj(srcsStr: String, nameStr: String) = proj(srcsStr, nameStr).settings(
  scalaSource := moduleDir.value / "src",
  Compile/scalaSource := moduleDir.value / "src",
  artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
    val cl = artifact.classifier match {
      case Some(st) => "-" + st
      case _ => ""
    }
    artifact.name + "-" + module.revision + cl + "." + artifact.extension }
)

def mainJvmProj(srcsStr: String) = mainProj(srcsStr, srcsStr).settings(
  Compile/unmanagedSourceDirectories := List("src", "JvmSrc", "JvmFxSrc").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List(moduleDir.value / "TestRes", (Test/resourceDirectory).value),
  resourceDirectory := moduleDir.value / "res",
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.8.3" % "test" withSources() withJavadoc(),
  testFrameworks += new TestFramework("utest.runner.Framework"),
)

def exsJvmProj(srcsStr: String) = proj(srcsStr, srcsStr + "Exs").settings(
  scalaSource := moduleDir.value / "ExsSrc",
  Compile/scalaSource := moduleDir.value / "ExsSrc",
  Compile/unmanagedSourceDirectories := List("ExsSrc", "ExsJvmSrc").map(moduleDir.value / _),
  resourceDirectory := moduleDir.value / "ExsRes",
  Test/unmanagedResourceDirectories := List(moduleDir.value / "ExsRes", (Test/resourceDirectory).value),
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.8.3" % "test"  withSources() withJavadoc(),
  testFrameworks += new TestFramework("utest.runner.Framework"),
)

def jsProj(name: String) = mainProj(name, name + "Js").enablePlugins(ScalaJSPlugin).settings(
  Compile/unmanagedSourceDirectories := List("src", "JsSrc").map(moduleDir.value / _),
  libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "2.8.0")  withSources() withJavadoc(),
)

def natProj(name: String) = mainProj(name, name + "Nat").enablePlugins(ScalaNativePlugin).settings(
  Compile/unmanagedSourceDirectories := List("src", "NatSrc", "Exs/src").map(moduleDir.value / _),
  Compile/resourceDirectories := List("res", "NatRes").map(moduleDir.value / _),
)

def utilSett = List(
  Compile/unmanagedSourceDirectories ++= List("srcArr", "srcPersist", "srcParse").map(s => (ThisBuild/baseDirectory).value / "Util" / s),
)

lazy val Util = mainJvmProj("Util").settings(utilSett).settings(
  name := "rutil",
  Compile/unmanagedSourceDirectories += moduleDir.value / "srcRArr",
  libraryDependencies += "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" withSources() withJavadoc(),
)

lazy val UtilExs = exsJvmProj("Util").dependsOn(Geom).settings(
  name := "rutilexs",
  Compile/mainClass:= Some("ostrat.UtilExsJvmApp"),
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
    "srcEarth").map(s => (ThisBuild/baseDirectory).value / "Geom" / s),
)

lazy val Geom = mainJvmProj("Geom").dependsOn(Util).settings(geomSett).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1" withSources() withJavadoc(),
  )

lazy val GeomExs = exsJvmProj("Geom").dependsOn(Geom).settings(
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GeomJs = jsProj("Geom").dependsOn(UtilJs).settings(geomSett)

def tilingSett = List(
  Compile/unmanagedSourceDirectories ++= List("srcHex", "srcHLayer", "srcSq", "srcSqLayer").map(s => (ThisBuild/baseDirectory).value / "Tiling" / s),
)
lazy val Tiling = mainJvmProj("Tiling").dependsOn(Geom).settings(tilingSett).settings(
  Test/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/ExsSrc"
)
lazy val TilingExs = exsJvmProj("Tiling").dependsOn(Tiling)
lazy val TilingJs = jsProj("Tiling").dependsOn(GeomJs).settings(tilingSett).dependsOn(GeomJs)
lazy val TilingNat = natProj("Tiling").dependsOn(UtilNat).settings(tilingSett)

lazy val EGrid = mainJvmProj("EGrid").dependsOn(Tiling).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "EGrid/srcPts",
)

lazy val EGridExs = exsJvmProj("EGrid").dependsOn(EGrid)

lazy val EGridJs = jsProj("EGrid").dependsOn(TilingJs).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "EGrid/srcPts",
)

lazy val EGridNat = natProj("EGrid").dependsOn(TilingNat).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Tiling/srcPts",
)

lazy val EarthAppJs = jsApp("EarthApp").settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "EGrid/JsSrcApp",
)

def appsSett = List(
  Compile/unmanagedSourceDirectories ++= List("srcStrat").map(s => (ThisBuild/baseDirectory).value / "Apps" / s),
)
lazy val Apps = mainJvmProj("Apps").dependsOn(EGrid).settings(appsSett)
lazy val AppsJs = jsProj("Apps").dependsOn(EGridJs)

lazy val Dev = mainJvmProj("Dev").dependsOn(UtilExs, GeomExs, TilingExs, EGridExs, Apps).settings(
  Compile/unmanagedSourceDirectories := List("src", "JvmSrc", "JvmFxSrc").map(moduleDir.value / _) :::
    List("Util", "Tiling").map((ThisBuild/baseDirectory).value / _ / "Test/src"),

  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value, (ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),

  libraryDependencies ++= Seq(
    "io.github.cquiroz" %%% "scala-java-time" % "2.5.0" withSources() withJavadoc(),
    "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.5.0" withSources() withJavadoc(),
    ),
)

lazy val DevNat = natProj("Dev").dependsOn(EGridNat)

def jsApp(name: String) = mainProj(name, name + "Js").enablePlugins(ScalaJSPlugin).dependsOn(AppsJs).settings(
  Compile/unmanagedSourceDirectories := (ThisBuild/baseDirectory).value / "Apps/srcStrat" ::
    List("Geom", "Earth", "Tiling", "EGrid").map((ThisBuild/baseDirectory).value / _ / "ExsSrc"),
  libraryDependencies ++= Seq(
    "io.github.cquiroz" %%% "scala-java-time" % "2.5.0" withSources() withJavadoc(),
    "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.5.0 withSources() withJavadoc()"
  ),
)

lazy val GenAppJs = jsApp("GenApp")

val moduleDirs: List[String] = List("Util", "Geom", "Tiling", "EGrid", "Apps", "Dev")

val specDirs: List[String] = List("Util/srcArr", "Util/srcParse", "Util/srcPersist", "Geom/srcGraphic", "Geom/srcLines", "Geom/srcPoly", "Geom/srcShapes",
  "Geom/src3d", "Geom/srcGui", "Geom/srcWeb", "Geom/srcTrans", "Tiling/srcHex", "Tiling/srcHLayer", "Tiling/srcSq", "Tiling/srcSqLayer", "EGrid/srcPts", "Apps/srcStrat")

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
    List("Util/srcRArr", "Geom/JvmFxSrc", "Dev/JvmFxSrc")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1",
  libraryDependencies += "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" withSources() withJavadoc(),
  Compile/doc/scalacOptions ++= Seq("-project-version", "0.3.2snap", "-groups"),
)

lazy val DocJs = (project in file("Dev/SbtDir/DocJs")).enablePlugins(ScalaJSPlugin).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := (CommonDirs ::: moduleDirs.map(_ + "/JsSrc") ::: List("Apps/JsAppSrc")).map(s => baseDir.value / s),

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
