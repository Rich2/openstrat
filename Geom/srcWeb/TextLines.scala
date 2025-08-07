/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Class for returning output from a syntax hierarchy such as XML. HTML, CSS, JavaScript, C code etc. The class is created with a given max line length and an
 * indentation line. The second and subsequent lines will be indented to the given level. Indentation for the first line is the responsibility of the calling
 * object. */
case class TextLines(lines: Array[String])
{
  def text: String = lines.mkString("\n")
  def numLines: Int = lines.length
  def firstLen: Int = lines.headElseMap(0, _.length)
  def lastLen: Int = lines.lastElseMap(0, _.length)
  def isEmpty: Boolean = lines.isEmpty

  def appendInLines(operand: XConInline, indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines =
  { val opStr: String = operand match
    { case elem: XConElemInline => elem.out(indent, 0, maxLineLen)
      case str: String => str
    }

    val chars: Array[Char] = opStr.toCharArray
    var i: Int = 0
    var newLines: Array[String] = lines.initSafe
    var currLine: String = lines.lastElse("")
    var currWord: String = ""

    /** This takes account of if it is the first line, unknown characters will precede this method's first line. */
    def trueLength: Int = ife(newLines.length == 0, currLine.length + line1InputLen, currLine.length)

    while (i < chars.length)
    {
      chars(i) match
      { case c if c.isWhitespace && currWord == "" =>
        case c if c.isWhitespace && currLine == "" => {
          currLine = ife(lines.length == 0, currWord, indent.spaces + currWord)
          currWord = ""
        }
        case c if c.isWhitespace && (trueLength + 1 + currWord.length) > maxLineLen => {
          newLines = newLines :+ currLine
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
      case w if (trueLength + 1 + currWord.length) > maxLineLen => {
        newLines = newLines :+ currLine
        currLine = indent.spaces + currWord
      }
      case w => {currLine = currLine + " " + w}
    }
    if (currLine != "") newLines = newLines :+ currLine
    TextLines(newLines)
  }

  def appendSiblings(operand: XCon, indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines ={
    val r2 = operand.outLines(indent, line1InputLen, maxLineLen)
    if (r2.isEmpty) this
    else {
      val newArray = new Array[String](numLines + r2.numLines)
      Array.copy(lines, 0, newArray, 0, numLines)
      newArray(numLines) = indent.spaces + r2.lines(0)
      r2.lines.tailIForeach((str, i) => newArray(numLines + i) = str)
      new TextLines(newArray)
    }
  }
}

object TextLines
{ /** An empty [[TextLines]] with 0 lines. */
  def empty: TextLines = new TextLines(Array())
}