ThisBuild/version := "0.0.4snap"
name := "OpenStrat"
ThisBuild/scalaVersion := "2.12.8"
ThisBuild/organization := "OpenStratOrg"
ThisBuild/autoAPIMappings := true

val commonSettings = List(	
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
    watchSources += (ThisBuild/baseDirectory).value / "DevSettings"
)

def crossSettings(name: String) = commonSettings ::: List(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / name / "src",
  Compile/unmanagedResourceDirectories += (ThisBuild/baseDirectory).value / name / "res"
)

def proj(name: String): Project = Project(name , file("target/" + name))

def projJvm(name: String): Project = proj(name + "Jvm").settings(crossSettings(name)).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / name / "srcJvm",
  Test/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / name /"test/src", 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.6" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),
)

def jsSettings = List(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6")

def projJs(name: String): Project = proj(name + "Js").settings(crossSettings(name) ::: jsSettings).enablePlugins(ScalaJSPlugin).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / name / "js/src",  
)

def macroSettings = commonSettings ::: List(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Util/Macros/src")
lazy val MacrosJvm = Project("MacrosJvm", file("target/" + "MacrosJvm")).settings(macroSettings)
lazy val MacrosJs = Project("MacrosJs", file("target/" + "MacrosJs")).settings(macroSettings).settings(jsSettings).enablePlugins(ScalaJSPlugin)

lazy val UtilJvm = projJvm("Util").dependsOn(MacrosJvm).settings(	
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile,  
  // include the macro classes and resources in the main jar
  //mappings in (Compile, packageBin) ++= mappings.in(Macros, Compile, packageBin).value,
  // include the macro sources in the main source jar
  //mappings in (Compile, packageSrc) ++= mappings.in(Macros, Compile, packageSrc).value
)

lazy val UtilJs = projJs("Util").dependsOn(MacrosJs)

lazy val GraphicJvm = projJvm("Graphic").dependsOn(UtilJvm).settings(	
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile,  
  // include the macro classes and resources in the main jar
  //mappings in (Compile, packageBin) ++= mappings.in(Macros, Compile, packageBin).value,
  // include the macro sources in the main source jar
  //mappings in (Compile, packageSrc) ++= mappings.in(Macros, Compile, packageSrc).value
)

lazy val GraphicJs = projJs("Graphic").dependsOn(UtilJs)

lazy val StratJvm = projJvm("Strat").dependsOn(GraphicJvm % "test->test;compile->compile").settings(
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
  // include the macro classes and resources in the main jar
  //mappings in (Compile, packageBin) ++= mappings.in(Macros, Compile, packageBin).value,
  // include the macro sources in the main source jar
  //mappings in (Compile, packageSrc) ++= mappings.in(Macros, Compile, packageSrc).value
)

lazy val StratJs = projJs("Strat").dependsOn(GraphicJs)

lazy val LearnJvm = projJvm("Learn").dependsOn(StratJvm)

def appFile(name: String): String = "Strat/js/srcApps/ostrat/pSJs/" + name.take(1).toUpperCase + name.drop(1) + "JsApp.scala"

def jsApp(name: String): Project = proj(name).enablePlugins(ScalaJSPlugin).dependsOn(StratJs).settings(jsSettings).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value  / appFile(name),
)

lazy val ww2 = jsApp("ww2")
lazy val y1783 = jsApp("y1783")
lazy val bc305 = jsApp("bc305")
lazy val dungeon = jsApp("dungeon")
lazy val planets = jsApp("planets")
lazy val browsertest = jsApp("browsertest")
lazy val zug = jsApp("zug")
lazy val civ = jsApp("civ")
lazy val draughts = jsApp("draughts")

lazy val js1 = proj("js1").aggregate(ww2, y1783, bc305, dungeon)
lazy val js2 = proj("js2").aggregate(browsertest, zug, civ, draughts)

//lazy val DocProj = project.dependsOn(MacrosJvm).settings(coreSettings).settings(
  //libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",  
//  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "FxStrat/src",
//)

//val root = proj("root").settings(commonSettings).enablePlugins(ScalaUnidocPlugin)
//  enablePlugins(ScalaUnidocPlugin).settings(name := "Agg").settings(scalacOptions in (ScalaUnidoc, unidoc) += "-Ymacro-expand:none").aggregate(MacrosJvm, StratJvm, MacrosJs, StratJs)
