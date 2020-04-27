/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv._

case class CircleFill(circle: Circle, colour: Colour) extends TransSimerUser with PaintElem
{ override type ThisT = CircleFill
  override type MemT = Circle
  override def geomMem: MemT = circle
  override def newThis(transer: Circle): CircleFill = CircleFill(transer, colour)

  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)
}

case class CircleDraw(circle: Circle, lineWidth: Double, colour: Colour) extends TransSimerUser with PaintElem
{ override type ThisT = CircleDraw
  override type MemT = Circle
  override def geomMem: MemT = circle
  override def newThis(transer: Circle): CircleDraw = CircleDraw(transer, lineWidth, colour)

  override def rendToCanvas(cp: CanvasPlatform): Unit = ??? // cp.circleFill(this)
}