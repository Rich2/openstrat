/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

sealed trait TextPosn
{
  def lineNum: Int
  def linePosn: Int
  def right(num: Int): TextPosn
  def nextChar: TextPosn = right(1)  
  def addLinePosn(offset: Int): TextPosn
  def addChars(chars: Seq[Char]): TextPosn
  def addStr(str: String): TextPosn
  def newLine: TextPosn
}

case class StrPosn(lineNum: Int, linePosn: Int) extends TextPosn
{
  override def right(num: Int): TextPosn = StrPosn(lineNum, linePosn + num)
  
  override def addChars(chars: Seq[Char]): TextPosn = StrPosn(lineNum, linePosn + chars.length)
  override def addStr(str: String): TextPosn = StrPosn(lineNum, linePosn + str.length)
  override def addLinePosn(offset: Int): TextPosn = StrPosn(lineNum, linePosn + offset)
  override def newLine: TextPosn = StrPosn(lineNum + 1, 1)
}

case class FilePosn(fileName: String, lineNum :Int, linePosn: Int) extends TextPosn
{ 
  override def right(num: Int): TextPosn = FilePosn(fileName, lineNum, linePosn + num)
  override def addChars(chars: Seq[Char]): TextPosn = FilePosn(fileName, lineNum, linePosn + chars.length)
  override def addStr(str: String): TextPosn = FilePosn(fileName, lineNum, linePosn + str.length)
  override def addLinePosn(offset: Int): TextPosn = FilePosn(fileName, lineNum, linePosn + offset)
  override def newLine: TextPosn = FilePosn(fileName, lineNum + 1, 1)
}

object FilePosn
{ def fromServer(linePosn: Int = 1, lineNum: Int = 1): FilePosn = new FilePosn("Server error", lineNum, linePosn)
  def empty: FilePosn = new FilePosn("Empty object", 0, 0)
  def emptyError[A](errStr: String): Bad[A] = bad1(empty, errStr)
  
  implicit object FilePosnShow extends Show[FilePosn]
  {
    def persist(obj: FilePosn): String = obj.fileName.toString -- obj.lineNum.toString -- obj.linePosn.toString 
  }
}

trait TextSpan
{ def startPosn: TextPosn
  def endPosn: TextPosn
}
 object TextSpan
 {
   def empty = new TextSpan{def startPosn = FilePosn.empty; def endPosn = FilePosn.empty }
 }

trait TextSpanMems extends TextSpan
{ def startMem: TextSpan
  def endMem: TextSpan
  override def startPosn = startMem.startPosn
  override def endPosn = endMem.endPosn
}

object TextSpanMems
{
  /** needs adjusting for empty Seq */
  implicit class FilePosnSeqImplicit(thisSeq: Seq[TextSpan]) extends TextSpanMems
  { override def startMem = thisSeq.head
    override def endMem = thisSeq.last
  }
}
