/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, pParse._

/** Show class for algebraic sum types. If you are using your own code then Show sum types handled by inheritance. */
abstract class ShowSum2[ST <: AnyRef, A1 <: ST, A2 <: ST](val typeStr: String)(implicit val ct1: ClassTag[A1], val ct2: ClassTag[A2]) extends Show[ST]
{
  def ev1: Show[A1]
  def ev2: Show[A2]

  override def strT(obj: ST): String = obj match
  { case a1: A1 => ev1.strT(a1)
    case a2: A2 => ev2.strT(a2)
    case _ => excep("Case not implemented")
  }
  
  override def syntaxDepth(obj: ST): Int = 3//ev1.syntaxDepth(obj.).max(ev2.syntaxDepth())

  override def showDec(obj: ST, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String =  obj match
  { case a1: A1 => ev1.strT(a1)
    case a2: A2 => ev2.strT(a2)
    case _ => excep("Case not implemented")
  }
}

object ShowSum2
{
  def apply[ST <: AnyRef, A1 <: ST, A2 <: ST](typeIn: String, ev1In: Show[A1], ev2In: Show[A2])(implicit ct1: ClassTag[A1], ct2: ClassTag[A2]):
    ShowSum2[ST, A1, A2] = new ShowSum2[ST, A1, A2](typeIn)(ct1, ct2)
  { //override def typeStr: String = typeIn
    override def ev1: Show[A1] = ev1In
    override def ev2: Show[A2] = ev2In
  }
}

/** Algebraic sum type for [[Unshow]]. */
trait UnshowSum[+A] extends Unshow[A]
{
  def elems: RArr[Unshow[A]]

  override def fromExpr(expr: Expr): EMon[A] = elems.findGood(_.fromExpr(expr))

  override def ++[AA >: A](operand: Unshow[AA])(implicit ev: Unshow[AA]): Unshow[AA] = operand match
  { case uSum: UnshowSum[AA] => UnshowSum[AA](ev.typeStr, elems ++ uSum.elems)
    case op => UnshowSum[AA](ev.typeStr, elems +% op)
  }
}

object UnshowSum
{
  def apply[A](typeStrIn: String, elemsIn: RArr[Unshow[A]]): UnshowSum[A] = new UnshowSum[A]
  { override def typeStr: String = typeStrIn
    override def elems: RArr[Unshow[A]] = elemsIn
  }

  def apply[A](typeStrIn: String, elemsIn: Unshow[A]*): UnshowSum[A] = new UnshowSum[A]
  { override def typeStr: String = typeStrIn
    override def elems: RArr[Unshow[A]] = elemsIn.toArr
  }
}

trait UnShowSum2[+ST <: AnyRef, A1 <: ST , A2 <: ST] extends Unshow[ST]
{
  def ev1: Unshow[A1]
  def ev2: Unshow[A2]
  
  def pList: List[Unshow[ST]] = List(ev1, ev2)
  override def fromExpr(expr: Expr): EMon[ST] =
    pList.mapFirstGood(_.fromExpr(expr), expr.startPosn.bad("fromExpr, No value of" -- typeStr -- "found."))
}