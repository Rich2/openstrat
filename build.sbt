name := "OpenStrat"
scalaVersion in ThisBuild := "2.12.6"
organization in ThisBuild := "OpenStratOrg"

val commonSett = Seq(	
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint", "-Yno-adapted-args"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value
)

val anteSett = Seq(
   scalaSource in Compile := (baseDirectory in ThisBuild).value / "AnteCompono/src",
   version := "0.0.1",
) ++ commonSett

lazy val AnteJvm = project.settings(anteSett)
lazy val AnteJs = project.settings(anteSett).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6")

val coreSett = Seq(
   scalaSource in Compile := (baseDirectory in ThisBuild).value / "Core/src",
   version := "0.0.1") ++ commonSett

lazy val CoreJvm = project.dependsOn(AnteJvm).settings(coreSett),settings(
  unmanagedSourceDirectories in Test += testFile("Core"), 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.3" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),

)
lazy val CoreJs = project.dependsOn(AnteJs).settings(coreSett).enablePlugins(ScalaJSPlugin).settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6")

lazy val FxPlay = project.dependsOn(CoreJvm) .settings(commonSett).settings(
  version := "0.0.1",
  scalaSource in Compile := (baseDirectory in ThisBuild).value / "FxStrat/src",    
  libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",
  mainClass in Compile := Some("rich.pFx.PlayApp"),      
  artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) => "fx.jar" },
  assemblyJarName in assembly := "Fx.jar"
)

lazy val JsPlay = project.dependsOn(CoreJs).enablePlugins(ScalaJSPlugin).settings(commonSett).settings(
  scalaJSUseMainModuleInitializer := true,
  unmanagedSourceDirectories in Compile += (baseDirectory in ThisBuild).value / "JsStrat/src",
  unmanagedSourceDirectories in Compile += (baseDirectory in ThisBuild).value / "JsStrat/srcPlay",
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6",
  version := "0.0.1",
)
