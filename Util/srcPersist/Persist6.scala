/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** A base trait for [[Unshow6]], declares the common properties of name1 - 6 and opt1 - 6. */
trait PersistBase6Plus[A1, A2, A3, A4, A5, A6] extends Any with PersistBase5Plus[A1, A2, A3, A4, A5]
{ /** 6th parameter name. */
  def name6: String

  /** The optional default value for parameter 6. */
  def opt6: Option[A6]

  /** The declaration here allows the same field to be to cover [[Show]][A6] [[UnShow]][A6] and [[Persist]][A6]. */
  def persist6: Show[A6] | Unshow[A6]
}

trait PersistBase6[A1, A2, A3, A4, A5, A6] extends Any with PersistBase6Plus[A1, A2, A3, A4, A5, A6]
{ override def paramNames: StrArr = StrArr(name1, name2, name3, name4, name5, name6)
  override def numParams: Int = 6
}

/** [[Show]] type class for 6 parameter case classes. */
trait Show6T[A1, A2, A3, A4, A5, A6, R] extends ShowN[R] with PersistBase6[A1, A2, A3, A4, A5, A6]
{ override def persist1: Show[A1]
  override def persist2: Show[A2]
  override def persist3: Show[A3]
  override def persist4: Show[A4]
  override def persist5: Show[A5]
  override def persist6: Show[A6]
}

/** Companion object for [[Show6T]] contains implementation class and factory apply method. */
object Show6T
{
  def apply[A1, A2, A3, A4, A5, A6, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, name6: String, fArg6: R => A6, opt6: Option[A6] = None, opt5: Option[A5] = None,
    opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], ev4: Show[A4], ev5: Show[A5], ev6: Show[A6]): Show6T[A1, A2, A3, A4, A5, A6, R] =
    new Show6ingImp[A1, A2, A3, A4, A5, A6, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, name6, fArg6,
      opt6, opt5, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4, ev5, ev6)

  /** Implementation class for general cases of [[Show6ing]] type class. */
  class Show6ingImp[A1, A2, A3, A4, A5, A6, R](val typeStr: String, val name1: String, fArg1: R => A1, val name2: String, fArg2: R => A2, val name3: String,
    fArg3: R => A3, val name4: String, fArg4: R => A4, val name5: String, fArg5: R => A5, val name6: String, fArg6: R => A6, val opt6: Option[A6],
    val opt5In: Option[A5] = None, opt4In: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val persist1: Show[A1], val persist2: Show[A2], val persist3: Show[A3], val persist4: Show[A4], val persist5: Show[A5],
    val persist6: Show[A6]) extends Show6T[A1, A2, A3, A4, A5, A6, R] with ShowN[R]
  { val opt5: Option[A5] = ife(opt6.nonEmpty, opt5In, None)
    val opt4: Option[A4] = ife(opt5.nonEmpty, opt4In, None)
    val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepthT(obj: R): Int = persist1.syntaxDepthT(fArg1(obj)).max(persist2.syntaxDepthT(fArg2(obj))).max(persist3.syntaxDepthT(fArg3(obj))).
      max(persist4.syntaxDepthT(fArg4(obj))).max(persist5.syntaxDepthT(fArg5(obj))).max(persist6.syntaxDepthT(fArg6(obj))) + 1

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr =
      StrArr(persist1.showT(fArg1(obj), way), persist2.showT(fArg2(obj), way), persist3.showT(fArg3(obj), way), persist4.showT(fArg4(obj), way),
        persist5.showT(fArg5(obj), way), persist6.showT(fArg6(obj), way))
  }
}

/** UnShow trait for 6 parameter product / case classes. */
trait Unshow6[A1, A2, A3, A4, A5, A6, R] extends UnshowN[R] with PersistBase6Plus[A1, A2, A3, A4, A5, A6]
{ def fArg1: R => A1
  def fArg2: R => A2
  def fArg3: R => A3
  def fArg4: R => A4
  def fArg5: R => A5
  def fArg6: R => A6
  val newT: (A1, A2, A3, A4, A5, A6) => R
  def opt6: Option[A6]
  def opt5: Option[A5] = None
  def opt4: Option[A4] = None
  def opt3: Option[A3] = None
  def opt2: Option[A2] = None
  def opt1: Option[A1] = None
  implicit override def persist1: Unshow[A1]
  implicit override def persist2: Unshow[A2]
  implicit override def persist3: Unshow[A3]
  implicit override def persist4: Unshow[A4]
  implicit override def persist5: Unshow[A5]
  implicit override def persist6: Unshow[A6]

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), persist1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), persist2.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), persist3.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    def e4: EMon[A4] = ife(len > pSeq(3), persist4.fromSettingOrExpr(name4, sortedExprs(pSeq(3))), opt4.toEMon)
    def e5: EMon[A5] = ife(len > pSeq(4), persist5.fromSettingOrExpr(name5, sortedExprs(pSeq(4))), opt5.toEMon)
    def e6: EMon[A6] = ife(len > pSeq(5), persist6.fromSettingOrExpr(name6, sortedExprs(pSeq(5))), opt6.toEMon)
    e1.map6(e2, e3, e4, e5, e6)(newT)
  }
}