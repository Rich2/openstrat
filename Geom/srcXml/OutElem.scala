/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** An element that outputs a [[String]]. This could be XML HTML other HTTP content */
trait OutElem
{ /** The output [[String]]. Includes HTML and CSS code. */
  def out: String
}

/** [[OutElem]] with a default filename. */
trait OutElemFile extends OutElem
{ /** The [[String]] of default file name when this output to a file. */
  def fileNameStr: String
  
  /** The default file name when this output to a file. */
  def fileName: FileName
}

/** [[OutElem]] with a filename with an extension. */
trait OutElemFileExt extends OutElemFile
{ /** The stem of the default file name without the extension. */
  def fileStemStr: String

  /** The [[String]] for the fileName extension such as "html" or "txt". */
  def fileExtStr: String

  /** The stem of the default file name without the extension. */
  final def fileNameStem: FileNameStem = FileNameStem(fileStemStr)

  override def fileName: FileNameExtended
  final override def fileNameStr: String = fileStemStr + "." + fileExtStr
}