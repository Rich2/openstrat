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

  /** The full length of the opening tag without attributes. */
  def openTagMinLen: Int = tag.length + 2

  /** The attributes of this XML / HTML element. */
  def attribs: RArr[XAtt]

  def attribsLen = attribs.length

  /** The content of this XML / HTML element. */
  def contents: RArr[XCon]

  def attribsOutLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines = attribs.length match
  { case 0 => TextLines()

    case n if n == 1 =>
    { val str = attribs(0).out(indent + 2, line1InputLen, maxLineLen)
      val len = str.length
      TextLines(str)
    }

    case n =>
    { val lines = StringBuff()
      var currLine = ""
      def currLen = currLine.length
      attribs.iForeach{ (i, att) =>
        val newStr = att.out(indent + 2, indent, MaxLineLen)
        if (currLen == 0 || (currLen + newStr.length + indent) <= maxLineLen) currLine --= newStr
        else
        { lines.grow(currLine)
          currLine = "\n" + (indent + 2).spaces + newStr
        }
      }
      lines.grow(currLine)
      TextLines(lines.mkStr())
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

trait XHmlMulti extends XHmlElem