/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

trait PersistN extends Any with Persist
{ /** Sequence of the names of parameter constituents of this class. */
  def paramNames: StrArr
}

/** Base trait for [[ShowNFixed]] and [[UnshowN]]. */
trait PersistNFixed extends Any with PersistN
{ /** Number of parameter constituents of this class. */
  def numParams: Int
}

/** The base trait for the persistence of algebraic product types, including case classes. */
trait ShowNFixed[A] extends ShowCompound[A] with PersistNFixed
{
  def fieldShows: RArr[Show[_]]

  /** Produces the [[String]]s to represent the values of the components of this N component [[Show]]. */
  def strs(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr

  /** Single identifiers for values. */
  def shortKeys: ArrPairStr[A]

  override def show(obj: A, style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String =
  { def semisStr = strs(obj, ShowCommas, maxPlaces).mkStr("; ")

    style match
    { case ShowUnderScore => "_"
      case ShowSemis => semisStr
      case ShowCommas => strs(obj, ShowStandard, maxPlaces).mkStr(", ")

      case ShowFieldNames =>
      { val r1: StrArr = strs(obj, ShowStandard, maxPlaces).iMap { (i, s1) => paramNames(i) + " = " + s1 }
        val r2 = r1.mkStr("; ")
        typeStr.appendParenth(r2)
      }

    case ShowStdTypedFields =>
    { val r1: StrArr = strs(obj, ShowStandard, maxPlaces).iMap { (i, s1) => paramNames(i) + ": " + fieldShows(i).typeStr + " = " + s1 }
      val r2 = r1.mkStr("; ")
      typeStr.appendParenth(r2)
    }

      case _ => typeStr.appendParenth(semisStr)
    }
  }
}

/** [[Show]] trait for types with N show fields that extend [[TellN]]. */
trait ShowTellN[A <: TellN] extends ShowNFixed[A] with ShowTell[A]
{ override def strs(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr = obj.tellElemStrs(way, maxPlaces, minPlaces)
}

trait UnshowN[R] extends Unshow[R] with PersistNFixed
{
  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R]

  /** Single identifiers for values. */
  def shortKeys: ArrPairStr[R]

  final override def fromExpr(expr: Expr): EMon[R] = expr match
  { case IdentifierToken(str) => shortKeys.a1FindA2(str).toEMon
    case AlphaMaybeSquareParenth(typeName, sts) if typeStr == typeName => fromExprSeq(sts.map(_.expr))
    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case ExprSeqNonEmpty(exprs) => fromExprSeq(exprs)
    case _ => expr.exprParseErr[R](this)
  }

  /** Tries to construct the type from a sequence of parameters using out of order named parameters and default values. */
  final def fromExprSeq(exprs: RArr[Expr]): EMon[R] =
    if(exprs.length > numParams) Bad(StrArr(exprs.length.toString + s" parameters for $numParams parameter constructor."))
    else
    {
      def exprsLoop(i: Int, usedNames: StrArr): EMon[R] =
        if (i >= exprs.length)
          if (i >= numParams) fromSortedExprs(exprs, paramNames.map(pn => usedNames.findIndex(_ == pn)))
          else exprsLoop(i + 1, usedNames +% paramNames.find(u => !usedNames.exists(_ == u)).get)
        else exprs(i) match
        { case AsignExprName(name) if !paramNames.contains(name) => bad1(exprs(i),"Unrecognised setting identifer name.")
          case AsignExprName(name) if usedNames.contains(name) => bad1(exprs(i), name + " Multiple parameters of the same name.")
          case AsignExprName(name) => exprsLoop(i + 1, usedNames +% name)
          case _ => exprsLoop(i + 1, usedNames +% paramNames.find(u => !usedNames.exists(_ == u)).get)
        }
      exprsLoop(0, StrArr())
    }
}

/** Base trait for [[ShowNFixed]] and [[UnshowN]]. */
trait PersistNRepeat[AR] extends Any with PersistN
{ /** Sequence of the names of parameter constituents of this class. */
  def paramFixedNames: StrArr

  def repeatName: String

  /** Number of parameter constituents of this class. */
  def numFixedParams: Int

  override def paramNames: StrArr = paramFixedNames +% repeatName
}


/** The base trait for the persistence of algebraic product types, including case classes where the last parameter repeats.. */
trait ShowNRepeat[Ar, A] extends ShowCompound[A] with PersistNRepeat[Ar]
{
  def fixedfieldShows: RArr[Show[_]]

  /** Gets the 2nd show field from the object. The Show fields do not necessarily correspond to the fields in memory. */
  def fArgR: A => Arr[Ar]

  /** Show type class instance for the 2nd Show field. */
  implicit def showEvR: Show[Ar]

  /** Shows parameter 2 of the object. */
  def showR(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr =
    fArgR(obj).map { ar => showEvR.show(ar, way, maxPlaces, minPlaces) }

  /** Produces the [[String]]s to represent the values of the components of this N component [[Show]]. */
  def strs(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr

  override def show(obj: A, style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String =
  { def semisStr = strs(obj, ShowCommas, maxPlaces).mkStr("; ")

    style match
    { case ShowUnderScore => "_"
    case ShowSemis => semisStr
    case ShowCommas => strs(obj, ShowStandard, maxPlaces).mkStr(", ")

    case ShowFieldNames =>
    { val r1: StrArr =
    { val strs2 = strs(obj, ShowStandard, maxPlaces, minPlaces)
      val named = iUntilMap(numFixedParams){ i => paramFixedNames(i) + " = " + strs2(i) }
      val reps = strs2.drop(numFixedParams)
      named ++ reps
    }
      val r2 = r1.mkStr("; ")
      typeStr.appendParenth(r2)
    }

    case ShowStdTypedFields =>
    { val r1: StrArr =
    { val strs2 = strs(obj, ShowStandard, maxPlaces, minPlaces)
      val named = iUntilMap(numFixedParams){ i => paramFixedNames(i) + ": " + fixedfieldShows(i).typeStr + " = " + strs2(i) }
      val reps = strs2.drop(numFixedParams)
      named ++ reps
    }
      val r2 = r1.mkStr("; ")
      typeStr.appendParenth(r2)
    }

    case _ => typeStr.appendParenth(semisStr)
    }
  }
}

/** The base trait for the persistence of algebraic product types, where the last component is a repeat parameter. */
trait UnshowNRepeat[AR, A] extends Unshow[A] with PersistNRepeat[AR]
{
  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[A]

  final override def fromExpr(expr: Expr): EMon[A] = expr match
  { case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => fromExprSeq(sts.map(_.expr))
    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case ExprSeqNonEmpty(exprs) => fromExprSeq(exprs)
    case _ => expr.exprParseErr[A](this)
  }

  /** Tries to construct the type from a sequence of parameters using out of order named parameters and default values. */
  final def fromExprSeq(exprs: RArr[Expr]): EMon[A] =
  {
    def exprsLoop(i: Int, usedNames: StrArr): EMon[A] =
      if (i >= exprs.length)
        if (i >= numFixedParams) fromSortedExprs(exprs, paramFixedNames.map(pn => usedNames.findIndex(_ == pn)))
        else exprsLoop(i + 1, usedNames +% paramFixedNames.find(u => !usedNames.exists(_ == u)).get)
      else exprs(i) match
      { case AsignExprName(name) if !paramFixedNames.contains(name) => bad1(exprs(i), "Unrecognised setting identifier name.")
        case AsignExprName(name) if usedNames.contains(name) => bad1(exprs(i), name + " Multiple parameters of the same name.")
        case AsignExprName(name) => exprsLoop(i + 1, usedNames +% name)
        case _ => exprsLoop(i + 1, usedNames +% paramFixedNames.find(u => !usedNames.exists(_ == u)).get)
      }

    exprsLoop(0, StrArr())
  }
}