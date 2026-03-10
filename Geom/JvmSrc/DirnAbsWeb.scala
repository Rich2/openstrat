/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utiljvm.{ writeFile => wFile }

/** Extension methods for [[DirsAbs]], that require JVM, Java Virtual Machine. */
implicit class DirAbsWebExtensions (thisPath: DirsAbs)
{ /** Writes the contents [[String]] to the HTML File to this absolute directory path. Returns a success message if no error. */
  def writeFile(fileName: String, contents: String): ErrBi[IOExc, FileWritten] = wFile(thisPath /+ fileName, contents)
  
  /** Writes the contents [[String]] to the subdirectory. Returns a successful message on success. */
  def subWriteFile(subDir: String, fileName: String, contents: String): ErrBi[IOExc, FileWritten] = wFile(thisPath / subDir /+ fileName, contents)

  /** Writes the HTML File to the subdirectory. Returns a successful message on success. */
  def subWriteFiles(subDir: String, fileName: String, page: HtmlPage): ErrBi[IOExc, FileWritten] = wFile(thisPath / subDir /+ fileName, page.out)

  /** Writes the HTML File given in the second parameter to this full path and filename given by the first parameter. Returns a successful message on
   * success. */
  def writeHtml(fileName: String, page: HtmlPage): ErrBi[IOExc, HtmlFileWritten] = writeStrsHtml(thisPath.asStr / fileName, page.out)

  /** Writes the HTML File to this full path and filename given by the [[HtmlFilePAge]]. Returns a successful message on success. */
  def writeHtml(page: HtmlPageFile): ErrBi[IOExc, HtmlFileWritten] = writeStrsHtml(thisPath.asStr / page.fileName, page.out)

  /** Writes the HTML File to this full path and filename given by the [[HtmlFilePAge]]. Returns a successful message on success. */
  def subWriteHtml(subDir: String, page: HtmlPageFile): ErrBi[IOExc, HtmlFileWritten] = writeStrsHtml(thisPath.asStr / subDir / page.fileName, page.out)

  /** Writes the [[HtmlPage]] to the subdirectory of this path. Returns a successful message on success. */
  def subWriteHtml(subDir: String, fileName: String, page: HtmlPage): ErrBi[IOExc, HtmlFileWritten] =
    writeStrsHtml(thisPath.asStr / subDir / fileName, page.out)

  /** Writes the HTML File to this full path and filename given by the [[HtmlFilePAge]]. Returns a successful message on success. */
  def subWriteHtml(subDir: String, fileName: String, content: String): ErrBi[IOExc, HtmlFileWritten] =
    writeStrsHtml(thisPath.asStr / subDir / fileName, content)

  /** Writes the String given in the third parameter to the full path and filename given by the first name. Returns a successful message on success. There is a
   * name overload that takes a [[String]] for the path. */
  def writeCss(fileName: String, content: String): ErrBi[IOExc, CssFileWritten] = writeStrsCss(thisPath.asStr / fileName, content)

  /** Writes the CSS file to the path and filename given in the CSS file. Returns a successful message on success. */
  def writeCss(cssFile: CssRulesFile): ErrBi[IOExc, CssFileWritten] = writeStrsCss(thisPath.asStr / cssFile.fileName, cssFile.out)
}