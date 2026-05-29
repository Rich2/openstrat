/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** An XML element. */
trait XmlElem extends XHmlElem

trait XmlInline extends XmlElem, XHmlInedit

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
class SwVersion(val n1: Int, val n2: Int, val n3: Int, val optSpecStr: Option[String], val optN4: Option[Int]) extends VersionElem
{
  def str: String =
  { val endStr: String = optSpecStr match
    { case None => ""
      case Some(ss) => optN4 match
      { case None => ss
        case Some(n4) => ss + n4.str
      }
    }
    s"$n1.$n2.$n3" + endStr
  }
  override def contents: RArr[XCon] = RArr(str)
}

object SwVersion
{ /** Factory apply method to create a Software Version object. */
  def apply(n1: Int, n2: Int, n3: Int, optSpecStr: Option[String] = None, optN4: Option[Int] = None): SwVersion = new SwVersion(n1, n2, n3, optSpecStr, optN4)

  /** Factory method to create a snap Software Version object. */
  def snap(n1: Int, n2: Int, n3: Int): SwVersion = new SwVersion(n1, n2, n3, Some("snap"), None)
}