/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Extractor object for an [[AlphaBracketExpr]] with a square brackets followed by a single parentheses block. */
object AlphaSquareParenth
{ /** Extractor unapply method for an [[AlphaBracketExpr]] with a square brackets block followed by a single parentheses block. */
  def unapply(expr: ColonMemExpr): Option[(String, RArr[Statement], RArr[Statement])] = expr match
  { case AlphaBracketExpr(IdentifierToken(name), Arr2(SquareBlock(sts1, _, _) , ParenthBlock(sts2, _, _))) => Some((name, sts1, sts2))
    case _ => None
  }
}

/** Extractor object for an [[AlphaBracketExpr]] with a single parentheses block. */
object AlphaParenth
{ /** Extractor unapply method for an [[AlphaBracketExpr]] with a single parentheses block. */
  def unapply(expr: ColonMemExpr): Option[(String, RArr[Statement])] = expr match
  { case AlphaBracketExpr(IdentifierToken(name), Arr1(ParenthBlock(sts, _, _))) => Some((name, sts))
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
  override def syntaxDepth(obj: R): Int = obj.foldLeft[Int](1)((acc: Int, el: A) => acc.max(evA.syntaxDepth(el))) + 1

  override def showMap(obj: R)(f: A => String): StrArr = obj.mapArr(f)


}

/** [[Show] type class for showing [[Sequ]][A] objects. */
trait ShowSequ[A, R <: Sequ[A]] extends ShowSeq[A, R]
{ override def syntaxDepth(obj: R): Int = obj.foldLeft(1)((acc, a) => acc.max(evA.syntaxDepth(a)))

  override def showMap(obj: R)(f: A => String): StrArr = obj.map(f)
 // override def showDec(obj: R, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
   // typeStr + obj.map(a => evA.showDec(a, ShowCommas, maxPlaces, minPlaces)).mkStr("; ").enParenth
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