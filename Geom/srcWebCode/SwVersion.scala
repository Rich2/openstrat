/* Copyright 2024-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package wcode

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
trait SwVersion extends VersionElem
{ /** Major version number */
  def major: Int

  def oPreStr: Option[String]

  def oPreNum: Option[Int]

  /** STandard [[String]] representation of the version number. */
  def str: String
}

trait VersionMinorPlus extends SwVersion
{ /** Minor version number */
  def minor: Int
}

/** Software version. */
class VersionPatch(val major: Int, val minor: Int, val n3: Int, val oPreStr: Option[String], val oPreNum: Option[Int]) extends VersionMinorPlus
{
  def str: String =
  { val endStr: String = oPreStr match
  { case None => ""
    case Some(ss) => oPreNum match
    { case None => ss
      case Some(n4) => ss + n4.str
    }
  }
    s"$major.$minor.$n3" + endStr
  }
  override def contents: RArr[XCon] = RArr(str)
}

object VersionPatch
{ /** Factory apply method to create a Software Version object. */
  def apply(n1: Int, n2: Int, n3: Int, optSpecStr: Option[String] = None, optN4: Option[Int] = None): VersionPatch = new VersionPatch(n1, n2, n3, optSpecStr, optN4)

  /** Factory method to create a snap Software Version object. */
  def snap(n1: Int, n2: Int, n3: Int): VersionPatch = new VersionPatch(n1, n2, n3, Some("snap"), None)
}

/** The XML value attribute. */
case class VersionAtt(valueStr: String) extends XAttShort{
  override def name: String = "version"
}