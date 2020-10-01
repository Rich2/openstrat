/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** Reflect Axis type class. It has two methods to reflect across the X and the Y axes. This has been created as a separate typeclass to
 * [[ReflectAxisOffset]], as these transformations may preserve types that ReflectAxisOffset's transformations can not. */
trait ReflectAxis[T]
{ /** Reflect, mirror an object of type T across the X axis. */
  def reflectXT(obj: T): T

  /** Reflect, mirror an object of type T across the Y axis. */
  def reflectYT(obj: T): T
}

/** Companion object for the [[ReflectAxis]] typeclass trait, contains instances for common container objects including Functor instances. */
object ReflectAxis
{
  implicit def transAlignerImplicit[T <: SimilarPreserve]: ReflectAxis[T] = new ReflectAxis[T]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXT(obj: T): T = obj.negY.asInstanceOf[T]

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYT(obj: T): T = obj.negX.asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], evA: ReflectAxis[A]): ReflectAxis[AA] = new ReflectAxis[AA]
  { /** Reflect, mirror across the X axis. */
    override def reflectXT(obj: AA): AA = obj.map(evA.reflectXT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYT(obj: AA): AA = obj.map(evA.reflectYT(_))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: ReflectAxis[A]): ReflectAxis[F[A]] = new ReflectAxis[F[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXT(obj: F[A]): F[A] = evF.mapT(obj, evA.reflectXT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYT(obj: F[A]): F[A] = evF.mapT(obj, evA.reflectYT(_))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: ReflectAxis[A]): ReflectAxis[Array[A]] = new ReflectAxis[Array[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXT(obj: Array[A]): Array[A] = obj.map(ev.reflectXT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYT(obj: Array[A]): Array[A] = obj.map(ev.reflectYT(_))
  }
}

/** Class to provide extension methods for ReflectAxis typeclass. */
class ReflectAxisExtension[A](thisReflector: A)(implicit ev: ReflectAxis[A])
{
  /** Reflect, mirror across the X axis. */
  @inline def reflectX: A = ev.reflectXT(thisReflector)

  /** Reflect, mirror across the Y axis. */
  @inline def reflectY: A = ev.reflectYT(thisReflector)
}