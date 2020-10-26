/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Show trait for Compound types contain elements, requiring the Show class or classes for the type or types of the constituent elements. */
trait ShowCompound[R] extends Show[R]
{ final override def show(obj: R): String = typeStr + showSemi(obj).enParenth
  @inline override def showTyped(obj: R): String = show(obj)
}

/** Persistence base trait for PersistCase and PersistSeqLike. Some methods probably need to be moved down into sub classes. */
trait PersistCompound[R] extends ShowCompound[R] with Persist[R]
{
  override def fromExpr(expr: ParseExpr): EMon[R] =  expr match
  {
    case AlphaBracketExpr(IdentifierUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? //fromParameterStatements(sts)
    case AlphaBracketExpr(IdentifierUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }
}

trait ShowSeqLike[A, R] extends ShowCompound[R]
{
  def evA: Show[A]
  override def typeStr = "Seq" + evA.typeStr.enSquare
  override def syntaxDepth = evA.syntaxDepth + 1
}