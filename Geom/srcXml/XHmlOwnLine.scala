/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** XML / HTML Element that can be defined on a single line but can not share its line / lines with other content. */
trait XHmlOwnLine extends XHmlElem
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = outLines(indent, line1InputLen, maxLineLen).text

  override def outLines(indent: Int = 0, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines =
  { val cons: RArr[TextLines] = contents.map(_.outLines(indent + 2, indent + 2, maxLineLen))
    val childIndent = indent + 2
    val stt: String = openTag(indent, line1InputLen, maxLineLen)
    val totalNum: Int = cons.sumBy(_.numLines)
    cons.length match
    {
      case 0 =>
      { val text = stt + closeTag
        TextLines(text, 1, text.length, text.length)
      }
      case 1 if cons.head.numLines == 1 =>{
        val text = stt + cons.head.text + closeTag
        TextLines(text, 1, text.length, text.length)
      }
      case n if cons.forAll(_.numLines <= 1) && cons.sumBy(_.firstLen) < 100 =>{
        val text = stt + cons.mkStr(_.text, " ") + closeTag
        TextLines(text, 1, text.length, text.length)
      }
      case n if totalNum < 3 =>{
        val text = stt + cons.tail.foldLeft(cons.head.text){ (acc, el) => acc --- childIndent.spaces + el.text } + closeTag
        TextLines(text, totalNum, line1InputLen + cons.head.firstLen, indent + 2 + cons.last.lastLen + closeTag.length)
      }
      case n =>{
        val lastLine = indent.spaces + closeTag
        val text = stt + cons.tail.foldLeft(cons.head.text){ (acc, el) => acc --- childIndent.spaces + el.text } + "\n" + lastLine
        TextLines(text, totalNum + 1, line1InputLen + cons.head.firstLen, lastLine.length)
      }
    }
  }
}