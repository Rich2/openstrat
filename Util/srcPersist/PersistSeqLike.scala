/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Not sure if these are still useful. */
object AlphaSquareParenth
{
  def unapply(expr: ColonMemExpr): Option[(String, RArr[Statement], RArr[Statement])] = expr match
  {
    case AlphaBracketExpr(IdentLowerToken(_, name), Arr2(SquareBlock(ts, _, _) , ParenthBlock(sts, _, _))) => Some((name, ts, sts))
    case _ => None
  }
}

/** Not sure if these are still usefull. */
object AlphaParenth
{
  def unapply(expr: ColonMemExpr): Option[(String, RArr[Statement])] = expr match
  {
    case AlphaBracketExpr(IdentLowerToken(_, name), Arr1(ParenthBlock(sts, _, _))) => Some((name, sts))
    case _ => None
  }
}

abstract class PersistSeqLike[A, R](override val evA: Persist[A]) extends ShowSeq[A, R] with PersistCompound[R]
{
  def fromExprLike(expr: Expr): EMon[List[A]] = expr match
  {
    case SemicolonToken(_) => Good(List[A]())
    case AlphaSquareParenth("Seq", ts, sts) => ??? //sts.eMap(s => evA.fromExpr(s.expr)).toList
    case AlphaParenth("Seq", sts) => ??? // sts.eMap[A](_.errGet[A](evA))
    case e => bad1(expr, expr.toString + " Unknown Expression for Seq")
  }
}

abstract class PersistIterable[A, R <: Iterable[A]](ev: Persist[A]) extends PersistSeqLike[A, R](ev) with ShowIterable[A, R]

trait ShowIterable[A, R <: Iterable[A]] extends ShowSeq[A, R]
{
  override def syntaxDepthT(obj: R): Int = obj.foldLeft[Int](1)((acc: Int, el: A) => acc.max(evA.syntaxDepthT(el)))

  final override def showDec(obj: R, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = way match
  {
    case ShowCommas if obj.foldLeft[Int](1)((acc: Int, el: A) =>
      acc.max(evA.syntaxDepthT(el))) == 1 => obj.map(el => evA.showDec(el, ShowStandard, maxPlaces, 0)).commaFold

    case ShowSemis if obj.foldLeft(1)((acc, el) =>
      acc.max(evA.syntaxDepthT(el))) <= 2 => obj.map(el => evA.showDec(el, ShowCommas, maxPlaces, 0)).semiFold

    case _ => typeStr + obj.map(el => evA.showDec(el, ShowCommas, maxPlaces, 0)).semiFold.enParenth
  }
}

/** [[Show] type class for showing [[Sequ]][A] objects. */
trait ShowSequ[A, R <: Sequ[A]] extends ShowSeq[A, R]
{ override def syntaxDepthT(obj: R): Int = obj.foldLeft(1)((acc, a) => acc.max(evA.syntaxDepthT(a)))

  override def showDec(obj: R, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
    typeStr + obj.map(a => evA.showDec(a, ShowCommas, maxPlaces, minPlaces)).mkStr("; ").enParenth
}
/*class PersistConsImplicit[A](ev: Persist[A]) extends PersistIterable[A, ::[A]](ev)
{
  override def fromExpr(expr: Expr): EMon[::[A]] = fromExprLike(expr).flatMap[::[A]]
  {
    case h :: tail => Good[::[A]](::(h, tail))
    case Nil => bad1(TextSpan.empty, "Empty List can not be parsed into Cons.")
  }
}*/
 
/*class PersistNilImplicit[A](ev: Persist[A]) extends PersistSeqLike[A, Nil.type](ev)
{
  override def fromExpr(expr: Expr): EMon[Nil.type] = fromExprLike(expr).flatMap[Nil.type]
  { case h :: tail => bad1[Nil.type](TextSpan.empty, "Non empty List can not be parsed into Nil.")
    case Nil => Good[Nil.type](Nil) 
  }
}*/

class PersistSeqImplicit[A](ev: Persist[A]) extends PersistIterable[A, Seq[A]](ev)
{
  override def fromExpr(expr: Expr): EMon[Seq[A]] = fromExprLike(expr)
}