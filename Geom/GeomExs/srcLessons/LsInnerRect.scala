/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

object LsInnerRect extends LessonStatic
{ override val title: String = "Inner Rect"
  override def bodyStr: String = "Inner rectangles."
  val hex: HexParrX = HexParrX(400)
  val hd: PolygonDraw = hex.draw()
  val br: Rect = hex.boundingRect
  val brd = br.draw(1, Red)
  val hi = hex.inRectFrom(Pt2(0, 0), 2)
  val sps = br.spacedPts(5, 5)
  val ccs = sps.map(Circle.d(10, _).fill(Violet))
  val ipoly = PolygonGen.dbls(400,400, 450,380, 455,280, 520,280, 520,230, 450,220, 460,100, 300,0)
  val ipd = ipoly.draw()
  val icen = Pt2(470, 260)
  val cross = Cross.draw(icen)
  val in2: Rect = ipoly.inRectFrom(icen, 1)
  val out2 = ipoly.boundingRect.draw(1, Green)
  val in2d = in2.draw(1, Blue)
  val in2d2 = ipoly.inRect(1).draw(2, Violet)
  override def output: GraphicElems = RArr(hd, brd, hi.draw(2, Blue), ipd, in2d, in2d2) ++ ccs +% cross
}