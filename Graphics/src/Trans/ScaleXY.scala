/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** type class for scale transformation where the X and Y components can be scaled independently. */
trait ScaleXY[T]
{ def scaleTXY(obj: T, xOperand: Double, yOperand: Double): T
}

object ScaleXY
{
  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: ScaleXY[A]): ScaleXY[AA] =
    (obj, xOperand: Double, yOperand) => obj.map(ev.scaleTXY(_, xOperand, yOperand))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: ScaleXY[A]): ScaleXY[F[A]] =
    (obj, xOperand, yOperand) => evF.map(obj, evA.scaleTXY(_, xOperand, yOperand))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: ScaleXY[A]): ScaleXY[Array[A]] =
    (obj, xOperand: Double, yOperand) => obj.map(ev.scaleTXY(_, xOperand, yOperand))
}
