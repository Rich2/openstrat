/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** A transformation type class allowing only translations and scaling, in order to maintain the alignment of the graphical objects. */
trait TransAlign[T]
{ def slate(obj: T, offset: VecPt2): T
  def scale(obj: T, operand: Double): T
}

/** Companion object for the TransAlign type class. Contains instances for various container classes. */
object TransAlign
{
  implicit def transImplicit: TransAlign[Aff2Elem] = new TransAlign[Aff2Elem]
  { override def slate(obj: Aff2Elem, offset: VecPt2): Aff2Elem = obj.slate(offset)
    override def scale(obj: Aff2Elem, operand: Double): Aff2Elem = obj.scale(operand)
  }

  implicit def transAlignerImplicit[T <: SimilarPreserve]: TransAlign[T] = new TransAlign[T]
  { override def slate(obj: T, offset: VecPt2): T = obj.slate(offset).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: Arr[A]](implicit build: BuilderArrMap[A, AA], ev: TransAlign[A]): TransAlign[AA] = new TransAlign[AA]
  { override def slate(obj: AA, offset: VecPt2): AA = obj.map(ev.slate(_, offset))
    override def scale(obj: AA, operand: Double): AA = obj.map(ev.scale(_, operand))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransAlign[A]): TransAlign[F[A]] = new TransAlign[F[A]]
  { override def slate(obj: F[A], offset: VecPt2): F[A] = evF.mapT(obj, evA.slate(_, offset))
    override def scale(obj: F[A], operand: Double): F[A] = evF.mapT[A, A](obj, ts => evA.scale(ts, operand))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransAlign[A]): TransAlign[Array[A]] = new TransAlign[Array[A]]
  { override def slate(obj: Array[A], offset: VecPt2): Array[A] = obj.map(ev.slate(_, offset))
   override def scale(obj: Array[A], operand: Double): Array[A] = obj.map(ev.scale(_, operand))
  }
}