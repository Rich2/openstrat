/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML head element. */
case class HeadHtml(contents : RArr[XConCompound], attribs: RArr[XAtt] = RArr()) extends HtmlTagLines, HtmlUnvoid
{ override def tagName: String = "head"
  override def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag1(indent, line1InputLen, maxLineLen) + contents.mkStr(_.out(indent + 2), "\n") + "\n" + closeTag
}

/** Companion object for the [[HeadHtml]] case class. */
object HeadHtml
{ /** Factory apply method for creating an HTML head element from repeat parameters. Generally the title and titleCss methods will be more convenient. */
  def apply(titleStr: String, otherContents: XConCompound*): HeadHtml = new HeadHtml(TitleHtml(titleStr) %: otherContents.toRArr)

  /** Factory method for creating an HTML head element with [[TitleHtml]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter elements. */
  def title(titleStr: String, otherContents: XConCompound*): HeadHtml =
  { val seq = List(TitleHtml(titleStr), HtmlUtf8, HtmlViewDevWidth) ++ otherContents
    new HeadHtml(seq.toArr)
  }

  /** Factory method for creating an HTML head element with [[TitleHtml]], [[CssLink]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter
   *  elements. */
  def titleCss(titleStr: String, cssFileStem: String, otherContents: XConCompound*): HeadHtml =
    new HeadHtml(RArr[XConCompound](TitleHtml(titleStr), CssLink(cssFileStem), HtmlUtf8, HtmlViewDevWidth) ++ otherContents)

  /** Factory method for creating an HTML head element with [[TitleHtml]], [[FaviconSvgLink]], [[CssLink]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the
   * repeat parameter elements. */
  def titleFavCss(titleStr: String, cssFileStem: String, otherContents: XConCompound*): HeadHtml =
    new HeadHtml(RArr[XConCompound](TitleHtml(titleStr), FaviconSvgLink, CssLink(cssFileStem), HtmlUtf8, HtmlViewDevWidth) ++ otherContents)
}

/** HTML title element. */
case class TitleHtml(str: String, attribs: RArr[XAtt] = RArr()) extends HtmlOwnLine
{ override def tagName = "title"
  override def contents: RArr[XCon] = RArr(str)  
}

/** HTML link element. */
trait LinkHtml extends HtmlVoid
{ override def tagName: String = "link"
}

/** HTML CSS link. */
class CssLink(val fullFileName: String) extends LinkHtml
{ override def attribs: RArr[XAtt] = RArr(RelStylesheet, TypeCssAtt, HrefAtt(fullFileName))
}

/** Companion object for [[CssLink]] class, contains factory apply method. */
object CssLink
{ /** Factory apply method for [[CssLink]] class from filename stem, adds the .css file ending. */
  def apply(fileNameStem: String): CssLink = new CssLink(fileNameStem + ".css")
}

/** SVG favicon link and icon attributes. */
object FaviconSvgLink extends LinkHtml
{ override def attribs: RArr[XAtt] = RArr(RelAtt("icon"), TypeSvgAtt, FaviconSvgHref)
}