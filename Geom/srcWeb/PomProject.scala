/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

class PomProject(val artifactSTr: String, versionStr: String, val groupStr: String) extends XmlElem
{
  val artifactId: XmlElem = XmlElemSimple("artifactId", artifactSTr)
  val groudId: XmlElem = XmlElemSimple("groupId", groupStr)
  val version: XmlVersion = XmlVersion(versionStr)
  override def tag: String = "project"
  override def attribs: RArr[XmlAtt] = RArr()

  /** The content of this XML / HTML element. */
  override def contents: RArr[XCon] = RArr(artifactId, groudId, version)

  /** Returns the XML / HTML source code, formatted according to the input. This allows the XML to be indented according to its context. */
  override def out(indent: Int, line1Delta: Int, maxLineLen: Int): String = ???
}
