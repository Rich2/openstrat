/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait TransAffer extends Any with TransSimer
{ type ThisT <: TransAffer
  def shear(xScale: Double, yScale: Double): ThisT
}

/** Affine Transformation */
trait TransAff[T] extends TransSim[T]
{ def shear(obj: T, xScale: Double, yScale: Double): T
}

object TransAff
{
  implicit def transAfferImplicit[T <: TransAffer]: TransAff[T] = new TransAff[T]
  { override def shear(obj: T, xScale: Double, yScale: Double): T = obj.shear(xScale, yScale).asInstanceOf[T]
    override def mirrorXOffset(obj: T, yOffset: Double): T = obj.mirrorXOffset(yOffset).asInstanceOf[T]
    override def mirrorYOffset(obj: T, xOffset: Double): T = obj.mirrorYOffset(xOffset).asInstanceOf[T]
    override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransAff[A]): TransAff[AA] = new TransAff[AA]
  {
    override def shear(obj: AA, xScale: Double, yScale: Double): AA = obj.map{ ta => ev.shear(ta, xScale, yScale)}
    override def scale(obj: AA, operand: Double): AA = obj.map{ts => ev.scale(ts, operand)}
    override def slate(obj: AA, offset: Vec2): AA = obj.map{ts => ev.slate(ts, offset)}
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map{ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: AA, xOffset: Double): AA = obj.map{ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: AA, yOffset: Double): AA = obj.map{ts => ev.mirrorXOffset(ts, yOffset) }
  }
}

class TransAffExtension[T](value: T, ev: TransAff[T])
{

}