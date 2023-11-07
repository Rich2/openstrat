/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

trait UnshowCompound[+R] extends Unshow[R]
{
  override def fromExpr(expr: Expr): EMon[R] = expr match
  { case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? //fromParameterStatements(sts)
    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }
}

trait UnshowSeqLike[A, R <: SeqLike[A]] extends UnshowCompound[R]
{
  def evA: Unshow[A]
  def build: BuilderSeqLikeMap[A, R]

  override def fromExpr(expr: Expr): EMon[R] = expr match
  { case _: EmptyExprToken => Good(build.empty)
    case AlphaSquareParenth(str1, _, sts) if str1 == typeStr => sts.eMapLike(s => evA.fromExpr(s.expr))(build)
    case AlphaParenth(str1, sts) if str1 == typeStr => sts.eMapLike(s => evA.fromExpr(s.expr))(build)
    case e => bad1(expr, expr.toString + " unknown Expression for Seq")
  }
}

class UnshowSequ[A, R <: Sequ[A]](val evA: Unshow[A], val build: BuilderSeqLikeMap[A, R]) extends UnshowSeqLike[A, R]
{ def typeStr: String = "Seq"
}

object UnshowSequ
{
  def apply[A, R <: Sequ[A]]()(implicit evA: Unshow[A], build: BuilderSeqLikeMap[A, R]): UnshowSequ[A, R] = new UnshowSequ[A, R](evA, build)
}