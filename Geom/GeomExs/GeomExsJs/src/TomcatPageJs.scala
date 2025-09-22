/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*,geom.*, pSJs.*, scalajs.js.annotation.*, org.scalajs.dom.*

@JSExportTopLevel("TomcatPageJs")
object TomcatPageJs
{
  @JSExport def main(args: Array[String]): Unit =
  {
    deb("Starting TomcatPageJs")
    val uName = document.getElementById("uName").asInstanceOf[html.Input]
    var currName = "tommy"
    uName.addEventListener("change", e => {
      val newUserName = e.target.asInstanceOf[html.Input].value
      debvar(newUserName)      
      val array2 = document.getElementsByClassName("nset")
      array2.foreach{sp1 =>
        val sp2 = sp1.asInstanceOf[HTMLElement]
        val str = sp2.textContent
        val regex = currName.r
        val newText = regex.replaceAllIn(str, newUserName)
        sp2.textContent = newText
      }
      currName = newUserName
    })
  }
} 