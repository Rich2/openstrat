/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.*

/** Directories and file name path. */
trait DirsFilePath extends DirsOrFilePathBase
{ def asStr: String = arrayUnsafe.length match
  { case 0 => excep("File name backing array must have at least 1 [[String]] element.")
    case _ => arrayUnsafe.mkString("/")
  }
}

/** Absolute directory (or folder) path and file name. */
class DirsFileAbs(val arrayUnsafe: Array[String]) extends DirsFilePath
{ override def asStr: String = arrayUnsafe.length match
  { case 0 => excep("A DirsFileAbs should have at least a file name.")
    case _ => arrayUnsafe.foldLeft("/")(_ + "/" + _)
  }
}

/** Relative directory (or folder) path and file name. */
class DirsFileRel(val arrayUnsafe: Array[String]) extends DirsFilePath
{
  override def asStr: String = arrayUnsafe.length match
  { case 0 => excep("A DirsFileRel should have at least a file name stem.")
    case _ => arrayUnsafe.mkString("/") }
}

object DirsFileRel
{ /** Factory apply method for [[DirsRel]]. */
  def apply(str0: String, inp: String*): DirsFileRel =
  { val newArray = (str0 +: inp).foldLeft(Array[String]())((acc, st) => acc ++ DirsPath.strToStrs(st))
    new DirsFileRel(newArray)
  }
}

trait DirsFileStem extends DirsFilePath
{ /** Utilitu method to append the underlying [[Array]]s. */
  protected def arrayAppend(operand: String): Array[String] =
  { val newArray: Array[String] = new Array[String](arrayLen)
    Array.copy(arrayUnsafe, 0, newArray, 0, arrayLen - 1)
    newArray(arrayLen - 1) = arrayUnsafe(arrayLen) + operand
    newArray
  }
}

/** Absolute directory (or folder) path and file name stem. */
class DirsAbsStem(val arrayUnsafe: Array[String]) extends DirsFileStem
{ /** Appends the [[String]] to the file name stem, without completing the file name. */
  @targetName("append") def %+(operand: String): DirsAbsStem = new DirsAbsStem(arrayAppend(operand))

  /** Appends the [[String]] to the file name stem completing the file name. */
  @targetName("complete") def ++(operand: String): DirsFileAbs = new DirsFileAbs(arrayAppend(operand))

  override def asStr: String = ife(arrayUnsafe.length == 0, "/", arrayUnsafe.foldLeft("")(_ + "/" + _))
}

/** Relative directory path and file name stem. */
class DirsRelStem(val arrayUnsafe: Array[String]) extends DirsFileStem
{ /** Appends the [[String]] to the file name stem, without completing the file name. */
  @targetName("append") def %+(operand: String): DirsRelStem = new DirsRelStem(arrayAppend(operand))
  
  /** Appends the [[String]] to the file name stem completing the file name. */
  @targetName("complete") def ++(operand: String): DirsFileRel = new DirsFileRel(arrayAppend(operand))

  override def asStr: String = arrayUnsafe.length match { case 0 => ""; case _ => arrayUnsafe.mkString("/") }
}