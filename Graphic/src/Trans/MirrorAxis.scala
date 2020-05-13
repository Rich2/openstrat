/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

trait MirrorAxis[T]
{ /** Reflect, mirror across the X axis. */
  def mirrorXOffset(obj: T, yOffset: Double): T

  /** Reflect, mirror across the Y axis. */
  def mirrorYOffset(obj: T, xOffset: Double): T
}

object MirrorAxis
{
  implicit def transAlignerImplicit[T <: TransSimer]: MirrorAxis[T] = new MirrorAxis[T]
  { /** Reflect, mirror across the X axis. */
    override def mirrorXOffset(obj: T, yOffset: Double): T = obj.mirror(Line2(-1, yOffset, 1, yOffset)).asInstanceOf[T]

    /** Reflect, mirror across the Y axis. */
    override def mirrorYOffset(obj: T, xOffset: Double): T = ???
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: MirrorAxis[A]): MirrorAxis[AA] = new MirrorAxis[AA]
  { /** Reflect, mirror across the X axis. */
    override def mirrorXOffset(obj: AA, yOffset: Double): AA = ???

    /** Reflect, mirror across the Y axis. */
    override def mirrorYOffset(obj: AA, xOffset: Double): AA = ???
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: MirrorAxis[A]): MirrorAxis[F[A]] = new MirrorAxis[F[A]]
  { /** Reflect, mirror across the X axis. */
    override def mirrorXOffset(obj: F[A], yOffset: Double): F[A] = evF.map(obj, evA.mirrorXOffset(_, yOffset))

    /** Reflect, mirror across the Y axis. */
    override def mirrorYOffset(obj: F[A], xOffset: Double): F[A] = evF.map(obj, evA.mirrorYOffset(_, xOffset))
  }


  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: MirrorAxis[A]): MirrorAxis[Array[A]] = new MirrorAxis[Array[A]]
  { /** Reflect, mirror across the X axis. */
    override def mirrorXOffset(obj: Array[A], yOffset: Double): Array[A] = obj.map(ev.mirrorXOffset(_, yOffset))

    /** Reflect, mirror across the Y axis. */
    override def mirrorYOffset(obj: Array[A], xOffset: Double): Array[A] = obj.map(ev.mirrorYOffset(_, xOffset))
  }
}