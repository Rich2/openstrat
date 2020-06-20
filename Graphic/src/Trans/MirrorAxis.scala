/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

trait MirrorAxis[T]
{ /** Reflect, mirror across a line parallel to the X axis. */
  def mirrorXOffset(obj: T, yOffset: Double): T

  /** Reflect, mirror across a line parallel to the Y axis. */
  def mirrorYOffset(obj: T, xOffset: Double): T
}

object MirrorAxis
{
  implicit def transAlignerImplicit[T <: SimilarPreserve]: MirrorAxis[T] = new MirrorAxis[T]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def mirrorXOffset(obj: T, yOffset: Double): T = obj.mirror(Line2(-1, yOffset, 1, yOffset)).asInstanceOf[T]

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def mirrorYOffset(obj: T, xOffset: Double): T = obj.mirror(Line2(xOffset, -1, xOffset, 1)).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], evA: MirrorAxis[A]): MirrorAxis[AA] = new MirrorAxis[AA]
  { /** Reflect, mirror across the X axis. */
    override def mirrorXOffset(obj: AA, yOffset: Double): AA = obj.map(evA.mirrorXOffset(_, yOffset))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def mirrorYOffset(obj: AA, xOffset: Double): AA = obj.map(evA.mirrorYOffset(_, xOffset))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: MirrorAxis[A]): MirrorAxis[F[A]] = new MirrorAxis[F[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def mirrorXOffset(obj: F[A], yOffset: Double): F[A] = evF.map(obj, evA.mirrorXOffset(_, yOffset))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def mirrorYOffset(obj: F[A], xOffset: Double): F[A] = evF.map(obj, evA.mirrorYOffset(_, xOffset))
  }


  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: MirrorAxis[A]): MirrorAxis[Array[A]] = new MirrorAxis[Array[A]]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def mirrorXOffset(obj: Array[A], yOffset: Double): Array[A] = obj.map(ev.mirrorXOffset(_, yOffset))

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def mirrorYOffset(obj: Array[A], xOffset: Double): Array[A] = obj.map(ev.mirrorYOffset(_, xOffset))
  }
}

class MirrorAxisExtension[A](thisReflector: A)(implicit ev: MirrorAxis[A])
{
  /** Reflect, mirror across a line parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): A = ev.mirrorXOffset(thisReflector, yOffset)

  /** Reflect, mirror across a line parallel to the Y axis. */
  def mirrorYOffset(xOffset: Double): A = ev.mirrorYOffset(thisReflector, xOffset)

  /** Reflect, mirror across the X axis. */
  @inline def mirrorX: A = mirrorXOffset(0)

  /** Reflect, mirror across the Y axis. */
  @inline def mirrorY: A = mirrorYOffset(0)
}