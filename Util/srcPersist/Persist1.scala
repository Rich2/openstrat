/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, reflect.ClassTag

trait Persist1Plus[A1]  extends Any with PersistN
{ /** 1st parameter name. */
  def name1: String

  /** The optional default value for parameter 1. */
  def opt1: Option[A1]
}

trait Persist1RepeatPlus[A1, Ar] extends Persist1Plus[A1] with PersistNRepeat[Ar]

trait Persist1Repeat[A1, Ar, A] extends Persist1RepeatPlus[A1, Ar] with PersistNRepeat[Ar]
{ override def numFixedParams: Int = 1
  override def paramFixedNames: StrArr = StrArr(name1)
}

class Show1Repeat[A1, Ar, A](val typeStr: String, val name1: String, val repeatName: String, repeats: Arr[Ar], val opt1: Option[A1] = None) extends ShowNRepeat[Ar, A] with Persist1Repeat[A1, Ar, A]
{
  override def fieldShows: RArr[Show[_]] = ???

  /** Produces the [[String]]s to represent the values of the components of this N component [[Show]]. */
  override def strs(obj: A, way: ShowStyle, maxPlaces: Int, minPlaces: Int): StrArr = ???

  /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
   * should always be determined at compile time or if sometimes it should be determined at runtime. */
  override def syntaxDepth(obj: A): Int = ???
}



/** [[Unshow]] type class instances for 2 components where the final parameter repeats. */
class Unshow1Repeat[A1, Ar, A](val typeStr: String, val name1: String, val repeatName: String, f: (A1, Seq[Ar]) => A, val opt1: Option[A1] = None)(implicit val unshowA1: Unshow[A1],
  val unshowAr: Unshow[Ar]) extends Unshow[A] with Persist1Repeat[A1, Ar, A]
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
      def reps = if (unshowAr.useMultiple) Multiple.collFromArrExpr(exprs.drop1)(unshowAr, BuilderCollMap.listEv)
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