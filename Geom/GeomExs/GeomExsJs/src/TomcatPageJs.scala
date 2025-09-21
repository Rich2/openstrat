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
    uName.addEventListener("change", e => {
      println(e.target.toString)
      val newName = e.target.asInstanceOf[html.Input].value
      println(newName)
      val array1 = document.getElementsByClassName("nset")//.asInstanceOf[html.Span]
      array1.foreach(_.asInstanceOf[html.Span].textContent = newName)
    })
  }
} 