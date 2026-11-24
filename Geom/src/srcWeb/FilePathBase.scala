/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.*

/** Common trait for file paths and file name stem paths. */
trait FilePathBase extends FileSystemPath
{ def asStr: String = arrayUnsafe.length match
  { case 0 => excep("File name backing array must have at least 1 [[String]] element.")
    case _ => arrayUnsafe.mkString("/")
  }
}

/** Absolute directory (or folder) path and file name. */
class FilePathAbs(val arrayUnsafe: Array[String]) extends FilePathBase, FileSystemPathAbs
{ 
  def fileNameStr: String = arrayUnsafe.last
  
  def dirPathStr: String = arrayUnsafe.dropRight(1).foldLeft("/")(_ + "/" + _)
  
  override def asStr: String = arrayUnsafe.length match
  { case 0 => excep("A DirsFileAbs should have at least a file name.")
    case _ => arrayUnsafe.foldLeft("/")(_ + "/" + _)
  }
}

/** Relative directory (or folder) path and file name. */
class FilePathRel(val arrayUnsafe: Array[String]) extends FilePathBase
{
  override def asStr: String = arrayUnsafe.length match
  { case 0 => excep("A DirsFileRel should have at least a file name stem.")
    case _ => arrayUnsafe.mkString("/") }
}

object FilePathRel
{ /** Factory apply method for [[DirPathRel]]. */
  def apply(str0: String, inp: String*): FilePathRel =
  { val newArray = (str0 +: inp).foldLeft(Array[String]())((acc, st) => acc ++ st.pathArray)
    new FilePathRel(newArray)
  }
}

trait FileStemPath extends FilePathBase
{ /** Utility method to append [[String]] to the last element of the underlying [[Array]]s. */
  protected def arrayLastAppendStr(operand: String): Array[String] =
  { val newArray: Array[String] = new Array[String](arrayLen)
    Array.copy(arrayUnsafe, 0, newArray, 0, arrayLen - 1)
    newArray(arrayLen - 1) = arrayUnsafe(arrayLen - 1) + operand
    newArray
  }
}

/** Absolute directory (or folder) path and file name stem. */
class FileStemPathAbs(val arrayUnsafe: Array[String]) extends FileStemPath
{ /** Appends the [[String]] to the file name stem, without completing the file name. */
  @targetName("append") def %+(operand: String): FileStemPathAbs = new FileStemPathAbs(arrayLastAppendStr(operand))

  /** Appends the [[String]] to the file name stem completing the file name. */
  @targetName("complete") def ++(operand: String): FilePathAbs = new FilePathAbs(arrayLastAppendStr(operand))

  override def asStr: String = ife(arrayUnsafe.length == 0, "/", arrayUnsafe.foldLeft("")(_ + "/" + _))
}

/** Relative directory path and file name stem. */
class FileStemPathRel(val arrayUnsafe: Array[String]) extends FileStemPath
{ /** Appends the [[String]] to the file name stem, without completing the file name. */
  @targetName("append") def %+(operand: String): FileStemPathRel = new FileStemPathRel(arrayLastAppendStr(operand))
  
  /** Appends the [[String]] to the file name stem completing the file name. */
  @targetName("complete") def ++(operand: String): FilePathRel = new FilePathRel(arrayLastAppendStr(operand))

  override def asStr: String = arrayUnsafe.length match { case 0 => ""; case _ => arrayUnsafe.mkString("/") }
}