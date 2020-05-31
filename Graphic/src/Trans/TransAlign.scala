/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** An object that implements the TransAlign interface through its own methods. */
trait TransAligner extends Transer
{ type AlignT <: TransAligner
  def slate(offset: Vec2): AlignT

  def scale(operand: Double): AlignT
  //def ySlate(yDelta: Double): AlignT = slateOld(0 vv yDelta)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): AlignT = slate(xOffset vv yOffset)
  //def slated(xOffset: Double, yOffset: Double): AlignT = slate(xOffset vv yOffset)
}

/** A transformation type class allowing only translations and scaling, in order to maintain the alignment of the graphical objects. */
trait TransAlign[T]
{ def slate(obj: T, offset: Vec2): T
  def scale(obj: T, operand: Double): T
}

object TransAlign
{
  implicit def transImplicit: TransAlign[Transer] = new TransAlign[Transer]
  {
    override def slate(obj: Transer, offset: Vec2): Transer = obj match
    { case ta: TransAligner => ta.slate(offset).asInstanceOf[Transer]
      case gea: Transer => gea.slate(offset)
    }

    override def scale(obj: Transer, operand: Double): Transer = obj match
    { case ta: TransAligner => ta.scale(operand).asInstanceOf[Transer]
      case gea: Transer => gea.scale(operand)
    }
  }

  implicit def transAlignerImplicit[T <: TransAligner]: TransAlign[T] = new TransAlign[T]
  { override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransAlign[A]): TransAlign[AA] = new TransAlign[AA]
  { override def slate(obj: AA, offset: Vec2): AA = obj.map(ev.slate(_, offset))
    override def scale(obj: AA, operand: Double): AA = obj.map(ev.scale(_, operand))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransAlign[A]): TransAlign[F[A]] = new TransAlign[F[A]]
  { override def slate(obj: F[A], offset: Vec2): F[A] = evF.map(obj, evA.slate(_, offset))
    override def scale(obj: F[A], operand: Double): F[A] = evF.map[A, A](obj, ts => evA.scale(ts, operand))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransAlign[A]): TransAlign[Array[A]] = new TransAlign[Array[A]]
  { override def slate(obj: Array[A], offset: Vec2): Array[A] = obj.map(ev.slate(_, offset))
   override def scale(obj: Array[A], operand: Double): Array[A] = obj.map(ev.scale(_, operand))
  }
}

class TransAlignExtension[T](value: T, ev: TransAlign[T])
{
//  /** Translate 2 dimensional vectors along the X axis */
//  def slateX(xOffset: Double): T = ev.slate(value, xOffset vv 0)
//
//  /** Translate 2 dimensional vectors along the Y axis */
//  def slateY(yOffset: Double): T = ev.slate(value, 0 vv yOffset)
//
//  /** Translate in 2 dimensional space. */
//  def slate(offset: Vec2): T = ev.slate(value, offset)
//
//  /** Translate in 2 dimensional space. */
//  def slate(xOffset: Double, yOffset: Double): T = ev.slate(value, xOffset vv yOffset)

  /** this.asInstanceOf[T] */
  def identity: T = this.asInstanceOf[T]

  //def scale(operand: Double): T = ev.scale(value, operand)

  /** The scale transformation on 2 dimensional vectors. */
  def scaleSlate(factor: Double, addVec: Vec2): T =
  { val r1 = ev.scale(value, factor)
    ev.slate(r1, addVec)
  }
}