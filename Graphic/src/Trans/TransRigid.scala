/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

import scala.reflect.ClassTag

trait TransRigider extends Any
{ type RigidT <: TransRigider
  def slate(offset: Vec2): RigidT
  def rotateRadians(radians: Double): RigidT
  def rotate(angle: Angle): RigidT = rotateRadians(angle.radians)
  //def mirrorYOffset(xOffset: Double): RigidT
 // def mirrorXOffset(yOffset: Double): RigidT
  //def mirrorY: RigidT = mirrorYOffset(0)
  //def mirrorX: RigidT = mirrorXOffset(0)
  def mirror(line: Line2): RigidT
  def ySlate(yDelta: Double): RigidT = slate(0 vv yDelta)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): RigidT = slate(xOffset vv yOffset)
}

/** A Rigid or Euclidean transformations type class. */
trait TransRigid[T]
{ def slate(obj: T, offset: Vec2): T
  def rotateRadians(obj: T, radians: Double): T
  //def mirrorYOffset(obj: T, xOffset: Double): T
  //def mirrorXOffset(obj: T, yOffset: Double): T
  def mirror(obj: T, line: Line2): T
}

object TransRigid
{
  implicit def transRigiderImplicit[T <: TransRigider]: TransRigid[T] = new TransRigid[T]
  { override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def mirror(obj: T, line: Line2): T = obj.mirror(line).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransRigid[A]): TransRigid[AA] = new TransRigid[AA]
  { override def slate(obj: AA, offset: Vec2): AA = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map(ev.rotateRadians(_, radians))
    override def mirror(obj: AA, line: Line2): AA = obj.map(ev.mirror(_, line))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransRigid[A]): TransRigid[F[A]] = new TransRigid[F[A]]
  { override def slate(obj: F[A], offset: Vec2): F[A] = evF.map(obj, evA.slate(_, offset))
    override def rotateRadians(obj: F[A], radians: Double): F[A] = evF.map(obj, evA.rotateRadians(_, radians))
    override def mirror(obj: F[A], line: Line2): F[A] = evF.map(obj, evA.mirror(_, line))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransRigid[A]): TransRigid[Array[A]] = new TransRigid[Array[A]]
  { override def slate(obj: Array[A], offset: Vec2): Array[A] = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: Array[A], radians: Double): Array[A] = obj.map(ev.rotateRadians(_, radians))
    override def mirror(obj: Array[A], line: Line2): Array[A] = obj.map(ev.mirror(_, line))
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

  def mirrorParallelX(yOffset: Double): T = mirror(-1, yOffset, 1, yOffset)
  def mirrorParallelY(xOffset: Double): T = mirror(xOffset, -1, xOffset, 1)
  def mirrorY: T = mirror(0, -1, 0, 1)
  def mirrorX: T = mirror(-1, 0, 1, 0)
  def mirror(line: Line2) = ev.mirror(value, line)
  def mirror(v1: Vec2, v2: Vec2): T = ev.mirror(value, v1.lineTo(v2))
  def mirror(x1: Double, y1: Double, x2: Double, y2: Double): T = ev.mirror(value, new Line2(x1, y1, x2, y2))

  override def rotateRadians(radians: Double): T = ev.rotateRadians(value, radians)

  override def rotate(angle: Angle): T = ev.rotateRadians(value, angle.radians)

  /** this.asInstanceOf[T] */
  def identity: T = this.asInstanceOf[T]

}