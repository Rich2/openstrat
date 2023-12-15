/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** [[Unshow]] type classes for SeqLike. This trait actually implements fromExpr method.  */
trait UnshowSeqLike[Ae, A] extends Unshow[A]
{ /** [[Unshow]] type class instance for the elements of the seqLike. */
  def unshowAeEv: Unshow[Ae]
  def build: BuilderCollMap[Ae, A]

  override def fromExpr(expr: Expr): EMon[A] = expr match
  { case _: EmptyExprToken => Good(build.empty)

    case AlphaMaybeSquareParenth(str1, sts) if str1 == typeStr => if(unshowAeEv.useMultiple) Multiple.collFromArrStatement(sts)(unshowAeEv, build)
      else sts.mapEMon(build)(s => unshowAeEv.fromExpr(s.expr))

    case ExprSeqNonEmpty(mems) => if (unshowAeEv.useMultiple) Multiple.collFromArrExpr(mems)(unshowAeEv, build)
    else mems.mapEMon(build)(e => unshowAeEv.fromExpr(e))

    case e => bad1(expr, expr.toString + " unknown Expression for this sequence based class.")
  }
}

object UnshowSeqLike
{ /** Factory apply method for creating [[Unshow]] type class instances for [[SeqLike]] objects. */
  def apply[A, R](typeStr: String)(implicit evA: Unshow[A], build: BuilderCollMap[A, R]): UnshowSeqLike[A, R] =
    new UnshowSeqLikeImp[A, R](typeStr, evA, build)

  /** Implementation class for the general cases of [[UnshowSeqLike]]. Use [[UnshowSeq]] for any actual sequence classes. */
  class UnshowSeqLikeImp[A, R](val typeStr: String, val unshowAeEv: Unshow[A], val build: BuilderCollMap[A, R]) extends UnshowSeqLike[A, R]
}

/** [[Unshow]] type class instances for sequences, both [[Sequ]] and standard library classes such as [[List]] and
 * [[Array]]. Uses the typeStr "Seq". As all these different types are persisted as logical sequences. Their in memory
 * storage structure is irrelevant. They can all be reconstructed / unshown from an RSON Seq. */
class UnshowSeq[A, R](val unshowAeEv: Unshow[A], val build: BuilderCollMap[A, R]) extends UnshowSeqLike[A, R]
{ def typeStr: String = "Seq"
  override def useMultiple: Boolean = false
}

object UnshowSeq
{ /** Factory apply method for creating [[Unshow]] type class instances / evidence for any type of sequence. */
  def apply[A, R]()(implicit evA: Unshow[A], build: BuilderCollMap[A, R]): UnshowSeq[A, R] = new UnshowSeq[A, R](evA, build)
}

/** [[Unshow]] type class instances for building classes from sequences through two builders. */
class UnshowFromArr[A, ArrA <: Arr[A], R](val typeStr: String, f: ArrA => R)(implicit evA: Unshow[A],
  build1: BuilderArrMap[A, ArrA]) extends Unshow[R]
{ /** [[Unshow]]s the sequence from which the actual wanted type is mapped. */
  val stage: UnshowSeqLike[A, ArrA] = UnshowSeqLike[A, ArrA](typeStr)(evA, build1)

  override def fromExpr(expr: Expr): EMon[R] = stage.fromExpr(expr).map(f)
}