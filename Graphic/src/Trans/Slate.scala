/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

import scala.reflect.ClassTag

trait Slate[T]
{ def slate(obj: T, offset: Vec2): T
}

object Slate
{
  implicit def transAlignerImplicit[T <: TransAligner]: Slate[T] = (obj, offset) => obj.slateOld(offset).asInstanceOf[T]

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: Slate[A]): Slate[AA] =
    (obj, offset) => obj.map(ev.slate(_, offset))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Slate[A]): Slate[F[A]] = (obj, offset) => evF.map(obj, evA.slate(_, offset))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Slate[A]): Slate[Array[A]] = (obj, offset) => obj.map(ev.slate(_, offset))
}

class SlateExtension[T](value: T, ev: Slate[T])
{
  /** Translate 2 dimensional vectors along the X axis */
  def slateX(xOffset: Double): T = ev.slate(value, xOffset vv 0)

  /** Translate 2 dimensional vectors along the Y axis */
  def slateY(yOffset: Double): T = ev.slate(value, 0 vv yOffset)

  /** Translate in 2 dimensional space. */
  def slate(offset: Vec2): T = ev.slate(value, offset)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): T = ev.slate(value, xOffset vv yOffset)
}