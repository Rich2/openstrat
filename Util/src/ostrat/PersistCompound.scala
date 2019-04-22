/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** Persistence base trait for PersistCase and PerististSeqLike. Some methods probably need to be moved down into sub classes. */
abstract class PersistCompound[R](typeSym: Symbol) extends ShowCompound[R](typeSym) with Persist[R]
{ 
  override def fromExpr(expr: ParseExpr): EMon[R] =  expr match
  {
    case AlphaBracketExpr(AlphaToken(_, typeName), Seq(ParenthBlock(sts, _, _))) if typeSym == typeName => fromParameterStatements(sts)
    case AlphaBracketExpr(AlphaToken(fp, typeName), _) => bad1(fp, typeName.name -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }

  /** Not sure avout this method */
  def fromParameterStatements(sts: List[Statement]): EMon[R]
  override def fromStatement(st: Statement): EMon[R] = st match
  { case MonoStatement(expr, _) => fromExpr(expr)
    case ClausedStatement(cls, _) => bad1(cls.head.startPosn, "Claused Statement")
    case es @ EmptyStatement(st) => es.asError
  } 
} 







