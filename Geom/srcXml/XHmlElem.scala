/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML or an HTML element. */
trait XHmlElem extends XConElem
{ /** The XML /HTML tag String. A tag is a markup construct that begins with < and ends with > */
  def tag: String
  
  /** The number of characters in the tag. This is useful for calculating new lines in multi line elements. */
  def tagLen: Int = tag.length
  
  /** The length of the < character plus the tag. */
  def openTagOpenLen: Int = tagLen + 1

  /** The attributes of this XML / HTML element. */
  def attribs: RArr[XAtt]

  def attribsLen = attribs.length

  /** The content of this XML / HTML element. */
  def contents: RArr[XCon]

  def attribsOutLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines = attribs.length match{
    case 0 => TextLines(Array(), 0, 0, 0)

    case n if n == 1 || (attribs.sumBy(_.outLen) + n) < 75 =>
    { val str = attribs.mkStr(_.out, " ")
      val len = str.length
      TextLines(Array(str), 1, len, len)
    }

    case n =>
    { val lines = StringBuff()
      var currLine = ""
      def currLen = currLine.length
      attribs.iForeach{ (i, att) =>
        val newStr = att.out
        if (currLen == 0 || (currLen + newStr.length) <= maxLineLen) currLine --= newStr
        else
        { lines.grow(currLine)
          currLine = "\n" + (indent + 2).spaces + newStr
        }
      }
      lines.grow(currLine)
      TextLines(Array(lines.mkStr()), lines.length, lines(0).length, lines.last.length)
    }
  }

  def openAtts(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String =
  { val res: TextLines = attribsOutLines(indent, openTagOpenLen)
    val text: String = ife(res.numLines == 0, "", " " + res.text)
    "<" + tag + text
  }

  def openUnclosed(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = openAtts(indent, line1InputLen, maxLineLen) + ">"
  def openTag(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = openAtts(indent, line1InputLen, maxLineLen) + ">"
  def closeTag: String = "</" + tag + ">"
  def n1CloseTag: String = "\n" + closeTag
  def n2CloseTag: String = "\n\n" + closeTag
}

trait XmlLikeMulti extends XHmlElem
{
  override def out(indent: Int = 0, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String =
    if (contents.empty) openAtts(indent, line1InputLen, maxLineLen) + "/>"
    else openUnclosed(indent, line1InputLen, maxLineLen).nli(indent + 2) + contents.mkStr(_.out(indent + 2, line1InputLen, 160), "\n" + (indent + 2).spaces).nli(indent) + closeTag
}

/** An XML /Html element that may be output on a single line. */
trait XHmlInline extends XHmlElem
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = outLines(indent, line1InputLen, maxLineLen).text
  
  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines = contents match
  { case RArr0() =>
    { val str = openAtts(indent, 0) + "/>"
      TextLines(Array(str), 1, str.length, str.length)
    }
    case RArr1(_) =>
    { val str = openUnclosed(indent, line1InputLen, maxLineLen) + contents(0).out(0, 0, MaxLineLen) + closeTag
      TextLines(Array(str), 1, str.length, str.length)
    }
    case _ =>
    { val str = openUnclosed(indent, line1InputLen, maxLineLen).nli(indent + 2) + contents.mkStr(_.out(indent + 2, MaxLineLen),
        "\n" + (indent + 2).spaces).nli(indent) + closeTag
      TextLines(Array(str), 1, str.length, str.length)
    }
  }
}