/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance, pParse.*

/** A completed effect. */
trait DoneEff
{
  def effStr: String
  def detailStr: String
}

trait DoneIO extends DoneEff

trait FileWritten extends DoneIO
{ override def effStr: String = "File written"
}

object FileWritten
{
  def apply(detailStr: String): FileWritten = FileWrittenJust(detailStr)
}

case class FileWrittenJust(detailStr: String) extends FileWritten

case class FileCopied(detailStr: String) extends FileWritten
{ override def effStr: String = "File copied"
}

/** Directory now exists. It may have already existed or have just been created. */
trait DirExists extends DoneIO

/** Confirmation that a directory existed. */
case class DirExisted(detailStr: String) extends DirExists
{ override def effStr: String = "Directory existed"
}

/** Confirmation that a directory was created. */
case class DirCreated(detailStr: String) extends DirExists
{ override def effStr: String = "Directory created"
}