/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

trait TransAlignElem extends TransElem
{ type ThisT <: TransAlignElem
  def fTrans(f: Vec2 => Vec2): ThisT
}

/** A transformation type class allowing only translations and scaling, in order to maintain the alignment of the graphical objects. */
trait TransAlign[T]
{ def slate(obj: T, offset: Vec2): T
  def scale(obj: T, operand: Double): T
}

/** Companion object for the TransAlign type class. Contains instances for various container classes. */
object TransAlign
{
  implicit def transImplicit: TransAlign[TransElem] = new TransAlign[TransElem]
  {
    override def slate(obj: TransElem, offset: Vec2): TransElem = obj.slate(offset)    

    override def scale(obj: TransElem, operand: Double): TransElem = obj.scale(operand)
  }

  implicit def transAlignerImplicit[T <: TransSimElem]: TransAlign[T] = new TransAlign[T]
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