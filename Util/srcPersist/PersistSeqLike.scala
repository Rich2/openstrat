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

trait ShowIterable[A, R <: Iterable[A]] extends ShowSeq[A, R]
{ override def syntaxDepth(obj: R): Int = obj.foldLeft[Int](1)((acc: Int, el: A) => acc.max(evA.syntaxDepth(el))) + 1
  override def showMap(obj: R)(f: A => String): StrArr = obj.mapArr(f)
}

/** [[Show] type class for showing [[Sequ]][A] objects. */
trait ShowSequ[A, R <: Sequ[A]] extends ShowSeq[A, R]
{ override def syntaxDepth(obj: R): Int = obj.foldLeft(1)((acc, a) => acc.max(evA.syntaxDepth(a)))
  override def showMap(obj: R)(f: A => String): StrArr = obj.map(f)
}

object ShowSequ
{
  def apply[A, R <: Sequ[A]]()(implicit evAIn: Show[A]): ShowSequ[A, R] = new ShowSequ[A, R]
  { override val evA: Show[A] = evAIn
  }
}