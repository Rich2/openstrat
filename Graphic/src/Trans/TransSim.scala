/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** All leaf classes of this type that will preserve their types for all the Similar 2d geometrical transformations. */
trait SimilarPreserve extends ProlignPreserve
{ type ThisT <: SimilarPreserve  
  def mirror(line: Line): ThisT = fTrans(_.mirror(line))
  def mirror(lineSeg: LineSeg): ThisT = fTrans(_.mirror(lineSeg))
  def rotate(angle: Angle): ThisT = rotateRadians(angle.radians)
  def rotateRadians(radians: Double): ThisT = fTrans(_.rotateRadians(radians))
 // def mirror(v1: Vec2, v2: Vec2): ThisT = fTrans(_.mirror(v1, v2))
}

/** A Similar Transformations type class */
trait TransSim[T] extends TransAlign[T]
{ def mirror(obj: T, line: Line): T
  def mirrorSeg(obj: T, lineSeg: LineSeg): T
  def rotateRadians(obj: T, radians: Double): T
}

object TransSim
{
  implicit def transSimerImplicit[T <: SimilarPreserve]: TransSim[T] = new TransSim[T]
  { override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def mirrorSeg(obj: T, line: LineSeg): T = obj.mirror(line).asInstanceOf[T]
    override def mirror(obj: T, line: Line): T = obj.mirror(line).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransSim[A]): TransSim[AA] = new TransSim[AA]
  { override def slate(obj: AA, offset: Vec2): AA = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map(ev.rotateRadians(_, radians))
    override def mirrorSeg(obj: AA, line: LineSeg): AA = obj.map(ev.mirrorSeg(_, line))
    override def mirror(obj: AA, line: Line): AA = obj.map(ev.mirror(_, line))
    override def scale(obj: AA, operand: Double): AA = obj.map(ev.scale(_, operand))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransSim[A]): TransSim[F[A]] = new TransSim[F[A]]
  { override def slate(obj: F[A], offset: Vec2): F[A] = evF.map(obj, ts => evA.slate(ts, offset))
    override def rotateRadians(obj: F[A], radians: Double): F[A] = evF.map(obj, ts => evA.rotateRadians(ts, radians))
    override def mirrorSeg(obj: F[A], line: LineSeg): F[A] = evF.map(obj, evA.mirrorSeg(_, line))
    override def mirror(obj: F[A], line: Line): F[A] = evF.map(obj, evA.mirror(_, line))
    override def scale(obj: F[A], operand: Double): F[A] = evF.map[A, A](obj, ts => evA.scale(ts, operand))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransSim[A]): TransSim[Array[A]] = new TransSim[Array[A]]
  { override def slate(obj: Array[A], offset: Vec2): Array[A] = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: Array[A], radians: Double): Array[A] = obj.map(ev.rotateRadians(_, radians))
    override def mirrorSeg(obj: Array[A], line: LineSeg): Array[A] = obj.map(ev.mirrorSeg(_, line))
    override def mirror(obj: Array[A], line: Line): Array[A] = obj.map(ev.mirror(_, line))
    override def scale(obj: Array[A], operand: Double): Array[A] = obj.map(ev.scale(_, operand))
  }
}

class TransSimExtension[T](value: T, ev: TransSim[T])
{ def mirror(line: Line) = ev.mirror(value, line)
  def mirror(lineSeg: LineSeg): T = ev.mirrorSeg(value, lineSeg)
  //def mirror(x1: Double, y1: Double, x2: Double, y2: Double): T = ev.mirror(value, new LineSeg(x1, y1, x2, y2))
 
  /** this.asInstanceOf[T] */
  def identity: T = this.asInstanceOf[T]

  /** The scale transformation on 2 dimensional vectors. */
  def scaleSlate(factor: Double, addVec: Vec2): T =
  { val r1 = ev.scale(value, factor)
    ev.slate(r1, addVec)
  }
}