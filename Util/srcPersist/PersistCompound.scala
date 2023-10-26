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

trait ShowSeqBased[A, R] extends ShowCompound[R]
{ def evA: Show[A]

  def showMap(obj: R)(f: A => String): StrArr

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

object ShowSeqBased
{
  class ShowSeqBasedImp[A, R] extends ShowSeqBased[A, R]{
    override def evA: Show[A] = ???

    override def showMap(obj: R)(f: A => String): StrArr = ???

    /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
     * should always be determined at compile time or if sometimes it should be determined at runtime. */
    override def syntaxDepth(obj: R): Int = ???

    /** The RSON type of T. This the only data that a ShowT instance requires, that can't be implemented through delegation to an object of type
     * Show. */
    override def typeStr: String = ???
  }
}