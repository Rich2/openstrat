/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Show trait for Compound types contain elements, requiring the Show class or classes for the type or types of the constituent elements. */
trait ShowCompoundT[R] extends ShowT[R]
{ override def strT(obj: R): String = showT(obj, ShowStandard)//, -1, 0)
}

/** Persistence base trait for PersistCase and PersistSeqLike. Some methods probably need to be moved down into sub classes. */
trait PersistCompound[R] extends ShowCompoundT[R] with PersistDec[R]
{
  override def fromExpr(expr: Expr): EMon[R] =  expr match
  {
    case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ???//fromParameterStatements(sts)
    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }
}

trait ShowTSeqLike[A, R] extends ShowCompoundT[R] with ShowDecT[R]
{ def evA: ShowDecT[A]
  override def typeStr = "Seq" + evA.typeStr.enSquare
}