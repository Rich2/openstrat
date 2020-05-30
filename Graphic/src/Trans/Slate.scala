/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** Type class for translate 2 dimensional vector transformations. Each transformation method has been given its own Type class and associated
 * extension class. Different sets of transformations can then be combined. */
trait Slate[T]
{ def slate(obj: T, offset: Vec2): T
}

object Slate
{
  implicit def slateImplicit: Slate[GeomElem] = (obj: GeomElem, offset: Vec2) => obj match
  { case ta: TransAligner => ta.slate(offset).asInstanceOf[GeomElem]
    case gea: GeomElem => gea.slate(offset)
  }

  implicit def transAlignerImplicit[T <: TransAligner]: Slate[T] = (obj, offset) => obj.slate(offset).asInstanceOf[T]

  implicit def arrImplicit[A](implicit ct: ClassTag[A], ev: Slate[A]): Slate[Arr[A]] =
    (obj, offset) => obj.map(ev.slate(_, offset))(new AnyBuildAlt[A](ct))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Slate[A]): Slate[F[A]] = (obj, offset) => evF.map(obj, evA.slate(_, offset))
  
  //implicit def listImplicit[A](implicit evA: Slate[A]): Slate[List[A]] = (list, offset) => list.map(evA.slate(_, offset))

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