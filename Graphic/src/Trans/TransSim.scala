/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import reflect.ClassTag

trait TransSimer extends Any with TransAligner
{// type RigidT <: TransSimer
  def scale(operand: Double): AlignT
  def shear(xScale: Double, yScale: Double): TransAffer
}

trait TransSimerUser extends TransSimer
{ type AlignT <: TransSimerUser
  type MemT <: TransSimer
  def geomMem: MemT
  def newThis(transer: MemT): AlignT
  override def slate(offset: Vec2): AlignT = newThis(geomMem.slate(offset).asInstanceOf[MemT])
  override def rotateRadians(radians: Double): AlignT = newThis(geomMem.rotateRadians(radians).asInstanceOf[MemT])
  override def scale(operand: Double): AlignT = newThis(geomMem.scale(operand).asInstanceOf[MemT])
  override def mirror(line: Line2): AlignT = newThis(geomMem.mirror(line).asInstanceOf[MemT])
}

/** A Similar Transformations type class */
trait TransSim[T] extends TransAlign[T]
{ def scale(obj: T, operand: Double): T
}

object TransSim
{
  implicit def transSimerImplicit[T <: TransSimer]: TransSim[T] = new TransSim[T]
  { override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
    override def mirror(obj: T, line: Line2): T = obj.mirror(line).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransSim[A]): TransSim[AA] = new TransSim[AA]
  { override def scale(obj: AA, operand: Double): AA = obj.map(ev.scale(_, operand))
    override def slate(obj: AA, offset: Vec2): AA = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map(ev.rotateRadians(_, radians))
    override def mirror(obj: AA, line: Line2): AA = obj.map(ev.mirror(_, line))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransSim[A]): TransSim[F[A]] = new TransSim[F[A]]
  { override def scale(obj: F[A], operand: Double): F[A] = evF.map[A, A](obj, ts => evA.scale(ts, operand))
    override def slate(obj: F[A], offset: Vec2): F[A] = evF.map(obj, ts => evA.slate(ts, offset))
    override def rotateRadians(obj: F[A], radians: Double): F[A] = evF.map(obj, ts => evA.rotateRadians(ts, radians))
    override def mirror(obj: F[A], line: Line2): F[A] = evF.map(obj, evA.mirror(_, line))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransSim[A]): TransSim[Array[A]] = new TransSim[Array[A]]
  { override def scale(obj: Array[A], operand: Double): Array[A] = obj.map(ev.scale(_, operand))
    override def slate(obj: Array[A], offset: Vec2): Array[A] = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: Array[A], radians: Double): Array[A] = obj.map(ev.rotateRadians(_, radians))
    override def mirror(obj: Array[A], line: Line2): Array[A] = obj.map(ev.mirror(_, line))
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