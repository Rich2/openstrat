/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** type class for scale transformation where the X and Y components can be scaled independently. */
trait XYScale[T]
{ def xyScaleT(obj: T, xOperand: Double, yOperand: Double): T
}

object XYScale
{
  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: XYScale[A]): XYScale[AA] =
    (obj, xOperand: Double, yOperand) => obj.map(ev.xyScaleT(_, xOperand, yOperand))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: XYScale[A]): XYScale[F[A]] =
    (obj, xOperand, yOperand) => evF.mapT(obj, evA.xyScaleT(_, xOperand, yOperand))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: XYScale[A]): XYScale[Array[A]] =
    (obj, xOperand: Double, yOperand) => obj.map(ev.xyScaleT(_, xOperand, yOperand))
}

class XYScaleExtensions[T](val value: T, ev: XYScale[T])
{ /** Performs 2d vector xy Scale transformation on objects of type T. */
  def xyScale(xOperand: Double, yOperand: Double): T = ev.xyScaleT(value, xOperand, yOperand)
  def xScale(xOperand: Double): T = ev.xyScaleT(value, xOperand, 1)
  def yScale(yOperand: Double): T = ev.xyScaleT(value, 1, yOperand)
}