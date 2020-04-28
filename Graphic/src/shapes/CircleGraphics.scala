/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv._, Colour.Black

case class CircleFill(circle: Circle, colour: Colour) extends TransSimerUser with PaintElem
{ override type RigidT = CircleFill
  override type MemT = Circle
  override def geomMem: MemT = circle
  override def newThis(transer: Circle): CircleFill = CircleFill(transer, colour)

  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)
}

case class CircleDraw(circle: Circle, lineWidth: Double, colour: Colour) extends TransSimerUser with PaintElem
{ override type RigidT = CircleDraw
  override type MemT = Circle
  override def geomMem: MemT = circle
  override def newThis(transer: Circle): CircleDraw = CircleDraw(transer, lineWidth, colour)

  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDraw(this)
}

case class CircleFillDraw(circle: Circle, fillColour: Colour, lineWidth: Double = 2.0, lineColour: Colour = Black) extends TransSimerUser
  with PaintElem
{ override type RigidT = CircleFillDraw
  override type MemT = Circle
  override def geomMem: MemT = circle
  override def newThis(transer: Circle): CircleFillDraw = CircleFillDraw(transer, fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillDraw(this)
}