/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** XML / HTML just stored as a [[String]]. This is not desirable, except as a temporary expedient. */
case class XmlAsString(value: String) extends XCon
{ override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): String = value
}

/** An XML or an HTML element. */
trait XmlElemLike extends XCon
{ /** The XML /HTML tag String. A tag is a markup construct that begins with < and ends with > */
  def tag: String
  
  /** The number of characters in the tag. This is useful for calculating new lines in multi line elements. */
  def tagLen: Int = tag.length
  
  /** The length of the < character plus the tag. */
  def openTagOpenLen: Int = tagLen + 1

  /** The attributes of this XML / HTML element. */
  def attribs: RArr[XmlAtt]

  def attribsLen = attribs.length

  /** The content of this XML / HTML element. */
  def contents: RArr[XCon]

  def attribsOutLines(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): TextLines = attribs.length match{
    case 0 => TextLines("", 0, 0, 0)

    case n if n == 1 || (attribs.sumBy(_.outLen) + n) < 75 =>
    { val str = attribs.mkStr(_.out, " ")
      val len = str.length
      TextLines(str, 1, len, len)
    }

    case n =>
    { val lines = StringBuff()
      var currLine = ""
      def currLen = currLine.length
      attribs.iForeach{ (i, att) =>
        val newStr = att.out
        if (currLen == 0 || (currLen + newStr.length) <= 60) currLine --= newStr
        else
        { lines.grow(currLine)
          currLine = (indent + 2).spaces + newStr
        }
      }
      lines.grow(currLine)
      TextLines(lines.mkStr("\n"), lines.length, lines(0).length, lines.last.length)
    }
  }

  def openAtts(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): String =
  { val res: TextLines = attribsOutLines(indent, openTagOpenLen)
    val text: String = ife(res.numLines == 0, "", " " + res.text)
    "<" + tag + text
  }

  def openUnclosed(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): String = openAtts(indent, line1InputLen, maxLineLen) + ">"
  def openTag(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): String = openAtts(indent, line1InputLen, maxLineLen) + ">"
  def closeTag: String = "</" + tag + ">"
  def n1CloseTag: String = "\n" + closeTag
  def n2CloseTag: String = "\n\n" + closeTag
}

trait XmlLikeMulti extends XmlElemLike
{
  override def out(indent: Int = 0, line1InputLen: Int, maxLineLen: Int = lineLenDefault): String =
    if (contents.empty) openAtts(indent, line1InputLen, maxLineLen) + "/>"
    else openUnclosed(indent, line1InputLen, maxLineLen).nli(indent + 2) + contents.mkStr(_.out(indent + 2, line1InputLen, 160), "\n" + (indent + 2).spaces).nli(indent) + closeTag
}

/** An XML like element that may be output on a single line. */
trait XmlLikeMaybeSingle extends XmlElemLike
{
  override def out(indent: Int = 0, line1InputLen: Int, maxLineLen: Int = lineLenDefault): String = contents match
  { case RArr0() => openAtts(indent, 0) + "/>"
    case RArr1(_) => openUnclosed(indent, line1InputLen, maxLineLen) + contents(0).out(0, 150) + closeTag
    case _ => openUnclosed(indent, line1InputLen, maxLineLen).nli(indent + 2) + contents.mkStr(_.out(indent + 2, 150), "\n" + (indent + 2).spaces).nli(indent) + closeTag
  }
}

trait XmlConInline extends XmlElemLike
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): String = outLines(indent, line1InputLen, maxLineLen).text

  override def outLines(indent: Int = 0, line1InputLen: Int, maxLineLen: Int = lineLenDefault): TextLines =
  { val cons: RArr[TextLines] = contents.map(_.outLines(indent + 2, indent + 2, maxLineLen))
    val childIndent = indent + 2
    val stt: String = openTag(indent, line1InputLen, maxLineLen)
    val totalNum: Int = cons.sumBy(_.numLines)
    cons.length match
    { 
      case 0 => {
        val text = stt + closeTag
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