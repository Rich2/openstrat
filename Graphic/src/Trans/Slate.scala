/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** Type class for translate 2 dimensional vector transformations. Each transformation method has been given its own Type class and associated
 * extension class. Different sets of transformations can then be combined. */
trait Slate[T]
{ def slateT(obj: T, offset: Vec2): T
}

/** Companion object for the Slate type class. Contains instances. */
object Slate
{
  implicit def transAlignerImplicit[T <: TransAligner]: Slate[T] = (obj, offset) => obj.slate(offset).asInstanceOf[T]

  implicit def arrImplicit[A](implicit ct: ClassTag[A], ev: Slate[A]): Slate[Arr[A]] =
    (obj, offset) => obj.map(ev.slateT(_, offset))(new AnyBuildAlt[A](ct))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Slate[A]): Slate[F[A]] = (obj, offset) => evF.map(obj, evA.slateT(_, offset))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Slate[A]): Slate[Array[A]] = (obj, offset) => obj.map(ev.slateT(_, offset))
}

/** Extension class for instances of the Slate type class. */
class SlateExtensions[T](value: T, ev: Slate[T])
{
  /** Translate 2 dimensional vectors along the X axis */
  def slateX(xOffset: Double): T = ev.slateT(value, xOffset vv 0)

  /** Translate 2 dimensional vectors along the Y axis */
  def slateY(yOffset: Double): T = ev.slateT(value, 0 vv yOffset)

  /** Translate in 2 dimensional space. */
  def slate(offset: Vec2): T = ev.slateT(value, offset)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): T = ev.slateT(value, xOffset vv yOffset)
}