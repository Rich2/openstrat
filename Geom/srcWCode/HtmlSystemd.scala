/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package wcode

/** Creates a systemd Unit file. */
class HtmlSystemd(val lines: StrArr, otherAttribs: RArr[XAtt]) extends HtmlCodeLines
{ override def attribs: RArr[XAtt] = otherAttribs
  override def contents: RArr[HtmlDiv] = HtmlSystemd.toDivs(lines)
}

object HtmlSystemd
{ /** Factory apply method to create Systemd Unit file */
  def apply(lines: String*): HtmlSystemd = new HtmlSystemd(lines.toArr, RArr())

  /** transforms text lines into lines for Systemd Unit file. */
  def toDivs(inp: Arr[String]): RArr[HtmlDiv] = inp.map {
    case "" => HtmlDiv(HtmlBr)
    case line if line(0) == '[' => HtmlDiv.colour(Colour.LightGreen, line)
    case l => HtmlDiv(l)
  }
}