/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** [[Tell]] trait for classes with 3+ Show parameters. */
trait Tell4Plused[A1, A2, A3, A4] extends Any with Tell3Plused[A1, A2, A3] with Persist4Plus[A1, A2, A3, A4]
{ /** Element 4 of this Tell4+ element product. */
  def tell4: A4

  def show4: Show[A4]

  override def opt4: Option[A4] = None
}

trait Tell4[A1, A2, A3, A4] extends Tell4Plused[A1, A2, A3, A4] with Persist4[A1, A2, A3, A4] with TellN
{ override def syntaxDepth: Int = ???

  override def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr =
    StrArr(show1.show(tell1, way, decimalPlaces, 0), show2.show(tell2, way, decimalPlaces, 0),
      show3.show(tell3, way, decimalPlaces, 0), show4.show(tell4, way, decimalPlaces, 0))
}

trait TellInt4 extends Tell4[Int, Int, Int, Int]
{ override def show1: Show[Int] = Show.intEv
  override def show2: Show[Int] = Show.intEv
  override def show3: Show[Int] = Show.intEv
  override def show4: Show[Int] = Show.intEv
  override def syntaxDepth: Int = 2
  override def elemTypeNames: StrArr = StrArr("Int", "Int", "Int", "Int")
}

trait ShowTell4[A1, A2, A3, A4, R <: Tell4[A1, A2, A3, A4]] extends ShowTell[R]

object ShowTell4
{
  class ShowTell4Imp[A1, A2, A3, A4, R <: Tell4[A1, A2, A3, A4]](val typeStr: String) extends ShowTell4[A1, A2, A3, A4, R]
}

case class ShowTellInt4[R <: TellInt4](typeStr: String) extends ShowTell4[Int, Int, Int, Int, R]