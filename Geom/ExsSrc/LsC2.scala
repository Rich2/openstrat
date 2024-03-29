/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

/** Lesson C2. Shows the location and [[Pt2]] coordinates of the point on the screen the user has clicked. */
case class LsC2(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C2")
{ repaints(TextFixed("Please click on the screen in different places.", 28, 0 pp 200, Green))
  setMouseSimple(pt => repaints(TextFixed("You clicked the screen at: " + pt.tellSemisNames(), 28, pt, Red)))
}