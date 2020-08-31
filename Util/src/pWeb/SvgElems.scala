/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

case class SvgSvgElem(content: Arr[XCon], attribs: Arr[XmlAtt]) extends XmlElem
{
  override def tag: String = "svg"
  override def out(indent: Int, linePosn: Int, lineLen: Int): String =
    openUnclosed.nl(indent + 2) + content.toStrsFold("\n", _.out(indent + 2, 0, 150)).nl(indent) + closeTag
}

object SvgSvgElem
{
  def apply(width: Double, height: Double, contents: XCon*): SvgSvgElem = new SvgSvgElem(contents.toArr, Arr(WidthAtt(width), HeighAtt(height)))
}


