/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** A file name. */
trait FileName
{ /** The file name as a [[String]]. */
  def str: String
}

object FileName
{ /** Factory apply method for file name. */
  def apply(str: String): FileName = new FileNameGen(str)

  /** Implementation class for the general case of a [[FileName]]. */
  class FileNameGen(val str: String) extends FileName
}

class FileNameStem(str: String)

trait FileNameExtended extends FileName
{
  def fileNameStemStr: String
  def fileNameStem: FileNameStem = FileNameStem(str)
}