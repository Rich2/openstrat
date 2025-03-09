/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import ostrat.pWeb.*

object RichstratID extends GroupId("com.richstrat")

case class OpenStratPomDep(idStr: String, versionStr: String) extends PomDep(RichstratID, ArtifactId(idStr), VersionElem(versionStr))
{
  //override def artifactId: ArtifactId  = 

  //override val groupId: GroupId = 

  //override def version: VersionElem = 
}

class OpenStratPomProject(val artifactStr: String, val versionStr: String, val dependencies: RArr[PomDep]) extends PomProject
{ override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override val groudId: GroupId = RichstratID
  override def version: VersionElem = VersionElem(versionStr) 
}

object OpenStratPomProject
{
  def apply(artifactStr: String, versionStr: String, scalaVersion: String, dependencies: RArr[PomDep]): OpenStratPomProject =
    new OpenStratPomProject(artifactStr: String, versionStr: String, dependencies +% ScalaLibDependency(scalaVersion))

  def apply(artifactStr: String, versionStr: String, scalaVersion: String, moduleStrs: StrArr): OpenStratPomProject =
  { val dependencies: RArr[PomDep] = moduleStrs.map(s => OpenStratPomDep(s, versionStr)) +% ScalaLibDependency(scalaVersion)
    new OpenStratPomProject(artifactStr: String, versionStr: String, dependencies)
  }
}