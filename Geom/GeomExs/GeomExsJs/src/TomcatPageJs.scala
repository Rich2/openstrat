/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*,geom.*, pSJs.*, scalajs.js.annotation.*, org.scalajs.dom.*

@JSExportTopLevel("TomcatPageJs")
object TomcatPageJs
{
  @JSExport def main(args: Array[String]): Unit =
  {
    println("Hello tomcat")
    val uName = document.querySelector("input").asInstanceOf[html.Input]
    uName.addEventListener("change", e => println("New value"))
  }
} 