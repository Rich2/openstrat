/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** A transformation type class allowing only translations and scaling, in order to maintain the alignment of the graphical objects. */
trait AlignTrans[T]
{ def slate(obj: T, operand: VecPt2): T
  def scale(obj: T, operand: Double): T
  def slateFrom(obj: T, offset: VecPt2): T = slate(obj, Pt2(-offset.x, -offset.y))
}

/** Companion object for the TransAlign type class. Contains instances for various container classes. */
object AlignTrans
{
  given arrEv[A, AA <: Arr[A]](using build: BuilderArrMap[A, AA], ev: AlignTrans[A]): AlignTrans[AA] = new AlignTrans[AA]
  { override def slate(obj: AA, operand: VecPt2): AA = obj.map(ev.slate(_, operand))
    override def scale(obj: AA, operand: Double): AA = obj.map(ev.scale(_, operand))
  }

  given functorEv[A, F[_]](using evF: Functor[F], evA: AlignTrans[A]): AlignTrans[F[A]] = new AlignTrans[F[A]]
  { override def slate(obj: F[A], operand: VecPt2): F[A] = evF.mapT(obj, evA.slate(_, operand))
    override def scale(obj: F[A], operand: Double): F[A] = evF.mapT[A, A](obj, ts => evA.scale(ts, operand))
  }

  given arrayEv[A](using ct: ClassTag[A], ev: AlignTrans[A]): AlignTrans[Array[A]] = new AlignTrans[Array[A]]
  { override def slate(obj: Array[A], operand: VecPt2): Array[A] = obj.map(ev.slate(_, operand))
   override def scale(obj: Array[A], operand: Double): Array[A] = obj.map(ev.scale(_, operand))
  }
}