/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LsA12(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A12")
{
  val c1 = Circle(400).draw()
  val c2 = Circle(462).draw(2, DarkBlue)

  val hex = Hexlign(400)
  val hd = hex.draw()
  debvar(hex.s1.length)
  debvar(hex.s2.length)
  val htv = hex.vertsMap(v => Circle(50, v).fill(Yellow))
  val hts = hex.vertsMap(v => TextGraphic(v.str0, v, 20))
  repaint(htv ++ hts +- hd +- c1 +- c2)
}