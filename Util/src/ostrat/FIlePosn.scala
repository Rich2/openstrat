/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

sealed trait TextOrigin
{
}

case class StrPosn(lineNum :Int, linePosn: Int) extends TextOrigin

case class FilePosn(fileName: String, lineNum :Int, linePosn: Int) extends TextOrigin
{ def nextChar: FilePosn = FilePosn(fileName, lineNum, linePosn + 1)
  def addChars(chars: Seq[Char]): FilePosn = FilePosn(fileName, lineNum, linePosn + chars.length)
  def addStr(str: String): FilePosn = FilePosn(fileName, lineNum, linePosn + str.length)
  def addLinePosn(offset: Int): FilePosn = FilePosn(fileName,  lineNum, linePosn + offset)
  def newLine: FilePosn = FilePosn(fileName, lineNum + 1, 1)
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

trait FileSpan
{ def startPosn: FilePosn
  def endPosn: FilePosn
}
 object FileSpan
 {
   def empty = new FileSpan{def startPosn = FilePosn.empty; def endPosn = FilePosn.empty }
 }

trait FileSpanMems extends FileSpan
{ def startMem: FileSpan
  def endMem: FileSpan
  override def startPosn = startMem.startPosn
  override def endPosn = endMem.endPosn
}

object FileSpanMems
{
  /** needs adjusting for empty Seq */
  implicit class FilePosnSeqImplicit(thisSeq: Seq[FileSpan]) extends FileSpanMems
  { override def startMem = thisSeq.head
    override def endMem = thisSeq.last
  }
}
