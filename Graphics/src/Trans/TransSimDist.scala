/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait TransSimDister extends Any with TransRigidDister
{ type ThisT <: TransSimDister
  def scale(operand: Double): ThisT
}

trait TransSimDisterUser extends TransSimDister
{ type ThisT <: TransSimDisterUser
  type MemT <: TransSimDister
  def geomMem: MemT
  def newThis(transer: MemT): ThisT
  override def slate(offset: PtMetre2): ThisT = newThis(geomMem.slate(offset).asInstanceOf[MemT])
  override def rotateRadians(radians: Double): ThisT = newThis(geomMem.rotateRadians(radians).asInstanceOf[MemT])
  override def mirrorYOffset(xOffset: Metres): ThisT = newThis(geomMem.mirrorYOffset(xOffset).asInstanceOf[MemT])
  override def mirrorXOffset(yOffset: Metres): ThisT = newThis(geomMem.mirrorXOffset(yOffset).asInstanceOf[MemT])
  override def scale(operand: Double): ThisT = newThis(geomMem.scale(operand).asInstanceOf[MemT])
}

/** A Similar Transformations type class for Dist2 distance 2 dimensional vectors. */
trait TransSimDist[T] extends TransRigidDist[T]
{ def scale(obj: T, operand: Double): T
}

object TransSimDist
{
  implicit def transSimDisterImplicit[T <: TransSimDister]: TransSimDist[T] = new TransSimDist[T]
  { override def mirrorXOffset(obj: T, yOffset: Metres): T = obj.mirrorXOffset(yOffset).asInstanceOf[T]
    override def mirrorYOffset(obj: T, xOffset: Metres): T = obj.mirrorYOffset(xOffset).asInstanceOf[T]
    override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: PtMetre2): T = obj.slate(offset).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuilder[A, AA], ev: TransSimDist[A]): TransSimDist[AA] = new TransSimDist[AA]
  { override def scale(obj: AA, operand: Double): AA = obj.map{ts => ev.scale(ts, operand)}
    override def slate(obj: AA, offset: PtMetre2): AA = obj.map{ ts => ev.slate(ts, offset)}
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map{ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: AA, xOffset: Metres): AA = obj.map{ ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: AA, yOffset: Metres): AA = obj.map{ ts => ev.mirrorXOffset(ts, yOffset) }
  }
}

class TransSimDistExtension[T](value: T, ev: TransSimDist[T]) extends RotateGenExtensions[T]
{ override def rotateRadians(r: Double): T = ev.rotateRadians(value, r)
  override def rotate(angle: AngleVec): T = ev.rotateRadians(value, angle.radians)
}