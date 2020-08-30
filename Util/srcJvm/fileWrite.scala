/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

object fileWrite
{
  def apply(fileName: String, str: String): EMon[Unit] =
  { import java.io._
    var eStr: String = ""
    var opw: Option[FileWriter] = None

    try { opw = Some(new FileWriter(new File(fileName)))
      opw.get.write(str)
    }

    catch { case e: Throwable => eStr = e.toString }
    finally{ opw.foreach(_.close()) }
    if (eStr == "") Good(()) else Bad(Arr(eStr))
  }
}

object homeWrite
{
  def apply(fileName: String, str: String): EMon[Unit] =
  { val h = System.getProperty("user.home")
    fileWrite(h + "/" + fileName, str)
  }
}

object homeHtmlWrite
{
  import pWeb._
  /** Takes Two strings. The first is used for the HTML title and the file name. The second is contents of the HTML body element.  */
  def apply(title: String, bodyContent: String): EMon[Unit] =
  { val h = System.getProperty("user.home")
    fileWrite(h + "/" + title + ".html", HtmlPage.title(title, bodyContent).out)
  }
}
