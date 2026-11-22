/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package utiljvm
import java.io.File, pweb.*

/** Extension methods for [[DirsAbs]], that require JVM, Java Virtual Machine. */
implicit class DirAbsJvmExts (thisPath: DirsAbs)
{ /** Writes the contents [[String]] to the HTML File to this absolute directory path. Returns a success message if no error. */
  def writeFile(fileName: String, contents: String): Either[IOExc, FileWritten] = pathStrWriteStr((thisPath /+ fileName).asStr, contents)
  
  /** Writes the contents [[String]] to the subdirectory. Returns a successful message on success. */
  def subWriteFile(subDir: String, fileName: String, contents: String): Either[IOExc, FileWritten] = pathStrWriteStr((thisPath / subDir /+ fileName).asStr, contents)

  /** Writes the HTML File to the subdirectory. Returns a successful message on success. */
  def subWriteFiles(subDir: String, fileName: String, page: HtmlPage): Either[IOExc, FileWritten] = pathStrWriteStr(thisPath.asStr / subDir / fileName, page.out)

  /** Writes the RSON File given in the second parameter to this full path and filename given by the first parameter. Returns a successful message on
   * success. */
  def writeRson(fileName: String, page: String): Either[IOExc, RsonFileWritten] =
  { val filePath: DirsFileAbs = thisPath :/ fileName
    pathStrWriteStr(filePath.asStr, page).map(_ => new RsonFileWritten(filePath.arrayUnsafe))
  }

  /** Writes the HTML File given in the second parameter to this full path and filename given by the first parameter. Returns a successful message on
   * success. */
  def writeHtml(fileName: String, page: HtmlPage): Either[IOExc, HtmlWritten] =
  { val filePath: DirsFileAbs = thisPath :/ fileName
    pathStrWriteStr(filePath.asStr, page.out).map(_ => new HtmlWritten(filePath.arrayUnsafe))
  }

  /** Writes the HTML File to this full path and filename given by the [[HtmlPageFile]]. Returns a successful message on success. */
  def writeHtml(page: HtmlPageFile): Either[IOExc, HtmlWritten] =
  { val filePath: DirsFileAbs = thisPath :/ page.fileName
    pathStrWriteStr(filePath.asStr, page.out).map(_ => new HtmlWritten(filePath.arrayUnsafe))
  }

  /** Writes the HTML File to this full path and filename given by the [[HtmlPageFile]]. Returns a successful message on success. */
  def subWriteHtml(subDirStr: String, page: HtmlPageFile): Either[IOExc, HtmlWritten] =
  { val filePath: DirsFileAbs = thisPath / subDirStr :/ page.fileName
    pathStrWriteStr(thisPath.asStr / subDirStr / page.fileNameStr, page.out).map(_ => new HtmlWritten(filePath.arrayUnsafe))
  }

  /** Writes the [[HtmlPage]] to the subdirectory of this path. Returns a successful message on success. */
  def subWriteHtml(subDirStr: String, fileNameStr: String, page: HtmlPage): Either[IOExc, HtmlWritten] =
  { val filePath: DirsFileAbs = thisPath / subDirStr :/ fileNameStr
    pathStrWriteStr(filePath.asStr, page.out).map(_ => new HtmlWritten(filePath.arrayUnsafe))
  }

  /** Writes the HTML File to this full path and filename given by the [[HtmlPageFile]]. Returns a successful message on success. */
  def subWriteHtml(subDir: String, fileName: String, content: String): Either[IOExc, HtmlWritten] =
  { val filePath: DirsFileAbs = thisPath / subDir :/ fileName
    pathStrWriteStr(filePath.asStr, content).map(fw => new HtmlWritten(thisPath.arrayUnsafe))
  }

  /** Writes the String given in the third parameter to the full path and filename given by the first name. Returns a successful message on success. There is a
   * name overload that takes a [[String]] for the path. */
  def writeCss(fileName: String, content: String): Either[IOExc, CssWritten] =
  { val filePath = thisPath :/ fileName
    pathStrWriteStr(filePath.asStr, content).map(_ => new CssWritten(thisPath.arrayUnsafe))
  }

  /** Writes the CSS file to the path and filename given in the CSS file. Returns a successful message on success. */
  def writeCss(cssFile: CssRulesFile): Either[IOExc, CssWritten] =
  { val filePath: DirsFileAbs = thisPath :/ cssFile.fileName
    pathStrWriteStr(filePath.asStr, cssFile.out).map(_ => new CssWritten(filePath.arrayUnsafe))
  }

 def writesCss(files: CssRulesFile*): FileWrittenResults[IOExc, CssWritten] =
 { val res1: Seq[Either[IOExc, CssWritten]] = files.map(writeCss(_))
   FileWrittenResults.fromSeq(thisPath.asStr, res1)
 }

  /** Writes the POM file given in the second parameter to this full path and filename given by the first parameter. Returns a successful message on success. */
  def writePom(fileName: String, pomModule: PomModule): Either[IOExc, PomFileWritten] =
  { val filePath: DirsFileAbs = thisPath :/ fileName
    pathStrWriteStr(filePath.asStr, pomModule.out).map(_ => new PomFileWritten(filePath.arrayUnsafe))
  }

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
  def mkExist: IOExcEither[DirExists] = mkDirExist(thisPath.asStr)

  /** Try to make subdirectory exist. */
  def mkSubExist(tailStr: String): IOExcEither[DirsAbs] = mkDirExist(thisPath.asStr / tailStr).map(_ => thisPath / tailStr)
}