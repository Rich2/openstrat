/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** I'm just trying out a new package, not sure whether you will use pWeb. */
package object pWeb
{ /** The max line lenght for code is set at 160 characters. */
  inline val MaxLineLen: 160 = 160

  /** XML /HTML element content. Can be an XCon element with out and outLines methods or a [[String]]. */
  type XCon = XConElem | String

  extension (thisXCon: XCon)
  { def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = MaxLineLen): String = outLines(indent, line1InputLen, maxLineLen).text

    def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines = thisXCon match
    { case xce: XConElem => xce.outLines(indent, line1InputLen, maxLineLen)

      case value: String =>
      { val chars: Array[Char] = value.toCharArray
        var i: Int = 0
        var lines: Array[String] = Array ()
        var currLine: String = ""
        var currWord: String = ""

        /** This takes account of if it is the first line, unknown characters will precede this method's first line. */
        def trueLength: Int = ife (lines.length == 0, currLine.length + line1InputLen, currLine.length)

        while (i < chars.length)
        { chars (i) match
          { case c if c.isWhitespace && currWord == "" =>
            case c if c.isWhitespace && currLine == "" =>
            { currLine = ife (lines.length == 0, currWord, indent.spaces + currWord)
              currWord = ""
            }
            case c if c.isWhitespace && (trueLength + 1 + currWord.length) > maxLineLen =>
            { lines = lines :+ currLine
              currLine = indent.spaces + currWord
              currWord = ""
            }
            case c if c.isWhitespace =>
            { currLine = currLine + " " + currWord
              currWord = ""
            }
            case c => { currWord = currWord + c }
          }
          i += 1
        }

        currWord match
        { case "" =>
          case w if currLine == "" => { currLine = w }
          case w if (trueLength + 1 + currWord.length) > maxLineLen =>
          { lines = lines :+ currLine
            currLine = indent.spaces + currWord
          }
          case w => { currLine = currLine + " " + w }
        }
        if (currLine != "") lines = lines :+ currLine
        TextLines (lines.mkString ("\n"), lines.length, ife (lines.length == 0, 0, lines.head.length), ife (lines.length == 0, 0, lines.last.length) )
      }
    }
  }

  extension(thisString: String)
  { /** Implicit method to return an HTML bold element */
    def htmlB: HtmlB = HtmlB("thisString")

    def xmlAsString: XmlAsString = XmlAsString(thisString)
    
    def enTag(tag: String): String = "<" + tag + ">" + thisString + "</" + tag + ">"

    def htmlPath: String = "<code class='path'>" + thisString + "</code>"

    def htmlBash: String = "<code class='bash'>" + thisString + "</code>"
  }

  implicit class IntWebExtensions(thisInt: Int)
  { /** Extension method for CSS px units. Pixels (px) are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the display.
     * For printers and high resolution screens 1px implies multiple device pixels. */
    def px: CssPx = CssPx(thisInt)

    /** Extension method for CSS em units. Relative to the font-size of the element (2em means 2 times the size of the current font) */
    def em: CssEm = CssEm(thisInt)

    /** Extension method for CSS vw units. Relative to 1% of the width of the viewport */
    def vw: CssVw = CssVw(thisInt)

    /** Extension method for CSS vh units. Relative to 1% of the height of the viewport */
    def vh: CssVh = CssVh(thisInt)
  }

  implicit class DoubleWebExtensions(thisDouble: Double)
  { /** Extension method for CSS px units. Pixels (px) are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the display.
     * For printers and high resolution screens 1px implies multiple device pixels. */
    def px: CssPx = CssPx(thisDouble)

    /** Extension method for CSS em units. Relative to the font-size of the element (2em means 2 times the size of the current font) */
    def em: CssEm = CssEm(thisDouble)

    /** Extension method for CSS vw units. Relative to 1% of the width of the viewport. */
    def vw: CssVw = CssVw(thisDouble)

    /** Extension method for CSS vh units. Relative to 1% of the height of the viewport. */
    def vh: CssVh = CssVh(thisDouble)
  }
}