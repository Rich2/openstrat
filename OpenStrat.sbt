ThisBuild/version := "0.0.2snap"
name := "OpenStrat"
ThisBuild/scalaVersion := "2.12.7"
ThisBuild/organization := "OpenStratOrg"

val commonSettings = List(	
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
    watchSources += (ThisBuild/baseDirectory).value / "DevSettings"
)

def proj(name: String): Project = Project(name, file("target/" + name)).settings(commonSettings)

val macrosSettings = List(Compile/scalaSource := (ThisBuild/baseDirectory).value / "Macros/src")

lazy val Macros = proj("Macros").settings(macrosSettings)
lazy val MacrosJs = proj("MacroJs").settings(macrosSettings).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6")

val utilSettings = List(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Util/src",
  Compile/unmanagedResourceDirectories += (ThisBuild/baseDirectory).value / "Util/resources")

lazy val Util = proj("Util").dependsOn(Macros).settings(utilSettings).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Util/srcJvm",
  Test/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Util/test/src/", 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.6" % "test",
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile, 
  testFrameworks += new TestFramework("utest.runner.Framework"),
  // include the macro classes and resources in the main jar
  mappings in (Compile, packageBin) ++= mappings.in(Macros, Compile, packageBin).value,
  // include the macro sources in the main source jar
  mappings in (Compile, packageSrc) ++= mappings.in(Macros, Compile, packageSrc).value
)

lazy val UtilJs = proj("UtilJs").dependsOn(MacrosJs).settings(utilSettings).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6")


val graphicSettings = List(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Graphic/src",
  Compile/unmanagedResourceDirectories += (ThisBuild/baseDirectory).value / "Graphic/resources")

lazy val Graphic = proj("Graphic").dependsOn(Util).settings(graphicSettings).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Graphic/srcJvm",
  Test/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Graphic/test/src/", 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.6" % "test",
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile, 
  testFrameworks += new TestFramework("utest.runner.Framework"),
  // include the macro classes and resources in the main jar
  mappings in (Compile, packageBin) ++= mappings.in(Macros, Compile, packageBin).value,
  // include the macro sources in the main source jar
  mappings in (Compile, packageSrc) ++= mappings.in(Macros, Compile, packageSrc).value
)

lazy val GraphicJs = proj("GraphicJs").dependsOn(UtilJs).settings(graphicSettings).enablePlugins(ScalaJSPlugin).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Graphic/srcJs",
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6"
)

val stratSettings = List(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Strat/src",  
  Compile/unmanagedResourceDirectories += (ThisBuild/baseDirectory).value / "Strat/resources"

)

lazy val Strat = proj("Strat").dependsOn(Graphic % "test->test;compile->compile").settings(stratSettings).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Strat/srcJvm", 
  Test/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Strat/test/src/", 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.6" % "test",
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile, 
  testFrameworks += new TestFramework("utest.runner.Framework"),
  // include the macro classes and resources in the main jar
  mappings in (Compile, packageBin) ++= mappings.in(Macros, Compile, packageBin).value,
  // include the macro sources in the main source jar
  mappings in (Compile, packageSrc) ++= mappings.in(Macros, Compile, packageSrc).value
)
lazy val StratJs = proj("StratJs").dependsOn(GraphicJs).settings(stratSettings).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6").settings(
  //scalaJSUseMainModuleInitializer := true,
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Strat/srcJs",
  //Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "JsStrat/srcPlay",
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6",  
)

//lazy val DocProj = project.dependsOn(MacrosJvm).settings(coreSettings).settings(
  //libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",  
//  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "FxStrat/src",
//)

val root = proj("root").aggregate(Strat)//ect in file(".")).settings(commonSettings).
//  enablePlugins(ScalaUnidocPlugin).settings(name := "Agg").settings(scalacOptions in (ScalaUnidoc, unidoc) += "-Ymacro-expand:none").aggregate(MacrosJvm, StratJvm, MacrosJs, StratJs)
