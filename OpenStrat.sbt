ThisBuild/version := "0.0.2snap"
name := "OpenStrat"
ThisBuild/scalaVersion := "2.12.7"
ThisBuild/organization := "OpenStratOrg"

val commonSettings = List(	
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
    watchSources += (ThisBuild/baseDirectory).value / "DevSettings"
)

val macrosSettings = List(Compile/scalaSource := (ThisBuild/baseDirectory).value / "Macros/src") ::: commonSettings

lazy val MacrosJvm = project.settings(macrosSettings)
lazy val MacrosJs = project.settings(macrosSettings).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6")

val graphicSettings = List(
   Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Graphic/src",
  Compile/unmanagedResourceDirectories += (ThisBuild/baseDirectory).value / "Graphic/resources"
) ::: commonSettings

lazy val GraphicJvm = project.dependsOn(MacrosJvm).settings(graphicSettings).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Graphic/srcJvm",
  Test/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Graphic/test/src/", 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.6" % "test",
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile, 
  testFrameworks += new TestFramework("utest.runner.Framework"),
  // include the macro classes and resources in the main jar
  mappings in (Compile, packageBin) ++= mappings.in(MacrosJvm, Compile, packageBin).value,
  // include the macro sources in the main source jar
  mappings in (Compile, packageSrc) ++= mappings.in(MacrosJvm, Compile, packageSrc).value
)
lazy val GraphicJs = project.dependsOn(MacrosJs).settings(graphicSettings).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6")

val stratSettings = List(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Strat/src",  
  Compile/unmanagedResourceDirectories += (ThisBuild/baseDirectory).value / "Strat/resources"
) ::: commonSettings

lazy val StratJvm = project.dependsOn(GraphicJvm).settings(stratSettings).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Strat/srcJvm", 
  Test/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Strat/test/src/", 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.6" % "test",
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile, 
  testFrameworks += new TestFramework("utest.runner.Framework"),
  // include the macro classes and resources in the main jar
  mappings in (Compile, packageBin) ++= mappings.in(MacrosJvm, Compile, packageBin).value,
  // include the macro sources in the main source jar
  mappings in (Compile, packageSrc) ++= mappings.in(MacrosJvm, Compile, packageSrc).value
)
lazy val StratJs = project.dependsOn(GraphicJs).settings(stratSettings).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6").settings(
  //scalaJSUseMainModuleInitializer := true,
  //Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "JsStrat/src",
  //Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "JsStrat/srcPlay",
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6",  
)

//lazy val DocProj = project.dependsOn(MacrosJvm).settings(coreSettings).settings(
  //libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",  
//  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "FxStrat/src",
//)

val root = (project in file(".")).
  settings(commonSettings).
  enablePlugins(ScalaUnidocPlugin).settings(name := "Agg").settings(scalacOptions in (ScalaUnidoc, unidoc) += "-Ymacro-expand:none").aggregate(MacrosJvm, StratJvm, MacrosJs, StratJs)
