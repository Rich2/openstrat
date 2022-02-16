/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A base trait for [[Show5T]] and [[Unshow5]], declares the common properties of name1 - 5 and opt1 - 5. */
trait TypeStr5[A1, A2, A3, A4, A5] extends Any with TypeStr4[A1, A2, A3, A4]
{ /** 5th parameter name. */
  def name5: String

  /** The optional default value for parameter 5. */
  def opt5: Option[A5]
}

/** Show type class for 5 parameter case classes. */
class Show5T[A1, A2, A3, A4, A5, R](val typeStr: String, val name1: String, fArg1: R => A1, val name2: String, fArg2: R => A2,
  val name3: String, fArg3: R => A3, val name4: String, fArg4: R => A4, val name5: String, fArg5: R => A5, val opt5: Option[A5],
  opt4In: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
  implicit ev1: ShowT[A1], ev2: ShowT[A2], ev3: ShowT[A3], ev4: ShowT[A4], ev5: ShowT[A5]) extends ShowProductT[R] with TypeStr5[A1, A2, A3, A4, A5]
{
  val opt4: Option[A4] = ife(opt5.nonEmpty, opt4In, None)
  val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

  final override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))).max(ev3.syntaxDepthT(fArg3(obj))).
    max(ev4.syntaxDepthT(fArg4(obj))).max(ev5.syntaxDepthT(fArg5(obj))) + 1

  override def strs(obj: R, way: ShowStyle): Strings =
    Strings(ev1.showT(fArg1(obj), way), ev2.showT(fArg2(obj), way), ev3.showT(fArg3(obj), way), ev4.showT(fArg4(obj), way),
    ev5.showT(fArg5(obj), way))
}

object Show5T
{
  def apply[A1, A2, A3, A4, A5, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, opt5: Option[A5] = None, opt4: Option[A4] = None, opt3: Option[A3] = None,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: ShowT[A1], ev2: ShowT[A2], ev3: ShowT[A3], ev4: ShowT[A4], ev5: ShowT[A5]) =
    new Show5T[A1, A2, A3, A4, A5, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, opt5, opt4, opt3, opt2, opt1)(
      ev1, ev2, ev3, ev4, ev5)
}

/** [[Unshow]] trait for 5 parameter product / case classes. */
trait Unshow5[A1, A2, A3, A4, A5, R] extends Unshow[R] with TypeStr5[A1, A2, A3, A4, A5]
{
  def fArg1: R => A1
  def fArg2: R => A2
  def fArg3: R => A3
  def fArg4: R => A4
  def fArg5: R => A5
  def newT: (A1, A2, A3, A4, A5) => R
  def opt5: Option[A5]
  def opt4: Option[A4] = None
  def opt3: Option[A3] = None
  def opt2: Option[A2] = None
  def opt1: Option[A1] = None
  implicit def ev1: Unshow[A1]
  implicit def ev2: Unshow[A2]
  implicit def ev3: Unshow[A3]
  implicit def ev4: Unshow[A4]
  implicit def ev5: Unshow[A5]
}