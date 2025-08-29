/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** XML / HTML Element that can be defined on a single line but can not share its line / lines with other content. */
trait XHmlOwnLine extends XHmlElem
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = outLines(indent, line1InputLen, maxLineLen).text

  override def outLines(indent: Int = 0, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines =
  { val cons: TextLines = contents.outLines(indent + 2, indent + 2, maxLineLen)
    val childIndent = indent + 2
    val stt: String = openTag(indent, line1InputLen, maxLineLen)
    cons.numLines match
    { case 0 =>
      { val text = stt + closeTag
        TextLines(Array(text))
      }
      case 1 =>
      { val text = stt + cons.text + closeTag
        TextLines(Array(text))
      }
      case n if contents.forAll(_.isInstanceOf[XConInline]) =>
      { val text = stt + cons.text + closeTag
        TextLines(Array(text))
      }
      case n =>
      { val lastLine = indent.spaces + closeTag
        val text = stt + childIndent.nlSpaces + cons.text + "\n" + lastLine
        TextLines(Array(text))
      }
    }
  }
}

/** An XML element that can not share lines with sibling content. */
trait XmlOwnLine extends XmlElem, XHmlOwnLine