/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML element. */
trait XmlElem extends XmlElemLike
{ //override def out(indent: Int = 0, maxLineLen: Int = 150): String = if (contents.empty) openAtts + "/>"
  //else openUnclosed.nli(indent + 2) + contents.foldStr(_.out(indent + 2, 150), "\n" + (indent + 2).spaces).nli(indent) + closeTag
}

trait XmlMulti extends XmlElem with XmlLikeMulti

trait Xml1Lineable extends XmlElem with XmlLike1Lineable

trait XmlNoAtts extends XmlElem
{ override def attribs: RArr[XmlAtt] = RArr()
}

trait XmlMultiNoAtts extends XmlMulti with XmlNoAtts

class XmlElemSimple(val tag: String, val str: String) extends Xml1Lineable
{ override def attribs: RArr[XmlAtt] = RArr()
  override def contents: RArr[XCon] = RArr(str.xCon)
}

object XmlElemSimple
{
  def apply(tag: String, str: String): XmlElemSimple = new XmlElemSimple(tag, str)
}

class VersionElem(versionStr: String) extends XmlElemSimple("version", versionStr)

object VersionElem
{
  def apply(versionStr: String): VersionElem = new VersionElem(versionStr)
}