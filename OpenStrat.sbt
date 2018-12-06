ThisBuild/version := "0.0.2snap"
name := "OpenStrat"
ThisBuild/scalaVersion := "2.12.8"
ThisBuild/organization := "OpenStratOrg"

val commonSettings = List(	
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
    watchSources += (ThisBuild/baseDirectory).value / "DevSettings"
)

def proj(name: String): Project = Project(name, file("target/" + name)).settings(commonSettings).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / name / "src",
  Compile/unmanagedResourceDirectories += (ThisBuild/baseDirectory).value / name / "res"
)

def projJvm(name: String): Project = proj(name).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / name / "srcJvm",
  Test/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / name /"srcTest", 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.6" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),
)

def projJs(name: String): Project = proj(name).enablePlugins(ScalaJSPlugin).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / name / "srcJs",
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6"
)


lazy val Macros = projJvm("Macros")//.settings(macrosSettings)
lazy val MacrosJs = projJs("MacrosJs")
	

lazy val Util = projJvm("Util").dependsOn(Macros).settings(	
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile,  
  // include the macro classes and resources in the main jar
  mappings in (Compile, packageBin) ++= mappings.in(Macros, Compile, packageBin).value,
  // include the macro sources in the main source jar
  mappings in (Compile, packageSrc) ++= mappings.in(Macros, Compile, packageSrc).value
)

lazy val UtilJs = projJs("UtilJs").dependsOn(MacrosJs)

lazy val Graphic = projJvm("Graphic").dependsOn(Util).settings(	
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile,  
  // include the macro classes and resources in the main jar
  mappings in (Compile, packageBin) ++= mappings.in(Macros, Compile, packageBin).value,
  // include the macro sources in the main source jar
  mappings in (Compile, packageSrc) ++= mappings.in(Macros, Compile, packageSrc).value
)

lazy val GraphicJs = projJs("GraphicJs").dependsOn(UtilJs)

lazy val Strat = projJvm("Strat").dependsOn(Graphic % "test->test;compile->compile").settings(	
  // include the macro classes and resources in the main jar
  mappings in (Compile, packageBin) ++= mappings.in(Macros, Compile, packageBin).value,
  // include the macro sources in the main source jar
  mappings in (Compile, packageSrc) ++= mappings.in(Macros, Compile, packageSrc).value
)

lazy val StratJs = projJs("StratJs").dependsOn(GraphicJs).settings(
  //scalaJSUseMainModuleInitializer := true,  
  //Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "JsStrat/srcPlay",  
)

//lazy val DocProj = project.dependsOn(MacrosJvm).settings(coreSettings).settings(
  //libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",  
//  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "FxStrat/src",
//)

val root = proj("root").aggregate(Strat)//ect in file(".")).settings(commonSettings).
//  enablePlugins(ScalaUnidocPlugin).settings(name := "Agg").settings(scalacOptions in (ScalaUnidoc, unidoc) += "-Ymacro-expand:none").aggregate(MacrosJvm, StratJvm, MacrosJs, StratJs)
