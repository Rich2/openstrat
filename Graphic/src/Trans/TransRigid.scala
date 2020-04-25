/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

import scala.reflect.ClassTag

trait TransRigider extends Any
{ type ThisT <: TransRigider
  def slate(offset: Vec2): ThisT
  def rotateRadians(radians: Double): ThisT
  def rotate(angle: Angle): ThisT = rotateRadians(angle.radians)
  def mirrorYOffset(xOffset: Double): ThisT
  def mirrorXOffset(yOffset: Double): ThisT
  def mirrorY: ThisT = mirrorYOffset(0)
  def mirrorX: ThisT = mirrorXOffset(0)
  def ySlate(yDelta: Double): ThisT = slate(0 vv yDelta)
  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): ThisT = slate(xOffset vv yOffset)
}

/** A Rigid or Euclidean transformations type class. */
trait TransRigid[T]
{
  def slate(obj: T, offset: Vec2): T
  def rotateRadians(obj: T, radians: Double): T
  def mirrorYOffset(obj: T, xOffset: Double): T
  def mirrorXOffset(obj: T, yOffset: Double): T
}

object TransRigid
{
  implicit def transRigiderImplicit[T <: TransRigider]: TransRigid[T] = new TransRigid[T]
  { override def mirrorXOffset(obj: T, yOffset: Double): T = obj.mirrorXOffset(yOffset).asInstanceOf[T]
    override def mirrorYOffset(obj: T, xOffset: Double): T = obj.mirrorYOffset(xOffset).asInstanceOf[T]
    override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransRigid[A]): TransRigid[AA] = new TransRigid[AA]
  { override def slate(obj: AA, offset: Vec2): AA = obj.map{ts => ev.slate(ts, offset)}
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map{ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: AA, xOffset: Double): AA = obj.map{ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: AA, yOffset: Double): AA = obj.map{ts => ev.mirrorXOffset(ts, yOffset) }
  }

  /*implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransRigid[A]): TransRigid[F[A]] = new TransRigid[F[A]]
  { //override def scale(obj: F[A], operand: Double): F[A] = evF.map[A, A](obj, ts => evA.scale(ts, operand))
    override def slate(obj: F[A], offset: Vec2): F[A] = evF.map(obj, ts => evA.slate(ts, offset))
    override def rotateRadians(obj: F[A], radians: Double): F[A] = evF.map(obj, ts => evA.rotateRadians(ts, radians))
    override def mirrorYOffset(obj: F[A], xOffset: Double): F[A] = evF.map(obj, ts => evA.mirrorYOffset(ts, xOffset))
    override def mirrorXOffset(obj: F[A], yOffset: Double): F[A] = evF.map(obj, ts => evA.mirrorXOffset(ts, yOffset))
  }*/

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransRigid[A]): TransRigid[Array[A]] = new TransRigid[Array[A]]
  { //override def scale(obj: Array[A], operand: Double): Array[A] = obj.map { ts => ev.scale(ts, operand) }
    override def slate(obj: Array[A], offset: Vec2): Array[A] = obj.map { ts => ev.slate(ts, offset) }
    override def rotateRadians(obj: Array[A], radians: Double): Array[A] = obj.map { ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: Array[A], xOffset: Double): Array[A] = obj.map { ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: Array[A], yOffset: Double): Array[A] = obj.map { ts => ev.mirrorXOffset(ts, yOffset) }
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

  def rotateRadians(radians: Double): T = ev.rotateRadians(value, radians)
}