/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

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