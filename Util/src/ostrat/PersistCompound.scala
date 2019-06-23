/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** Persistence base trait for PersistCase and PerististSeqLike. Some methods probably need to be moved down into sub classes. */
trait PersistCompound[R] extends ShowCompound[R] with Persist[R]
{ 
  override def fromExpr(expr: ParseExpr): EMon[R] =  expr match
  {
    case AlphaBracketExpr(AlphaToken(_, typeName), Arr(ParenthBlock(sts, _, _))) if typeStr == typeName => fromParameterStatements(sts)
    case AlphaBracketExpr(AlphaToken(fp, typeName), _) => bad1(fp, typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }

  /** Not sure about this method */
  def fromParameterStatements(sts: Arr[Statement]): EMon[R]
  
  override def fromStatements(sts: Arr[Statement]): EMon[R] = fromParameterStatements(sts)
}

trait ShowCompound[R] extends Show[R]
{
  final override def show(obj: R): String = typeStr + showSemi(obj).enParenth 
  @inline override def showTyped(obj: R): String = show(obj)
}









