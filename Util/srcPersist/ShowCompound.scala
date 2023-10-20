/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

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

/** Persistence base trait for PersistCase and PersistSeqLike. Some methods probably need to be moved down into sub classes. */
trait PersistCompound[R] extends Persist[R] with ShowCompound[R] with UnshowCompound[R]

trait ShowSeqBased[A, R] extends ShowCompound[R]
{ def evA: Show[A]

  def showMap(obj: R)(f: A => String): StrArr

  final override def showDec(obj: R, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = {
    val depth = syntaxDepth(obj)
    way match {
      case ShowCommas if depth == 2 => showMap(obj)(el => evA.showDec(el, ShowStandard, maxPlaces, 0)).mkStr(", ")
      case ShowSemis if depth <= 2 => showMap(obj)(el => evA.showDec(el, ShowCommas, maxPlaces, 0)).mkStr("; ")
      case ShowTyped => typeStr + evA.typeStr.enSquare + showMap(obj)(el => evA.showDec(el, ShowCommas, maxPlaces, 0)).mkStr("; ").enParenth
      case _ => typeStr + showMap(obj)(el => evA.showDec(el, ShowCommas, maxPlaces, 0)).mkStr("; ").enParenth
    }
  }
}

/** All logical sequence classes are shown as "Seq"s. There encoding in memory and the immutability are irrelevant for their persistence. */
trait ShowSeq[A, R] extends ShowSeqBased[A, R]
{ override def typeStr = "Seq"
}