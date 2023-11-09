/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** [[Unshow]] type classes for SeqLike. This trait actually implements fromExpr method.  */
trait UnshowSeqLike[A, R] extends Unshow[R]
{
  def evA: Unshow[A]
  def build: BuilderCollMap[A, R]

  override def fromExpr(expr: Expr): EMon[R] = expr match
  { case _: EmptyExprToken => Good(build.empty)
    case AlphaSquareParenth(str1, _, sts) if str1 == typeStr => sts.mapEMon(build)(s => evA.fromExpr(s.expr))
    case AlphaParenth(str1, sts) if str1 == typeStr => sts.mapEMon(build)(s => evA.fromExpr(s.expr))
    case e => bad1(expr, expr.toString + " unknown Expression for this sequence based class.")
  }
}

object UnshowSeqLike
{
  def apply[A, R](typeStr: String)(implicit evA: Unshow[A], build: BuilderCollMap[A, R]): UnshowSeqLike[A, R] =
    new UnshowSeqLikeImp[A, R](typeStr, evA, build)

  class UnshowSeqLikeImp[A, R](val typeStr: String, val evA: Unshow[A], val build: BuilderCollMap[A, R]) extends UnshowSeqLike[A, R]
}

/** [[Unshow]] type class instances for sequences, both [[Sequ]] and standard library classes such as [[List]] and
 * [[Array]]. Uses the typeStr "Seq". As all these different types are persisted as logical sequences. Their in memory
 * storage structure is irrelevant. They can all be reconstructed / unshown from an RSON Seq. */
class UnshowSeq[A, R](val evA: Unshow[A], val build: BuilderCollMap[A, R]) extends UnshowSeqLike[A, R]
{ def typeStr: String = "Seq"
}

object UnshowSeq
{
  def apply[A, R]()(implicit evA: Unshow[A], build: BuilderCollMap[A, R]): UnshowSeq[A, R] = new UnshowSeq[A, R](evA, build)
}

class UnshowFromArr[A, ArrA <: Arr[A], R](val typeStr: String, f: ArrA => R)(implicit evA: Unshow[A], build1: BuilderArrMap[A, ArrA]) extends Unshow[R]
{
  val stage: UnshowSeqLike[A, ArrA] = UnshowSeqLike[A, ArrA](typeStr)(evA, build1)

  override def fromExpr(expr: Expr): EMon[R] = stage.fromExpr(expr).map(f)
}