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

  val sides = hex.slateX(500).sides.iMap{ (i, ls) => ls.draw(2, Colours.rainbow(i)) }
  override def output: GraphicElems = RArr(hd, bd, hi.draw(2, Blue)) ++ sides ++ ccs
}