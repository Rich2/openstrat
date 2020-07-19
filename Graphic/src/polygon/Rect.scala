/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A rectangle class that has position and may not be aligned to the X and Y axes. */
final case class Rect(x0: Double, y0: Double, x1: Double, y1: Double, width: Double) extends RectV0V1 with AffinePreserve
{ override type ThisT = Rect
  override def height: Double = (v1 - v2).magnitude
  override def fTrans(f: Vec2 => Vec2): Rect = Rect.points(f(cen), f(v0), f(v1))

  override def rotation: Angle = (v0 - v3).angle

  override def fill(fillColour: Colour): ShapeFill = ???

  override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

  override def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???
}


object Rect
{ def points(cen: Vec2, topRight: Vec2, bottomRight: Vec2): Rect = ??? // new Rect(cen.x, cen.y, topRight.x, topRight.y, bottomRight.x, bottomRight.y)
}