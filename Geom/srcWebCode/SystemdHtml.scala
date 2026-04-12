/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package wcode

/** Creates a systemd Unit file. */
class SystemdHtml(val lines: StrArr, otherAttribs: RArr[XAtt]) extends CodeLinesHtml
{ override def attribs: RArr[XAtt] = otherAttribs
  override def contents: RArr[DivHtml] = SystemdHtml.toDivs(lines)
}

object SystemdHtml
{ /** Factory apply method to create Systemd Unit file */
  def apply(lines: String*): SystemdHtml = new SystemdHtml(lines.toArr, RArr())

  /** transforms text lines into lines for Systemd Unit file. */
  def toDivs(inp: Arr[String]): RArr[DivHtml] = inp.map {
    case "" => DivHtml(HtmlBr)
    case line if line(0) == '[' => DivHtml.colour(Colour.LightGreen, line)
    case l => DivHtml(l)
  }
}