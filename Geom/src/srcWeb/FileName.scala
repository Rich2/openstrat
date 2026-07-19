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

/** A file name stem, missing the last part of the file name. */
case class FileNameStem(str: String)
{ /** Appends operand [[String]] to this file name stem. */
  def / (operand: String): FileNameStem = FileNameStem(str + operand)

  /** Adds a ",html" extension to the file name. */
  def html: HtmlFileName = HtmlFileName(str)
}

/** File name with an extension usch as .txt or .html file name. */
trait FileNameExtended extends FileName
{ /** The [[String]] for the file name stem. */
  def fileNameStemStr: String

  /** The file name stem without its extension such as ".txt" or ".html". */
  def fileNameStem: FileNameStem = FileNameStem(str)
}

object FileNameExtended
{
  /** Implementation class for the gneral case of a file name with an extension. */
  class FileNameExtendedGen(val arrayUnsafe: Array[String]) extends FileNameExtended
  { override def fileNameStemStr: String = arrayUnsafe(0)
    override def str: String = arrayUnsafe.mkString(".")
  }
}

/** A text file name. */
case class TextFileName(fileNameStemStr: String) extends FileNameExtended
{ override def str: String = fileNameStemStr + ".txt"
}

/** An HTML file name. */
case class HtmlFileName(fileNameStemStr: String) extends FileNameExtended
{ override def str: String = fileNameStemStr + ".html"
}

/** A JavaScript file name. */
case class JsFileName(fileNameStemStr: String) extends FileNameExtended
{ override def str: String = fileNameStemStr + ".js"
}

/** A JavaScript Map file name. */
case class JsMapFileName(fileNameStemStr: String) extends FileNameExtended
{ override def str: String = fileNameStemStr + ".js.map"
}

/** A CSS file name. */
case class CssFileName(fileNameStemStr: String) extends FileNameExtended
{ override def str: String = fileNameStemStr + ".css"
}