/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag
import scala.annotation.unchecked.uncheckedVariance

/** Type class for translate 2 dimensional vector transformations. Each transformation method has been given its own Type class and associated
 * extension class. Different sets of transformations can then be combined. */
trait Slate[T]
{ def slateT(obj: T, offset: Vec2Like): T
  def xSlateT(obj: T @uncheckedVariance, xOffset: Double): T = slateT(obj, xOffset pp 0)
  def ySlateT(obj: T @uncheckedVariance, yOffset: Double): T = slateT(obj, 0 pp yOffset)
}

/** Companion object for the Slate type class. Contains instances for collections and other container classes. */
object Slate
{
  implicit def transSimerImplicit[T <: SimilarPreserve]: Slate[T] = (obj, offset) => obj.slate(offset).asInstanceOf[T]

  /*implicit def arrImplicit[A](implicit ct: ClassTag[A], ev: Slate[A]): Slate[Arr[A]] =
    (obj, offset) => obj.map(ev.slateT(_, offset))(new AnyBuild[A])*/

  implicit def arrImplicit[A](implicit ev: Slate[A]): Slate[Arr[A]] = (obj, offset) => obj.smap(ev.slateT(_, offset))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Slate[A]): Slate[F[A]] = (obj, offset) => evF.mapT(obj, evA.slateT(_, offset))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Slate[A]): Slate[Array[A]] = (obj, offset) => obj.map(ev.slateT(_, offset))
}

/** Extension class for instances of the Slate type class. */
class SlateExtensions[T](value: T, ev: Slate[T])
{
  /** Translate 2 dimensional vectors along the X axis */
  def xSlate(xOffset: Double): T = ev.slateT(value, xOffset pp 0)

  /** Translate 2 dimensional vectors along the Y axis */
  def ySlate(yOffset: Double): T = ev.slateT(value, 0 pp yOffset)

  /** Translate in 2 dimensional space. */
  def slate(offset: Vec2Like): T = ev.slateT(value, offset)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): T = ev.slateT(value, xOffset pp yOffset)
}