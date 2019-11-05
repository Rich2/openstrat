/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

case class TextPosn(fileName: String, lineNum :Int, linePosn: Int)// extends TextPosn
{ /** moves the value of the TextPosn to the right. */
  def right(num: Int): TextPosn = TextPosn(fileName, lineNum, linePosn + num)
  def addChars(chars: Seq[Char]): TextPosn = TextPosn(fileName, lineNum, linePosn + chars.length)
  def addStr(str: String): TextPosn = TextPosn(fileName, lineNum, linePosn + str.length)
  def addLinePosn(offset: Int): TextPosn = TextPosn(fileName, lineNum, linePosn + offset)
  def newLine: TextPosn = TextPosn(fileName, lineNum + 1, 1)
  def nextChar: TextPosn = right(1)
  def shortStr: String =  lineNum.toString.appendCommas(linePosn.toString)
}


object StrPosn
{ def apply(lineNum: Int = 1, linePosn: Int = 1): TextPosn = new TextPosn("String", lineNum, linePosn)
  def unapply(inp: TextPosn): Option[(Int, Int)] = inp match
  { case TextPosn("String", ln, lp) => Some((ln, lp))
    case _ => None
  }
}

object TextPosn
{ //def apply(fileName: String, lineNum: Int, linePosn: Int): FilePosn = new FilePosn(fileName, lineNum, linePosn)
  def fromServer(linePosn: Int = 1, lineNum: Int = 1): TextPosn = TextPosn("Server error", lineNum, linePosn)
  def empty: TextPosn = TextPosn("Empty object", 0, 0)
  def emptyError[A](errStr: String): Bad[A] = bad1(empty, errStr)
  
  implicit object TextPosnShow extends Show3[String, Int, Int, TextPosn]("TextPosn", "fileName", _.fileName, "lineNum", _.lineNum, "linePosn", _.linePosn)
}

trait TextSpan
{ def startPosn: TextPosn
  def endPosn: TextPosn
}

object TextSpan
{
  def empty = new TextSpan{def startPosn = TextPosn.empty; def endPosn = TextPosn.empty }
}

/** The purpose of this trait is to give the position span of the syntax from its opening and closing components. */
trait TextSpanCompound extends TextSpan
{ def startMem: TextSpan
  def endMem: TextSpan
  override def startPosn = startMem.startPosn
  override def endPosn = endMem.endPosn
}

object TextSpanCompound
{
  /** needs adjusting for empty Seq */
  implicit class FilePosnSeqImplicit(thisSeq: Seq[TextSpan]) extends TextSpanCompound
  { override def startMem = thisSeq.head
    override def endMem = thisSeq.last
  }
}
