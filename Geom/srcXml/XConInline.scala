/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

type XConInline = XConElemInline | String

object XConInline
{
  def outLines(target: XConInline, indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen) =
    TextLines.empty.appendInLines(target, indent, line1InputLen, maxLineLen)
}