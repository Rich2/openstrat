/* Copyright 2024-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package wcode

/** Software version. Final class Can be XML element or attribute. */
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

/** Software version with at least a minor version number. Final class Can be XML element or attribute. */
trait VersionMinorPlus extends SwVersion
{ /** Minor version number */
  def minor: Int
}

/** Software version code with a minor point number but no patch number. Final class Can be XML element or attribute. */
trait VersionMinor extends VersionMinorPlus
{ override def str: String = s"$major.$minor" + endStr
}

/** Software version code XML element. */
trait SwVersionElem extends XmlInEdit, SwVersion
{ override def tagName: String = "version"
  override def attribs: RArr[XAtt] = RArr()
  override def contents: RArr[XCon] = RArr(str)
}

/** Software version code XML element, with at least a minor point number. */
trait VersionMinorPlusElem extends SwVersionElem, VersionMinorPlus

/** Software version code XML element, with minor point number but no patch number. */
class VersionMinorElem(val major: Int, val minor: Int, val oPreStr: Option[String], val oPreNum: Option[Int]) extends VersionMinorPlusElem
{ override def str: String = s"$major.$minor" + endStr
}

object VersionMinorElem
{ /** Factory apply method to create a Software Version XML element with a minor point number but no patch number. */
  def apply(n1: Int, n2: Int, optSpecStr: Option[String] = None, optN4: Option[Int] = None): VersionMinorElem = new VersionMinorElem(n1, n2, optSpecStr, optN4)
}

/** Software version XML element with a patchc number.. */
class VersionPatchElem(val major: Int, val minor: Int, val n3: Int, val oPreStr: Option[String], val oPreNum: Option[Int]) extends VersionMinorPlusElem
{ override def str: String = s"$major.$minor.$n3" + endStr
}

object VersionPatchElem
{ /** Factory apply method to create a Software Version XML element with a patch number. */
  def apply(n1: Int, n2: Int, n3: Int, optSpecStr: Option[String] = None, optN4: Option[Int] = None): VersionPatchElem = new VersionPatchElem(n1, n2, n3, optSpecStr, optN4)

  /** Factory method to create a snap Software Version XML element. */
  def snap(n1: Int, n2: Int, n3: Int): VersionPatchElem = new VersionPatchElem(n1, n2, n3, Some("snap"), None)
}

/** The XML value attribute. */
trait VersionAtt extends XAttShort, SwVersion
{ override def name: String = "version"
  override def valueStr: String = str
}

/** Software version code attribute, with a minor point number but no patch number. */
case class VersionMinorAtt(major: Int, minor: Int, oPreStr: Option[String] = None, oPreNum: Option[Int] = None) extends VersionAtt, VersionMinor