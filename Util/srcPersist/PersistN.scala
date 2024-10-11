/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Base trait for [[TellN]], [[ShowN]] and [[UnshowN]] which share the paramNames property. */
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
  def fieldShows: RArr[Show[?]]

  /** Produces the [[String]]s to represent the values of the components of this N component [[Show]]. */
  def strs(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr

  /** Single identifiers for values. */
  def shortKeys: ArrPairStr[A]

  override def show(obj: A, style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String =
  { def semisStr: String = strs(obj, ShowCommas, maxPlaces).mkSemiSpaceSpecial
    def namedStrs: StrArr = strs(obj, ShowStdNoSpace, maxPlaces).iMap { (i, s1) => paramNames(i) + " = " + s1 }
    val shortOpt = shortKeys.a2FindA1(obj)

    style match
    { case ShowStdNoSpace | ShowSemis | ShowCommas if shortOpt.nonEmpty => shortOpt.get
      case ShowUnderScore => "_"
      case ShowSemis => semisStr
      case ShowSemisNames => namedStrs.mkStr("; ")// semisStr
      case ShowCommas => strs(obj, ShowStdNoSpace, maxPlaces).mkStr(", ")

      case ShowFieldNames =>
      { val r1: StrArr = strs(obj, ShowStdNoSpace, maxPlaces).iMap { (i, s1) => paramNames(i) + " = " + s1 }
        val r2 = r1.mkStr("; ")
        typeStr.appendParenth(r2)
      }

    case ShowStdTypedFields =>
    { val r1: StrArr = strs(obj, ShowStdNoSpace, maxPlaces).iMap { (i, s1) => paramNames(i) + ": " + fieldShows(i).typeStr + " = " + s1 }
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
  protected def fromSortedExprsOld(sortedExprs: RArr[Expr], pSeq: IntArr): EMonOld[R]

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): ExcMon[R]

  /** Single identifiers for values. */
  def shortKeys: ArrPairStr[R]

  final override def fromExprOld(expr: Expr): EMonOld[R] = expr match
  { case IdentifierToken(str) => shortKeys.a1FindA2(str).toEMon
    case AlphaMaybeSquareParenth(typeName, sts) if typeStr == typeName => fromExprSeqOld(sts.map(_.expr))
    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case ExprSeqNonEmpty(exprs) => fromExprSeqOld(exprs)
    case _ => expr.exprParseErrOld[R](this)
  }

  final override def fromExpr(expr: Expr): ExcMon[R] = expr match
  { case IdentifierToken(str) => shortKeys.a1FindA2(str).toErrBi
    case AlphaMaybeSquareParenth(typeName, sts) if typeStr == typeName => fromExprSeq(sts.map(_.expr))
    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.fail(typeName -- "does not equal" -- typeStr)
    case ExprSeqNonEmpty(exprs) => fromExprSeq(exprs)
    case _ => expr.exprParseErr[R](this)
  }

  /** Tries to construct the type from a sequence of parameters using out of order named parameters and default values. */
  final def fromExprSeqOld(exprs: RArr[Expr]): EMonOld[R] =
    if(exprs.length > numParams) Bad(StrArr(exprs.length.toString + s" parameters for $numParams parameter constructor."))
    else
    {
      def exprsLoop(i: Int, usedNames: StrArr): EMonOld[R] =
        if (i >= exprs.length)
          if (i >= numParams) fromSortedExprsOld(exprs, paramNames.map(pn => usedNames.findIndex(_ == pn)))
          else exprsLoop(i + 1, usedNames +% paramNames.find(u => !usedNames.exists(_ == u)).get)
        else exprs(i) match
        { case AsignExprName(name) if !paramNames.contains(name) => bad1(exprs(i),"Unrecognised setting identifer name.")
          case AsignExprName(name) if usedNames.contains(name) => bad1(exprs(i), name + " Multiple parameters of the same name.")
          case AsignExprName(name) => exprsLoop(i + 1, usedNames +% name)
          case _ => exprsLoop(i + 1, usedNames +% paramNames.find(u => !usedNames.exists(_ == u)).get)
        }
      exprsLoop(0, StrArr())
     
    }

  /** Tries to construct the type from a sequence of parameters using out of order named parameters and default values. */
  final def fromExprSeq(exprs: RArr[Expr]): ExcMon[R] =
     if (exprs.length > numParams) FailExc(exprs.length.toString + s" parameters for $numParams parameter constructor.")
     else
     {
       def exprsLoop(i: Int, usedNames: StrArr): ExcMon[R] =
         if (i >= exprs.length)
           if (i >= numParams) fromSortedExprs(exprs, paramNames.map(pn => usedNames.findIndex(_ == pn)))
           else exprsLoop(i + 1, usedNames +% paramNames.find(u => !usedNames.exists(_ == u)).get)
         else exprs(i) match
         {
           case AsignExprName(name) if !paramNames.contains(name) => exprs(i).failExc("Unrecognised setting identifer name.")
           case AsignExprName(name) if usedNames.contains(name) => exprs(i).failExc(name + " Multiple parameters of the same name.")
           case AsignExprName(name) => exprsLoop(i + 1, usedNames +% name)
           case _ => exprsLoop(i + 1, usedNames +% paramNames.find(u => !usedNames.exists(_ == u)).get)
         }
 
       exprsLoop(0, StrArr())
     }
}