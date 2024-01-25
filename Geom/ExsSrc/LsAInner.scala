/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._

object LsAInner extends GraphicsAE{
  override val title: String = "Inner Rect"
  override def bodyStr: String = "Inner rectangles."
  val hex = HexParrX(200)
  val hd = hex.draw()
  override def output: GraphicElems = RArr(hd)
}