/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*,geom.*, pSJs.*, scalajs.js.annotation.*, org.scalajs.dom.*

@JSExportTopLevel("TomcatPageJs")
object TomcatPageJs
{
  @JSExport def main(args: Array[String]): Unit =
  {
    println("Hello tomcat 3")
    val uName = document.getElementById("uName").asInstanceOf[html.Input]
    var currName = "tommy"
    uName.addEventListener("change", e => {
      deb(e.target.toString)
      val newName = e.target.asInstanceOf[html.Input].value
      println(newName)
      val array1 = document.getElementsByClassName("nset")
      array1.foreach(_.asInstanceOf[html.Span].textContent = newName)
      val array2 = document.getElementsByClassName("nsetmulti")
      array2.foreach{sp1 =>
        deb("multi found")
        val sp2 = sp1.asInstanceOf[HTMLElement]
        val str = sp2.textContent
        val regex = currName.r
        val newText = regex.replaceAllIn(str, newName)
        sp2.textContent = newText
      }
      currName = newName
    })
  }
} 