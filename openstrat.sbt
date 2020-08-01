ThisBuild/version := "0.1.1snap"
ThisBuild/test in assembly := {}
name := "OpenStrat"
ThisBuild/scalaVersion := "2.13.3"
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

def commonSett = List(
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-Ywarn-value-discard", "-encoding", "UTF-8", "-unchecked", "-Xlint"),
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,  
  testFrameworks += new TestFramework("utest.runner.Framework"),  
)

lazy val root = (project in file(".")).aggregate(Util, Graphic)//, Tiling, Strat, Dev, JsDev)

lazy val UtilMacros = Project("UtilMacros", file("target/JvmUtilMacros")).settings(commonSett).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Util/Macros/testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.4" % "test",
)

def mainJvmProj(nameStr: String) = Project(nameStr, file("target/Jvm" + nameStr)).settings(commonSett).settings(
  scalaSource := (ThisBuild/baseDirectory).value / nameStr / "/src",
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.4" % "test",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / nameStr / "/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value, (ThisBuild/baseDirectory).value / nameStr / "srcJvm"),
  resourceDirectory := (ThisBuild/baseDirectory).value / nameStr / "/res",
  Test/scalaSource := (ThisBuild/baseDirectory).value / nameStr / "testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/resourceDirectory :=  (ThisBuild/baseDirectory).value / nameStr / "testRes",
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  version := (ThisBuild/version).value
)

def exsJvmProj(nameStr: String) = Project(nameStr + "Exs", file("target/ExsJvm" + nameStr)).settings(commonSett).settings(
  scalaSource := (ThisBuild/baseDirectory).value / nameStr / "/ExsSrc",
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.4" % "test",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / nameStr / "/ExsSrc",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  resourceDirectory := (ThisBuild/baseDirectory).value / nameStr / "/ExsRes",
  Test/scalaSource := (ThisBuild/baseDirectory).value / nameStr / "ExsTestSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value, (ThisBuild/baseDirectory).value / nameStr / "ExsTestSrc"),
  Test/resourceDirectory :=  (ThisBuild/baseDirectory).value / nameStr / "ExsTestRes",
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  version := (ThisBuild/version).value
)

lazy val Util = mainJvmProj("Util").dependsOn(UtilMacros)
lazy val Graphic = mainJvmProj("Graphic").dependsOn(Util).settings(libraryDependencies += "org.openjfx" % "javafx-controls" % "14")
lazy val GraphicExs = exsJvmProj("Graphic").dependsOn(Graphic)//.settings(libraryDependencies += "org.openjfx" % "javafx-controls" % "14")
lazy val Tiling = mainJvmProj("Tiling").dependsOn(Graphic)
lazy val TilingExs = exsJvmProj("Tiling").dependsOn(Tiling, GraphicExs)
lazy val Strat = mainJvmProj("Strat").dependsOn(Tiling)
lazy val StratExs = exsJvmProj("Strat").dependsOn(Strat, TilingExs)

lazy val Dev = mainJvmProj("Dev").dependsOn(StratExs).settings(commonSett).settings(
  //scalaSource := (ThisBuild/baseDirectory).value / "Dev/src",
  //Compile/scalaSource := (ThisBuild/baseDirectory).value / "Dev/src",
  //Test/scalaSource := (ThisBuild/baseDirectory).value / "Dev/testSrc",
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value, (ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

val libModules =  List("Util", "Graphic", "Tiling", "Strat")

lazy val StratLib = Project("StratLib", file("target/JvmStratLib")).dependsOn(UtilMacros).settings(commonSett).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/unmanagedSourceDirectories := libModules.flatMap(nameStr => List("src", "srcJvm").map(endStr => (ThisBuild/baseDirectory).value / nameStr / endStr)),
  Compile/unmanagedResourceDirectories := libModules.map(str => (ThisBuild/baseDirectory).value / str / "res"), 
  Test/scalaSource := (ThisBuild/baseDirectory).value / "Util/test/src",
  Test/unmanagedSourceDirectories := List(),
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false, includeDependency = false),
  //artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) => artifact.name + "-" + module.revision + artifact.classifier + "." + artifact.extension },
  assemblyJarName in assembly := "stratlib_2.13-" + version.value + ".jar",
  libraryDependencies += "org.openjfx" % "javafx-controls" % "14",
)

val docDirs: List[String] = List("Util", "Graphic", "Tiling", "Strat", "Dev")

lazy val DocMain = (project in file("target/DocMain")).dependsOn(UtilMacros).settings(commonSett).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJvm", el + "/srcExamples")).map(s => (ThisBuild/baseDirectory).value / s),
  version := "0.0.7snap",
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "14",
)

lazy val DocJs = (project in file("target/DocJs")).dependsOn(JsUtilMacros).settings(commonSett).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJs", el + "/srcExamples")).map(s => (ThisBuild/baseDirectory).value / s),
  version := "0.0.7snap",
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
)

def jsProj(name: String) = Project("Js" + name, file("target/Js" + name)).enablePlugins(ScalaJSPlugin).settings(commonSett).settings(
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value, 
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.0.0",
  scalaSource := (ThisBuild/baseDirectory).value / name / "src",
  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.7.4" % "test",
)

lazy val JsUtilMacros = jsProj("UtilMacros").settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
)

lazy val JsUtil = jsProj("Util").dependsOn(JsUtilMacros).settings(  
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
)

lazy val JsGraphic = jsProj("Graphic").dependsOn(JsUtil).settings(  
  Compile/unmanagedSourceDirectories := List("Graphic/src", "Graphic/srcJs").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val JsTiling = jsProj("Tiling").dependsOn(JsGraphic).settings(  
  Compile/unmanagedSourceDirectories := List("Tiling/src", "Tiling/srcJs").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val JsStrat = jsProj("Strat").dependsOn(JsTiling).settings(  
  Compile/unmanagedSourceDirectories := List("Strat/src", "Strat/srcJs").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val JsDev = jsProj("Dev").dependsOn(JsStrat).settings(  
  Compile/unmanagedSourceDirectories := List("Dev/src", "Dev/srcJs", "Graphic/srcExamples", "Strat/srcExamples").map(s => (ThisBuild/baseDirectory).value / s),
)

def dottySettings = List(
	scalaVersion := "0.25.0-RC2",
  resolvers += Resolver.url("typesafe", url("http://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns),
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8", "-unchecked"),
)

lazy val DotMacros = Project("DotModule", file("target/DotModule")).settings(dottySettings).settings(  
  scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/srcDot",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/srcDot",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Util/Macros/test/srcDot",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),  
)

lazy val DotUtil = Project("DotUtil", file("target/DotUtil")).dependsOn(DotMacros).settings(dottySettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Util/test/src",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

lazy val DotGraphic = Project("DotGraphic", file("target/Graphic")).dependsOn(DotUtil).settings(dottySettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Graphic/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Graphic/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Graphic/test/src",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)