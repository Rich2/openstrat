/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag, annotation.unchecked.uncheckedVariance

/** Type class trait for scale transformation where the X and Y components can be scaled independently. */
trait ScaleXY[T]
{
  /** XY scaling 2D geometric transformation on an obect of type T, returns a new type T. This allows different scaling factors across X and Y
   * dimensions. For many instances the implementation of this method will delegate to the object itself. This is an affine transformation but it is
   * not a similar transformation.*/
  def scaleXYT(obj: T @uncheckedVariance, xOperand: Double, yOperand: Double): T
}

/** Companion object for scale transformation where the X and Y components can be scaled independently. Contains instance for comon types. */
object ScaleXY
{
  implicit def arrImplicit[A, AA <: Arr[A]](implicit build: ArrBuilder[A, AA], ev: ScaleXY[A]): ScaleXY[AA] =
    (obj, xOperand: Double, yOperand) => obj.map(ev.scaleXYT(_, xOperand, yOperand))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: ScaleXY[A]): ScaleXY[F[A]] =
    (obj, xOperand, yOperand) => evF.mapT(obj, evA.scaleXYT(_, xOperand, yOperand))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: ScaleXY[A]): ScaleXY[Array[A]] =
    (obj, xOperand: Double, yOperand) => obj.map(ev.scaleXYT(_, xOperand, yOperand))
}

class XYScaleExtensions[T](val value: T, ev: ScaleXY[T])
{
  /** 2d geometric scale transformation extension method, where X and Y axes can be scaled independently for type T, returning a type T. This is an
   * affine transformation but it is not a similar transformation. */
  def scaleXY(xOperand: Double, yOperand: Double): T = ev.scaleXYT(value, xOperand, yOperand)

  /** scale X axis 2d geometric transformation extension method, for type T, returning a type T. This is an affine transformation but it is not a
   *  similar transformation. */
  def scaleX(xOperand: Double): T = ev.scaleXYT(value, xOperand, 1)

  /** scale Y axis 2d geometric transformation extension method, for type T, returning a type T. This is an affine transformation but it is not a
   *  similar transformation. */
  def scaleY(yOperand: Double): T = ev.scaleXYT(value, 1, yOperand)
}