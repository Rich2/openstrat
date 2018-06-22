/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

abstract class PersistCompound[R](typeSym: Symbol) extends Persist[R](typeSym)
{   
   def memStrs(obj: R): List[String]
   
   override def persistComma(obj: R): String = syntaxDepth match
   {
      case 2 => memStrs(obj).commaFold
      case _ => persist(obj)
   }
   override def persistSemi(obj: R): String = syntaxDepth match
   {
      case 2 | 3 => memStrs(obj).semicolonFold
      case _ => persist(obj)
   }
   
   override def persist(obj: R): String = typeStr - memStrs(obj).strFold("; ").enParenth 
   //def memStrs: R => Seq[String]
   override def fromExpr(expr: Expr): EMon[R] =  expr match
   {
      case AlphaBracketExpr(AlphaToken(_, typeName), Seq(ParenthBlock(sts, _, _))) if typeSym == typeName => fromParameterStatements(sts)
      case AlphaBracketExpr(AlphaToken(fp, typeName), _) => bad1(fp, typeName.name -- "does not equal" -- typeStr)
      case _ => expr.exprParseErr[R](this)
   }  
   def fromParameterStatements(sts: Seq[Statement]): EMon[R]
   override def fromStatement(st: Statement): EMon[R] = st match
   {
      case MonoStatement(expr, _) => fromExpr(expr)
      case ClausedStatement(cls, _) => bad1(cls.head.startPosn, "Claused Statement")
      case es @ EmptyStatement(st) => es.asError
   }
}

//class PersistSeqImplicit[A](thisSeq: Seq[A])(implicit ev: Persist[A]) extends PersistCompound[Seq[A]]
//{
//   override def syntaxDepth: Int = ev.syntaxDepth + 1
//   override def persistName = "Seq" + ev.typeStr.enSquare
//   override def persistMems = thisSeq.map(ev.persistObj(_)).toSeq
//}

