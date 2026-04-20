/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

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
  def apply(groupStr: String, artifactStr: String, versionStr: String): PomDep =
    new PomDepGen(GroupId(groupStr), ArtifactId(artifactStr), VersionElem(versionStr))

  /** General implementation class for [[PomDep]] anXML element for POM file dependency */
  case class PomDepGen(groupId: GroupId, artifactId: ArtifactId, version: VersionElem) extends PomDep
}

/** XML element for POM file dependencies. Note the plural. Takes individual [[PopDep]]s as its child elements. */
class PomDependenciesElem(val dependencies: RArr[PomDep]) extends XmlTagLines
{ override def tagName: String = "Dependencies"
  override def attribs: RArr[XAtt] = RArr()
  override def contents: RArr[PomDep] = dependencies
}

object PomDependenciesElem
{ /** Factory apply method for the Pom Dependencies element. Note the plural.Takes individual [[PopDep]]s as its child elements. */
  def apply(dependencies: RArr[PomDep]): PomDependenciesElem = new PomDependenciesElem(dependencies)
}

/** XML element for a POM dependency for a version of the Scala3 library. */
class ScalaLibDep(val version: SwVersion) extends PomDep
{ override def groupId: GroupId = ScalaGroup
  override def artifactId: ArtifactId = ArtifactId("scala3-library_3")
}

object ScalaLibDep
{ /** Factory apply method to create an XML dependency element for the Scala standard Library to use in a POM file. */
  def apply(version: SwVersion): ScalaLibDep = new ScalaLibDep(version)
}

/** XML element for a POM dependency for a version of the Scala3.js library. */
class ScalaJsLibDep(val version: SwVersion) extends PomDep
{ override def groupId: GroupId = ScalaGroup
  override def artifactId: ArtifactId = ArtifactId("scala3-library_sjs1_3")
}

object ScalaJsLibDep
{ /** Factory apply method to create an XML dependency element for the Scala.js standard Library to use in a POM file. */
  def apply(version: SwVersion): ScalaJsLibDep = new ScalaJsLibDep(version)
}

/** Scala.js DOM libray dependency. */
class ScalaDomDep(val version: SwVersion) extends PomDep
{ override def groupId: GroupId = ScalaJsGroup
  override def artifactId: ArtifactId = ArtifactId("Scalajs-dom")
}

object ScalaDomDep
{ /** Factory apply method to create an XML POM dependency for Scal.js DOM library. */
  def apply(version: SwVersion = SwVersion(2, 8, 1)): ScalaDomDep = new ScalaDomDep(version)
}

/** XML element for a POM dependency for a version of the javafx-controls library. */
class JavaFxControlsDep(val version: SwVersion) extends PomDep
{ override def groupId: GroupId = OpenJfxId
  override def artifactId: ArtifactId = ArtifactId("javafx-controls")
}

object JavaFxControlsDep
{ /** Factory apply method for creating JavaFx Controls dependency for a POM. There is an apply name overload that takes the point numbers as parameters */
  def apply(version: SwVersion): JavaFxControlsDep = new JavaFxControlsDep(version)

  /** Factory apply method for creating JavaFx Controls dependency for a POM. There is an apply name overload that takes an [[SwVersion]] as its parameter. */
  def apply(n1: Int = 25, n2: Int = 0, n3: Int = 2): JavaFxControlsDep = new JavaFxControlsDep(SwVersion(n1, n2, n3))
}