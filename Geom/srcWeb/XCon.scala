/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Content for XML and HTML elements. */
trait XCon
{
  /** Returns the XML / HTML source code, formatted according to the input. This allows the XML to be indented according to its context. This will generally use
   * the outLines method for its implementation. */
  def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 160): String

  /** This method returns the HTML output code, but also information for the parent XML / HTML element. The class should not add any indentation to its first
   * line. This is the responsibility of the parent element. */
  def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): TextLines = TextLines(out(indent, maxLineLen), 3, 30, 30)
}

object XCon
{
  extension(seq: Seq[XCon | String])
  { /** Converts the [[String]]s in this seqeunce into [[XCon]] XML / HTML content. */
    def xCons: RArr[XCon | String] = seq.mapArr{
      case xc: XCon => xc
      case s: String => s.xCon
    }
  }
}

/** XML / HTML content that can be inlined. */
trait XConInLineable extends XCon
{ def value: String
  
  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): TextLines =
  { given charArr: CharArr = new CharArr(value.toCharArray)

    def line1Len: Int = indent + line1InputLen

    def in1Loop(rem: CharsOff, currStr: String, lineLen: Int): TextLines = rem match
    { case CharsOff0() => TextLines(currStr, 1, lineLen, lineLen)
      case CharsOff1Tail(c, tail) if c.isWhitespace => in1Loop(tail, currStr, lineLen)
      case s =>
      { val (newRem, newWord) = getWord(s)
        val newLen = lineLen + newWord.length + 1
        if (newLen > maxLineLen) in2Loop(newRem, currStr + "\n" + indent.spaces + newWord, indent + newWord.length)
        else in1Loop(newRem, currStr -- newWord, newLen)
      }
    }

    def in2Loop(rem: CharsOff, currStr: String, lineLen: Int): TextLines = rem match
    { case CharsOff0() => TextLines(currStr, 2, lineLen, lineLen)
      case CharsOff1Tail(c, tail) if c.isWhitespace => in2Loop(tail, currStr, lineLen)
      case rem =>
      { val (newRem, newWord) = getWord(rem)
        val newLen = lineLen + newWord.length + 1
        if (newLen > maxLineLen) multiLoop(newRem, currStr, "\n" + indent.spaces + newWord)
        else in2Loop(newRem, currStr -- newWord, newLen)
      }
    }

    def multiLoop(rem: CharsOff, lines: String, currLine: String): TextLines = rem match
    { case CharsOff0() => TextLines(lines + currLine, 3, currLine.length, currLine.length)
      case CharsOff1Tail(c, tail) if c.isWhitespace => multiLoop(tail, lines, currLine)
      case s => {
        val (newRem, newWord) = getWord(rem)
        val newLen = currLine.length + newWord.length + 1
        if (newLen > maxLineLen) multiLoop(newRem, lines + currLine, "\n" + indent.spaces + newWord)
        else multiLoop(newRem, lines, currLine -- newWord)
      }
    }

    def getWord(rem: CharsOff): (CharsOff, String) =
    {
      def loop(rem: CharsOff, newWord: String): (CharsOff, String) = rem match
      { case CharsOff0() => (rem, newWord)
        case CharsOffHead(c) if c.isWhitespace => (rem, newWord)
        case CharsOff1Tail(c, tail) => loop(tail, newWord + c)
      }

      loop(rem, "")
    }

    val (newRem, firstWord) = getWord(charArr.offsetter0)
    in1Loop(newRem, firstWord, line1Len + firstWord.length)
  }
}

/** XML / HTML text that can have its line breaks changed. */
case class XConText(value: String) extends XConInLineable
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
trait XmlConStr extends XmlConInline
{ def str: String
  override def contents: RArr[XCon] = RArr(XConText(str))
}