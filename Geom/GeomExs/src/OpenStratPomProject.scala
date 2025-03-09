/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import ostrat.pWeb.*

object RichstratID extends GroupId("com.richstrat")

case class OpenStratPomDep(idStr: String, versionStr: String) extends PomDep
{
  override def artifactId: ArtifactId  = ArtifactId(idStr)

  override val groupId: GroupId = RichstratID

  override def version: VersionElem = VersionElem(versionStr)
}

class OpenStratPomProject(val artifactStr: String, val versionStr: String, val dependencies: RArr[PomDep]) extends PomProject
{ override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override val groudId: GroupId = RichstratID
  override def version: VersionElem = VersionElem(versionStr)
  //override def  = moduleStrs.map(s => OpenStratPomDep(s, versionStr)) +% ScalaLibDependency("3.4.1")
}

object OpenStratPomProject
{
  def apply(artifactStr: String, versionStr: String, moduleStrs: StrArr): OpenStratPomProject =
  {
    val dependencies: RArr[PomDep] = moduleStrs.map(s => OpenStratPomDep(s, versionStr)) +% ScalaLibDependency("3.4.1")
    new OpenStratPomProject(artifactStr: String, versionStr: String, dependencies)
  }
}