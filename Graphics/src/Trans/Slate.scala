/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag
import scala.annotation.unchecked.uncheckedVariance

/** Type class for translate 2 dimensional vector transformations. Each transformation method has been given its own Type class and associated
 * extension class. Different sets of transformations can then be combined. */
trait Slate[T]
{
  def slateT(obj: T, xDelta: Double, yDelta: Double): T
  def xSlateT(obj: T @uncheckedVariance, xOffset: Double): T = slateT(obj, xOffset, 0)
  def ySlateT(obj: T @uncheckedVariance, yOffset: Double): T = slateT(obj, 0, yOffset)
}

/** Companion object for the Slate type class. Contains instances for collections and other container classes. */
object Slate
{
  implicit def transSimerImplicit[T <: SimilarPreserve]: Slate[T] = (obj, dx, dy) => obj.slate(dx, dy).asInstanceOf[T]

  /*implicit def arrImplicit[A](implicit ct: ClassTag[A], ev: Slate[A]): Slate[Arr[A]] =
    (obj, dx, dy) => obj.map(ev.slateT(_, dx, dy))(new AnyBuild[A])*/

  implicit def arrImplicit[A](implicit ev: Slate[A]): Slate[Arr[A]] = (obj, dx, dy) => obj.smap(ev.slateT(_, dx, dy))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Slate[A]): Slate[F[A]] = (obj, dx, dy) => evF.mapT(obj, evA.slateT(_, dx, dy))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Slate[A]): Slate[Array[A]] = (obj, dx, dy) => obj.map(ev.slateT(_, dx, dy))
}

/** Extension class for instances of the Slate type class. */
class SlateExtensions[T](value: T, ev: Slate[T])
{
  /** Translate 2 dimensional vectors along the X axis */
  def xSlate(xOffset: Double): T = ev.slateT(value, xOffset, 0)

  /** Translate 2 dimensional vectors along the Y axis */
  def ySlate(yOffset: Double): T = ev.slateT(value, 0, yOffset)

  /** Translate in 2 dimensional space. */
  def slate(xDelta: Double, yDelta: Double): T = ev.slateT(value, xDelta, yDelta)

  /** Translate in 2 dimensional space. */
  def slate(offset: Vec2Like): T = ev.slateT(value, offset.x, offset.y)

  /** Translate in 2D this object by the delta or vector form the first parameter point to the second parameter point. */
  def slateDelta(ptA: Pt2, ptB: Pt2): T = ev.slateT(value, ptB.x - ptA.x, ptA.y -ptB.y)
}