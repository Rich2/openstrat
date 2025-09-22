/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML or an HTML element. Multiple instances of an attribute are allowed. If there are multiple instances of the same attribute, the same attribute name,
 * then the values are combined into a single attribute, when outputting into XML / HTML code.*/
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

  /** Outputs the attributes into XML / HTML code. If there are multiple instances of the same attribute, the same attribute name, the values are combined into
   * a single attribute. */
  def attribsOutLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines =
  {
    val atts2: RPairArr[String, RArr[XAtt]] = attribs.groupBy(_.name)
    val atts3 = atts2.pairMap {(name, attrs) => if (attrs.length == 1) attrs(0) else XAtt(name, attrs.mkStr(_.valueOut(0, 0), " ")) }

    atts3.length match
    { case 0 => TextLines()

      case n if n == 1 =>
      { val str = atts3(0).out(indent + 2, line1InputLen, maxLineLen)
        val len = str.length
        TextLines(str)
      }

      case n =>
      { val lines = StringBuff()
        var currLine = ""
        def currLen = currLine.length
        atts3.iForeach{ (i, att) =>
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
  }

  /** The opening tag without its final ">" or "/>" characters. */
  def openTagInit(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String =
  { val res: TextLines = attribsOutLines(indent, openTagOpenLen)
    val text: String = ife(res.numLines == 0, "", " " + res.text)
    "<" + tag + text
  }

  def openUnclosed(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = openTagInit(indent, line1InputLen, maxLineLen) + ">"
  def openTag(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = openTagInit(indent, line1InputLen, maxLineLen) + ">"
  def closeTag: String = "</" + tag + ">"
  def n1CloseTag: String = "\n" + closeTag
  def n2CloseTag: String = "\n\n" + closeTag
}

/** An XML or HTML element where the opening and closing tags will always appear on their own lines in the XML / HTML code. */
trait XHmlTagLines extends XHmlElem