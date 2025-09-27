/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package wcode

/** Creates a systemd Unit file. */
class HtmlSystemd(val lines: StrArr, otherAttribs: RArr[XAtt]) extends HtmlCodeLines
{ override def attribs: RArr[XAtt] = otherAttribs
  override def contents: RArr[XCon] = HtmlSystemd.toSpans(lines)
}

object HtmlSystemd
{ /** Factory apply method to create Systemd Unit file */
  def apply(lines: String*): HtmlSystemd = new HtmlSystemd(lines.toArr, RArr())
  
  /** transoforms text lines into lines for Systemd Unit file. */
  def toSpans(inp: Arr[String]): RArr[HtmlSpan] = inp.map{
    case "" => SpanLine("<br>")
    case line if line(0) == '[' => SpanLine.display(line)(DecColour(Colour.LightGreen))
    case l => SpanLine(l)
  }
}