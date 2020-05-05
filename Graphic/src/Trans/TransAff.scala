/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait TransAffer extends Any with TransSimer
{ type AlignT <: TransAligner
  def shear(xScale: Double, yScale: Double): TransAffer
}

/** Affine Transformation */
trait TransAff[T] extends TransSim[T]
{ def shear(obj: T, xScale: Double, yScale: Double): T
}

object TransAff
{
  implicit def transAfferImplicit[T <: TransAffer]: TransAff[T] = new TransAff[T]
  { override def shear(obj: T, xScale: Double, yScale: Double): T = obj.shear(xScale, yScale).asInstanceOf[T]
    override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
    override def mirror(obj: T, line: Line2): T = obj.mirror(line).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransAff[A]): TransAff[AA] = new TransAff[AA]
  { override def shear(obj: AA, xScale: Double, yScale: Double): AA = obj.map(ev.shear(_, xScale, yScale))
    override def scale(obj: AA, operand: Double): AA = obj.map(ev.scale(_, operand))
    override def slate(obj: AA, offset: Vec2): AA = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map(ev.rotateRadians(_, radians))
    override def mirror(obj: AA, line: Line2): AA = obj.map(ev.mirror(_, line))
  }
}

class TransAffExtension[T](value: T, ev: TransAff[T])
{

}