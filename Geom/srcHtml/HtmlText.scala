/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
{
  def apply(contents: RArr[XCon], attribs: RArr[XAtt]): HtmlSup = new HtmlSup(contents, attribs)

  def apply(contents: XCon*): HtmlSup = new HtmlSup(contents.toRArr, RArr())
}