/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package wcode

class HtmlSystemd(val lines: StrArr, otherAttribs: RArr[XAtt]) extends HtmlCodeLines
{
  override def attribs: RArr[XAtt] = otherAttribs

  override def contents: RArr[XCon] = lines.map{
    case "" => SpanLine("<br>")
    case line if line(0) == '[' => {deb("Span"); SpanLine.attribs(line)(StyleAtt("color:lightgreen")) }
    case l => SpanLine(l)
  }
}

object HtmlSystemd
{
  def apply(lines: String*): HtmlSystemd = new HtmlSystemd(lines.toArr, RArr())
}