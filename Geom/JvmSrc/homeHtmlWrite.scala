/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utiljvm._

object homeHtmlWrite
{ /** A quick and crude method for creating / overwriting an html file in the user's home directory. It takes 2 strings. The first is used for the
 * HTML title and the file name. The second is contents of the HTML body element. */
  def apply(dir: String, title: String, bodyContent: String): EMon[String] =
  { val home: String = System.getProperty("user.home")
    fileWrite(home / dir, title + ".html", HtmlPage.titleOnly(title, bodyContent).out)
  }
}

object opensettHtmlWrite
{
  def apply(title: String, bodyContent: String): EMon[String] =
  { val home: String = System.getProperty("user.home")
    fileWrite(home + "/opensett", title + ".html", HtmlPage.titleOnly(title, bodyContent).out)
  }
}