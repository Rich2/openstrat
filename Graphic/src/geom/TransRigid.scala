/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait TransRigider extends Any
{ type ThisT <: TransRigider
  def slate(offset: Vec2): ThisT
  def rotateRadians(radians: Double): ThisT
  def rotate(angle: Angle): ThisT = rotateRadians(angle.radians)
  def mirrorYOffset(xOffset: Double): ThisT
  def mirrorXOffset(yOffset: Double): ThisT
  def mirrorY: ThisT = mirrorYOffset(0)
  def mirrorX: ThisT = mirrorXOffset(0)
  def ySlate(yDelta: Double): ThisT = slate(0 vv yDelta)
  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): ThisT = slate(xOffset vv yOffset)
}

/** A Rigid or Euclidean transformations type class. */
trait TransRigid[T]
{
  def slate(obj: T, offset: Vec2): T
  def rotateRadians(obj: T, radians: Double): T
  def mirrorYOffset(obj: T, xOffset: Double): T
  def mirrorXOffset(obj: T, yOffset: Double): T
}