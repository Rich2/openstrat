/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** A base trait for Show6+ and Unshow6+ classses. Declares the common properties of name1 - 6 and opt1 - 6. */
trait Persist6Plus[A1, A2, A3, A4, A5, A6] extends Any with PersistBase5Plus[A1, A2, A3, A4, A5]
{ /** 6th parameter name. */
  def name6: String

  /** The optional default value for parameter 6. */
  def opt6: Option[A6]
}

trait Persist6[A1, A2, A3, A4, A5, A6] extends Any with Persist6Plus[A1, A2, A3, A4, A5, A6]
{ override def paramNames: StrArr = StrArr(name1, name2, name3, name4, name5, name6)
  override def numParams: Int = 6
}

/** [[Show]] type class for 6 parameter case classes. */
trait Show6[A1, A2, A3, A4, A5, A6, R] extends ShowN[R] with Persist6[A1, A2, A3, A4, A5, A6]
{ override def persist1: Show[A1]
  override def persist2: Show[A2]
  def persist3: Show[A3]
  def unshow4: Show[A4]
  def show5: Show[A5]
  def show6: Show[A6]
}

/** Companion object for [[Show6]] contains implementation class and factory apply method. */
object Show6
{
  def apply[A1, A2, A3, A4, A5, A6, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, name6: String, fArg6: R => A6, opt6: Option[A6] = None, opt5: Option[A5] = None,
    opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], ev4: Show[A4], ev5: Show[A5], ev6: Show[A6]): Show6[A1, A2, A3, A4, A5, A6, R] =
    new Show6Imp[A1, A2, A3, A4, A5, A6, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, name6, fArg6,
      opt6, opt5, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4, ev5, ev6)

  /** Implementation class for general cases of [[Show6]] type class. */
  class Show6Imp[A1, A2, A3, A4, A5, A6, R](val typeStr: String, val name1: String, fArg1: R => A1, val name2: String, fArg2: R => A2, val name3: String,
    fArg3: R => A3, val name4: String, fArg4: R => A4, val name5: String, fArg5: R => A5, val name6: String, fArg6: R => A6, val opt6: Option[A6],
    val opt5In: Option[A5] = None, opt4In: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val persist1: Show[A1], val persist2: Show[A2], val persist3: Show[A3], val unshow4: Show[A4], val show5: Show[A5],
    val show6: Show[A6]) extends Show6[A1, A2, A3, A4, A5, A6, R] with ShowN[R]
  { val opt5: Option[A5] = ife(opt6.nonEmpty, opt5In, None)
    override val opt4: Option[A4] = ife(opt5.nonEmpty, opt4In, None)
    override val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepthT(obj: R): Int = persist1.syntaxDepthT(fArg1(obj)).max(persist2.syntaxDepthT(fArg2(obj))).max(persist3.syntaxDepthT(fArg3(obj))).
      max(unshow4.syntaxDepthT(fArg4(obj))).max(show5.syntaxDepthT(fArg5(obj))).max(show6.syntaxDepthT(fArg6(obj))) + 1

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr =
      StrArr(persist1.showT(fArg1(obj), way), persist2.showT(fArg2(obj), way), persist3.showT(fArg3(obj), way), unshow4.showT(fArg4(obj), way),
        show5.showT(fArg5(obj), way), show6.showT(fArg6(obj), way))
  }
}

/** UnShow trait for 6 parameter product / case classes. */
trait Unshow6[A1, A2, A3, A4, A5, A6, R] extends UnshowN[R] with Persist6[A1, A2, A3, A4, A5, A6]
{ def fArg1: R => A1
  def fArg2: R => A2
  def fArg3: R => A3
  def fArg4: R => A4
  def fArg5: R => A5
  def fArg6: R => A6
  val newT: (A1, A2, A3, A4, A5, A6) => R
  def opt6: Option[A6]
  def opt5: Option[A5] = None
  override def opt4: Option[A4] = None
  override def opt3: Option[A3] = None
  override def opt2: Option[A2] = None
  override def opt1: Option[A1] = None
  implicit override def persist1: Unshow[A1]
  implicit override def persist2: Unshow[A2]
  implicit def persist3: Unshow[A3]
  implicit def unshow4: Unshow[A4]
  implicit def unshow5: Unshow[A5]
  implicit def unshow6: Unshow[A6]

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), persist1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), persist2.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), persist3.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    def e4: EMon[A4] = ife(len > pSeq(3), unshow4.fromSettingOrExpr(name4, sortedExprs(pSeq(3))), opt4.toEMon)
    def e5: EMon[A5] = ife(len > pSeq(4), unshow5.fromSettingOrExpr(name5, sortedExprs(pSeq(4))), opt5.toEMon)
    def e6: EMon[A6] = ife(len > pSeq(5), unshow6.fromSettingOrExpr(name6, sortedExprs(pSeq(5))), opt6.toEMon)
    e1.map6(e2, e3, e4, e5, e6)(newT)
  }
}