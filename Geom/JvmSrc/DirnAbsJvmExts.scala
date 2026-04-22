/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package webjvm
import utiljvm.writeFile as wFile, java.io.File

/** Extension methods for [[DirsAbs]], that require JVM, Java Virtual Machine. */
implicit class DirAbsJvmExts (thisPath: DirsAbs)
{ /** Writes the contents [[String]] to the HTML File to this absolute directory path. Returns a success message if no error. */
  def writeFile(fileName: String, contents: String): ErrBi[IOExc, FileWritten] = wFile((thisPath /+ fileName).asStr, contents)
  
  /** Writes the contents [[String]] to the subdirectory. Returns a successful message on success. */
  def subWriteFile(subDir: String, fileName: String, contents: String): ErrBi[IOExc, FileWritten] = wFile((thisPath / subDir /+ fileName).asStr, contents)

  /** Writes the HTML File to the subdirectory. Returns a successful message on success. */
  def subWriteFiles(subDir: String, fileName: String, page: PageHtml): ErrBi[IOExc, FileWritten] = wFile((thisPath / subDir /+ fileName).asStr, page.out)

  /** Writes the RSON File given in the second parameter to this full path and filename given by the first parameter. Returns a successful message on
   * success. */
  def writeRson(fileName: String, page: String): ErrBi[IOExc, RsonFileWritten] =
    wFile(thisPath.asStr / fileName, page).map(fw => RsonFileWritten(fw.detailStr))

  /** Writes the HTML File given in the second parameter to this full path and filename given by the first parameter. Returns a successful message on
   * success. */
  def writeHtml(fileName: String, page: PageHtml): ErrBi[IOExc, HtmlFileWritten] =
    wFile(thisPath.asStr / fileName, page.out).map(fw => HtmlFileWritten(fw.detailStr))

  /** Writes the HTML File to this full path and filename given by the [[HtmlFilePAge]]. Returns a successful message on success. */
  def writeHtml(page: PageFile): ErrBi[IOExc, HtmlFileWritten] = wFile(thisPath.asStr / page.fileNameStr, page.out).map(fw => HtmlFileWritten(fw.detailStr))

  /** Writes the HTML File to this full path and filename given by the [[HtmlFilePAge]]. Returns a successful message on success. */
  def subWriteHtml(subDir: String, page: PageFile): ErrBi[IOExc, HtmlFileWritten] =
    wFile(thisPath.asStr / subDir / page.fileNameStr, page.out).map(fw => HtmlFileWritten(fw.detailStr))

  /** Writes the [[PageHtml]] to the subdirectory of this path. Returns a successful message on success. */
  def subWriteHtml(subDir: String, fileName: String, page: PageHtml): ErrBi[IOExc, HtmlFileWritten] =
    wFile(thisPath.asStr / subDir / fileName, page.out).map(fw => HtmlFileWritten(fw.detailStr))

  /** Writes the HTML File to this full path and filename given by the [[HtmlFilePAge]]. Returns a successful message on success. */
  def subWriteHtml(subDir: String, fileName: String, content: String): ErrBi[IOExc, HtmlFileWritten] =
    wFile(thisPath.asStr / subDir / fileName, content).map(fw => HtmlFileWritten(fw.detailStr))

  /** Writes the String given in the third parameter to the full path and filename given by the first name. Returns a successful message on success. There is a
   * name overload that takes a [[String]] for the path. */
  def writeCss(fileName: String, content: String): ErrBi[IOExc, CssFileWritten] = wFile(thisPath.asStr / fileName, content).map(fw => CssFileWritten(fw.detailStr))

  /** Writes the CSS file to the path and filename given in the CSS file. Returns a successful message on success. */
  def writeCss(cssFile: CssRulesFile): ErrBi[IOExc, CssFileWritten] = wFile(thisPath.asStr / cssFile.fileNameStr, cssFile.out).map(fw => CssFileWritten(fw.detailStr))

  def toJava: File = File(thisPath.asStr)

  /** Perform the side effecting procedure if the location exists and is a directory as opposed to a file. */
  def doIfDirExists(f: DirsAbs => Unit) =
  {
    val jd = thisPath.toJava
    if (jd.exists)
      if (jd.isDirectory) f(thisPath)
      else println(thisPath.notDirStr)
    else println(thisPath.noExistStr)
  }

  /** Try to make this directory exist. */
  def mkExist: ExcIOMon[DirExists] = mkDirExist(thisPath.asStr)

  /** Try to make subdirectory exist. */
  def mkSubExist(tailStr: String): ErrBi[IOExc, DirsAbs] = mkDirExist(thisPath.asStr / tailStr).map(_ => thisPath / tailStr)
}