/* Copyright 2024-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package wcode

/** Software version. */
trait SwVersion
{ /** Major version number */
  def major: Int

  def oPreStr: Option[String]

  def oPreNum: Option[Int]

  /** Standard [[String]] representation of the version number. */
  def str: String

  def endStr: String = oPreStr match
  { case None => ""
    case Some(ss) => oPreNum match
    { case None => ss
      case Some(n4) => ss + n4.str
    }
  }
}

trait VersionMinorPlus extends SwVersion
{ /** Minor version number */
  def minor: Int
}

trait VersionMinor extends VersionMinorPlus
{ override def str: String = s"$major.$minor" + endStr
}

trait SwVersionElem extends XmlInline, SwVersion
{ override def tagName: String = "version"
  override def attribs: RArr[XAtt] = RArr()
  override def contents: RArr[XCon] = RArr(str)
}

trait VersionMinorPlusElem extends SwVersionElem, VersionMinorPlus

trait VersionMinorElem extends VersionMinorPlusElem

/** Software version. */
class VersionPatchElem(val major: Int, val minor: Int, val n3: Int, val oPreStr: Option[String], val oPreNum: Option[Int]) extends VersionMinorPlusElem
{ override def str: String = s"$major.$minor.$n3" + endStr

}

object VersionPatchElem
{ /** Factory apply method to create a Software Version object. */
  def apply(n1: Int, n2: Int, n3: Int, optSpecStr: Option[String] = None, optN4: Option[Int] = None): VersionPatchElem = new VersionPatchElem(n1, n2, n3, optSpecStr, optN4)

  /** Factory method to create a snap Software Version object. */
  def snap(n1: Int, n2: Int, n3: Int): VersionPatchElem = new VersionPatchElem(n1, n2, n3, Some("snap"), None)
}

/** The XML value attribute. */
trait VersionAtt extends XAttShort, SwVersion
{ override def name: String = "version"
  override def valueStr: String = str
}

case class VersionMinorAtt(major: Int, minor: Int, oPreStr: Option[String] = None, oPreNum: Option[Int] = None) extends VersionAtt, VersionMinor