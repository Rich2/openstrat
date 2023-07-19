/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML element. */
trait HtmlElem extends XmlElemLike
{ thisHElem: HtmlUnvoid | HtmlVoid =>
}

/** An HTML element that is not void. */
trait HtmlUnvoid extends HtmlElem
{ //def openTag: String = openAtts + ">"
  def openTag1: String = openTag + "\n"
  def openTag2: String = openTag + "\n\n"
}

/** An HTML element that is not void, but has no content. */
trait HtmlEmpty extends HtmlUnvoid
{ override def out(indent: Int, line1Delta: Int = 0, maxLineLen: Int = 150): String = openUnclosed + closeTag
  override def contents: RArr[XCon] = RArr()
}

/** An HTML element that will be multiline such as an OL or a UL and will not be inlined like an LI list item. */
trait HtmlMultiLine extends HtmlUnvoid
{
  override def out(indent: Int, line1Delta: Int = 0, maxLineLen: Int = 150): String =
  { val cons = contents.map(_.outEither(indent + 2, maxLineLen))
    val middle = cons.foldLeft("") { (acc, el) => acc --- ife(el._1, (indent + 2).spaces, "") + el._2 } + "\n"
    openTag + middle + closeTag
  }
}

/** An HTML element that we may wish to inline such as an LI list item, as opposed to a OL or a UL, which will be multi line. */
trait HtmlInline extends HtmlUnvoid with XmlLikeInline

/** An HTML whose contents can be represented by a [[String]]. */
trait HtmlStr extends HtmlInline
{ def str: String
  override def contents: RArr[XCon] = RArr(str.xCon)
}