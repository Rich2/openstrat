/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** Reflect Axis type class. It has two methods to reflect across the X and the Y axes. This has been created as a separate typeclass to
 * [[ReflectAxes]], as these transformations may preserve types that ReflectAxisOffset's transformations can not. */
trait ReflectAxes[T]
{ /** Reflect, mirror an object of type T across the X axis, by negating Y. */
  def negYT(obj: T): T

  /** Reflect, mirror an object of type T across the Y axis by negating X. */
  def negXT(obj: T): T

  /** Rotate an object of type T by positive 90 degrees or in an anti clockwise direction. */
  def rotate90(obj: T): T
}

/** Companion object for the [[ReflectAxes]] typeclass trait, contains instances for common container objects including Functor instances. */
object ReflectAxes
{
  implicit def transAlignerImplicit[T <: SimilarPreserve]: ReflectAxes[T] = new ReflectAxes[T]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def negYT(obj: T): T = obj.negY.asInstanceOf[T]

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: T): T = obj.negX.asInstanceOf[T]

    /** Rotate an object of type T by positive 90 degrees or in an anti clockwise direction. */
    override def rotate90(obj: T): T = obj.rotate90.asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], evA: ReflectAxes[A]): ReflectAxes[AA] = new ReflectAxes[AA]
  { /** Reflect, mirror across the X axis. */
    override def negYT(obj: AA): AA = obj.map(evA.negYT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: AA): AA = obj.map(evA.negXT(_))

    /** Rotate an object of type T by positive 90 degrees or in an anti clockwise direction. */
    override def rotate90(obj: AA): AA = obj.map(evA.rotate90)
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: ReflectAxes[A]): ReflectAxes[F[A]] = new ReflectAxes[F[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def negYT(obj: F[A]): F[A] = evF.mapT(obj, evA.negYT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: F[A]): F[A] = evF.mapT(obj, evA.negXT(_))

    override def rotate90(obj: F[A]): F[A] = evF.mapT(obj, evA.rotate90)
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: ReflectAxes[A]): ReflectAxes[Array[A]] = new ReflectAxes[Array[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def negYT(obj: Array[A]): Array[A] = obj.map(ev.negYT(_))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def negXT(obj: Array[A]): Array[A] = obj.map(ev.negXT(_))

    override def rotate90(obj: Array[A]): Array[A] = obj.map(ev.rotate90)
  }
}

/** Class to provide extension methods for TransAxes type class. */
class ReflectAxesExtension[T](thisT: T)(implicit ev: ReflectAxes[T])
{
  /** Reflect, mirror across the X axis by negating Y. */
  @inline def negY: T = ev.negYT(thisT)

  /** Reflect, mirror across the Y axis by negating X. */
  @inline def negX: T = ev.negXT(thisT)
  
  /** Negates X and Y, functionally the same as rotate180. */
  @inline def negXY: T = ev.negYT(ev.negXT(thisT))
}

/** Extension class for types that fulfill the type class interface for [[ReflectAxes]] and [[Slate]]. */
class ReflectAxesSlateExtension[T](thisT: T)(implicit evR: ReflectAxes[T], evS: Slate[T])
{
  /** Reflect across a line parallel to the X axis. */
  def reflectXParallel(yValue: Double): T =
  { val res1 = evR.negYT(thisT)
    evS.ySlateT(res1, 2 * yValue)
  }

  /** Reflect across a line parallel to the Y axis. */
  def reflectYParallel(xValue: Double): T =
  { val res1 = evR.negXT(thisT)
    evS.xSlateT(res1, 2 * xValue)
  }
}