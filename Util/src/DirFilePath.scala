/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse.*, collection.mutable.ArrayBuffer, annotation.*

/** Directories and file name path. */
trait DirsFilePath extends DirPathBase
{
  def asStr: String = arrayUnsafe.length match
  { case 0 => excep("File name backing array must have at least 1 [[String]] element.")
    case _ => arrayUnsafe.mkString("/")
  }
}

/** Absolute directory (or folder) path and file name. */
class DirsFileAbs(val arrayUnsafe: Array[String]) extends DirsFilePath

/** Relative directory (or folder) path and file name. */
class DirsFileRel(val arrayUnsafe: Array[String]) extends DirsFilePath
{
  
}

object DirsFileRel
{ /** Factory apply method for [[DirsRel]]. */
  def apply(str0: String, inp: String*): DirsFileRel =
  { val newArray = (str0 +: inp).foldLeft(Array[String]())((acc, st) => acc ++ DirPath.strToStrs(st))
    new DirsFileRel(newArray)
  }
}