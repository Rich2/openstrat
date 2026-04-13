/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse.*, collection.mutable.ArrayBuffer, annotation.*

/** Common trait for directory paths, absolute and relative and directory-filename paths, absolute or relative */
trait DirsOrFilePathBase
{ /** The backing for this path. */
  def arrayUnsafe: Array[String]
  
  /** The length of the backing Array. */
  def arrayLen = arrayUnsafe.length

  /** The path as a string with the slash characters inserted */
  def asStr: String
}

trait DirsPath extends DirsOrFilePathBase
{ /** A notification [[String]] to inform that the path doesn't exist. */
  def noExistStr: String = asStr -- "Doesn't exist"

  /** A notification [[String]] to inform that the path is not a directory. */
  def notDirStr: String = asStr -- "is not a directory"

  /** Appends a file stem to this directory path. */
  @targetName("appendStem") def :-/(operand: String): DirsFileStem

  /** Appends a file name [[String]] to produce a file path. */
  @targetName("appendFile") def :/(operand: String): DirsFilePath
}

object DirsPath
{
  def strToStrs(inp: String): Array[String] =
  { val res0: String = inp.dropWhile(_.isWhitespace).dropWhile(!_.isLetter).dropRightWhile(_.isWhitespace).dropRightWhile(_ == '/')
    val acc: ArrayBuffer[String] = Buffer[String]()
    def loop(rem: String): Unit = if (rem.length == 0) {}
    else{
      val newStr = rem.takeWhile(_ != '/')
      acc.append(newStr)
      val rem2 = rem.drop(newStr.length)
      if(rem2.length == 0){}
      else loop(rem2.dropWhile(!_.isLetter))
    }
    loop(res0)
    acc.toArray
  }
}