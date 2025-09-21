/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*,geom.*, pSJs.*, scalajs.js.annotation.*, org.scalajs.dom.*

@JSExportTopLevel("TomcatPageJs")
object TomcatPageJs
{
  @JSExport def main(args: Array[String]): Unit =
  {
    println("Hello tomcat 2")
    val uName = document.querySelector("input").asInstanceOf[html.Input]
    uName.addEventListener("change", e => {
      println(e.toString)
      println(e.target.toString)
      println(e.target.asInstanceOf[html.Input].value. toString)
    })
  }
} 