/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

trait TransSimer extends TransElem
{ type SimerT <: TransSimer
  def shear(xScale: Double, yScale: Double): AffineElem
  def mirror(line: Line2): SimerT
  def rotateRadians(radians: Double): SimerT
  def rotate(angle: Angle): SimerT = rotateRadians(angle.radians)
  def scale(operand: Double): SimerT
  def slate(offset: Vec2): SimerT

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): SimerT = slate(xOffset vv yOffset)

  override def rotate90: SimerT
  override def rotate180: SimerT
  override def rotate270: SimerT
}

trait TransSimerUser extends TransSimer
{ type SimerT <: TransSimerUser
  type MemT <: TransSimer
  def geomMem: MemT
  def newThis(transer: MemT): SimerT
  override def slate(offset: Vec2): SimerT = newThis(geomMem.slate(offset).asInstanceOf[MemT])
  override def rotateRadians(radians: Double): SimerT = newThis(geomMem.rotateRadians(radians).asInstanceOf[MemT])
  override def scale(operand: Double): SimerT = newThis(geomMem.scale(operand).asInstanceOf[MemT])
  override def mirror(line: Line2): SimerT = newThis(geomMem.mirror(line).asInstanceOf[MemT])
}

/** A Similar Transformations type class */
trait TransSim[T] extends TransAlign[T]
{ def mirror(obj: T, line: Line2): T
  def rotateRadians(obj: T, radians: Double): T
}

object TransSim
{
  implicit def transSimerImplicit[T <: TransSimer]: TransSim[T] = new TransSim[T]
  { override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def mirror(obj: T, line: Line2): T = obj.mirror(line).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransSim[A]): TransSim[AA] = new TransSim[AA]
  { override def slate(obj: AA, offset: Vec2): AA = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map(ev.rotateRadians(_, radians))
    override def mirror(obj: AA, line: Line2): AA = obj.map(ev.mirror(_, line))
    override def scale(obj: AA, operand: Double): AA = obj.map(ev.scale(_, operand))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransSim[A]): TransSim[F[A]] = new TransSim[F[A]]
  { override def slate(obj: F[A], offset: Vec2): F[A] = evF.map(obj, ts => evA.slate(ts, offset))
    override def rotateRadians(obj: F[A], radians: Double): F[A] = evF.map(obj, ts => evA.rotateRadians(ts, radians))
    override def mirror(obj: F[A], line: Line2): F[A] = evF.map(obj, evA.mirror(_, line))
    override def scale(obj: F[A], operand: Double): F[A] = evF.map[A, A](obj, ts => evA.scale(ts, operand))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransSim[A]): TransSim[Array[A]] = new TransSim[Array[A]]
  { override def slate(obj: Array[A], offset: Vec2): Array[A] = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: Array[A], radians: Double): Array[A] = obj.map(ev.rotateRadians(_, radians))
    override def mirror(obj: Array[A], line: Line2): Array[A] = obj.map(ev.mirror(_, line))
    override def scale(obj: Array[A], operand: Double): Array[A] = obj.map(ev.scale(_, operand))
  }
}

class TransSimExtension[T](value: T, ev: TransSim[T]) extends TransSimGenExtension[T]
{ def mirror(line: Line2) = ev.mirror(value, line)
  def mirror(v1: Vec2, v2: Vec2): T = ev.mirror(value, v1.lineTo(v2))
  def mirror(x1: Double, y1: Double, x2: Double, y2: Double): T = ev.mirror(value, new Line2(x1, y1, x2, y2))
  override def rotateRadiansSim(radians: Double): T = ev.rotateRadians(value, radians)
  override def rotateSim(angle: Angle): T = ev.rotateRadians(value, angle.radians)
  /** this.asInstanceOf[T] */
  def identity: T = this.asInstanceOf[T]

  /** The scale transformation on 2 dimensional vectors. */
  def scaleSlate(factor: Double, addVec: Vec2): T =
  { val r1 = ev.scale(value, factor)
    ev.slate(r1, addVec)
  }
}