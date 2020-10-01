/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** Reflect Axis type class. It has two methods to reflect across the X and the Y axes. This has been created as a separate typeclass to
 * [[ReflectAxesOffset]], as these transformations may preserve types that ReflectAxisOffset's transformations can not. */
trait ReflectAxes[T]
{ /** Reflect, mirror an object of type T across the X axis, by negating Y. */
  def negYT(obj: T): T

  /** Reflect, mirror an object of type T across the Y axis by negating X. */
  def negXT(obj: T): T
}

/** Companion object for the [[ReflectAxes]] typeclass trait, contains instances for common container objects including Functor instances. */
object ReflectAxes
{
  implicit def transAlignerImplicit[T <: SimilarPreserve]: ReflectAxes[T] = new ReflectAxes[T]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def negYT(obj: T): T = obj.negY.asInstanceOf[T]

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: T): T = obj.negX.asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], evA: ReflectAxes[A]): ReflectAxes[AA] = new ReflectAxes[AA]
  { /** Reflect, mirror across the X axis. */
    override def negYT(obj: AA): AA = obj.map(evA.negYT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: AA): AA = obj.map(evA.negXT(_))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: ReflectAxes[A]): ReflectAxes[F[A]] = new ReflectAxes[F[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def negYT(obj: F[A]): F[A] = evF.mapT(obj, evA.negYT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: F[A]): F[A] = evF.mapT(obj, evA.negXT(_))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: ReflectAxes[A]): ReflectAxes[Array[A]] = new ReflectAxes[Array[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def negYT(obj: Array[A]): Array[A] = obj.map(ev.negYT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: Array[A]): Array[A] = obj.map(ev.negXT(_))
  }
}

/** Class to provide extension methods for ReflectAxis typeclass. */
class ReflectAxesExtension[A](thisReflector: A)(implicit ev: ReflectAxes[A])
{
  /** Reflect, mirror across the X axis. */
  @inline def reflectX: A = ev.negYT(thisReflector)

  /** Reflect, mirror across the Y axis. */
  @inline def reflectY: A = ev.negXT(thisReflector)
}