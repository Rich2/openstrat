/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML /Html element that may be output on a single line. */
trait XHmlInline extends XHmlElem, XConElemInline
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = outLines(indent, line1InputLen, maxLineLen).text

  def out0: String = contents match
  { case RArr0() => openAtts(0, 0) + "/>"
    case RArr1(_) => openUnclosed(0, 0, MaxLineLen) + contents(0).out(0, 0) + closeTag
    case _ => openUnclosed(0, 0, MaxLineLen).nli(2) + contents.mkStr(_.out(2, MaxLineLen), "\n" + (2).spaces).nli(0) + closeTag
  }
}