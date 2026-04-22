/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import collection.mutable.ArrayBuffer, annotation.*

/** Common trait for file system paths or part of a file system path. Absolute and relative and directory-filename paths. */
trait FileSystemPath
{ /** The backing for this path. */
  def arrayUnsafe: Array[String]
  
  /** The length of the backing Array. */
  def arrayLen = arrayUnsafe.length

  /** The path as a string with the slash characters inserted */
  def asStr: String
}

trait DirsPath extends FileSystemPath
{ /** A notification [[String]] to inform that the path doesn't exist. */
  def noExistStr: String = asStr -- "Doesn't exist"

  /** A notification [[String]] to inform that the path is not a directory. */
  def notDirStr: String = asStr -- "is not a directory"
  
  /** Appends a file name [[FileName]] to produce a file path. */
  @targetName("appendFile") def :/(operand: FileName): DirsFilePath
  
  /** Appends a file name [[String]] to produce a file path. */
  @targetName("appendFile") def :/(operand: String): DirsFilePath

  /** Appends a file stem to this directory path. */
  @targetName("appendStem") def :-/(operand: FileNameStem): DirsFileStem

  /** Appends a file stem to this directory path. */
  @targetName("appendStem") def :-/(operand: String): DirsFileStem
}

object DirsPath
{
  def strToStrs(inp: String): Array[String] =
  { val res0: String = inp.dropWhile(_.isWhitespace).dropWhile(!_.isLetter).dropRightWhile(_.isWhitespace).dropRightWhile(_ == '/')
    val acc: ArrayBuffer[String] = Buffer[String]()
    def loop(rem: String): Unit = onlyIfNot(rem.length == 0){
      val newStr = rem.takeWhile(_ != '/')
      acc.append(newStr)
      val rem2 = rem.drop(newStr.length)
      onlyIfNot(rem2.length == 0){ loop(rem2.dropWhile(!_.isLetter)) }
    }
    loop(res0)
    acc.toArray
  }
}