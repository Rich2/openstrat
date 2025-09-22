/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML element where the opening and closing tags will always appear on their own lines, separate from their content, in the XML code. */
trait XmlTagLines extends XmlElem, XHmlTagLines
{
  override def out(indent: Int = 0, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String =
    if (contents.empty) openTagInit(indent, line1InputLen, maxLineLen) + "/>"
    else openUnclosed(indent, line1InputLen, maxLineLen).nli(indent + 2) + contents.mkStr(_.out(indent + 2, line1InputLen, 160), "\n" +
      (indent + 2).spaces).nli(indent) + closeTag
}