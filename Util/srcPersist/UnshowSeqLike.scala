/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

trait UnshowSeqLike[A, R] extends Unshow[R]
{
  def evA: Unshow[A]
  def build: BuilderCollMap[A, R]

  override def fromExpr(expr: Expr): EMon[R] = expr match
  { case _: EmptyExprToken => Good(build.empty)
    case AlphaSquareParenth(str1, _, sts) if str1 == typeStr => sts.eMapLike(s => evA.fromExpr(s.expr))(build)
    case AlphaParenth(str1, sts) if str1 == typeStr => sts.eMapLike(s => evA.fromExpr(s.expr))(build)
    case e => bad1(expr, expr.toString + " unknown Expression for this sequence based class.")
  }
}

object UnshowSeqLike
{
  def apply[A, R](typeStr: String)(implicit evA: Unshow[A], build: BuilderCollMap[A, R]): UnshowSeqLike[A, R] =
    new UnshowSeqLikeImp[A, R](typeStr, evA, build)

  class UnshowSeqLikeImp[A, R](val typeStr: String, val evA: Unshow[A], val build: BuilderCollMap[A, R]) extends UnshowSeqLike[A, R]
}

class UnshowSequ[A, R](val evA: Unshow[A], val build: BuilderCollMap[A, R]) extends UnshowSeqLike[A, R]
{ def typeStr: String = "Seq"
}

object UnshowSequ
{
  def apply[A, R]()(implicit evA: Unshow[A], build: BuilderCollMap[A, R]): UnshowSequ[A, R] = new UnshowSequ[A, R](evA, build)
}