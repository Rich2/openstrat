/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import reflect.ClassTag

trait TransSimer extends Any with TransRigider
{// type RigidT <: TransSimer
  def scale(operand: Double): RigidT
  def shear(xScale: Double, yScale: Double): TransAffer
}

trait TransSimerUser extends TransSimer
{ type RigidT <: TransSimerUser
  type MemT <: TransSimer
  def geomMem: MemT
  def newThis(transer: MemT): RigidT
  override def slate(offset: Vec2): RigidT = newThis(geomMem.slate(offset).asInstanceOf[MemT])
  override def rotateRadians(radians: Double): RigidT = newThis(geomMem.rotateRadians(radians).asInstanceOf[MemT])
  override def mirrorYOffset(xOffset: Double): RigidT = newThis(geomMem.mirrorYOffset(xOffset).asInstanceOf[MemT])
  override def mirrorXOffset(yOffset: Double): RigidT = newThis(geomMem.mirrorXOffset(yOffset).asInstanceOf[MemT])
  override def scale(operand: Double): RigidT = newThis(geomMem.scale(operand).asInstanceOf[MemT])
}

/** A Similar Transformations type class */
trait TransSim[T] extends TransRigid[T]
{ def scale(obj: T, operand: Double): T
}

object TransSim
{
  implicit def transSimerImplicit[T <: TransSimer]: TransSim[T] = new TransSim[T]
  { override def mirrorXOffset(obj: T, yOffset: Double): T = obj.mirrorXOffset(yOffset).asInstanceOf[T]
    override def mirrorYOffset(obj: T, xOffset: Double): T = obj.mirrorYOffset(xOffset).asInstanceOf[T]
    override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransSim[A]): TransSim[AA] = new TransSim[AA]
  { override def scale(obj: AA, operand: Double): AA = obj.map{ts => ev.scale(ts, operand)}
    override def slate(obj: AA, offset: Vec2): AA = obj.map{ts => ev.slate(ts, offset)}
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map{ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: AA, xOffset: Double): AA = obj.map{ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: AA, yOffset: Double): AA = obj.map{ts => ev.mirrorXOffset(ts, yOffset) }
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransSim[A]): TransSim[F[A]] = new TransSim[F[A]]
  { override def scale(obj: F[A], operand: Double): F[A] = evF.map[A, A](obj, ts => evA.scale(ts, operand))
    override def slate(obj: F[A], offset: Vec2): F[A] = evF.map(obj, ts => evA.slate(ts, offset))
    override def rotateRadians(obj: F[A], radians: Double): F[A] = evF.map(obj, ts => evA.rotateRadians(ts, radians))
    override def mirrorYOffset(obj: F[A], xOffset: Double): F[A] = evF.map(obj, ts => evA.mirrorYOffset(ts, xOffset))
    override def mirrorXOffset(obj: F[A], yOffset: Double): F[A] = evF.map(obj, ts => evA.mirrorXOffset(ts, yOffset))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransSim[A]): TransSim[Array[A]] = new TransSim[Array[A]]
  { override def scale(obj: Array[A], operand: Double): Array[A] = obj.map { ts => ev.scale(ts, operand) }
    override def slate(obj: Array[A], offset: Vec2): Array[A] = obj.map { ts => ev.slate(ts, offset) }
    override def rotateRadians(obj: Array[A], radians: Double): Array[A] = obj.map { ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: Array[A], xOffset: Double): Array[A] = obj.map { ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: Array[A], yOffset: Double): Array[A] = obj.map { ts => ev.mirrorXOffset(ts, yOffset) }
  }
}

class TransSimExtension[T](value: T, ev: TransSim[T])
{ def scale(operand: Double): T = ev.scale(value, operand)

  /** The scale transformation on 2 dimensional vectors. */
  def scaleSlate(factor: Double, addVec: Vec2): T =
  { val r1 = ev.scale(value, factor)
    ev.slate(r1, addVec)
  }
}