/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.mutable.ArrayBuffer

/** Base trait for [[ShowNFixed]] and [[UnshowN]]. */
trait PersistNRepeat[AR] extends Any with PersistN
{ /** Sequence of the names of parameter constituents of this class. */
  def paramFixedNames: StrArr

  def repeatName: String

  /** Number of parameter constituents of this class. */
  def numFixedParams: Int

  override def paramNames: StrArr = paramFixedNames +% repeatName
}

trait ShowNRepeat[Ar, A] extends ShowCompound[A] with PersistNRepeat[Ar]
{
  /** Show type class instance for the 2nd Show field. */
  implicit def showEvR: Show[Ar]

  def fixedfieldShows: RArr[Show[_]]

  /** Foreach's all the elements of the sequence like object that is being shown. */
  def showForeach(obj: A, f: Ar => Unit): Unit

  /** Produces the [[String]]s to represent the values of the components of this N component [[Show]]. */
  def strs(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr

  /** Shows parameter 2 of the object. */
  def showR(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr =
    showMap(obj){ ar => showEvR.show(ar, way, maxPlaces, minPlaces) }

  /** Maps over all the elements of the sequence like object that is being shown. */
  final def showMap(obj: A)(f: Ar => String): StrArr = {
    val buffer: ArrayBuffer[String] = Buffer[String]()
    showForeach(obj, a => buffer.append(f(a)))
    new StrArr(buffer.toArray)
  }

  override def show(obj: A, style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String =
  {
    def semisStr = strs(obj, ShowCommas, maxPlaces).mkSemiSpaceSpecial

    style match {
      case ShowUnderScore => "_"
      case ShowSemis => semisStr
      case ShowCommas => strs(obj, ShowStdNoSpace, maxPlaces).mkCommaSpaceSpecial

      case ShowFieldNames => {
        val r1: StrArr = {
          val strs2 = strs(obj, ShowStdNoSpace, maxPlaces, minPlaces)
          val named = iUntilMap(numFixedParams) { i => paramFixedNames(i) + " = " + strs2(i) }
          val reps = strs2.drop(numFixedParams)
          named ++ reps
        }
        val r2 = r1.mkStr("; ")
        typeStr.appendParenth(r2)
      }

      case ShowStdTypedFields => {
        val r1: StrArr = {
          val strs2 = strs(obj, ShowStdNoSpace, maxPlaces, minPlaces)
          val named = iUntilMap(numFixedParams) { i => paramFixedNames(i) + ": " + fixedfieldShows(i).typeStr + " = " + strs2(i) }
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

/** The base trait for the persistence of algebraic product types, including case classes where the last parameter repeats.. */
trait ShowNOptRepeat[Ar, A] extends ShowNRepeat[Ar, A]//ShowCompound[A] with PersistNRepeat[Ar]
{
  /** Shows parameter 2 of the object. */
  override def showR(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr =
    showMap(obj){ ar => Show.nullOptionEv[Ar].show(ar, way, maxPlaces, minPlaces) }
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