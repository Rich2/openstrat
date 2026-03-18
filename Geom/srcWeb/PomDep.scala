/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** XML element for POM file dependency */
trait PomDep() extends XmlTagLines
{ override def tagName: String = "dependency"
  def groupId: GroupId
  def artifactId: ArtifactId
  def version: VersionElem
  override def attribs: RArr[XAtt] = RArr()
  override def contents: RArr[XConCompound] = RArr(groupId, artifactId, version)
}

object PomDep
{ /** Factory apply method to construct [[PomDep]] from [[String]]s. */
  def apply(groupStr: String, artifactStr: String, versionStr: String): PomDep = new PomDepGen(GroupId(groupStr), ArtifactId(artifactStr), VersionElem(versionStr))

  /** General implementation class for [[PomDep]] anXML element for POM file dependency */
  case class PomDepGen(groupId: GroupId, artifactId: ArtifactId, version: VersionElem) extends PomDep
}

/** XML element for POM file dependencies. Note the plural. Takes individual [[PopDep]]s as its child elements. */
class PomDepenenciesElem(val dependencies: RArr[PomDep]) extends XmlTagLines
{ override def tagName: String = "Dependencies"
  override def attribs: RArr[XAtt] = RArr()
  override def contents: RArr[PomDep] = dependencies
}

object PomDepenenciesElem
{ /** Factory apply method for the Pom Dependencies element. Note the plural.Takes individual [[PopDep]]s as its child elements. */
  def apply(dependencies: RArr[PomDep]): PomDepenenciesElem = new PomDepenenciesElem(dependencies)
}

/** XML element for a POM dependency for a version of the Scala3 library. */
class ScalaLibDependency(val version: SwVersion) extends PomDep
{ override def groupId: GroupId = ScalaGroupId
  override def artifactId: ArtifactId = ArtifactId("scala3-library_3")
}

/** XML element for a POM dependency for a version of the Scala3.js library. */
class ScalaJsLibDependency(val version: SwVersion) extends PomDep
{ override def groupId: GroupId = ScalaGroupId
  override def artifactId: ArtifactId = ArtifactId("scala3-library_sjs1_3")
}

object ScalaLibDependency
{ def apply(version: SwVersion): ScalaLibDependency = new ScalaLibDependency(version)
}

/** XML element for a POM dependency for a version of the javafx-controls library. */
class JavaFxControlsDependency(val version: SwVersion) extends PomDep
{ override def groupId: GroupId = OpenJfxId
  override def artifactId: ArtifactId = ArtifactId("javafx-controls")
}

object JavaFxControlsDependency
{ /** Factory apply method for creating JavaFx Controls dependency for a POM. */
  def apply(version: SwVersion): JavaFxControlsDependency = new JavaFxControlsDependency(version)

  /** Factory apply method for creating JavaFx Controls dependency for a POM. */
  def apply(n1: Int, n2: Int, n3: Int): JavaFxControlsDependency = new JavaFxControlsDependency(SwVersion(n1, n2, n3))
}