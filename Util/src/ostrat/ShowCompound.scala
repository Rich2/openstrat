/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

trait ShowCompound[R] extends Show[R]
{ final override def show(obj: R): String = typeStr + showSemi(obj).enParenth
  @inline override def showTyped(obj: R): String = show(obj)
}

/** Persistence base trait for PersistCase and PerististSeqLike. Some methods probably need to be moved down into sub classes. */
trait PersistCompound[R] extends ShowCompound[R] with Persist[R]
{
  override def fromExpr(expr: ParseExpr): EMon[R] =  expr match
  {
    case AlphaBracketExpr(IdentifierToken(_, typeName), Refs1(ParenthBlock(sts, _, _))) if typeStr == typeName => fromParameterStatements(sts)
    case AlphaBracketExpr(IdentifierToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }

  /** Not sure about this method */
  def fromParameterStatements(sts: Refs[Statement]): EMon[R]

  override def fromStatements(sts: Refs[Statement]): EMon[R] = fromParameterStatements(sts)
}







