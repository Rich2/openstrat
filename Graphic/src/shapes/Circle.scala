/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

object CircleIcon

final case class Circle(radius: Double, xCen: Double, yCen: Double) extends GeomElemNew with Ellipse
{ /** This is wong. */
  override def fTrans(f: Vec2 => Vec2): GeomElemNew = { deb("This is wrong."); Circle(radius, f(cen)) }
  
  override def slate(offset: Vec2): Circle = Circle(radius, cen + offset)

  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): Circle = Circle(radius, xCen + xOffset, yCen + yOffset)

  override def scale(operand: Double): Circle = Circle(radius * operand, cen * operand)

  override def mirrorXOffset(yOffset: Double): Circle = Circle(radius, cen.mirrorXOffset(yOffset))

  override def mirrorX: Circle = Circle(radius, xCen, -yCen)

  override def mirrorYOffset(xOffset: Double): Circle = Circle(radius, cen.mirrorYOffset(xOffset))

  override def mirrorY: Circle = Circle(radius, -xCen, yCen)

  override def prolign(matrix: ProlignMatrix): Circle = Circle(radius * matrix.vFactor, cen.prolignTrans(matrix))

  override def fill(colour: Colour): GraphicElemNew = ???
}

/** This object provides factory methods for circles. */
object Circle
{ def apply(radius: Double, cen: Vec2 = Vec2Z): Circle = new Circle(radius, cen.x, cen.y)
  implicit val slateImplicit: Slate[Circle] = (circle, offset) => circle.slate(offset)
}
