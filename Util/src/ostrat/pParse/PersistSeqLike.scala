/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

object AlphaSquareParenth
{
  def unapply(expr: Expr): Option[(String, List[Statement], List[Statement])] = expr match
  {
    case AlphaBracketExpr(AlphaToken(_, name), SquareBlock(ts, _, _) :: ParenthBlock(sts, _, _) :: Nil) => Some((name, ts, sts))
    case _ => None
  }
}
abstract class PersistSeqLike[A, R](val typeSym: Symbol, val ev: Persist[A]) extends ShowCompound[R] with PersistCompound[R]
{
  override def typeStr = "Seq" + ev.typeStr.enSquare
  override def syntaxDepth = ev.syntaxDepth + 1    
}

class PersistListImplicit[A](ev: Persist[A]) extends PersistSeqLike[A, List[A]]('List, ev)
{
  override def showSemi(thisSeq: List[A]): String = thisSeq.map(ev.showComma(_)).semiFold
  override def showComma(thisSeq: List[A]): String = thisSeq.map(ev.show(_)).commaFold
 
  override def fromExpr(expr: Expr): EMon[List[A]] = expr match
  { case SemicolonToken(_) => Good(List[A]())
//         //For Some reason the compile is not finding the implicit
    case AlphaSquareParenth("Seq", ts, sts) => sts.eMonMap[A](_.errGet[A](ev))
    case e => bad1(expr, "Unknown Exoression for Seq")
  }
  override def fromParameterStatements(sts: List[Statement]): EMon[List[A]] = ???
  override def fromClauses(clauses: List[Clause]): EMon[List[A]] = ???
}

class PersistSeqImplicit[A](ev: Persist[A]) extends PersistSeqLike[A, Seq[A]]('Seq, ev)
  {     
    override def showSemi(thisSeq: Seq[A]): String = thisSeq.map(ev.showComma(_)).semiFold
    override def showComma(thisSeq: Seq[A]): String = thisSeq.map(ev.show(_)).commaFold
 
   override def fromExpr(expr: Expr): EMon[Seq[A]] = expr match
   { case SemicolonToken(_) => Good(Seq[A]())
//         //For Some reason the compile is not finding the implicit
    case AlphaBracketExpr(AlphaToken(_, "Seq"), Seq(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) => sts.eMonMap[A](_.errGet[A](ev))
    case e => bad1(expr, "Unknown Exoression for Seq")
  }
//      override def fromClauses(clauses: Seq[Clause]): EMon[Seq[A]] = clauses.eMonMap (cl => ev.fromExpr(cl.expr))
//      override def fromStatement(st: Statement): EMon[Seq[A]] = st match
//      {
//         case MonoStatement(expr, _) => fromExpr(expr)
//         case ClausedStatement(clauses, _) => fromClauses(clauses)
//         case es @ EmptyStatement(_) => es.asError         
//  }
  override def fromParameterStatements(sts: List[Statement]): EMon[Seq[A]] = ???
  override def fromClauses(clauses: List[Clause]): EMon[Seq[A]] = ???
  }

class PersistVectorImplicit[A](ev: Persist[A]) extends PersistSeqLike[A, Vector[A]]('Vector, ev)
{
  override def showSemi(thisSeq: Vector[A]): String = thisSeq.map(ev.showComma(_)).semiFold
  override def showComma(thisSeq: Vector[A]): String = thisSeq.map(ev.show(_)).commaFold
 
  override def fromExpr(expr: Expr): EMon[Vector[A]] = expr match
  { case SemicolonToken(_) => Good(Vector[A]())
//         //For Some reason the compile is not finding the implicit
    case AlphaBracketExpr(AlphaToken(_, "Seq"), Seq(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) =>
      sts.eMonMap[A](_.errGet[A](ev)).map(_.toVector)
    case e => bad1(expr, "Unknown Exoression for Seq")
  }
//      override def fromClauses(clauses: Seq[Clause]): EMon[Seq[A]] = clauses.eMonMap (cl => ev.fromExpr(cl.expr))
//      override def fromStatement(st: Statement): EMon[Seq[A]] = st match
//      {
//         case MonoStatement(expr, _) => fromExpr(expr)
//         case ClausedStatement(clauses, _) => fromClauses(clauses)
//         case es @ EmptyStatement(_) => es.asError         
//  }
  override def fromParameterStatements(sts: List[Statement]): EMon[Vector[A]] = ???
  override def fromClauses(clauses: List[Clause]): EMon[Vector[A]] = ???
}
