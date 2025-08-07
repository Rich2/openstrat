/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
/** XML /HTML element content. Can be an XCon element with out and outLines methods or a [[String]]. */
type XCon = XConElem | String

extension (thisArr: RArr[XCon])
{
  def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines =
  { var res = Array[String]()
    var i = 0
    while (i < thisArr.length) thisArr(i) match
    {
      case xil: XConInline =>
      { var tls: TextLines = xil.outLines(indent, line1InputLen, maxLineLen)
        i += 1
        var cont = true
        while (i < thisArr.length && cont == true) thisArr(i) match
        { case xil2: XConInline =>
          { tls = tls.appendInLines(xil2, indent, line1InputLen, maxLineLen)
            i += 1
          }
          case xc => cont = false
        }
      }
      case xc => ???
    }
    ???
  }
}

extension (thisXCon: XCon)
{ def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = MaxLineLen): String = outLines(indent, line1InputLen, maxLineLen).text

  def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines = thisXCon match
  { case xc: XConInline => XConInline.outLines(xc, indent, line1InputLen, maxLineLen)
    case xce: XConElem => xce.outLines(indent, line1InputLen, maxLineLen)
  }
}