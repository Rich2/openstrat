/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
/** XML /HTML element content. Can be an XCon element with 'out' and 'outLines' methods or a [[String]]. */
type XCon = XConElem | String

extension (thisArr: RArr[XCon])
{
  def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines =
  { var res = TextLines.empty
    var i = 0
    while (i < thisArr.length) thisArr(i) match
    { case xil: XConInedit =>
      { var tls: TextLines = xil.outLines(indent, line1InputLen, maxLineLen)
        i += 1
        var cont = true
        while (i < thisArr.length && cont == true) thisArr(i) match
        { case xil2: XConInedit =>
          { tls = tls.appendInLines(xil2, indent, line1InputLen, maxLineLen)
            i += 1
          }
          case xc => cont = false
        }
        res = res.appendLines(tls, indent)
      }
      case xc =>
      { res = res.appendSibling(xc, indent, line1InputLen, maxLineLen)
        i += 1
      }
    }
    res
  }
  
  def out: String = outLines(0, 0).text
}

extension (thisXCon: XCon)
{ def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = MaxLineLen): String = outLines(indent, line1InputLen, maxLineLen).text

  def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines = thisXCon match
  { case xc: XConInedit => XConInedit.outLines(xc, indent, line1InputLen, maxLineLen)
    case xce: XConElem => xce.outLines(indent, line1InputLen, maxLineLen)    
  }

  /** True if this content should be joined to the preceding content without a separating space, because it is a String that begins with closing
   * punctuation such as a comma, semicolon or closing bracket, and that punctuation is followed by whitespace or the end of the String. So "." and
   * ", 'Admin');" attach, but a relative path such as "../index.html" does not. */
  def attachesToPrev: Boolean = thisXCon match
  { case str: String if str.nonEmpty && XCon.isAttachChar(str.head) =>
    { val rest: String = str.dropWhile(XCon.isAttachChar)
      rest.isEmpty || rest.head.isWhitespace
    }
    case _ => false
  }
}
object XCon
{ /** Closing punctuation characters that, when they begin a String content, attach that content to the preceding content without a separating space. */
  val attachChars: String = ";,.:!?)]"
  /** Is the character one of the closing punctuation characters in attachChars. */
  def isAttachChar(char: Char): Boolean = attachChars.exists(_ == char)
}