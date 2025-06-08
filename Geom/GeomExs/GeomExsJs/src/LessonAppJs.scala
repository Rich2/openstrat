/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*,geom.*, pSJs.*, scalajs.js.annotation.*

@JSExportTopLevel("LessonAppJs")
object LessonAppJs
{ @JSExport def main(args: Array[String]): Unit = LsArcs1.canv(CanvasJs)
} 