/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Not sure how useful this class is. It is diffcult to abstract over the general case. Its sub classes are the Persist case instances and
 *  the various persist collection classes */
abstract class PersistCompound[R](typeSym: Symbol) extends Persist[R](typeSym)
{ override def persist(obj: R): String = typeStr + persistSemi(obj).enParenth 
  @inline override def persistTyped(obj: R): String = persist(obj)
  
//  override def persistComma(obj: R): String = syntaxDepth match
//  { case 2 => memStrs(obj).commaFold
//    case _ => persist(obj)
//  }
//   
//  override def persistSemi(obj: R): String = syntaxDepth match
//  { case 2 | 3 => memStrs(obj).semicolonFold
//    case _ => persist(obj)
//  }
//   
//   override def persist(obj: R): String =  syntaxDepth match
//  {
//     case sd if sd < 2 => excep("PeristCompound should not have persistDepth of " + sd.toString)
//     case 2 => typeStr - memStrs(obj).strFold("; ").enParenth
//     case _ => typeStr - memStrs(obj).strFold("; ").enParenth
//  }
   
   //def memStrs: R => Seq[String]
  override def fromExpr(expr: Expr): EMon[R] =  expr match
  {
    case AlphaBracketExpr(AlphaToken(_, typeName), Seq(ParenthBlock(sts, _, _))) if typeSym == typeName => fromParameterStatements(sts)
    case AlphaBracketExpr(AlphaToken(fp, typeName), _) => bad1(fp, typeName.name -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }  
  def fromParameterStatements(sts: Seq[Statement]): EMon[R]
  override def fromStatement(st: Statement): EMon[R] = st match
  { case MonoStatement(expr, _) => fromExpr(expr)
    case ClausedStatement(cls, _) => bad1(cls.head.startPosn, "Claused Statement")
    case es @ EmptyStatement(st) => es.asError
  }
}


