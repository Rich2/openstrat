/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

trait TypeStrN extends Any with TypeStr
{
  def paramNames: Strings// = Strings(name1, name2)

  def numParams: Int// = 2
}

/** The base trait for the persistence of algebraic product types, including case classes. */
trait ShowNT[R] extends ShowCompoundT[R] with ShowT[R]
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

  def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): Strings

  def strs(obj: R, way: ShowStyle): Strings = strDecs(obj, way, -1)

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

trait ShowShowNT[R <: ShowN] extends ShowNT[R] with ShowShowT[R]
{ override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): Strings = obj.showElemStrDecs(way, maxPlaces)
}

trait UnshowN[R] extends Unshow[R] with TypeStrN
{
  protected def fromSortedExprs(sortedExprs: Arr[Expr], pSeq: Ints): EMon[R]

  final override def fromExpr(expr: Expr): EMon[R] = expr match
  { case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => fromExprSeq(sts.map(_.expr))
    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case ExprSeqNonEmpty(exprs) => fromExprSeq(exprs)
    case _ => expr.exprParseErr[R](this)
  }

  /** Tries to construct the type from a sequence of parameters using out of order named parameters and default values. */
  final def fromExprSeq(exprs: Arr[Expr]): EMon[R] =
    if(exprs.length > numParams) Bad(Strings(exprs.length.toString + " parameters for 2 parameter constructor."))
    else
    {
      def exprsLoop(i: Int, oldSeq: Ints, newSeq: Ints, usedNames: Strings): EMon[R] =
        if (i >= exprs.length) fromSortedExprs(exprs, newSeq ++ oldSeq)
        else exprs(i) match
        {
          case AsignExprName(name) if !paramNames.contains(name) => bad1(exprs(i),"Unrecognised setting identifer name.")
          case AsignExprName(name) if usedNames.contains(name) => bad1(exprs(i), name + " Multiple parameters of the same name.")

          case AsignExprName(name) => { val nameInd = paramNames.indexOf(name)
            val oldSeqInd = oldSeq.indexOf(nameInd)
            exprsLoop(i + 1,oldSeq.removeIndex(oldSeqInd), newSeq :+ oldSeq(oldSeqInd), usedNames :+ name)
          }

          case _ => exprsLoop(i + 1, oldSeq.drop1, newSeq :+ oldSeq(0), usedNames)
        }
      exprsLoop(0, Ints.until(0, numParams), Ints(), Strings())
  }
}