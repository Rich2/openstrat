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
 // def addLinePosn(offset: Int): TextPosn
 // def addChars(chars: Seq[Char]): TextPosn
 // def addStr(str: String): TextPosn
 // def newLine: TextPosn
  def shortStr: String =  lineNum.toString.appendCommas(linePosn.toString)
 // def fileName: String
  /*def canEqual(a: Any) = a.isInstanceOf[TextPosn]
  override def equals(that: Any): Boolean = that match
  { case tp: TextPosn if fileName == tp.fileName & lineNum == tp.lineNum & linePosn == tp.linePosn  => true
    case _ => false
  }
  override def hashCode = 104729 * lineNum + 88169 * linePosn + fileName.hashCode

   */
}

/*class StrPosn(val lineNum: Int, val linePosn: Int) extends TextPosn
{ override def fileName: String = "String"
  override def right(num: Int): TextPosn = StrPosn(lineNum, linePosn + num)
  override def addChars(chars: Seq[Char]): TextPosn = StrPosn(lineNum, linePosn + chars.length)
  override def addStr(str: String): TextPosn = StrPosn(lineNum, linePosn + str.length)
  override def addLinePosn(offset: Int): TextPosn = StrPosn(lineNum, linePosn + offset)
  override def newLine: TextPosn = StrPosn(lineNum + 1, 1)
}*/

object StrPosn{ def apply(lineNum: Int, linePosn: Int): TextPosn = new TextPosn("String", lineNum, linePosn)}
/*
class FilePosn(val fileName: String, val lineNum :Int, val linePosn: Int) extends TextPosn
{ override def right(num: Int): TextPosn = FilePosn(fileName, lineNum, linePosn + num)
  override def addChars(chars: Seq[Char]): TextPosn = FilePosn(fileName, lineNum, linePosn + chars.length)
  override def addStr(str: String): TextPosn = FilePosn(fileName, lineNum, linePosn + str.length)
  override def addLinePosn(offset: Int): TextPosn = FilePosn(fileName, lineNum, linePosn + offset)
  override def newLine: TextPosn = FilePosn(fileName, lineNum + 1, 1)
}*/

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
