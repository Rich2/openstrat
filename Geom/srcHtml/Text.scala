/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML bold element. */
case class BHtml(str: String) extends HtmlInedit
{ override def tagName: String = "b"
  override def attribs: RArr[XAtt] = RArr()
  override def contents: RArr[XCon] = RArr(str)
}

/** HTML superscript. */
class SupHtml(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlInedit
{  override def tagName: String = "sup"
}

object SupHtml
{ /** Factory apply method for HTML superscript element. There is an apply overload that takes the contents as a repeat parameter with no attributes. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): SupHtml = new SupHtml(contents, attribs)

  /** Factory apply method for HTML superscript element. There is an apply overload that takes [[RArr]]s of contents and attributes. */
  def apply(contents: XCon*): SupHtml = new SupHtml(contents.toRArr, RArr())

  /** Factory method for HTML superscript element, with 2nd repeat parameter list for attributes. */
  def atts(contents: XCon*)(attribs: XAtt*): SupHtml = new SupHtml(contents.toRArr, attribs.toRArr)

  /** Factory method for HTML superscript element with an ID attribute. There is an apply overload that takes [[RArr]]s of contents and attributes. */
  def id(idStr: String, contents: XCon*): SupHtml = new SupHtml(contents.toRArr, RArr(IdAtt(idStr)))
}