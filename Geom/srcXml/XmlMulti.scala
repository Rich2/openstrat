/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait XmlMulti extends XmlElem with XHmlMulti
{
  override def out(indent: Int = 0, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String =
    if (contents.empty) openAtts(indent, line1InputLen, maxLineLen) + "/>"
    else openUnclosed(indent, line1InputLen, maxLineLen).nli(indent + 2) + contents.mkStr(_.out(indent + 2, line1InputLen, 160), "\n" + (indent + 2).spaces).nli(indent) + closeTag
}

trait XmlMultiNamed extends XmlMulti
{
  def nameStr: String
}