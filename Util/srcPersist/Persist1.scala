/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, reflect.ClassTag

trait Persist1Plus[A1]  extends Any with PersistN
{ /** 1st parameter name. */
  def name1: String

  /** The optional default value for parameter 1. */
  def opt1: Option[A1]
}

trait Show1Plus[A1, A] extends Persist1Plus[A1]
{
  /** Gets the 1st show field from the object. The Show fields do not necessarily correspond to the fields in memory. */
  def fArg1: A => A1

  /** Show type class instance for the 1st Show field. */
  implicit def showEv1: Show[A1]

  /** Shows parameter 1 of the object. */
  def show1(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = showEv1.show(fArg1(obj), way, maxPlaces, minPlaces)
}

trait Show1PlusFixed[A1, A] extends ShowNFixed[A] with Show1Plus[A1, A]

/** [[Show]] type class for 2 parameter case classes. */
trait Show1PlusRepeat[A1, Ar, A] extends ShowNRepeat[Ar, A] with Show1Plus[A1, A]

trait Persist1PlusRepeat[A1, Ar] extends Persist1Plus[A1] with PersistNRepeat[Ar]

trait Persist1Repeat[A1, Ar, A] extends Persist1PlusRepeat[A1, Ar] with PersistNRepeat[Ar]
{ override def numFixedParams: Int = 1
  override def paramFixedNames: StrArr = StrArr(name1)
}

/** [[Show]] type class instances / evidence for objects with 1 fixed component and a repeated parameter. */
class Show1Repeat[A1, Ar, A](val typeStr: String, val name1: String, val fArg1: A => A1, val repeatName: String, val fArgR: A => Arr[Ar],
  val opt1: Option[A1] = None)(implicit val showEv1: Show[A1], val showEvR: Show[Ar]) extends Show1PlusRepeat[A1, Ar, A] with Persist1Repeat[A1, Ar, A]
{
  override def fixedfieldShows: RArr[Show[_]] = RArr(showEv1)

  /** Produces the [[String]]s to represent the values of the components of this N component [[Show]]. */
  override def strs(obj: A, way: ShowStyle, maxPlaces: Int, minPlaces: Int): StrArr = opt1 match
  { case Some(a1) if fArgR(obj).empty && a1 == fArg1(obj) => StrArr()
    case _ => show1(obj, way, maxPlaces, minPlaces) %: showR(obj, way, maxPlaces, minPlaces)
  }

  override def syntaxDepth(obj: A): Int = fArgR(obj).foldLeft(showEv1.syntaxDepth(fArg1(obj))){(acc, ar) => acc.max(showEvR.syntaxDepth(ar)) }
}

object Show1Repeat
{
  def apply[A1, Ar, A](typeStr: String, name1: String, fArg1: A => A1, repeatName: String, fArgR: A => Arr[Ar], opt1: Option[A1] = None)(implicit
    showEv1: Show[A1], showEvR: Show[Ar]) = new Show1Repeat[A1, Ar, A](typeStr, name1, fArg1, repeatName, fArgR, opt1)
}

/** [[Unshow]] type class instances / evidence for objects with 1 fixed component and 1 repeat parameter. */
class Unshow1Repeat[A1, Ar, A](val typeStr: String, val name1: String, val repeatName: String, f: (A1, Seq[Ar]) => A, val opt1: Option[A1] = None)(
  implicit val unshowA1: Unshow[A1], val unshowAr: Unshow[Ar]) extends Unshow[A] with Persist1Repeat[A1, Ar, A]
{ /** The function to construct an object of type R from its 2 components." */
  def newT: (A1, Seq[Ar]) => A = f

  override def fromExpr(expr: Expr): EMon[A] =
  {
    val Match1: NamedExprSeq = NamedExprSeq(typeStr)
    expr match
    { case Match1(exprs) if exprs.length == 0 => opt1 match
      { case Some(a1) => Good(f(a1, Nil))
        case None => bad1(expr, "No values")
      }

      case Match1(exprs) =>
      { val a1 = unshowA1.fromExpr(exprs(0))
        def reps: EMon[List[Ar]] = if (unshowAr.useMultiple) Multiple.collFromArrExpr(exprs.drop1)(unshowAr, BuilderCollMap.listEv)
        else exprs.drop1.mapEMonList(unshowAr.fromExpr)
        a1.flatMap(a1 => reps.map(l => newT(a1, l)))
      }

      case AlphaMaybeSquareParenth(name, _) => bad1(expr, s"Wrong name: $name not $typeStr.")
      case _ => expr.exprParseErr[A](this)
    }
  }
}

object Unshow1Repeat
{ /** Factory apply method for [[Unshow]] type class instances of 2 components where the final parameter repeats. */
  def apply[A1, Ar, A](typeStr: String, name1: String, repeatName: String, f: (A1, Seq[Ar]) => A)(implicit unshowA1: Unshow[A1], unshowA2: Unshow[Ar]) =
    new Unshow1Repeat[A1, Ar, A](typeStr, name1, repeatName, f)
}