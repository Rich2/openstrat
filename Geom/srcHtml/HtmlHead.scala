/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML head element. */
case class HtmlHead(contents : RArr[XConCompound], attribs: RArr[XAtt] = RArr()) extends HtmlTagLines, HtmlUnvoid
{ override def tagName: String = "head"
  override def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag1(indent, line1InputLen, maxLineLen) + contents.mkStr(_.out(indent + 2), "\n") + "\n" + closeTag
}

/** Companion object for the [[HtmlHead]] case class. */
object HtmlHead
{ /** Factory apply method for creating an HTML head element from repeat parameters. Generally the title and titleCss methods will be more convenient. */
  def apply(titleStr: String, otherContents: XConCompound*): HtmlHead = new HtmlHead(otherContents.toArr)

  /** Factory method for creating an HTML head element with [[HtmlTitle]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter elements. */
  def title(titleStr: String, otherContents: XConCompound*): HtmlHead =
  { val seq = List(HtmlTitle(titleStr), HtmlUtf8, HtmlViewDevWidth) ++ otherContents
    new HtmlHead(seq.toArr)
  }

  /** Factory method for creating an HTML head element with [[HtmlTitle]], [[HtmlCssLink]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter
   *  elements. */
  def titleCss(titleStr: String, cssFileStem: String): HtmlHead =
    new HtmlHead(RArr[XConCompound](HtmlTitle(titleStr), HtmlCssLink(cssFileStem), HtmlUtf8, HtmlViewDevWidth))
}

/** HTML title element. */
case class HtmlTitle(str: String, attribs: RArr[XAtt] = RArr()) extends HtmlOwnLine
{ override def tagName = "title"
  override def contents: RArr[XCon] = RArr(str)  
}

/** HTML link element. */
trait HtmlLink extends HtmlVoid
{ override def tagName: String = "link"
}

/** HTML CSS link. */
class HtmlCssLink(val fullFileName: String) extends HtmlLink
{ 
  override def attribs: RArr[XAtt] = RArr(XAtt("rel", "stylesheet"), TypeCssAtt, XAtt("href", fullFileName))
}

/** Companion object for [[HtmlCssLink]] class, contains factory apply method. */
object HtmlCssLink
{ /** Factory apply method for [[HtmlCssLink]] class from filename stem, adds the .css file ending. */
  def apply(fileNameStem: String): HtmlCssLink = new HtmlCssLink(fileNameStem + ".css")
}

object FaviconSvgLink extends HtmlLink
{
  override def attribs: RArr[XAtt] = RArr(XAtt("rel", "icon"), TypeSvgAtt, XAtt("href", "/favicon.svg"))
}