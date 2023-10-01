/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

trait PersistBaseN extends Any with PersistBase
{ /** Sequence of the names of parameter constituents of this class. */
  def paramNames: StrArr

  /** Number of parameter constituents of this class. */
  def numParams: Int
}

/** The base trait for the persistence of algebraic product types, including case classes. */
trait ShowNing[R] extends ShowCompoundT[R] with Showing[R]
{
  override def showT(obj: R, style: ShowStyle): String =
  { def semisStr = strs(obj, ShowCommas).mkStr("; ")

    style match
    { case ShowUnderScore => "_"
    case ShowSemis => semisStr
    case ShowCommas => strs(obj, ShowStandard).mkStr(", ")
    case _ => typeStr.appendParenth(semisStr)
    }
  }

  def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr

  def strs(obj: R, way: ShowStyle): StrArr = strDecs(obj, way, -1)

  override def showDecT(obj: R, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
  { def semisStr = strDecs(obj, ShowCommas, maxPlaces).mkStr("; ")

    style match
    { case ShowUnderScore => "_"
    case ShowSemis => semisStr
    case ShowCommas => strDecs(obj, ShowStandard, maxPlaces).mkStr(", ")
    case _ => typeStr.appendParenth(semisStr)
    }
  }
}

/** [[Showing]] trait for types with N show fields that extend [[ShowNed]]. */
trait ShowNeding[R <: ShowNed] extends ShowNing[R] with Showeding[R]
{ override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = obj.showElemStrDecs(way, maxPlaces)
}

trait UnshowN[R] extends Unshow[R] with PersistBaseN
{
  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R]

  final override def fromExpr(expr: Expr): EMon[R] = expr match
  { case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => fromExprSeq(sts.map(_.expr))
    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case ExprSeqNonEmpty(exprs) => fromExprSeq(exprs)
    case _ => expr.exprParseErr[R](this)
  }

  /** Tries to construct the type from a sequence of parameters using out of order named parameters and default values. */
  final def fromExprSeq(exprs: RArr[Expr]): EMon[R] =
    if(exprs.length > numParams) Bad(StrArr(exprs.length.toString + " parameters for 2 parameter constructor."))
    else
    {
      def exprsLoop(i: Int, usedNames: StrArr): EMon[R] =
        if (i >= exprs.length)
          if (i >= numParams) fromSortedExprs(exprs, paramNames.map(pn => usedNames.findIndex(_ == pn)))
          else exprsLoop(i + 1, usedNames +% paramNames.find(u => !usedNames.exists(_ == u)).get)
        else exprs(i) match
        {
          case AsignExprName(name) if !paramNames.contains(name) => bad1(exprs(i),"Unrecognised setting identifer name.")
          case AsignExprName(name) if usedNames.contains(name) => bad1(exprs(i), name + " Multiple parameters of the same name.")
          case AsignExprName(name) => exprsLoop(i + 1, usedNames +% name)
          case _ => exprsLoop(i + 1, usedNames +% paramNames.find(u => !usedNames.exists(_ == u)).get)
        }
      exprsLoop(0, StrArr())
    }
}