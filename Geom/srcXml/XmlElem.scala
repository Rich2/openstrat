/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML element. */
trait XmlElem extends XHmlElem

trait XmlInline extends XmlElem, XHmlInline

trait XmlNoAtts extends XmlElem
{ override def attribs: RArr[XAtt] = RArr()
}

trait XmlTagLinesNoAtts extends XmlTagLines, XmlNoAtts

class XmlElemSimple(val tagName: String, val str: String) extends XmlInline
{ override def attribs: RArr[XAtt] = RArr()
  override def contents: RArr[XCon] = RArr(str)
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