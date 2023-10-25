/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for shear transformations. just starting with the basic shears, rather than a generalised shear transformation. */
trait Shear[T]
{ /** Reflect, mirror an object of type T across the X axis. */
  def shearXT(obj: T, yFactor: Double): T

  /** Reflect, mirror an object of type T across the Y axis. */
  def shearYT(obj: T, xFactor: Double): T
}

/** Companion object for the Shear type class. Contains implicit instances for common types. */
object Shear
{
  implicit def arrImplicit[A, AA <: Arr[A]](implicit build: MapBuilderArr[A, AA], ev: Shear[A]): Shear[AA] = new Shear[AA]
  { def shearXT(obj: AA, yFactor: Double): AA = obj.map(ev.shearXT(_, yFactor))
    def shearYT(obj: AA, xFactor: Double): AA = obj.map(ev.shearXT(_, xFactor))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Shear[A]): Shear[F[A]] = new Shear[F[A]]
  { def shearXT(obj: F[A], yFactor: Double): F[A] = evF.mapT(obj, evA.shearXT(_, yFactor))
    def shearYT(obj: F[A], xFactor: Double): F[A] = evF.mapT(obj, evA.shearXT(_, xFactor))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Shear[A]): Shear[Array[A]] = new Shear[Array[A]]
  { def shearXT(obj: Array[A], yFactor: Double): Array[A] = obj.map(ev.shearXT(_, yFactor))
    def shearYT(obj: Array[A], xFactor: Double): Array[A] = obj.map(ev.shearXT(_, xFactor))
  }
}

/** Extensions class for the Shear type class. */
class ShearExtensions[T](obj: T, ev: Shear[T])
{ def shearX(yFactor: Double): T = ev.shearXT(obj, yFactor)
  def shearY(xFactor: Double): T = ev.shearYT(obj, xFactor)
}