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

/** An HTML element that will be multiline such as an OL or a UL and will not be inlined like an LI list item. */
trait HtmlTagLines extends HtmlElem, XHmlTagLines
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String =
  { val childIndent = indent + 2
    val cons: TextLines = contents.outLines(childIndent, line1InputLen + openTagMinLen, maxLineLen)
    if (cons.isEmpty) openTag(indent, indent) + indent.nlSpaces + closeTag
    else openTag(indent, indent) + childIndent.nlSpaces + cons.text + indent.nlSpaces + closeTag
  }

  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines =
  { val childIndent = indent + 2
    val cons: TextLines = contents.outLines(childIndent, line1InputLen + openTagMinLen, maxLineLen)
    if (cons.isEmpty) TextLines(openTag(indent, indent) + indent.nlSpaces + closeTag)
    else{
      val oldArray: Array[String] = cons.lines
      val newArray = new Array[String](oldArray.length + 2)
      newArray(0) = openTag(indent, indent)
      newArray(1) = childIndent.spaces + oldArray(0)
      Array.copy(oldArray, 1, newArray, 2, oldArray.length - 1)
      newArray(oldArray.length + 1) = indent.spaces + closeTag
      new TextLines(newArray)
    }
  }  
}

/** An HTML element that can be inlined. */
trait HtmlInline extends HtmlElem, XHmlInline