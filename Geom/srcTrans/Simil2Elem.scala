/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

trait Simil2Elem extends Any, Axlign2Elem
{ /** Rotation 2D geometric transformation on a GeomElem. The return type will be narrowed in subclasses and traits. */
  def rotate(rotation: AngleVec): Simil2Elem

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GeomElem. The return type will be narrowed in subclasses and traits. */
  def reflect(lineLike: LineLike): Simil2Elem
}

/** A Similar Transformations type class */
trait Simil2Trans[T] extends TransAlign[T]
{ def reflectT(obj: T, line: LineLike): T
  def rotate(obj: T, angle: AngleVec): T
}

/** Companion object for the [[Simil2Trans]] geometric transformation set type class trait. */
object Simil2Trans
{
  implicit def transSimerImplicit[T <: SimilarPreserve]: Simil2Trans[T] = new Simil2Trans[T]
  { override def rotate(obj: T, angle: AngleVec): T = obj.rotate(angle).asInstanceOf[T]
    override def slate(obj: T, offset: VecPt2): T = obj.slate(offset).asInstanceOf[T]
    override def reflectT(obj: T, line: LineLike): T = obj.reflect(line).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: Arr[A]](implicit build: BuilderMapArr[A, AA], ev: Simil2Trans[A]): Simil2Trans[AA] = new Simil2Trans[AA]
  { override def slate(obj: AA, offset: VecPt2): AA = obj.map(ev.slate(_, offset))
    override def rotate(obj: AA, angle: AngleVec): AA = obj.map(ev.rotate(_, angle))
    override def reflectT(obj: AA, line: LineLike): AA = obj.map(ev.reflectT(_, line))
    override def scale(obj: AA, operand: Double): AA = obj.map(ev.scale(_, operand))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Simil2Trans[A]): Simil2Trans[F[A]] = new Simil2Trans[F[A]]
  { override def slate(obj: F[A], offset: VecPt2): F[A] = evF.mapT(obj, ts => evA.slate(ts, offset))
    override def rotate(obj: F[A], angle: AngleVec): F[A] = evF.mapT(obj, ts => evA.rotate(ts, angle))
    override def reflectT(obj: F[A], line: LineLike): F[A] = evF.mapT(obj, evA.reflectT(_, line))
    override def scale(obj: F[A], operand: Double): F[A] = evF.mapT[A, A](obj, ts => evA.scale(ts, operand))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Simil2Trans[A]): Simil2Trans[Array[A]] = new Simil2Trans[Array[A]]
  { override def slate(obj: Array[A], offset: VecPt2): Array[A] = obj.map(ev.slate(_, offset))
    override def rotate(obj: Array[A], angle: AngleVec): Array[A] = obj.map(ev.rotate(_, angle))
    override def reflectT(obj: Array[A], line: LineLike): Array[A] = obj.map(ev.reflectT(_, line))
    override def scale(obj: Array[A], operand: Double): Array[A] = obj.map(ev.scale(_, operand))
  }
}

class TransSimExtension[T](value: T, ev: Simil2Trans[T])
{ def reflect(line: Line) = ev.reflectT(value, line)
  def reflect(lineSeg: LSeg2): T = ev.reflectT(value, lineSeg)

  /** this.asInstanceOf[T] */
  def identity: T = this.asInstanceOf[T]

  /** The scale transformation on 2 dimensional vectors. */
  def scaleSlate(factor: Double, addVec: VecPt2): T =
  { val r1 = ev.scale(value, factor)
    ev.slate(r1, addVec)
  }
}