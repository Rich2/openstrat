/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

case class FilePosn(lineNum :Int, linePosn: Int, fileName: String)
{ def nextChar : FilePosn = FilePosn(lineNum, linePosn + 1, fileName)
  def addChars(chars: Seq[Char]): FilePosn = FilePosn(lineNum, linePosn + chars.length, fileName)
  def addStr(str: String): FilePosn = FilePosn(lineNum, linePosn + str.length, fileName)
  def addChars(offset: Int): FilePosn = FilePosn(lineNum, linePosn + offset, fileName)
}

object FilePosn
{ def fromString(linePosn: Int = 1, lineNum: Int = 1): FilePosn = FilePosn(lineNum, linePosn, "From string")
  def fromServer(linePosn: Int = 1, lineNum: Int = 1): FilePosn = FilePosn(lineNum, linePosn, "Server error")
  def empty: FilePosn = FilePosn(0, 0, "Empty object")
  def emptyError[A](errStr: String): Bad[A] = bad1(empty, errStr)

}

trait FileSpan
{ def startPosn: FilePosn
  def endPosn: FilePosn
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
