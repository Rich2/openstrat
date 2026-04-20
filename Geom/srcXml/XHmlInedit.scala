/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** An XML /Html element that may be inlined in the editor. HTML elements may not be inline or inline-block for rendering. */
trait XHmlInedit extends XHmlElem, XConElemInedit
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = outLines(indent, line1InputLen, maxLineLen).text

  def outUnlined: String = contents match
  { case RArr0() => openTagInit(0, 0) + "/>"
    case RArr1(_) => openTag(0, 0, MaxLineLen) + contents(0).out(0, 0) + closeTag
    case _ => openTag(0, 0, MaxLineLen).nli(2) + contents.mkStr(_.out(2, MaxLineLen), "\n" + (2).spaces).nli(0) + closeTag
  }
}