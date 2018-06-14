name := "OpenStrat"
ThisBuild/scalaVersion := "2.12.6"
ThisBuild/organization := "OpenStratOrg"

val commonSett = List(	
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint", "-Yno-adapted-args"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value
)

val anteSett = List(Compile/scalaSource := (ThisBuild/baseDirectory).value / "AnteCompono/src", version := "0.0.1") ::: commonSett

lazy val AnteJvm = project.settings(anteSett)
lazy val AnteJs = project.settings(anteSett).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6")

val coreSett = List( Compile/scalaSource := (ThisBuild/baseDirectory).value / "Core/src", version := "0.0.1") ::: commonSett

lazy val CoreJvm = project.dependsOn(AnteJvm).settings(coreSett).settings(
  Test/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Core/test/src/", 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.3" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),

)
lazy val CoreJs = project.dependsOn(AnteJs).settings(coreSett).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6")

lazy val FxPlay = project.dependsOn(CoreJvm) .settings(commonSett).settings(
  version := "0.0.1",
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "FxStrat/src",    
  libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",
  Compile/mainClass := Some("rich.pFx.PlayApp"),  
)

lazy val JsPlay = project.dependsOn(CoreJs).enablePlugins(ScalaJSPlugin).settings(commonSett).settings(
  scalaJSUseMainModuleInitializer := true,
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "JsStrat/src",
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "JsStrat/srcPlay",
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6",
  version := "0.0.1",
)
