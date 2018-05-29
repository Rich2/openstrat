scalaVersion in ThisBuild := "2.12.6"
organization in ThisBuild := "OpenStrat"

def proj(name: String, versionStr: String): Project = Project(name, file(name)).settings(
	scalaSource in Compile := baseDirectory.value / "src",
	version := versionStr,
    scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint", "-Yno-adapted-args"),
    libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value
)

val ante = proj("AnteCompono","0.0.1")
val common = proj("Common", "0.0.1").dependsOn(ante)

lazy val fxStrat = proj("FxStrat", "0.0.1").dependsOn(common).settings(          
      libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",
      mainClass in Compile := Some("rich.pFx.PlayApp"),      
      artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) => "fx.jar" },
      assemblyJarName in assembly := "Fx.jar"
      )
