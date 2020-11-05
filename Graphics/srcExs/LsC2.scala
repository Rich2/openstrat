/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LsC2(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C2")
{
  repaints(TextGraphic("Please click on the screen in different places.", 0 pp 200, 28, Green))
  setMouseSimple(v => repaints(TextGraphic("You clicked the screen at " + v.strCommaNames, v, 28, Red)))
}