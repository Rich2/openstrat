/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** XML / HTML content that can possibly be inlined. */
trait XConElemInline extends XConElem
{ /** The out [[String]], prior to being formatted into text lines. */
  def outUnlined: String

  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines =
  { given charArr: CharArr = new CharArr(outUnlined.toCharArray)

    def line1Len: Int = indent + line1InputLen

    def in1Loop(rem: CharsOff, currStr: String, lineLen: Int): TextLines = rem match
    { case CharsOff0() => TextLines(currStr)
      case CharsOff1Tail(c, tail) if c.isWhitespace => in1Loop(tail, currStr, lineLen)

      case s =>
      { val (newRem, newWord) = getWord(s)
        val newLen = lineLen + 1 + newWord.length
        if (newLen > maxLineLen) in2Loop(newRem, currStr + "\n" + indent.spaces + newWord, indent + newWord.length)
        else in1Loop(newRem, currStr -- newWord, newLen)
      }
    }

    def in2Loop(rem: CharsOff, currStr: String, lineLen: Int): TextLines = rem match
    { case CharsOff0() => TextLines(currStr)
      case CharsOff1Tail(c, tail) if c.isWhitespace => in2Loop(tail, currStr, lineLen)
      case rem =>
      { val (newRem, newWord) = getWord(rem)
        val newLen = lineLen + 1 + newWord.length
        if (newLen > maxLineLen) multiLoop(newRem, currStr, "\n" + indent.spaces + newWord)
        else in2Loop(newRem, currStr -- newWord, newLen)
      }
    }

    def multiLoop(rem: CharsOff, lines: String, currLine: String): TextLines = rem match
    { case CharsOff0() => TextLines(lines + currLine)
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

/** Sn XML / HTML element that can possibly be inlined. */
trait XHtmlInline extends XHmlElem, XConElemInline