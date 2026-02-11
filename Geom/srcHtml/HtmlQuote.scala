/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Citation attribute for XML / HTML. */
case class CiteAtt(valueStr: String) extends XAttSimple
{ override def name: String = "cite"
}

/** HTML blockquote element. */
class HtmlBlockQuote(val citeStr: String, quoteStr: String) extends HtmlTagLines
{ override def tagName: String = "blockquote"
  override def attribs: RArr[XAtt] = RArr(CiteAtt(citeStr))
  override def contents: RArr[XCon] = RArr(quoteStr)
}

object HtmlBlockQuote
{ /** Factory apply method for HTML blockquote element. */
  def apply(citeStr: String, quoteStr: String): HtmlBlockQuote = new HtmlBlockQuote(citeStr, quoteStr)
}

/** HTML short quote element. */
case class HtmlQ(valueStr: String, citeStr: String = "") extends HtmlInline
{ override def tagName: String = "q"
  override def attribs: RArr[XAtt] = ife(citeStr == "", RArr(), RArr(CiteAtt(citeStr)))
  override def contents: RArr[XCon] = RArr(valueStr)
}