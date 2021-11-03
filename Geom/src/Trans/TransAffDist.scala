/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait TransAffDister extends Any with TransSimDister
{ type ThisT <: TransAffDister
  def shear(xScale: Double, yScale: Double): ThisT
}

/** AffDistine Transformation */
trait TransAffDist[T] extends TransSimDist[T]
{ def shear(obj: T, xScale: Double, yScale: Double): T
}

object TransAffDist
{
  implicit def transAffDisterImplicit[T <: TransAffDister]: TransAffDist[T] = new TransAffDist[T]
  { override def shear(obj: T, xScale: Double, yScale: Double): T = obj.shear(xScale, yScale).asInstanceOf[T]
    override def mirrorXOffset(obj: T, yOffset: Metres): T = obj.mirrorXOffset(yOffset).asInstanceOf[T]
    override def mirrorYOffset(obj: T, xOffset: Metres): T = obj.mirrorYOffset(xOffset).asInstanceOf[T]
    override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: PtMetre2): T = obj.slate(offset).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuilder[A, AA], ev: TransAffDist[A]): TransAffDist[AA] = new TransAffDist[AA]
  {
    override def shear(obj: AA, xScale: Double, yScale: Double): AA = obj.map{ ta => ev.shear(ta, xScale, yScale) }
    override def scale(obj: AA, operand: Double): AA = obj.map{ts => ev.scale(ts, operand)}
    override def slate(obj: AA, offset: PtMetre2): AA = obj.map{ ts => ev.slate(ts, offset)}
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map{ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: AA, xOffset: Metres): AA = obj.map{ ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: AA, yOffset: Metres): AA = obj.map{ ts => ev.mirrorXOffset(ts, yOffset) }
  }
}

class TransAffDistExtension[T](value: T, ev: TransAffDist[T])