/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

class PersistSeqImplicit[A](thisSeq: Seq[A], ev: Persist[A]) extends PersistCompound[Seq[A]]('Seq)
{ override def typeStr = "Seq" + ev.typeStr.enSquare
    override def syntaxDepth = ev.syntaxDepth + 1
    //override def memStrs(obj: Seq[A]): List[String] = obj.toList.map(ev.persist(_))
    def persist/*(thisSeq: Seq[A])*/: String = typeStr + persistSemi(thisSeq).enParenth    
    override def persistSemi(thisSeq: Seq[A]): String = thisSeq.map(ev.persistComma(_)).semicolonFold
    override def persistComma(thisSeq: Seq[A]): String = thisSeq.map(ev.persist(_)).commaFold
 
//} 
//      override def isType(obj: Any): Boolean = obj match
//      {
//         case s @ Seq(mems) => s.forall(el => ev.isType(el)) 
//         case _ => false   
//      }
    override def fromExpr(expr: Expr): EMon[Seq[A]] = ??? //expr match
//      {
//         case SemicolonToken(_) => Good(Seq[A]())
//         //For Some reason the compile is not finding the implicit
//         case AlphaBracketExpr(AlphaToken(_, "Seq"), Seq(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) => sts.eMonMap[A](_.errGet[A](ev))
//         case e => bad1(expr, "Unknown Exoression for Seq")
//      }
//      override def fromClauses(clauses: Seq[Clause]): EMon[Seq[A]] = clauses.eMonMap (cl => ev.fromExpr(cl.expr))
//      override def fromStatement(st: Statement): EMon[Seq[A]] = st match
//      {
//         case MonoStatement(expr, _) => fromExpr(expr)
//         case ClausedStatement(clauses, _) => fromClauses(clauses)
//         case es @ EmptyStatement(_) => es.asError         
//      }
    override def fromParameterStatements(sts: Seq[Statement]): EMon[Seq[A]] = ???
    override def fromClauses(clauses: Seq[Clause]): EMon[Seq[A]] = ???
  }
