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

trait VersionElem extends XmlInline
{ override def tagName: String = "version"
  override def attribs: RArr[XAtt] = RArr()
}

object VersionElem
{
  def apply(versionStr: String): VersionElem = VersionElemGen(versionStr)

  case class VersionElemGen(versionStr: String) extends VersionElem
  { override def contents: RArr[XCon] = RArr(versionStr)
  }
}

/** Software version. */
class SwVersion(val n1: Int, val n2: Int, val n3: Int, val specStr: Option[String], val nn4: Option[Int]) extends VersionElem
{
  override def contents: RArr[XCon] =
  { val endStr: String = specStr match
    { case None => ""
      case Some(ss) => nn4 match
      { case None => ss
        case Some(n4) => ss + n4.str
      }
    }
    RArr(s"$n1.$n2.$n3" + endStr)
  }
}