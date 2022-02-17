/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A base trait for [[Unshow6]], declares the common properties of name1 - 6 and opt1 - 6. */
trait TypeStr6[A1, A2, A3, A4, A5, A6] extends Any with TypeStr5[A1, A2, A3, A4, A5]
{ /** 6th parameter name. */
  def name6: String

  /** The optional default value for parameter 6. */
  def opt6: Option[A6]
}

/** [[ShowT]] type class for 6 parameter case classes. */
trait Show6T[A1, A2, A3, A4, A5, A6, R] extends ShowProductT[R]

/** Companion object for [[Show6T]] contains implementation class and factory apply method. */
object Show6T
{
  def apply[A1, A2, A3, A4, A5, A6, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, name6: String, fArg6: R => A6, opt6: Option[A6] = None, opt5: Option[A5] = None,
    opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    ev1: ShowT[A1], ev2: ShowT[A2], ev3: ShowT[A3], ev4: ShowT[A4], ev5: ShowT[A5], ev6: ShowT[A6]): Show6T[A1, A2, A3, A4, A5, A6, R] =
    new Show6TImp[A1, A2, A3, A4, A5, A6, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, name6, fArg6,
      opt6, opt5, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4, ev5, ev6)

  /** Show type class for 6 parameter case classes. */
  class Show6TImp[A1, A2, A3, A4, A5, A6, R](val typeStr: String, val name1: String, fArg1: R => A1, val name2: String, fArg2: R => A2, val name3: String,
    fArg3: R => A3, val name4: String, fArg4: R => A4, val name5: String, fArg5: R => A5, val name6: String, fArg6: R => A6, val opt6: Option[A6],
    val opt5In: Option[A5] = None, opt4In: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit
    ev1: ShowT[A1], ev2: ShowT[A2], ev3: ShowT[A3], ev4: ShowT[A4], ev5: ShowT[A5], ev6: ShowT[A6]) extends
    Show6T[A1, A2, A3, A4, A5, A6, R] with ShowProductT[R] with TypeStr6[A1, A2, A3, A4, A5, A6]
  {
    val opt5: Option[A5] = ife(opt6.nonEmpty, opt5In, None)
    val opt4: Option[A4] = ife(opt5.nonEmpty, opt4In, None)
    val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))).max(ev3.syntaxDepthT(fArg3(obj))).
      max(ev4.syntaxDepthT(fArg4(obj))).max(ev5.syntaxDepthT(fArg5(obj))).max(ev6.syntaxDepthT(fArg6(obj))) + 1

    override def strs(obj: R, way: ShowStyle): Strings =
      Strings(ev1.showT(fArg1(obj), way), ev2.showT(fArg2(obj), way), ev3.showT(fArg3(obj), way), ev4.showT(fArg4(obj), way),
        ev5.showT(fArg5(obj), way), ev6.showT(fArg6(obj), way))
  }
}

/** UnShow trait for 6 parameter product / case classes. */
trait Unshow6[A1, A2, A3, A4, A5, A6, R] extends Unshow[R] with TypeStr6[A1, A2, A3, A4, A5, A6]
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
  implicit def ev1: Unshow[A1]
  implicit def ev2: Unshow[A2]
  implicit def ev3: Unshow[A3]
  implicit def ev4: Unshow[A4]
  implicit def ev5: Unshow[A5]
  implicit def ev6: Unshow[A6]
}