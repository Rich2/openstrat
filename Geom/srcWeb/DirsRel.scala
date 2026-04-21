/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.*

/** Directory path relative. */
class DirsRel(val arrayUnsafe: Array[String]) extends DirsPath
{ /** Move back up the directory hierarchy, dropping the last directory if there is one. */
  def up: DirsRel = new DirsRel(arrayUnsafe.dropRight(1))

  /** Appends a relative directory path. There is a name overload that appends a [[String]]. */
  @targetName("append") def /(extraPath: DirsRel): DirsRel = new DirsRel(arrayUnsafe ++ extraPath.arrayUnsafe)

  /** Appends a relative directory path. There is a name overload that appends a [[DirsRel]] */
  @targetName("append") def /(operand: String): DirsRel = new DirsRel(arrayUnsafe ++ DirsPath.strToStrs(operand))

  /** From the root of this relative directory path, append the operand directories path. */
  @targetName("fromRootAppendDirs") def </(operand: DirsRel): DirsRel = new DirsRel(topFileAppendArray(operand.arrayUnsafe))

  /** From the root of this relative directory path append the operand directories and filename path. */
  @targetName("fromRootAppendDirsFile") def </>(operand: DirsFileRel): DirsFileRel = new DirsFileRel(topFileAppendArray(operand.arrayUnsafe))

  /** Appends a file name [[String]] to produce a relative file path. */
  @targetName("appendFile") def :/(operand: String): DirsFileRel = new DirsFileRel(arrayUnsafe :+ operand)

  /** Appends a file name [[String]] to produce a relative file stem path. */
  @targetName("appendStem") override def :-/(operand: String): DirsRelStem = new DirsRelStem(arrayUnsafe :+ operand)

  /** Utility method for fromRootAppendDirs and fromRootAppendDirsFile methods. Creates the backing [[Array]] for the returned classes. */
  def topFileAppendArray(operand: Array[String]): Array[String] =
  { val thisLen: Int = arrayUnsafe.length
    val opLen: Int = operand.length
    def loop(i: Int): Int = if (i < thisLen && i < opLen && arrayUnsafe(i) == operand(i)) loop(i + 1) else i
    val co = loop(0)
    val newArray: Array[String] = new Array[String](thisLen + opLen - 2 * co)
    iUntilForeach(thisLen - co) { i => newArray(i) = ".." }
    Array.copy(operand, co, newArray, thisLen - co, opLen - co)
    newArray
  }

  /** Append a directories and file name path. There is a name overload that takes the [[String]] representation as the operand. */
  @targetName("appendDirsFile") def /> (operand: DirsFileRel): DirsFileRel = new DirsFileRel(arrayUnsafe ++ operand.arrayUnsafe)

  /** Append a directories and file name path. There is a name overload that takes a [[DirsFileRel]] as the operand. */
  @targetName("appendDirsFile") def /> (operand: String): DirsFileRel = this /> DirsFileRel(operand)

  override def asStr: String = arrayUnsafe.length match { case 0 => ""; case _ => arrayUnsafe.mkString("/") }
  override def toString: String = "DirPathRel" + asStr.enParenth
}

object DirsRel
{ /** Factory apply method for [[DirsRel]]. */
  def apply(inp: String*): DirsRel =
  { val newArray = inp.foldLeft(Array[String]())((acc, st) => acc ++ DirsPath.strToStrs(st))
    new DirsRel(newArray)
  }
}