/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** trait for HTML Void elements such as br img and input. */
sealed trait HtmlVoid extends HtmlElem
{ final override def contents: Arr[XCon] = Arr()
}

trait HtmlMeta extends HtmlVoid
{
  override def tag: String = "meta"
}

object HtmlUtf8 extends HtmlMeta
{
  val utf8Attrib = XmlAtt("charset", "UTF-8")
  override def attribs: Arr[XmlAtt] = Arr(utf8Attrib)

  /** Returns the XML source code, formatted according to the input. */
  override def out(indent: Int, linePosn: Int, lineLen: Int): String = indent.spaces + openUnclosed
}