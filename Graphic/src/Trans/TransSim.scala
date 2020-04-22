/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait TransSimer extends Any with TransRigider
{ type ThisT <: TransSimer
  def scale(operand: Double): ThisT
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

/** A Similar Transformations type class */
trait TransSim[T] extends TransRigid[T]
{ def scale(obj: T, operand: Double): T
}

object TransSim
{
  implicit def transSimerImplicit[T <: TransSimer]: TransSim[T] = new TransSim[T]
  { override def mirrorXOffset(obj: T, yOffset: Double): T = obj.mirrorXOffset(yOffset).asInstanceOf[T]
    override def mirrorYOffset(obj: T, xOffset: Double): T = obj.mirrorYOffset(xOffset).asInstanceOf[T]
    override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransSim[A]): TransSim[AA] = new TransSim[AA]
  { override def scale(obj: AA, operand: Double): AA = obj.map{ts => ev.scale(ts, operand)}
    override def slate(obj: AA, offset: Vec2): AA = obj.map{ts => ev.slate(ts, offset)}
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map{ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: AA, xOffset: Double): AA = obj.map{ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: AA, yOffset: Double): AA = obj.map{ts => ev.mirrorXOffset(ts, yOffset) }
  }
}