/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML element. */
trait XmlElem extends XHmlElem

trait XmlMulti extends XmlElem with XmlLikeMulti
trait XmlMultiNamed extends XmlMulti
{
  def nameStr: String

}

trait XmlMaybeSingle extends XmlElem with XHmlInline

trait XmlNoAtts extends XmlElem
{ override def attribs: RArr[XAtt] = RArr()
}

trait XmlMultiNoAtts extends XmlMulti with XmlNoAtts

class XmlElemSimple(val tag: String, val str: String) extends XmlMaybeSingle
{ override def attribs: RArr[XAtt] = RArr()
  override def contents: RArr[XConElem] = RArr(str.xCon)
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