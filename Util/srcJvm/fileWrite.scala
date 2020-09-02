/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Function object for writing a file. */
object fileWrite
{ /** Writes the String given in the second parameter to the full path and filename given by the first name. Returns a successful message on success.*/
  def apply(pathFileName: String, str: String): EMon[String] =
  { import java.io._
    var eStr: String = ""
    var opw: Option[FileWriter] = None

    try { opw = Some(new FileWriter(new File(pathFileName)))
      opw.get.write(str)
    }

    catch { case e: Throwable => eStr = e.toString }
    finally{ opw.foreach(_.close()) }
    if (eStr == "") Good("Successfully written file to " + pathFileName) else Bad(Arr(eStr))
  }
}

object homeWrite
{
  def apply(fileName: String, str: String): EMon[String] =
  { val h = System.getProperty("user.home")
    fileWrite(h + "/" + fileName, str)
  }
}

object homeHtmlWrite
{
  import pWeb._
  /** A quick and crude method for creating / overwriting an html file in the user's home directory. It takes Two strings. The first is used for the
   *  HTML title and the file name. The second is contents of the HTML body element.  */
  def apply(title: String, bodyContent: String): EMon[String] =
  { val h = System.getProperty("user.home")
    fileWrite(h + "/" + title + ".html", HtmlPage.title(title, bodyContent).out)
  }
}