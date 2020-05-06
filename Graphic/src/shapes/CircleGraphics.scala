/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv._, Colour.Black

case class CircleFill(circle: Circle, colour: Colour) extends TransSimerUser with PaintElem
{ override type AlignT = CircleFill
  override type MemT = Circle
  override def geomMem: MemT = circle
  override def newThis(transer: Circle): CircleFill = CircleFill(transer, colour)
  def shear(xScale: Double, yScale: Double): TranserAll = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)
}

case class CircleDraw(circle: Circle, lineWidth: Double, colour: Colour) extends TransSimerUser with PaintElem
{ override type AlignT = CircleDraw
  override type MemT = Circle
  override def geomMem: MemT = circle
  override def newThis(transer: Circle): CircleDraw = CircleDraw(transer, lineWidth, colour)
  def shear(xScale: Double, yScale: Double): TranserAll = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDraw(this)
}

case class CircleFillDraw(circle: Circle, fillColour: Colour, lineWidth: Double = 2.0, lineColour: Colour = Black) extends TransSimerUser
  with PaintElem
{ override type AlignT = CircleFillDraw
  override type MemT = Circle
  override def geomMem: MemT = circle
  override def newThis(transer: Circle): CircleFillDraw = CircleFillDraw(transer, fillColour, lineWidth, lineColour)
  override def shear(xScale: Double, yScale: Double): TranserAll = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillDraw(this)
}