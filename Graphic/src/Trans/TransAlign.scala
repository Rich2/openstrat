/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import reflect.ClassTag

trait TransAligner extends Any
{ type AlignT <: TransAligner
  def slate(offset: Vec2): AlignT
  def rotateRadians(radians: Double): AlignT
  def rotate(angle: Angle): AlignT = rotateRadians(angle.radians)
  //def mirrorYOffset(xOffset: Double): RigidT
 // def mirrorXOffset(yOffset: Double): RigidT
  //def mirrorY: RigidT = mirrorYOffset(0)
  //def mirrorX: RigidT = mirrorXOffset(0)
  def mirror(line: Line2): AlignT
  def ySlate(yDelta: Double): AlignT = slate(0 vv yDelta)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): AlignT = slate(xOffset vv yOffset)
}

/** A Rigid or Euclidean transformations type class. */
trait TransAlign[T]
{ def slate(obj: T, offset: Vec2): T
  def rotateRadians(obj: T, radians: Double): T
  //def mirrorYOffset(obj: T, xOffset: Double): T
  //def mirrorXOffset(obj: T, yOffset: Double): T
  def mirror(obj: T, line: Line2): T
}

object TransAlign
{
  implicit def transRigiderImplicit[T <: TransAligner]: TransAlign[T] = new TransAlign[T]
  { override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def mirror(obj: T, line: Line2): T = obj.mirror(line).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransAlign[A]): TransAlign[AA] = new TransAlign[AA]
  { override def slate(obj: AA, offset: Vec2): AA = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map(ev.rotateRadians(_, radians))
    override def mirror(obj: AA, line: Line2): AA = obj.map(ev.mirror(_, line))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransAlign[A]): TransAlign[F[A]] = new TransAlign[F[A]]
  { override def slate(obj: F[A], offset: Vec2): F[A] = evF.map(obj, evA.slate(_, offset))
    override def rotateRadians(obj: F[A], radians: Double): F[A] = evF.map(obj, evA.rotateRadians(_, radians))
    override def mirror(obj: F[A], line: Line2): F[A] = evF.map(obj, evA.mirror(_, line))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransAlign[A]): TransAlign[Array[A]] = new TransAlign[Array[A]]
  { override def slate(obj: Array[A], offset: Vec2): Array[A] = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: Array[A], radians: Double): Array[A] = obj.map(ev.rotateRadians(_, radians))
    override def mirror(obj: Array[A], line: Line2): Array[A] = obj.map(ev.mirror(_, line))
  }
}

class TransAlignExtension[T](value: T, ev: TransAlign[T]) extends TransAlignGenExtension[T]
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