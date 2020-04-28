/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Elliptical Arc, not correct */
final class EArc extends TransAffer
{ override type RigidT = EArc
  override def rotate(angle: Angle): EArc = ???
  override def mirrorYOffset(xOffset: Double): EArc = ???
  override def mirrorXOffset(yOffset: Double): EArc = ???
  override def slate(offset: Vec2): EArc = ???
  override def rotateRadians(radians: Double): EArc = ???
  override def scale(operand: Double): EArc = ???
  override def shear(xScale: Double, yScale: Double): EArc = ???
}

object EArc
{
  def apply(): EArc = new EArc
}

