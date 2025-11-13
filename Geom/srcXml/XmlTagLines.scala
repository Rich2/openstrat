/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML element where the opening and closing tags will always appear on their own lines, separate from their content, in the XML code. */
trait XmlTagLines extends XmlElem, XHmlTagLines
{
  override def out(indent: Int = 0, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String =
    if (contents.empty) openTagInit(indent, line1InputLen, maxLineLen) + "/>"
    else openTag(indent, line1InputLen, maxLineLen).nli(indent + 2) + contents.mkStr(_.out(indent + 2, line1InputLen, 160), "\n" +
      (indent + 2).spaces).nli(indent) + closeTag

  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines =
  { val childIndent = indent + 2
    val cons: TextLines = contents.outLines(childIndent, line1InputLen + openTagMinLen, maxLineLen)
    if (cons.isEmpty) TextLines(openTag(indent, indent) + indent.nlSpaces + closeTag)
    else {
      val oldArray: Array[String] = cons.lines
      val newArray = new Array[String](oldArray.length + 2)
      newArray(0) = openTag(indent, indent)
      newArray(1) = childIndent.nlSpaces + oldArray(0)
      Array.copy(oldArray, 1, newArray, 2, oldArray.length - 1)
      newArray(oldArray.length + 1) = indent.nlSpaces + closeTag
      new TextLines(newArray)
    }
  }
}