/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

case class SvgElem(content: Arr[XCon], attribs: Arr[XmlAtt]) extends XmlElem
{
  override def tag: String = "svg"
  override def out: String = openTag + content.toStrsFold("\n", _.out) + "\n" + closeTag
}

object SvgElem
