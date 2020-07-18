/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Square extends Rectangle
{ def height: Double = width
}

/** Companion object for the Square trait. However its apply methods delegate to the SquareClass implementation class. */
object Square
{
  @inline def apply(width: Double, cen: Vec2 = Vec2Z, rotation: Angle): SquareClass = SquareClass(width, cen.x, cen.y, rotation)

  @inline def apply(width: Double, xCen: Double, yCen: Double, rotation: Angle): SquareClass = SquareClass(width, xCen, yCen, rotation)
}