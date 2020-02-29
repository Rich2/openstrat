/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

object AlphaSquareParenth
{
  def unapply(expr: Expr): Option[(String, Refs[Statement], Refs[Statement])] = expr match
  {
    case AlphaBracketExpr(IdentifierLowerToken(_, name), Refs2(SquareBlock(ts, _, _) , ParenthBlock(sts, _, _))) => Some((name, ts, sts))
    case _ => None
  }
}

object AlphaParenth
{
  def unapply(expr: Expr): Option[(String, Refs[Statement])] = expr match
  {
    case AlphaBracketExpr(IdentifierLowerToken(_, name), Refs1(ParenthBlock(sts, _, _))) => Some((name, sts))
    case _ => None
  }
}

abstract class PersistSeqLike[A, R](override val evA: Persist[A]) extends ShowSeqLike[A, R] with PersistCompound[R]
{
  def fromExprLike(expr: Expr): EMon[List[A]] = expr match
  {
    case SemicolonToken(_) => Good(List[A]())
    case AlphaSquareParenth("Seq", ts, sts) => ??? //sts.eMap(s => evA.fromExpr(s.expr)).toList
    case AlphaParenth("Seq", sts) => ??? // sts.eMap[A](_.errGet[A](evA))
    case e => bad1(expr, "Unknown Exoression for Seq")
  }
}

abstract class PersistIterable[A, R <: Iterable[A]](ev: Persist[A]) extends PersistSeqLike[A, R](ev) with ShowIterable[A, R]

trait ShowIterable[A, R <: Iterable[A]] extends ShowSeqLike[A, R]
{
  def showSemi(thisIter: R): String = thisIter.map(evA.showComma(_)).semiFold
  override def showComma(thisIter: R): String = ife (thisIter.size == 1, evA.show(thisIter.head) + ",", thisIter.map(evA.show(_)).commaFold)
}

class PersistConsImplicit[A](ev: Persist[A]) extends PersistIterable[A, ::[A]](ev)
{
  override def fromExpr(expr: Expr): EMon[::[A]] = fromExprLike(expr).flatMap
  {
    case h :: tail => Good(::(h, tail))
    case Nil => bad1(TextSpan.empty, "Empty List can not be parsed into Cons.")
  }
 // override def fromParameterStatements(sts: Refs[Statement]): EMon[::[A]] = ???
 // override def fromClauses(clauses: Refs[Clause]): EMon[::[A]] = ???
}
 
class PersistNilImplicit[A](ev: Persist[A]) extends PersistSeqLike[A, Nil.type](ev)
{
  override def showSemi(thisSeq: Nil.type): String = ""
  override def showComma(thisSeq: Nil.type): String = ""
 
  override def fromExpr(expr: Expr): EMon[Nil.type] = fromExprLike(expr).flatMap
  {
    case h :: tail => bad1(TextSpan.empty, "Non empty List can not be parsed into Nil.")
    case Nil => Good(Nil) 
  }
 // override def fromParameterStatements(sts: Refs[Statement]): EMon[Nil.type] = ???
 // override def fromClauses(clauses: Refs[Clause]): EMon[Nil.type] = ???
}

class PersistSeqImplicit[A](ev: Persist[A]) extends PersistIterable[A, Seq[A]](ev)
{
  override def fromExpr(expr: Expr): EMon[Seq[A]] = fromExprLike(expr)
 // override def fromParameterStatements(sts: Refs[Statement]): EMon[Seq[A]] = ???
  //override def fromClauses(clauses: Refs[Clause]): EMon[Seq[A]] = ???
}