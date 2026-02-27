/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** A quick and crude method for creating / overwriting an HTML file in the user's home directory. It takes 2 strings. The first is used for the HTML title
 * and the file name. The second is contents of the HTML body element. */
def homeHtmlWrite(dir: String, title: String, bodyContent: String): ErrBi[Exception, FileWritten] =
{ val home: String = System.getProperty("user.home")
  utiljvm.writeFile(home / dir / title + ".html", HtmlPage.titleOnly(title, bodyContent).out)
}

/** Writes the content to an [[HTML]] file to the opensett folder in the users home directory. Not sure if this a desirable facility. */
def opensettHtmlWrite(title: String, bodyContent: String) =
{ val home: String = System.getProperty("user.home")
  utiljvm.writeFile(home / "opensett" / title + ".html", HtmlPage.titleOnly(title, bodyContent).out)
}

/** HTTP now time object. */
object HttpNow extends HttpDate(utiljvm.httpNow)

implicit class HtmlPageFileExtensions(thisPage: HtmlPageFile)
{
  def writeFile(dirPathStr: String): ErrBi[IOExc, HtmlFileWritten] = utiljvm.writeFile(dirPathStr / thisPage.fileName, thisPage.out).map(_.html)
}

implicit class CssRulesHolderExtensions(thisRules: CssRulesHolder)
{
  def writeFile(dirPathStr: String, fileNameStem: String): ErrBi[IOExc, CssFileWritten] = utiljvm.writeFile(dirPathStr / fileNameStem + ".css", thisRules.out).map(_.css)
}