/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

object XCon
{
  def appendInLines(input: TextLines, operand: XInline, indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines =
  {
    val opStr: String = operand match
    { case elem: XConInline => elem.out(indent, 0, maxLineLen)
      case str: String => str
    } 

    val chars: Array[Char] = opStr.toCharArray
    var i: Int = 0
    var lines: Array[String] = ???
    var currLine: String = ""
    var currWord: String = ""

    /** This takes account of if it is the first line, unknown characters will precede this method's first line. */
    def trueLength: Int = ife(lines.length == 0, currLine.length + line1InputLen, currLine.length)

    while (i < chars.length)
    { chars(i) match {
        case c if c.isWhitespace && currWord == "" =>
        case c if c.isWhitespace && currLine == "" => {
          currLine = ife(lines.length == 0, currWord, indent.spaces + currWord)
          currWord = ""
        }
        case c if c.isWhitespace && (trueLength + 1 + currWord.length) > maxLineLen => {
          lines = lines :+ currLine
          currLine = indent.spaces + currWord
          currWord = ""
        }
        case c if c.isWhitespace => {
          currLine = currLine + " " + currWord
          currWord = ""
        }
        case c => {currWord = currWord + c}
      }
      i += 1
    }

    currWord match
    { case "" =>
      case w if currLine == "" => {currLine = w}
      case w if (trueLength + 1 + currWord.length) > maxLineLen =>
      { lines = lines :+ currLine
        currLine = indent.spaces + currWord
      }
      case w => {currLine = currLine + " " + w}
    }
    if (currLine != "") lines = lines :+ currLine
    TextLines(lines, lines.length, ife(lines.length == 0, 0, lines.head.length), ife(lines.length == 0, 0, lines.last.length))
  }
}