/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

import scala.reflect.ClassTag

trait TransRigider extends Any
{ type RigidT <: TransRigider
  def slate(offset: Vec2): RigidT
  def rotateRadians(radians: Double): RigidT
  def rotate(angle: Angle): RigidT = rotateRadians(angle.radians)
  def mirrorYOffset(xOffset: Double): RigidT
  def mirrorXOffset(yOffset: Double): RigidT
  def mirrorY: RigidT = mirrorYOffset(0)
  def mirrorX: RigidT = mirrorXOffset(0)
  def mirror(line: Line2): RigidT
  def ySlate(yDelta: Double): RigidT = slate(0 vv yDelta)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): RigidT = slate(xOffset vv yOffset)
}

/** A Rigid or Euclidean transformations type class. */
trait TransRigid[T]
{ def slate(obj: T, offset: Vec2): T
  def rotateRadians(obj: T, radians: Double): T
  def mirrorYOffset(obj: T, xOffset: Double): T
  def mirrorXOffset(obj: T, yOffset: Double): T
  def mirror(obj: T, line: Line2): T
}

object TransRigid
{
  implicit def transRigiderImplicit[T <: TransRigider]: TransRigid[T] = new TransRigid[T]
  { override def mirrorXOffset(obj: T, yOffset: Double): T = obj.mirrorXOffset(yOffset).asInstanceOf[T]
    override def mirrorYOffset(obj: T, xOffset: Double): T = obj.mirrorYOffset(xOffset).asInstanceOf[T]
    override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def mirror(obj: T, line: Line2): T = obj.mirror(line).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransRigid[A]): TransRigid[AA] = new TransRigid[AA]
  { override def slate(obj: AA, offset: Vec2): AA = obj.map{ts => ev.slate(ts, offset)}
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map{ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: AA, xOffset: Double): AA = obj.map{ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: AA, yOffset: Double): AA = obj.map{ts => ev.mirrorXOffset(ts, yOffset) }
    override def mirror(obj: AA, line: Line2): AA = ???
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransRigid[A]): TransRigid[F[A]] = new TransRigid[F[A]]
  { override def slate(obj: F[A], offset: Vec2): F[A] = evF.map(obj, ts => evA.slate(ts, offset))
    override def rotateRadians(obj: F[A], radians: Double): F[A] = evF.map(obj, ts => evA.rotateRadians(ts, radians))
    override def mirrorYOffset(obj: F[A], xOffset: Double): F[A] = evF.map(obj, ts => evA.mirrorYOffset(ts, xOffset))
    override def mirrorXOffset(obj: F[A], yOffset: Double): F[A] = evF.map(obj, ts => evA.mirrorXOffset(ts, yOffset))

    override def mirror(obj: F[A], line: Line2): F[A] = ???
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransRigid[A]): TransRigid[Array[A]] = new TransRigid[Array[A]]
  { override def slate(obj: Array[A], offset: Vec2): Array[A] = obj.map { ts => ev.slate(ts, offset) }
    override def rotateRadians(obj: Array[A], radians: Double): Array[A] = obj.map { ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: Array[A], xOffset: Double): Array[A] = obj.map { ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: Array[A], yOffset: Double): Array[A] = obj.map { ts => ev.mirrorXOffset(ts, yOffset) }

    override def mirror(obj: Array[A], line: Line2): Array[A] = ???
  }
}

class TransRigidExtension[T](value: T, ev: TransRigid[T]) extends TransRigidGenExtension[T]
{
  /** Translate 2 dimensional vectors along the X axis */
  def slateX(xOffset: Double): T = ev.slate(value, xOffset vv 0)

  /** Translate 2 dimensional vectors along the Y axis */
  def slateY(yOffset: Double): T = ev.slate(value, 0 vv yOffset)

  /** Translate in 2 dimensional space. */
  def slate(offset: Vec2): T = ev.slate(value, offset)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): T = ev.slate(value, xOffset vv yOffset)

  def mirrorXOffset(yOffset: Double): T = ev.mirrorXOffset(value, yOffset)
  def mirrorYOffset(xOffset: Double): T = ev.mirrorXOffset(value, xOffset)

  override def rotateRadians(radians: Double): T = ev.rotateRadians(value, radians)

  override def rotate(angle: Angle): T = ev.rotateRadians(value, angle.radians)

  /** this.asInstanceOf[T] */
  def identity: T = this.asInstanceOf[T]

}