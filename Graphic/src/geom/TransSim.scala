/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait TransSimer extends TransRigider
{ type ThisT <: TransSimer
  def scale(operand: Double): ThisT
}

/** A Similar Transformations type class */
trait TransSim[T] extends TransRigid[T]
{ def scale(obj: T, operand: Double): T
}

trait TransSimerUser extends TransSimer
{ type ThisT <: TransSimerUser
  type MemT <: TransSimer
  def geomMem: MemT
  def newThis(transer: MemT): ThisT
  override def slate(offset: Vec2): ThisT = newThis(geomMem.slate(offset).asInstanceOf[MemT])
  override def rotateRadians(radians: Double): ThisT = newThis(geomMem.rotateRadians(radians).asInstanceOf[MemT])
  override def mirrorYOffset(xOffset: Double): ThisT = newThis(geomMem.mirrorYOffset(xOffset).asInstanceOf[MemT])
  override def mirrorXOffset(yOffset: Double): ThisT = newThis(geomMem.mirrorXOffset(yOffset).asInstanceOf[MemT])
  override def scale(operand: Double): ThisT = newThis(geomMem.scale(operand).asInstanceOf[MemT])
}
