/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package docjs
import geom.*, pSJs.*, scalajs.js.annotation.*, org.scalajs.dom.*, pDoc.TomcatPage.*

@JSExportTopLevel("TomcatPageJs")
object TomcatPageJs
{
  @JSExport def main(args: Array[String]): Unit =
  {
    deb("Starting TomcatPageJs")
    HtmlClassTextModder("uName", "nset", uName1)
    HtmlClassTextModder("cName", "cset", "ser")
  }

  /** Finds a text input in an HTML page and adds event listener to change values in the page. */
  case class HtmlClassTextModder(idStr: String, className: String, initValue: String)
  {
    val inpElem: html.Input = document.getElementById(idStr).asInstanceOf[html.Input]
    var currValue = initValue
    inpElem.addEventListener("change", e => {
      val newUserName = e.target.asInstanceOf[html.Input].value
      val array = document.getElementsByClassName(className)
      deb(s"Found ${array.length} instances of $className")
      array.foreach { sp1 =>
        val sp2 = sp1.asInstanceOf[HTMLElement]
        val str = sp2.textContent
        val regex = currValue.r
        val newText = regex.replaceAllIn(str, newUserName)
        sp2.textContent = newText
      }
      currValue = newUserName
    })
  }
} 