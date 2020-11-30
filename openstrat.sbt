val versionStr = "0.2.2snap"
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
  scalacOptions ++=
    Seq("-feature", "-language:implicitConversions", "-deprecation", "-Ywarn-value-discard", "-encoding", "UTF-8", "-unchecked", "-Xlint"),
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
)

lazy val root = (project in file(".")).aggregate(Util, Graphics, Tiling, World, Dev, DevJs)
lazy val moduleDir = SettingKey[File]("moduleDir")
lazy val baseDir = SettingKey[File]("baseDir")
ThisBuild/baseDir := (ThisBuild/baseDirectory).value

lazy val UtilMacros = Project("UtilMacros", file("Dev/SbtDir/UtilMacros")).settings(commonSett).settings(
  moduleDir := baseDir.value / "Util",
  scalaSource := moduleDir.value / "srcMacros",
  Compile/scalaSource := moduleDir.value / "srcMacros",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  moduleDir.value / "srcMacrosTest",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.5" % "test",
)

def baseJvmProj(srcsStr: String, nameStr: String) = Project(nameStr, file("Dev/SbtDir/" + nameStr)).settings(commonSett).settings(
  moduleDir := baseDir.value / srcsStr,  
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.5" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),  
)

def coreJvmProj(srcsStr: String) = baseJvmProj(srcsStr, srcsStr + "Core").settings(
  scalaSource := moduleDir.value / "src",
  Compile/scalaSource := moduleDir.value / "src",
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx").map(moduleDir.value / _),
  resourceDirectory := moduleDir.value / "res",
  Test/scalaSource := moduleDir.value / "testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/resourceDirectory :=  moduleDir.value / "testRes",
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
)

def exsJvmProj(srcsStr: String) = baseJvmProj(srcsStr, srcsStr).settings(
  scalaSource := baseDir.value / srcsStr / "srcExs",
  testFrameworks += new TestFramework("utest.runner.Framework"), 
  libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.5" % "test",
  Compile/scalaSource := moduleDir.value / "srcExs",
  Compile/unmanagedSourceDirectories := List("srcExs", "srcExsJvm", "srcExsFx").map(moduleDir.value / _),
  resourceDirectory := moduleDir.value  / "ExsRes",
  Test/scalaSource := moduleDir.value / "testSrcExs",
  Test/unmanagedSourceDirectories := List(moduleDir.value / "testSrc", (Test/scalaSource).value),
  Test/resourceDirectory :=  moduleDir.value / "testResExs",
  Test/unmanagedResourceDirectories := List(moduleDir.value / "testRes", (Test/resourceDirectory).value),
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
  scalaSource := moduleDir.value / "src",
  Compile/scalaSource := moduleDir.value / "src",
  Compile/unmanagedSourceDirectories := List("src", "srcJvm", "srcFx").map(moduleDir.value / _),
  resourceDirectory := moduleDir.value / "res",
  Test/scalaSource := moduleDir.value / "testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
  Test/resourceDirectory :=  moduleDir.value / "testRes",
  Test/unmanagedResourceDirectories := List((Test/resourceDirectory).value),
  Compile/unmanagedResourceDirectories := List(resourceDirectory.value, (ThisBuild/baseDirectory).value / "Dev/User"),
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),
)

val libModules =  List("Util", "Graphics", "Tiling", "World")

lazy val StratLib = Project("StratLib", file("Dev/SbtDir/StratLib")).dependsOn(UtilMacros).settings(commonSett).settings(
  scalaSource := baseDir.value / "Util/src",
  Compile/scalaSource := baseDir.value / "Util/src",

  Compile/unmanagedSourceDirectories := libModules.flatMap(nameStr => List("src", "srcJvm").
    map(endStr => baseDir.value / nameStr / endStr)),

  Compile/unmanagedResourceDirectories := libModules.map(str => (ThisBuild/baseDirectory).value / str / "res"), 
  Test/scalaSource := baseDir.value / "Util/testSrc",
  Test/unmanagedSourceDirectories := List(),
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false, includeDependency = false),
  assemblyJarName in assembly := "stratlib_2.13-" + version.value + ".jar",
  libraryDependencies += "org.openjfx" % "javafx-controls" % "14",
)

lazy val StratExs = Project("StratExs", file("Dev/SbtDir/StratExs")).dependsOn(StratLib).settings(commonSett).settings(
  scalaSource := baseDir.value / "Util/srcExs",
  Compile/scalaSource := baseDir.value / "Util/srcExs",

  Compile/unmanagedSourceDirectories := libModules.flatMap(nameStr => List("srcExs", "srcExsJvm", "srcExsFx").
    map(endStr => baseDir.value / nameStr / endStr)),

  Compile/unmanagedResourceDirectories := libModules.map(str => baseDir.value / str / "resExs"), 
  Test/scalaSource := baseDir.value / "Util/testSrcExs",
  Test/unmanagedSourceDirectories := List(),
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false, includeDependency = false),
  //artifactName := { (sv: ScalaVersion, moduleDir: ModuleID, artifact: Artifact) =>
  // artifact.name + "-" + moduleDir.revision + artifact.classifier + "." + artifact.extension },
  assemblyJarName in assembly := "stratexs_2.13-" + version.value + ".jar",
  //libraryDependencies += "org.openjfx" % "javafx-controls" % "14",
)

val docDirs: List[String] = List("Util", "Graphics", "Tiling", "World", "Dev")

lazy val DocMain = (project in file("Dev/SbtDir/DocMain")).dependsOn(UtilMacros).settings(commonSett).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJvm", el + "/srcExs")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  libraryDependencies += "org.openjfx" % "javafx-controls" % "14",
  scalacOptions in (Compile, doc) ++= Seq("-groups"),

)

lazy val DocJs = (project in file("Dev/SbtDir/DocJs")).dependsOn(UtilMacrosJs).settings(commonSett).settings(
  name := "OpenStrat",
  Compile/unmanagedSourceDirectories := docDirs.flatMap(el => List(el + "/src", el + "/srcJs", el + "/srcExs")).map(s => baseDir.value / s),
  autoAPIMappings := true,
  apiURL := Some(url("https://richstrat.com/api/")),
  scalacOptions in (Compile, doc) ++= Seq("-groups"),
)

def jsProj(name: String) = Project(name + "Js", file("Dev/SbtDir/" + name + "Js")).enablePlugins(ScalaJSPlugin).settings(commonSett).settings(
  libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value, 
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0",
  scalaSource := (ThisBuild/baseDirectory).value / name / "src",
  Test/scalaSource := (ThisBuild/baseDirectory).value / name / "testSrc",

  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.7.4" % "test",
  testFrameworks += new TestFramework("utest.runner.Framework"),  
)

lazy val UtilMacrosJs = jsProj("UtilMacros").settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/srcMacros",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

lazy val UtilJs = jsProj("Util").dependsOn(UtilMacrosJs).settings(  
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

lazy val GraphicsJs = jsProj("Graphics").dependsOn(UtilJs).settings(
  Compile/unmanagedSourceDirectories := List("src", "srcJs").map(s => baseDir.value / "Graphics" / s)
)

lazy val TilingJs = jsProj("Tiling").dependsOn(GraphicsJs).settings(
  Compile/unmanagedSourceDirectories := List("Tiling/src", "Tiling/srcJs").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val WorldJs = jsProj("World").dependsOn(TilingJs).settings(  
  Compile/unmanagedSourceDirectories := List("World/src", "World/srcJs").map(s => (ThisBuild/baseDirectory).value / s)
)

lazy val DevJs = jsProj("Dev").dependsOn(WorldJs).settings(
  Compile/unmanagedSourceDirectories := List("Dev/src", "Dev/srcJs", "Util/srcExs", "Graphics/srcExs", "Tiling/srcExs", "World/srcExs").
    map(s => (ThisBuild/baseDirectory).value / s),
)

def dottySettings = List(
	scalaVersion := "3.0.0-M1",
  resolvers += Resolver.url("typesafe", url("http://repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns),
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8", "-unchecked"),
)

lazy val UtilMacrosDot = Project("UtilMacrosDot", file("Dev/SbtDir/UtilMacrosDot")).settings(dottySettings).settings(  
  scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/srcDot",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/Macros/srcDot",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Util/Macros/test/srcDot",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),  
)

lazy val UtilDot = Project("UtilDot", file("Dev/SbtDir/UtilDot")).dependsOn(UtilMacrosDot).settings(dottySettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Util/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Util/testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)

lazy val GraphicsDot = Project("GraphicsDot", file("Dev/SbtDir/GraphicsDot")).dependsOn(UtilDot).settings(dottySettings).settings(
  scalaSource := (ThisBuild/baseDirectory).value / "Graphics/src",
  Compile/scalaSource := (ThisBuild/baseDirectory).value / "Graphics/src",
  Compile/unmanagedSourceDirectories := List(scalaSource.value),
  Test/scalaSource :=  (ThisBuild/baseDirectory).value / "Graphics/testSrc",
  Test/unmanagedSourceDirectories := List((Test/scalaSource).value),
)