/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML element. */
trait HtmlElem extends XHmlElem

/** An HTML element that is not void. */
trait HtmlUnvoid extends HtmlElem
{ def openTag1(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = openTag(indent, line1InputLen, maxLineLen) + "\n"
  def openTag2(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = openTag(indent, line1InputLen, maxLineLen) + "\n\n"

  /** The full length of the opening tag without attributes. */
  def closeTagMinLen: Int = tagName.length + 3
}

/** An HTML element that can be inlined. */
trait HtmlInedit extends HtmlElem, XHmlInedit