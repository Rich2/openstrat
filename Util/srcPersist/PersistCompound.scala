/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

import scala.collection.mutable.ArrayBuffer

/** Show trait for Compound types contain elements, requiring the Show class or classes for the type or types of the constituent elements. */
trait ShowCompound[R] extends Show[R]
{ override def strT(obj: R): String = show(obj, ShowStandard)//, -1, 0)
}

trait UnshowCompound[+R] extends Unshow[R]
{
  override def fromExpr(expr: Expr): EMon[R] = expr match
  { case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? //fromParameterStatements(sts)
    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }
}

trait ShowSeqLike[A, R] extends ShowCompound[R]
{ def evA: Show[A]

  def showForeach(obj: R, f: A => Unit): Unit

  override def syntaxDepth(obj: R): Int =
  { var acc = 1
    showForeach(obj, a => acc = acc.max(evA.syntaxDepth(a)))
    acc
  }

  final def showMap(obj: R)(f: A => String): StrArr =
  { val buffer: ArrayBuffer[String] = Buffer[String]()
    showForeach(obj, a => buffer.append(f(a)))
    new StrArr(buffer.toArray)
  }

  final override def showDec(obj: R, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
  { val depth = syntaxDepth(obj)
    way match {
      case ShowCommas if depth == 2 => showMap(obj)(el => evA.showDec(el, ShowStandard, maxPlaces, 0)).mkComma
      case ShowSemis if depth <= 2 => showMap(obj)(el => evA.showDec(el, ShowCommas, maxPlaces, 0)).mkSemi
      case ShowTyped => typeStr + evA.typeStr.enSquare + showMap(obj)(el => evA.showDec(el, ShowCommas, maxPlaces, 0)).mkSemiParenth
      case _ => typeStr + showMap(obj)(el => evA.showDec(el, ShowCommas, maxPlaces, 0)).mkSemiParenth
    }
  }
}

object ShowSeqLike
{
  def apply[A, R](typeStr: String, fForeach: (R, A => Unit) => Unit)(implicit evA: Show[A]): ShowSeqLike[A, R] =
    new ShowSeqLikeImp[A, R](typeStr, fForeach)(evA)

  class ShowSeqLikeImp[A, R](val typeStr: String, fForeach: (R, A => Unit) => Unit)(implicit val evA: Show[A]) extends ShowSeqLike[A, R]
  { override def showForeach(obj: R, f: A => Unit): Unit = fForeach
  }
}