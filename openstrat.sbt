val versionStr = "0.2.1snap"
ThisBuild/version := versionStr
ThisBuild/test in assembly := {}
name := "OpenStrat"
val scalaMajor = "2.13"
val scalaMinor = "3"
lazy val jarVersion = "_" + scalaMajor + "-" + versionStr + ".jar"
ThisBuild/scalaVersion := scalaMajor + "." + scalaMinor
ThisBuild/organization := "com.richstrat"
ThisBuild/autoAPIMappings := true

def commonSett = List(
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-Ywarn-value-discard", "-encoding", "UTF-8", "-unchecked",
   "-Xlint"),
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
)

lazy val root = (project in file(".")).aggregate(Util, Graphics, Tiling, World, Dev, JsDev)
lazy val module = SettingKey[File]("module")

lazy val UtilMacros = Project("UtilMacros", file("target/JvmUtilMacros")).settings(commonSett).settings(
  module := (ThisBuild/baseDirectory).value / "Util",
  scalaSource := module.value / "srcMacros",
  Compile/scalaSource := module.value / "src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  module.value / "srcMacrosTest",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.5" % "test",
)

def baseJvmProj(srcsStr: String, nameStr: String) = Project(nameStr, file("target/Jvm" + nameStr)).settings(commonSett).settings(
  module := (ThisBuild/baseDirectory).value / srcsStr,  
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.5" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),  
)

def coreJvmProj(srcsStr: String) = baseJvmProj(srcsStr, srcsStr + "Core").settings(
  scalaSource := module.value / "src",
  Compile/scalaSource := module.value / "src",
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx").map(module.value / _),
  resourceDirectory := module.value / "res",
  Test/scalaSource := module.value / "testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/resourceDirectory :=  module.value / "testRes",
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
)

def exsJvmProj(srcsStr: String) = baseJvmProj(srcsStr, srcsStr).settings(
  scalaSource := (ThisBuild/baseDirectory).value / srcsStr / "srcExs",
  testFrameworks += new TestFramework("utest.runner.Framework"), 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.5" % "test",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / srcsStr / "srcExs",
  Compile/unmanagedSourceDirectories := List("srcExs", "srcExsJvm", "srcExsFx").map((ThisBuild/baseDirectory).value / srcsStr / _),
  resourceDirectory := (ThisBuild/baseDirectory).value / srcsStr / "/ExsRes",
  Test/scalaSource := (ThisBuild/baseDirectory).value / srcsStr / "testSrcExs",
  Test/unmanagedSourceDirectories := List((ThisBuild/baseDirectory).value / srcsStr / "testSrc", (Test/scalaSource).value),
  Test/resourceDirectory :=  (ThisBuild/baseDirectory).value / srcsStr / "testResExs",
  Test/unmanagedResourceDirectories := List((ThisBuild/baseDirectory).value / srcsStr / "testRes", (Test/resourceDirectory).value),
)

lazy val UtilCore = coreJvmProj("Util").dependsOn(UtilMacros).settings(
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false, includeDependency = false),
  assemblyJarName in assembly := "rutil" + jarVersion
)

lazy val Util = exsJvmProj("Util").dependsOn(UtilCore).settings(Compile/mainClass:= Some("ostrat.WebPage1"))
lazy val GraphicsCore = coreJvmProj("Graphics").dependsOn(UtilCore).settings(libraryDependencies += "org.openjfx" % "javafx-controls" % "14")
lazy val Graphics = exsJvmProj("Graphics").dependsOn(GraphicsCore).settings(Compile/mainClass:= Some("learn.LessonE1App"))
lazy val TilingCore = coreJvmProj("Tiling").dependsOn(GraphicsCore)
lazy val Tiling = exsJvmProj("Tiling").dependsOn(TilingCore)
lazy val WorldCore = coreJvmProj("World").dependsOn(TilingCore)
lazy val World = exsJvmProj("World").dependsOn(WorldCore)

lazy val Dev = baseJvmProj("Dev", "Dev").dependsOn(Graphics, Tiling, World).settings(
  scalaSource := module.value / "src",
  Compile/scalaSource := module.value / "src",
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx").map(module.value / _),
  resourceDirectory := module.value / "res",
  Test/scalaSource := module.value / "testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/resourceDirectory :=  module.value / "testRes",
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value, (ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

val libModules =  List("Util", "Graphics", "Tiling", "World")

lazy val StratLib = Project("StratLib", file("target/JvmStratLib")).dependsOn(UtilMacros).settings(commonSett).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/unmanagedSourceDirectories := libModules.flatMap(nameStr => List("src", "srcJvm").
    map(endStr => (ThisBuild/baseDirectory).value / nameStr / endStr)),
  Compile/unmanagedResourceDirectories := libModules.map(str => (ThisBuild/baseDirectory).value / str / "res"), 
  Test/scalaSource := (ThisBuild/baseDirectory).value / "Util/testSrc",
  Test/unmanagedSourceDirectories := List(),
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false, includeDependency = false),
  //artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  // artifact.name + "-" + module.revision + artifact.classifier + "." + artifact.extension },
  assemblyJarName in assembly := "stratlib_2.13-" + version.value + ".jar",
  libraryDependencies += "org.openjfx" % "javafx-controls" % "14",
)

val docDirs: List[String] = List("Util", "Graphics", "Tiling", "World", "Dev")

lazy val DocMain = (project in file("target/DocMain")).dependsOn(UtilMacros).settings(commonSett).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJvm", el + "/srcExs")).
    map(s => (ThisBuild/baseDirectory).value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "14",
)

lazy val DocJs = (project in file("target/DocJs")).dependsOn(JsUtilMacros).settings(commonSett).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJs", el + "/srcExs")).
    map(s => (ThisBuild/baseDirectory).value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
)

def jsProj(name: String) = Project("Js" + name, file("target/Js" + name)).enablePlugins(ScalaJSPlugin).settings(commonSett).settings(
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value, 
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.0.0",
  scalaSource := (ThisBuild/baseDirectory).value / name / "src",
  Test/scalaSource := (ThisBuild/baseDirectory).value / name / "testSrc",

  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.7.4" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),  
)

lazy val JsUtilMacros = jsProj("UtilMacros").settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

lazy val JsUtil = jsProj("Util").dependsOn(JsUtilMacros).settings(  
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

lazy val JsGraphics = jsProj("Graphics").dependsOn(JsUtil).settings(
  Compile/unmanagedSourceDirectories := List("Graphics/src", "Graphics/srcJs").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val JsTiling = jsProj("Tiling").dependsOn(JsGraphics).settings(
  Compile/unmanagedSourceDirectories := List("Tiling/src", "Tiling/srcJs").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val JsWorld = jsProj("World").dependsOn(JsTiling).settings(  
  Compile/unmanagedSourceDirectories := List("World/src", "World/srcJs").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val JsDev = jsProj("Dev").dependsOn(JsWorld).settings(  
  Compile/unmanagedSourceDirectories := List("Dev/src", "Dev/srcJs", "Util/srcExs", "Graphics/srcExs", "Tiling/srcExs", "World/srcExs").
    map(s => (ThisBuild/baseDirectory).value / s),
)

def dottySettings = List(
	scalaVersion := "0.27.0-RC1",
  resolvers += Resolver.url("typesafe", url("http://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns),
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8", "-unchecked"),
)

lazy val DotMacros = Project("DotMacros", file("target/DotMacros")).settings(dottySettings).settings(  
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
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Util/testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

lazy val DotGraphics = Project("DotGraphics", file("target/Graphics")).dependsOn(DotUtil).settings(dottySettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Graphics/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Graphics/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Graphics/testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)