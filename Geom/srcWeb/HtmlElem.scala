/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML element. */
trait HtmlElem extends XHmlElem
{ thisHElem: HtmlUnvoid | HtmlVoid =>
  
  /** The full length of the opening tag without attributes. */
  def openTagMinLen: Int = tag.length + 2
}

/** An HTML element that is not void. */
trait HtmlUnvoid extends HtmlElem
{ def openTag1(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = openTag(indent, line1InputLen, maxLineLen) + "\n"
  def openTag2(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = openTag(indent, line1InputLen, maxLineLen) + "\n\n"

  /** The full length of the opening tag without attributes. */
  def closeTagMinLen: Int = tag.length + 3
}

/** An HTML element that will be multiline such as an OL or a UL and will not be inlined like an LI list item. */
trait HtmlMultiLine extends HtmlUnvoid
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String =
  { val childIndent = indent + 2
    val cons = contents.outLines(childIndent, line1InputLen + openTagMinLen, maxLineLen).text
    openTag(indent, indent) + childIndent.nlSpaces + cons + indent.nlSpaces + closeTag
  }
}

/** An HTML element that we may wish to inline such as an LI list item, as opposed to a OL or a UL, which will be multi line. */
trait HtmlOwnLine extends HtmlUnvoid, XHmlOwnLine

/** An HTML whose contents can be represented by a [[String]]. */
trait HtmlStrOWnLine extends HtmlOwnLine
{ def str: String
  override def contents: RArr[XCon] = RArr(str)
}

/** An HTML element that can be inlined. */
trait HtmlInline extends XHmlInline