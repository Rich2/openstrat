/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML element. */
trait HtmlElem extends XmlElemLike
{ thisHElem: HtmlUnvoid | HtmlVoid =>
  
  /** The full length of the opening tag without attributes. */
  def openTagMinLen: Int = tag.length + 2

}

/** An HTML element that is not void. */
trait HtmlUnvoid extends HtmlElem
{ def openTag1(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): String = openTag(indent, line1InputLen, maxLineLen) + "\n"
  def openTag2(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): String = openTag(indent, line1InputLen, maxLineLen) + "\n\n"

  /** The full length of the opening tag without attributes. */
  def closeTagMinLen: Int = tag.length + 3
}

/** An HTML element that is not void, but has no content. */
trait HtmlEmpty extends HtmlUnvoid
{ override def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = 160): String = openUnclosed(indent, line1InputLen, maxLineLen) + closeTag
  override def contents: RArr[XCon] = RArr()
}

/** An HTML element that will be multiline such as an OL or a UL and will not be inlined like an LI list item. */
trait HtmlMultiLine extends HtmlUnvoid
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = 160): String =
  { val childIndent = indent + 2
    val cons: StrArr = contents.map(_.outLines(childIndent, line1InputLen + openTagMinLen, maxLineLen).text)
    val cons2: String = cons.foldLeft("")(_ --- childIndent.spaces + _)
    openTag(indent, indent) + cons2 --- indent.spaces + closeTag
  }
}

/** An HTML element that we may wish to inline such as an LI list item, as opposed to a OL or a UL, which will be multi line. */
trait HtmlInline extends HtmlUnvoid, XmlConInline

/** An HTML whose contents can be represented by a [[String]]. */
trait HtmlStr extends HtmlInline
{ def str: String
  override def contents: RArr[XCon] = RArr(str.xCon)
}