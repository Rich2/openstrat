/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import java.io.{File, FileWriter}

/** A quick and crude method for creating / overwriting an HTML file in the user's home directory. It takes 2 strings. The first is used for the HTML title
 * and the file name. The second is contents of the HTML body element. */
def homeHtmlWrite(dir: String, title: String, bodyContent: String): ErrBi[Exception, FileWritten] =
{ val home: String = System.getProperty("user.home")
  utiljvm.writeFile(home / dir / title + ".html", PageHtml.titleOnly(title, bodyContent).out)
}

/** Writes the content to an [[HTML]] file to the opensett folder in the users home directory. Not sure if this a desirable facility. */
def opensettHtmlWrite(title: String, bodyContent: String) =
{ val home: String = System.getProperty("user.home")
  utiljvm.writeFile(home / "opensett" / title + ".html", PageHtml.titleOnly(title, bodyContent).out)
}

/** HTTP now time object. */
object HttpNow extends HttpDate(utiljvm.gmtNowStr)

/** Writes the String given in the second parameter to the full path and filename given by the first name. Returns an HTML successful message on success. */
def writeStrsHtml(dirsFileNameStr: String, content: String): ErrBi[IOExc, HtmlFileWritten] =
{ var oErr: Option[IOExc] = None
  var opw: Option[FileWriter] = None
  try { opw = Some(new FileWriter(new File(dirsFileNameStr)))
    opw.get.write(content)
  }
  catch { case e: IOExc => oErr = Some(e) }
  finally { opw.foreach(_.close()) }
  oErr.fld(Succ(HtmlFileWritten(dirsFileNameStr)), FailIO(_))
}

/** Writes the String given in the second parameter to the full path and filename given by the first name. Returns a CSS successful message on success. */
def writeStrsCss(dirsFileNameStr: String, content: String): ErrBi[IOExc, CssFileWritten] =
{ var oErr: Option[IOExc] = None
  var opw: Option[FileWriter] = None
  try { opw = Some(new FileWriter(new File(dirsFileNameStr)))
    opw.get.write(content)
  }
  catch { case e: IOExc => oErr = Some(e) }
  finally { opw.foreach(_.close()) }
  oErr.fld(Succ(CssFileWritten(dirsFileNameStr)), FailIO(_))
}