/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Reflect Axis type class. It has two methods to reflect across the X and the Y axes. This has been created as a separate typeclass to
 * [[TransAxes]], as these transformations may preserve types that ReflectAxisOffset's transformations can not. */
trait TransAxes[T]
{ /** Reflect, mirror an object of type T across the X axis, by negating Y. */
  def negYT(obj: T): T

  /** Reflect, mirror an object of type T across the Y axis by negating X. */
  def negXT(obj: T): T

  /** Rotate an object of type T by positive 90 degrees or in an anti clockwise direction. */
  def rotate90(obj: T): T

  /** Rotate an object of type T by 180 degrees or in an anti clockwise direction. */
  def rotate180(obj: T): T

  /** Rotate an object of type T by positive 270 degrees or in an anti clockwise direction. */
  def rotate270(obj: T): T
}

/** Companion object for the [[TransAxes]] typeclass trait, contains instances for common container objects including Functor instances. */
object TransAxes
{
  implicit def transAlignerImplicit[T <: SimilarPreserve]: TransAxes[T] = new TransAxes[T]
  { override def negYT(obj: T): T = obj.negY.asInstanceOf[T]
    override def negXT(obj: T): T = obj.negX.asInstanceOf[T]
    override def rotate90(obj: T): T = obj.rotate90.asInstanceOf[T]
    override def rotate180(obj: T): T = obj.rotate180.asInstanceOf[T]
    override def rotate270(obj: T): T = obj.rotate270.asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: Arr[A]](implicit build: ArrMapBuilder[A, AA], evA: TransAxes[A]): TransAxes[AA] = new TransAxes[AA]
  { override def negYT(obj: AA): AA = obj.map(evA.negYT(_))
    override def negXT(obj: AA): AA = obj.map(evA.negXT(_))
    override def rotate90(obj: AA): AA = obj.map(evA.rotate90)
    override def rotate180(obj: AA): AA = obj.map(evA.rotate180)
    override def rotate270(obj: AA): AA = obj.map(evA.rotate270)
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransAxes[A]): TransAxes[F[A]] = new TransAxes[F[A]]
  { override def negYT(obj: F[A]): F[A] = evF.mapT(obj, evA.negYT(_))
    override def negXT(obj: F[A]): F[A] = evF.mapT(obj, evA.negXT(_))
    override def rotate90(obj: F[A]): F[A] = evF.mapT(obj, evA.rotate90)
    override def rotate180(obj: F[A]): F[A] = evF.mapT(obj, evA.rotate180)
    override def rotate270(obj: F[A]): F[A] = evF.mapT(obj, evA.rotate270)
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransAxes[A]): TransAxes[Array[A]] = new TransAxes[Array[A]]
  { override def negYT(obj: Array[A]): Array[A] = obj.map(ev.negYT(_))
    override def negXT(obj: Array[A]): Array[A] = obj.map(ev.negXT(_))
    override def rotate90(obj: Array[A]): Array[A] = obj.map(ev.rotate90)
    override def rotate180(obj: Array[A]): Array[A] = obj.map(ev.rotate180)
    override def rotate270(obj: Array[A]): Array[A] = obj.map(ev.rotate270)
  }
}

/** Class to provide extension methods for TransAxes type class. */
class TransAxesExtensions[T](thisT: T)(implicit ev: TransAxes[T])
{  @inline def negY: T = ev.negYT(thisT)
  @inline def negX: T = ev.negXT(thisT)
  @inline def negXY: T = ev.negYT(ev.negXT(thisT))
  @inline def rotate90: T = ev.rotate90(thisT)
  @inline def rotate180: T = ev.rotate180(thisT)
  @inline def rotate270: T = ev.rotate270(thisT)
  @inline def rotate180If(cond: Boolean): T = ife(cond, ev.rotate180(thisT), thisT)
  def rotateQuadrants(implicit ct: ClassTag[T]): RArr[T] = RArr(thisT, rotate270, rotate180, rotate90)
}

/** Extension class for types that fulfill the type class interface for [[TransAxes]] and [[Slate]]. */
class TransAxesSlateExtensions[T](thisT: T)(implicit evR: TransAxes[T], evS: Slate[T])
{
  /** Reflect across a line parallel to the X axis. */
  def reflectXParallel(yValue: Double): T =
  { val res1 = evR.negYT(thisT)
    evS.SlateYT(res1, 2 * yValue)
  }

  /** Reflect across a line parallel to the Y axis. */
  def reflectYParallel(xValue: Double): T =
  { val res1 = evR.negXT(thisT)
    evS.SlateXT(res1, 2 * xValue)
  }
}