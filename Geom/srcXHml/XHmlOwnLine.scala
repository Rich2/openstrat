/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** XML / HTML Element that can be defined on a single line but can not share its line / lines with other content. */
trait XHmlOwnLine extends XHmlElem
{
  def closeTagLine: Boolean = false

  final override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = outLines(indent, line1InputLen, maxLineLen).text

  override def outLines(indent: Int = 0, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines =
  { val cons: TextLines = contents.outLines(indent + 2, indent + tagLen + 2, maxLineLen)
    val childIndent = indent + 2
    val stt: String = openTag(indent, line1InputLen, maxLineLen)
    def closeTag2: String = ife(closeTagLine, indent.nlSpaces + closeTag, closeTag)
    cons.numLines match
    { case 0 =>
      { val text = stt + closeTag2
        TextLines(text)
      }
      case 1 =>
      { val text = stt + cons.text + closeTag2
        TextLines(text)
      }
      case n if contents.forAll(_.isInstanceOf[XConInline]) && cons.numLines < 4 =>
      { val text = stt + cons.text + closeTag2
        TextLines(text)
      }
      case n if contents(0).isInstanceOf[XConInline] =>
      { val text = stt + cons.text + indent.nlSpaces + closeTag
        TextLines(text)
      }
      case n =>
      { val text = stt + childIndent.nlSpaces + cons.text + indent.nlSpaces + closeTag
        TextLines(text)
      }
    }
  }
}

/** An XML element that can not share lines with sibling content. */
trait XmlOwnLine extends XmlElem, XHmlOwnLine