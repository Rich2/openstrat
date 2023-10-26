/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** A Similar Transformations type class */
trait TransSim[T] extends TransAlign[T]
{ def reflectT(obj: T, line: LineLike): T
  def rotate(obj: T, angle: AngleVec): T
}

/** Companion object for the TranSim transformation type class trait. */
object TransSim
{
  implicit def transSimerImplicit[T <: SimilarPreserve]: TransSim[T] = new TransSim[T]
  { override def rotate(obj: T, angle: AngleVec): T = obj.rotate(angle).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2Like): T = obj.slate(offset).asInstanceOf[T]
    override def reflectT(obj: T, line: LineLike): T = obj.reflect(line).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: Arr[A]](implicit build: BuilderArrMap[A, AA], ev: TransSim[A]): TransSim[AA] = new TransSim[AA]
  { override def slate(obj: AA, offset: Vec2Like): AA = obj.map(ev.slate(_, offset))
    override def rotate(obj: AA, angle: AngleVec): AA = obj.map(ev.rotate(_, angle))
    override def reflectT(obj: AA, line: LineLike): AA = obj.map(ev.reflectT(_, line))
    override def scale(obj: AA, operand: Double): AA = obj.map(ev.scale(_, operand))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransSim[A]): TransSim[F[A]] = new TransSim[F[A]]
  { override def slate(obj: F[A], offset: Vec2Like): F[A] = evF.mapT(obj, ts => evA.slate(ts, offset))
    override def rotate(obj: F[A], angle: AngleVec): F[A] = evF.mapT(obj, ts => evA.rotate(ts, angle))
    override def reflectT(obj: F[A], line: LineLike): F[A] = evF.mapT(obj, evA.reflectT(_, line))
    override def scale(obj: F[A], operand: Double): F[A] = evF.mapT[A, A](obj, ts => evA.scale(ts, operand))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransSim[A]): TransSim[Array[A]] = new TransSim[Array[A]]
  { override def slate(obj: Array[A], offset: Vec2Like): Array[A] = obj.map(ev.slate(_, offset))
    override def rotate(obj: Array[A], angle: AngleVec): Array[A] = obj.map(ev.rotate(_, angle))
    override def reflectT(obj: Array[A], line: LineLike): Array[A] = obj.map(ev.reflectT(_, line))
    override def scale(obj: Array[A], operand: Double): Array[A] = obj.map(ev.scale(_, operand))
  }
}

class TransSimExtension[T](value: T, ev: TransSim[T])
{ def reflect(line: Line) = ev.reflectT(value, line)
  def reflect(lineSeg: LineSeg): T = ev.reflectT(value, lineSeg)
   
  /** this.asInstanceOf[T] */
  def identity: T = this.asInstanceOf[T]

  /** The scale transformation on 2 dimensional vectors. */
  def scaleSlate(factor: Double, addVec: Vec2Like): T =
  { val r1 = ev.scale(value, factor)
    ev.slate(r1, addVec)
  }
}