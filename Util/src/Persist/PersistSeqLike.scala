/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.mutable.ArrayBuffer

object AlphaSquareParenth
{
  def unapply(expr: Expr): Option[(String, Arr[Statement], Arr[Statement])] = expr match
  {
    case AlphaBracketExpr(IdentLowerToken(_, name), Arr2(SquareBlock(ts, _, _) , ParenthBlock(sts, _, _))) => Some((name, ts, sts))
    case _ => None
  }
}

object AlphaParenth
{
  def unapply(expr: Expr): Option[(String, Arr[Statement])] = expr match
  {
    case AlphaBracketExpr(IdentLowerToken(_, name), Arr1(ParenthBlock(sts, _, _))) => Some((name, sts))
    case _ => None
  }
}

abstract class PersistSeqLike[A, R](override val evA: Persist[A]) extends ShowTSeqLike[A, R] with PersistCompound[R]
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

trait ShowIterable[A, R <: Iterable[A]] extends ShowTSeqLike[A, R]
{
  override def syntaxDepthT(obj: R): Int = obj.foldLeft[Int](1)((acc: Int, el: A) => acc.max(evA.syntaxDepthT(el)))

  final override def showT(obj: R, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = way match
  {
    case Show.Commas if obj.foldLeft[Int](1)((acc: Int, el: A) =>
      acc.max(evA.syntaxDepthT(el))) == 1 => obj.map(el => evA.showT(el, Show.Standard, maxPlaces, 0)).commaFold

    case Show.Semis if obj.foldLeft(1)((acc, el) =>
      acc.max(evA.syntaxDepthT(el))) <= 2 => obj.map(el => evA.showT(el, Show.Commas, maxPlaces, 0)).semiFold

    case _ => typeStr + obj.map(el => evA.showT(el, Show.Semis, maxPlaces, 0)).semiFold.enParenth
  }
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


/**  Class to persist specialised flat Array[Int] based collections of [[Int2Elem]]s. */
abstract class Int2sArrPersist[A <: Int2Elem, M <: Int2sSeq[A]](typeStr: String) extends DataIntNsPersist[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value.int1
    buf += value.int2
  }

  override def syntaxDepthT(obj: M): Int = 3
}