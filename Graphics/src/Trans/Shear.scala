/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** type class for shear transformations. just starting with the basic shears, rather than a generalised shear transfrmation. */
trait Shear[T]
{
  /** Reflect, mirror an object of type T across the X axis. */
  def xShearT(obj: T, yFactor: Double): T

  /** Reflect, mirror an object of type T across the Y axis. */
  def yShearT(obj: T, xFactor: Double): T 
}
/** Companion object for the Shear type class. Contains omplicit instances for Shear. */
object Shear
{
  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: Shear[A]): Shear[AA] = new Shear[AA]
  { def xShearT(obj: AA, yFactor: Double):AA = obj.map(ev.xShearT(_, yFactor))
    def yShearT(obj: AA, xFactor: Double):AA = obj.map(ev.xShearT(_, xFactor))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Shear[A]): Shear[F[A]] = new Shear[F[A]]
  { def xShearT(obj: F[A], yFactor: Double): F[A] = evF.map(obj, evA.xShearT(_, yFactor))
    def yShearT(obj: F[A], xFactor: Double): F[A] = evF.map(obj, evA.xShearT(_, xFactor))
  }
    
  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Shear[A]): Shear[Array[A]] = new Shear[Array[A]]
  { def xShearT(obj: Array[A], yFactor: Double): Array[A] = obj.map(ev.xShearT(_, yFactor))
    def yShearT(obj: Array[A], xFactor: Double): Array[A] = obj.map(ev.xShearT(_, xFactor))
  }
}