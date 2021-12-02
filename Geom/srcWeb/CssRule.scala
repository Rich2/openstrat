/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** CSS declaration */
trait CssDec
{ def prop: String
  def valueStr: String
  def out: String = prop + ": " + valueStr + ";"
}

/** CSS background-color property. */
case class CssBGColour(colour: Colour) extends CssDec
{ override def prop: String = "background-color"
  override def valueStr: String = colour.webStr
}

trait CssRule
{ def selec: String
  def props: Arr[CssDec]
  def out: String = selec + props.foldHeadTail("")(_.out, _ + "\n" + _).enCurly
}

case class CssBody(props: Arr[CssDec]) extends CssRule
{
  override def selec: String = "body"
}


object homeHtmlWrite
{ /** A quick and crude method for creating / overwriting an html file in the user's home directory. It takes 2 strings. The first is used for the
 * HTML title and the file name. The second is contents of the HTML body element.  */
  def apply(dir: String, title: String, bodyContent: String): EMon[String] =
  { val h = System.getProperty("user.home")
    fileWrite(h -/- dir, title + ".html", HtmlPage.titleOnly(title, bodyContent).out)
  }
}

object opensettHtmlWrite
{
  def apply(title: String, bodyContent: String): EMon[String] =
  { val h: String = System.getProperty("user.home")
    fileWrite(h + "/opensett", title + ".html", HtmlPage.titleOnly(title, bodyContent).out)
  }
}