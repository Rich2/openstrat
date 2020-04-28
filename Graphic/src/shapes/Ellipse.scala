/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait EllipseLike extends TransAffer
{ type RigidT <: EllipseLike
  override def shear(xScale: Double, yScale: Double): Ellipse
}

class Ellipse extends EllipseLike with TransAffer
{ type RigidT = Ellipse
  override def shear(xScale: Double, yScale: Double): Ellipse = this
  override def rotate(angle: Angle): Ellipse = this
  override def mirrorXOffset(yOffset: Double):  Ellipse = this
  override def mirrorYOffset(xOffset: Double):  Ellipse = this
  override def rotateRadians(radians: Double):  Ellipse = this
  override def slate(offset: Vec2):  Ellipse = this
  override def scale(operand: Double):  Ellipse = this
}
