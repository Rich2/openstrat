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
  def build: BuilderSeqLikeMap[A, R]
}