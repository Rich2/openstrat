/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML bold element. */
case class HtmlB(str: String) extends HtmlInline
{ override def tagName: String = "b"
  override def attribs: RArr[XAtt] = RArr()
  override def contents: RArr[XCon] = RArr(str)
}

class HtmlSup(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlInline
{  override def tagName: String = "sup"
}

object HtmlSup
{ /** Factory apply method for HTML superscript element. There is an apply overload that takes the contents as a repeat parameter with no attributes. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlSup = new HtmlSup(contents, attribs)

  /** Factory apply method for HTML superscript element. There is an apply overload that takes [[RArr]]s of contents and attributes. */
  def apply(contents: XCon*): HtmlSup = new HtmlSup(contents.toRArr, RArr())

  /** Factory method for HTML superscript element, with 2nd repeat parameter list for attributes. */
  def atts(contents: XCon*)(attribs: XAtt*): HtmlSup = new HtmlSup(contents.toRArr, attribs.toRArr)

  /** Factory method for HTML superscript element with an ID attribute. There is an apply overload that takes [[RArr]]s of contents and attributes. */
  def id(idStr: String, contents: XCon*): HtmlSup = new HtmlSup(contents.toRArr, RArr(IdAtt(idStr)))
}