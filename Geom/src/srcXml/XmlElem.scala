/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** An XML element. */
trait XmlElem extends XHmlElem
{ override def attribs: RArr[XAtt]

  /** Outputs the attributes into XML / HTML code. If there are multiple instances of the same attribute, the same attribute name, the values are combined into
   * a single attribute. */
  override def attribsOutLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines =
  { val atts2: RArrPair[String, RArr[XAtt]] = attribs.groupBy(_.name)
    val atts3 = atts2.pairMap{ (name, attrs) => if(attrs.length == 1) attrs(0) else XAtt(name, attrs.mkStr(_.valueOut(0, 0), " ")) }

    atts3.length match
    { case 0 => TextLines()

      case n if n == 1 => {
        val str = atts3(0).out(indent + 2, line1InputLen, maxLineLen)
        val len = str.length
        TextLines(str)
      }

      case n =>
      { val lines = StringBuff()
        var currLine = ""

        def currLen = currLine.length

        atts3.iForeach{ (i, att) =>
          val newStr = att.out(indent + 2, indent, MaxLineLen)
          if(currLen == 0 || (currLen + newStr.length + indent) <= maxLineLen) currLine --= newStr
          else {
            lines.grow(currLine)
            currLine = "\n" + (indent + 2).spaces + newStr
          }
        }
        lines.grow(currLine)
        TextLines(lines.mkStr())
      }
    }
  }
}

/** An XML trait that should be inlined in the editor. It is named thus to avoid confusion with inline for the display of HTML elements. */
trait XmlInEdit extends XmlElem, XHmlInedit

/** An XML element with no attributes. */
trait XmlNoAtts extends XmlElem
{ override def attribs: RArr[XAtt] = RArr()
}

/** An XML element whose opening and closing tags should be on their own lines in the editor, but has no attributes. */
trait XmlTagLinesNoAtts extends XmlTagLines, XmlNoAtts

/** A Simple XML element consisting of opening and closing  tags and a [[String]]. */
class XmlElemSimple(val tagName: String, val str: String) extends XmlInEdit
{ override def attribs: RArr[XAtt] = RArr()
  override def contents: RArr[XCon] = RArr(str)
}

object XmlElemSimple
{ /** Factory apply method to construct a Simple XML element consisting of opening and closing  tags and a String. */
  def apply(tag: String, str: String): XmlElemSimple = new XmlElemSimple(tag, str)
}