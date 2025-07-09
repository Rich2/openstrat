/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Content for XML and HTML elements. */
trait XCon
{ /** Returns the XML / HTML source code, formatted according to the input. This allows the XML to be indented according to its context. */
  def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 150): String

  /** This should replace the outEither method and possibly the out method as well. */
  def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): TextLines = TextLines(out(indent, maxLineLen), 3, 30, 30)
}

object XCon
{
  extension(seq: Seq[XCon | String])
  {
    def xCons: RArr[XCon | String] =
    { val array = xCons.mapArray{
        case xc: XCon => xc
        case s: String => s.xCon  
      }
      new RArr(array)
    }
  }
}

/** XML / HTML content that can be inlined. */
trait XConInLineable extends XCon
{ def value: String
  
  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): TextLines =
  {
    implicit val charArr: CharArr = new CharArr(value.toCharArray)

    def line1Len: Int = indent + line1InputLen

    def in1Loop(rem: CharsOff, currStr: String, lineLen: Int): TextLines = rem match
    {
      case CharsOff0() => TextLines(currStr, 1, lineLen, lineLen)
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
{ override def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = 150): String = value
  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault): TextLines = TextLines(out(indent, maxLineLen), 1, 30, 30)
}

/** Not sure about this trait. It is intended for short pieces of text that should be kept on the same line. */
trait XmlConStr extends XmlConInline
{ def str: String
  override def contents: RArr[XCon] = RArr(XConText(str))
}