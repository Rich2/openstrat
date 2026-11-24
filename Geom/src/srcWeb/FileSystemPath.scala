/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import collection.mutable.ArrayBuffer, annotation.*

/** Common trait for file system paths or part of a file system path. Absolute and relative and directory-filename paths. */
trait FileSystemPath
{ /** The backing for this path. */
  def arrayUnsafe: Array[String]
  
  /** The length of the backing Array. */
  def arrayLen = arrayUnsafe.length

  /** The path as a string with the slash characters inserted. */
  def asStr: String
}

/** Common trait for absolute file system paths or part of a file system path. */
trait FileSystemPathAbs extends FileSystemPath