/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._

object LsAInner extends GraphicsAE
{ override val title: String = "Inner Rect"
  override def bodyStr: String = "Inner rectangles."
  val hex: HexParrX = HexParrX(400)
  val hd: PolygonDraw = hex.draw()
  val bd: RectDraw = hex.boundingRect.draw(1, Red)
  val hi = hex.innerRect(Pt2(0, 0), 2)
  val hex2 = hex.vertsMultiply(4)
  val ccs = hex2.vertsMap(Circle(15, _).fill(Violet))
  val ipoly = PolygonGen.fromDbls(400,400, 450,380, 455,280, 520,280, 520,230, 450,220, 460,100, 300,0)
  val ipd = ipoly.draw()
  val icen = 470 pp 260
  val cross: RArr[LineSegDraw] = Cross(0.8, icen)
  val in2: Rect = ipoly.innerRect(icen, 1)
  val out2 = ipoly.boundingRect.draw(1, Green)
  val in2d = in2.draw(1, Blue)
  override def output: GraphicElems = RArr(hd, bd, hi.draw(2, Blue), ipd, in2d) ++ ccs ++ cross
}