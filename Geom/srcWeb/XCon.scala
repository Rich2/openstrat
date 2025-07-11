/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Content for XML and HTML elements. */
trait XCon
{ /** Returns the XML / HTML source code, formatted according to the input. This allows the XML to be indented according to its context. This will generally use
   * the outLines method for its implementation. */
  def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 160): String

  /** This method returns the HTML output code, but also information for the parent XML / HTML element. The class should not add any indentation to its first
   * line. This is the responsibility of the parent element. */
  def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): TextLines = TextLines(out(indent, maxLineLen), 3, 30, 30)
}

object XCon
{
  extension(seq: Seq[XCon | String])
  { /** Converts the [[String]]s in this sequence into [[XCon]] XML / HTML content. */
    def xCons: RArr[XCon | String] = seq.mapArr{
      case xc: XCon => xc
      case s: String => s.xCon
    }
  }
}

/** XML / HTML just stored as a [[String]]. This is not desirable, except as a temporary expedient. */
case class XmlAsString(value: String) extends XCon
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): String = value
}

/** XML / HTML text that can have its line breaks changed. */
case class XConText(value: String) extends XConFlow
{ override def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = 160): String = outLines(indent, line1InputLen, maxLineLen).text

  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): TextLines =
  { val chars: Array[Char] = value.toCharArray
    var i: Int = 0
    var lines: Array[String] = Array()
    var currLine: String = ""
    var currWord: String = ""

    /** This takes account of if it is the first line, unknown characters will precede this method's first line. */
    def trueLength: Int = ife(lines.length == 0, currLine.length + line1InputLen, currLine.length)

    while(i < chars.length)
    { chars(i) match
      { case c if c.isWhitespace && currWord == "" =>
        case c if c.isWhitespace && currLine == "" =>
        { currLine = ife(lines.length == 0, currWord, indent.spaces + currWord)
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
        case c =>{ currWord = currWord + c }
      }
      i += 1
    }
    currWord match
    { case "" =>
      case w if (trueLength + 1 + currWord.length) > maxLineLen =>
      { lines = lines :+ currLine
        currLine = indent.spaces + currWord
      }
      case w =>{ currLine = currLine + " " + w}
    }
    if (currLine != "") lines = lines :+ currLine
    TextLines(lines.mkString("\n"), lines.length, ife(lines.length == 0, 0, lines.head.length), ife(lines.length == 0, 0, lines.last.length))
  }
}

/** Not sure about this trait. It is intended for short pieces of text that should be kept on the same line. */
trait XmlConStr extends XHmlOwnLine
{ def str: String
  override def contents: RArr[XCon] = RArr(XConText(str))
}