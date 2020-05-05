/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import reflect.ClassTag

trait TransAligner extends Any
{ type AlignT <: TransAligner
  def slate(offset: Vec2): AlignT
  def rotateRadians(radians: Double): AlignT
  def rotate(angle: Angle): AlignT = rotateRadians(angle.radians)
  def ySlate(yDelta: Double): AlignT = slate(0 vv yDelta)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): AlignT = slate(xOffset vv yOffset)
}

/** A Rigid or Euclidean transformations type class. */
trait TransAlign[T]
{ def slate(obj: T, offset: Vec2): T
  def rotateRadians(obj: T, radians: Double): T
}

object TransAlign
{
  implicit def transRigiderImplicit[T <: TransAligner]: TransAlign[T] = new TransAlign[T]
  { override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]

  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransAlign[A]): TransAlign[AA] = new TransAlign[AA]
  { override def slate(obj: AA, offset: Vec2): AA = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map(ev.rotateRadians(_, radians))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransAlign[A]): TransAlign[F[A]] = new TransAlign[F[A]]
  { override def slate(obj: F[A], offset: Vec2): F[A] = evF.map(obj, evA.slate(_, offset))
    override def rotateRadians(obj: F[A], radians: Double): F[A] = evF.map(obj, evA.rotateRadians(_, radians))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransAlign[A]): TransAlign[Array[A]] = new TransAlign[Array[A]]
  { override def slate(obj: Array[A], offset: Vec2): Array[A] = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: Array[A], radians: Double): Array[A] = obj.map(ev.rotateRadians(_, radians))
  }
}

class TransAlignExtension[T](value: T, ev: TransAlign[T])
{
  /** Translate 2 dimensional vectors along the X axis */
  def slateX(xOffset: Double): T = ev.slate(value, xOffset vv 0)

  /** Translate 2 dimensional vectors along the Y axis */
  def slateY(yOffset: Double): T = ev.slate(value, 0 vv yOffset)

  /** Translate in 2 dimensional space. */
  def slate(offset: Vec2): T = ev.slate(value, offset)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): T = ev.slate(value, xOffset vv yOffset)

  /** this.asInstanceOf[T] */
  def identity: T = this.asInstanceOf[T]
}