ThisBuild/version := "0.0.4snap"
name := "OpenStrat"
ThisBuild/scalaVersion := "2.13.0-RC3"
ThisBuild/organization := "OpenStratOrg"
ThisBuild/autoAPIMappings := true

val commonSettings = List(	
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xlint"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value,
    watchSources += (ThisBuild/baseDirectory).value / "DevSettings"
)

def crossSettings(name: String) = commonSettings ::: List(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / name / "src",
  Compile/unmanagedResourceDirectories += (ThisBuild/baseDirectory).value / name / "res"
)

def proj(name: String): Project = Project(name , file("target/" + name))

def projJvm(name: String): Project = proj(name + "Jvm").settings(crossSettings(name)).settings(
  Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / name / "jvm/src",
) 

def utilMacroSettings = commonSettings ::: List(Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "UtilMacros/src")
lazy val UtilMacrosJvm = projJvm("UtilMacros").settings(utilMacroSettings)

lazy val UtilJvm = projJvm("Util").dependsOn(UtilMacrosJvm).settings(	
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile,  
)

lazy val GraphicJvm = projJvm("Graphic").dependsOn(UtilJvm).settings(	
  Compile/unmanagedResourceDirectories += file("~/AppData/Local/OpenStratData/Dev").getAbsoluteFile,  
)

lazy val StratJvm = projJvm("Strat").dependsOn(GraphicJvm)

lazy val DevJvm = projJvm("Dev").dependsOn(StratJvm).settings(
	Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "DevModule/srcLearn",
  Compile/mainClass	:= Some("ostrat.pFx.DevApp"),  
)
