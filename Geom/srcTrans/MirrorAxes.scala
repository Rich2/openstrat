/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Reflect Axis type class. It has two methods to reflect across the X and the Y axes. This has been created as a separate type class to [[MirrorAxes]], as
 * these transformations may preserve types that ReflectAxisOffset's transformations can not. */
trait MirrorAxes[T]
{ /** Reflect, mirror an object of type T across the X axis, by negating Y. */
  def negYT(obj: T): T

  /** Reflect, mirror an object of type T across the Y axis by negating X. */
  def negXT(obj: T): T

  /** Rotate an object of type T by positive 90 degrees or in an anti-clockwise direction. */
  def rotate90(obj: T): T

  /** Rotate an object of type T by 180 degrees or in an anti-clockwise direction. */
  def rotate180(obj: T): T

  /** Rotate an object of type T by positive 270 degrees or in an anti-clockwise direction. */
  def rotate270(obj: T): T
}

/** Companion object for the [[MirrorAxes]] type class trait, contains instances for common container objects including Functor instances. */
object MirrorAxes
{ /** [[MirrorAxes]] type class instances / evidence for [[Arr]]s. */
  given arrEv[A, AA <: Arr[A]](using build: BuilderArrMap[A, AA], evA: MirrorAxes[A]): MirrorAxes[AA] = new MirrorAxes[AA]
  { override def negYT(obj: AA): AA = obj.map(evA.negYT(_))
    override def negXT(obj: AA): AA = obj.map(evA.negXT(_))
    override def rotate90(obj: AA): AA = obj.map(evA.rotate90)
    override def rotate180(obj: AA): AA = obj.map(evA.rotate180)
    override def rotate270(obj: AA): AA = obj.map(evA.rotate270)
  }

  /** [[MirrorAxes]] type class instances / evidence for [[Functor]]s. */
  given functorEv[A, F[_]](using evF: Functor[F], evA: MirrorAxes[A]): MirrorAxes[F[A]] = new MirrorAxes[F[A]]
  { override def negYT(obj: F[A]): F[A] = evF.mapT(obj, evA.negYT(_))
    override def negXT(obj: F[A]): F[A] = evF.mapT(obj, evA.negXT(_))
    override def rotate90(obj: F[A]): F[A] = evF.mapT(obj, evA.rotate90)
    override def rotate180(obj: F[A]): F[A] = evF.mapT(obj, evA.rotate180)
    override def rotate270(obj: F[A]): F[A] = evF.mapT(obj, evA.rotate270)
  }

  /** [[MirrorAxes]] type class instances / evidence for [[Array]]s. */
  given arrayEv[A](using ct: ClassTag[A], ev: MirrorAxes[A]): MirrorAxes[Array[A]] = new MirrorAxes[Array[A]]
  { override def negYT(obj: Array[A]): Array[A] = obj.map(ev.negYT(_))
    override def negXT(obj: Array[A]): Array[A] = obj.map(ev.negXT(_))
    override def rotate90(obj: Array[A]): Array[A] = obj.map(ev.rotate90)
    override def rotate180(obj: Array[A]): Array[A] = obj.map(ev.rotate180)
    override def rotate270(obj: Array[A]): Array[A] = obj.map(ev.rotate270)
  }
}

/** Extension methods for [[MirrorAxes]] type class. */
extension[T](thisT: T)(using ev: MirrorAxes[T])
{ @inline def negY: T = ev.negYT(thisT)
  @inline def negX: T = ev.negXT(thisT)
  @inline def negXY: T = ev.negYT(ev.negXT(thisT))
  @inline def rotate90: T = ev.rotate90(thisT)
  @inline def rotate180: T = ev.rotate180(thisT)
  @inline def rotate270: T = ev.rotate270(thisT)
  @inline def rotate180If(cond: Boolean): T = ife(cond, ev.rotate180(thisT), thisT)
  def rotateQuadrants(using ct: ClassTag[T]): RArr[T] = RArr(thisT, rotate270, rotate180, rotate90)
}

/** Extension class for types that fulfill the type class interface for [[MirrorAxes]] and [[SlateXY]]. */
class TransAxesSlateExtensions[T](thisT: T)(using evR: MirrorAxes[T], evS: Slate2[T])
{
  /** Reflect across a line parallel to the X axis. */
  def reflectXParallel(yValue: Double): T =
  { val res1 = evR.negYT(thisT)
    evS.slateY(res1, 2 * yValue)
  }

  /** Reflect across a line parallel to the Y axis. */
  def reflectYParallel(xValue: Double): T =
  { val res1 = evR.negXT(thisT)
    evS.slateX(res1, 2 * xValue)
  }
}