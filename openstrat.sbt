/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */

val versionStr = "0.2.2snap"
ThisBuild/version := versionStr
name := "OpenStrat"
val scalaMajor = "3.0"
val scalaMinor = "0"
//lazy val jarVersion = "_" + scalaMajor + "-" + versionStr + ".jar"
//ThisBuild/scalaVersion := scalaMajor + "." + scalaMinor
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

//scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
// "-feature", "-language:implicitConversions", "UTF-8", "-deprecation", "-explaintypes"),// "-Xsource:3"),//, "-Ywarn-value-discard", "-Xlint"),

lazy val root = (project in file(".")).aggregate(GraphicsJvm3, TilingJvm3, EarthJvm3, DevJvm3)
lazy val moduleDir = SettingKey[File]("moduleDir")
lazy val baseDir = SettingKey[File]("baseDir")
ThisBuild/baseDir := (ThisBuild/baseDirectory).value


def baseProj(srcsStr: String, nameStr: String) = Project(nameStr, file("Dev/SbtDir/" + nameStr)).settings(
  moduleDir := baseDir.value / srcsStr,  
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),
  scalaSource := moduleDir.value / "src",
  Compile/scalaSource := moduleDir.value / "src",
  resourceDirectory := moduleDir.value / "res",
  Test/scalaSource := moduleDir.value / "testSrc",
  Test/resourceDirectory :=  moduleDir.value / "testRes",
)

def sett2 = List(
  scalaVersion := "2.13.6",
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-encoding", "UTF-8", "-Xsource:3"),
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
)

def sett3 = List(
  scalaVersion := "3.0.0",
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8"),
)

def jvm2Proj(srcsStr: String) = baseProj(srcsStr, srcsStr + "Jvm2").settings(sett2).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx", "src2", "srcExs", "srcExsJvm", "srcExsFx").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
)

def jvm3Proj(srcsStr: String) = baseProj(srcsStr, srcsStr + "Jvm3").settings(sett3).settings(
  testFrameworks += new TestFramework("utest.runner.Framework"), 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.10" % "test",
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx", "src3", "srcExs", "srcExsJvm", "srcExsFx").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List(moduleDir.value / "testSrc", (Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List(moduleDir.value / "testRes", (Test/resourceDirectory).value),
)

def js2Proj(name: String) = baseProj(name, name + "Js2").enablePlugins(ScalaJSPlugin).settings(sett2).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJs", "src2", "srcExs").map(moduleDir.value / _),
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0",
)

def js3Proj(name: String) = baseProj(name, name + "Js3").enablePlugins(ScalaJSPlugin).settings(sett3).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJs", "src3", "srcExs").map(moduleDir.value / _),
  libraryDependencies += ("org.scala-js" %%% "scalajs-dom" % "1.1.0").cross(CrossVersion.for3Use2_13),
)

def nat2Proj(name: String) = baseProj(name, name + "Nat2").enablePlugins(ScalaNativePlugin).settings(sett2).settings(
  Compile/unmanagedSourceDirectories := List("src", "src2", "srcNat", "srcExs").map(moduleDir.value / _),
)

lazy val MacrosJvm2 = jvm2Proj("Macros")
lazy val MacrosJvm3 = jvm3Proj("Macros")
lazy val MacrosJs2 = js2Proj("Macros")
lazy val MacrosJs3 = js3Proj("Macros")
lazy val MacrosNat2 = nat2Proj("Macros")

lazy val GraphicsJvm2 = jvm2Proj("Graphics").dependsOn(MacrosJvm2).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1",
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GraphicsJvm3 = jvm3Proj("Graphics").dependsOn(MacrosJvm3).settings(
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15.0.1",
  Compile/mainClass:= Some("learn.LsE1App"),
)

lazy val GraphicsJs2 = js2Proj("Graphics").dependsOn(MacrosJs2)
lazy val GraphicsJs3 = js3Proj("Graphics").dependsOn(MacrosJs3)
lazy val GraphicsNat2 = nat2Proj("Graphics").dependsOn(MacrosNat2)
lazy val TilingJvm2 = jvm2Proj("Tiling").dependsOn(GraphicsJvm2)
lazy val TilingJvm3 = jvm3Proj("Tiling").dependsOn(GraphicsJvm3)
lazy val TilingJs2 = js2Proj("Tiling").dependsOn(GraphicsJs2)
lazy val TilingNat2 = js2Proj("Tiling").dependsOn(GraphicsNat2)
lazy val EarthJvm2 = jvm2Proj("Earth").dependsOn(TilingJvm2)
lazy val EarthJvm3 = jvm3Proj("Earth").dependsOn(TilingJvm3)
lazy val EarthJs2 = js2Proj("Earth").dependsOn(TilingJs2)
lazy val EarthNat2 = js2Proj("Earth").dependsOn(TilingNat2)

lazy val DevJvm3 = jvm3Proj("Dev").dependsOn(EarthJvm3).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx").map(moduleDir.value / _),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value, (ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

lazy val DevJs2 = js2Proj("Dev").dependsOn(EarthJs2).settings(
  Compile/unmanagedSourceDirectories := List("Dev/src", "Dev/srcJs").map(s => (ThisBuild/baseDirectory).value / s),
)

lazy val DevNat2 = nat2Proj("Dev").dependsOn(EarthNat2).settings(
  resourceDirectory := (ThisBuild/baseDirectory).value / "Dev/resNat",
  Compile/resourceDirectory := (ThisBuild/baseDirectory).value / "Dev/resNat",
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value),
)

val docDirs: List[String] = List("Graphics", "Tiling", "Earth", "Dev")

lazy val bothDoc = taskKey[Unit]("Aims to be a task to aid buiding ScalaDocs")
bothDoc :=
{ val t1 = (DocMain/Compile/doc).value
  val t2 = (DocJs/Compile/doc).value
  println("Main docs and Js docs built")
}

lazy val DocMain = (project in file("Dev/SbtDir/DocMain")).dependsOn(MacrosJvm3).settings(sett3).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "src3", el + "/srcJvm", el + "/srcExs", el + "srcFx")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "15",
  Compile/doc/scalacOptions ++= Seq("-groups"),
)

lazy val DocJs = (project in file("Dev/SbtDir/DocJs")).dependsOn(MacrosJs2).settings(sett2).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJs", el + "/srcExs")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  Compile/doc/scalacOptions ++= Seq("-groups"),
)