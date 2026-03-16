/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** XML element for POM file dependency */
class PomDep(val groupId: GroupId, val artifactId: ArtifactId, val version: VersionElem) extends XmlTagLines
{ override def tagName: String = "dependency"
  override def attribs: RArr[XAtt] = RArr()
  override def contents: RArr[XConCompound] = RArr(groupId, artifactId, version)
}

object PomDep
{ /** Factory apply method to construct [[PomDep]] from [[String]]s. */
  def apply(groupStr: String, artifactStr: String, versionStr: String): PomDep = new PomDep(GroupId(groupStr), ArtifactId(artifactStr), VersionElem(versionStr))
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
class ScalaLibDependency(val versionStr: String) extends PomDep(ScalaGroupId, ArtifactId("scala3-library_3"), VersionElem(versionStr))

/** XML element for a POM dependency for a version of the Scala3.js library. */
class ScalaJsLibDependency(val versionStr: String) extends PomDep(ScalaGroupId, ArtifactId("scala3-library_sjs1_3"), VersionElem(versionStr))

object ScalaLibDependency
{ def apply(versionStr: String): ScalaLibDependency = new ScalaLibDependency(versionStr)
}

/** XML element for a POM dependency for a version of the javafx-controls library. */
class JavaFxControlsDependency(val versionStr: String) extends PomDep(OpenJfxId, ArtifactId("javafx-controls"), VersionElem(versionStr))

object JavaFxControlsDependency
{ def apply(versionStr: String): JavaFxControlsDependency = new JavaFxControlsDependency(versionStr)
}