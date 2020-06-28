/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

trait ReflectAxis[T]
{ /** Reflect, mirror an object of type T across a line parallel to the X axis. */
  def reflectXOffsetT(obj: T, yOffset: Double): T

  /** Reflect, mirror an object of type T across a line parallel to the Y axis. */
  def reflectYOffsetT(obj: T, xOffset: Double): T
}

object ReflectAxis
{
  implicit def transAlignerImplicit[T <: SimilarPreserve]: ReflectAxis[T] = new ReflectAxis[T]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: T, yOffset: Double): T = obj.reflect(LineSeg(-1, yOffset, 1, yOffset)).asInstanceOf[T]

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: T, xOffset: Double): T = obj.reflect(LineSeg(xOffset, -1, xOffset, 1)).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], evA: ReflectAxis[A]): ReflectAxis[AA] = new ReflectAxis[AA]
  { /** Reflect, mirror across the X axis. */
    override def reflectXOffsetT(obj: AA, yOffset: Double): AA = obj.map(evA.reflectXOffsetT(_, yOffset))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: AA, xOffset: Double): AA = obj.map(evA.reflectYOffsetT(_, xOffset))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: ReflectAxis[A]): ReflectAxis[F[A]] = new ReflectAxis[F[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: F[A], yOffset: Double): F[A] = evF.map(obj, evA.reflectXOffsetT(_, yOffset))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: F[A], xOffset: Double): F[A] = evF.map(obj, evA.reflectYOffsetT(_, xOffset))
  }


  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: ReflectAxis[A]): ReflectAxis[Array[A]] = new ReflectAxis[Array[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: Array[A], yOffset: Double): Array[A] = obj.map(ev.reflectXOffsetT(_, yOffset))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: Array[A], xOffset: Double): Array[A] = obj.map(ev.reflectYOffsetT(_, xOffset))
  }
}

class MirrorAxisExtension[A](thisReflector: A)(implicit ev: ReflectAxis[A])
{
  /** Reflect, mirror across a line parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): A = ev.reflectXOffsetT(thisReflector, yOffset)

  /** Reflect, mirror across a line parallel to the Y axis. */
  def mirrorYOffset(xOffset: Double): A = ev.reflectYOffsetT(thisReflector, xOffset)

  /** Reflect, mirror across the X axis. */
  @inline def mirrorX: A = mirrorXOffset(0)

  /** Reflect, mirror across the Y axis. */
  @inline def mirrorY: A = mirrorYOffset(0)
}