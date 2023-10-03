/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for [[TellDec]] for a product of 3 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[ShowEq3T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show3 and then use [[Show3ElemT]] or [[Persist3ElemT]] to create the type class instance for ShowT. The [[Show3ElemT]] or
 *  [[Persist3Elem]] class will delegate to Show3 for some of its methods. It is better to use Show3 to override toString method than delegating the
 *  toString override to a [[ShowEq3T]] instance. */
trait Tell3[A1, A2, A3] extends Any with Show3Plused[A1, A2, A3]
{ override def numParams: Int = 3
  override def paramNames: StrArr = StrArr(name1, name2, name3)
  override def elemTypeNames: StrArr = StrArr(persist1.typeStr, persist2.typeStr, persist3.typeStr)

  override def showElemStrs(way: ShowStyle): StrArr = StrArr(persist1.showT(show1, way), persist2.showT(show2, way), persist3.showT(show3, way))

  override def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr =
    StrArr(persist1.showDecT(show1, way, decimalPlaces, 0), persist2.showDecT(show2, way, decimalPlaces, 0),
      persist3.showDecT(show3, way, decimalPlaces, 0))
}

trait ShowTell3[A1, A2, A3, R <: Tell3[A1, A2, A3]] extends ShowTell[R]

object ShowTell3{
  class ShowTellImp[A1, A2, A3, R <: Tell3[A1, A2, A3]](val typeStr: String) extends ShowTell3[A1, A2, A3, R]
}

/** Show classes with 3 [[Int]] parameters. */
trait TellInt3 extends Any with Tell3[Int, Int, Int]
{ final override def syntaxDepth: Int = 2
  final override implicit def persist1: Persist[Int] = Show.intPersistEv
  final override implicit def persist2: Persist[Int] = Show.intPersistEv
  final override implicit def persist3: Persist[Int] = Show.intPersistEv
}

/** Show classes with 3 [[Double]] parameters. */
trait TellDbl3 extends Any with Tell3[Double, Double, Double]
{ final override def syntaxDepth: Int = 2
  final override implicit def persist1: Persist[Double] = Show.doublePersistEv
  final override implicit def persist2: Persist[Double] = Show.doublePersistEv
  final override implicit def persist3: Persist[Double] = Show.doublePersistEv
}

trait PersistTell3[A1, A2, A3, R <: Tell3[A1, A2, A3]] extends Persist[R] with ShowTell3[A1, A2, A3, R] with Unshow3[A1, A2, A3, R]

object PersistTell3
{
  def apply[A1, A2, A3, R <: Tell3[A1, A2, A3]](typeStr: String, name1: String, name2: String, name3: String, newT: (A1, A2, A3) => R,
    opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None) (implicit persist1: Unshow[A1], persist2: Unshow[A2],
    persist3: Unshow[A3]): PersistTell3[A1, A2, A3, R] =
    PersistTell3[A1, A2, A3, R](typeStr, name1, name2, name3, newT, opt3, opt2, opt1)(persist1, persist2, persist3)

  class PersistTell3Imp[A1, A2, A3, R <: Tell3[A1, A2, A3]](val typeStr: String, val name1: String, val name2: String, val name3: String,
    val newT: (A1, A2, A3) => R, val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val persist1: Unshow[A1], val persist2: Unshow[A2], val persist3: Unshow[A3]) extends PersistTell3[A1, A2, A3, R]
  { val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}