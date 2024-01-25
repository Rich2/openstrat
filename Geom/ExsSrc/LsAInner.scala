/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._

object LsAInner extends GraphicsAE{
  override val title: String = "Inner Rect"
  override def bodyStr: String = "Inner rectangles."
  val hex = HexParrX(400)
  val hd = hex.draw()
  val bd = hex.boundingRect.draw(1, Red)
  val hi = hex.innerRectApprox(Pt2(0, 0), 1)
  override def output: GraphicElems = RArr(hd, bd, hi.fill(Blue))
}