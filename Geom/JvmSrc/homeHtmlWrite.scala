/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utiljvm.*

object homeHtmlWrite
{ /** A quick and crude method for creating / overwriting an HTML file in the user's home directory. It takes 2 strings. The first is used for the HTML title
   * and the file name. The second is contents of the HTML body element. */
  def apply(dir: String, title: String, bodyContent: String): ErrBi[Exception, FileWritten] =
  {
    val home: String = System.getProperty("user.home")
    fileWrite(home / dir / title + ".html", HtmlPage.titleOnly(title, bodyContent).out)
  }
}

object opensettHtmlWrite
{
  def apply(title: String, bodyContent: String) =
  { val home: String = System.getProperty("user.home")
    fileWrite(home / "opensett" / title + ".html", HtmlPage.titleOnly(title, bodyContent).out)
  }
}

object HttpNow extends HttpDate(httpNow)

/** Writes the out [[String]] from the HTML page given in the second parameter to the directory path of the first parameter and filename in the HTML page.
 * Returns a successful message on success. */
def htmlFileWrite(dirPathStr: String, page: HtmlPageFile): ErrBi[IOExc, HtmlFileWritten] =
{ val filePathName = ife(dirPathStr == null || dirPathStr == "", "", dirPathStr + "/") + page.fileNameStem + ".html"
  fileWrite(filePathName, page.out).map(fw => HtmlFileWritten(fw.detailStr))
}

/** Writes the out [[String]] from the HTML page given in the second parameter to the directory path of the first parameter and filename in the HTML page.
 * Returns a successful message on success. */
def htmlFileWrite(path: DirsAbs, page: HtmlPageFile): ErrBi[IOExc, HtmlFileWritten] = fileWrite(path, page.fileName, page.out).map(fw => HtmlFileWritten(fw.detailStr))

/** Extension methods for [[DirsAbs]], that require JVM, Java Virtual Machine. */
extension (thisPath: DirsAbs) {/** Writes the HTML File given in the second parameter to this full path and filename given by the first parameter. Returns a successful message on
 * success. */
  def htmlWrite(fileNameStem: String, page: HtmlPage): ErrBi[IOExc, HtmlFileWritten] = htmlFileStrWrite(thisPath /% fileNameStem + ".html", page.out)

  /** Writes the HTML File to this full path and filename given by the [[HtmlFilePAge]]. Returns a successful message on success. */
  def htmlWrite(page: HtmlPageFile): ErrBi[IOExc, HtmlFileWritten] = htmlFileStrWrite(thisPath /% page.fileName, page.out)
}