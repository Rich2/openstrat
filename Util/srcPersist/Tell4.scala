/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait Tell4[A1, A2, A3, A4] extends Persist4[A1, A2, A3, A3] with TellN
{ override def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr = ???
  override def syntaxDepth: Int = ???
}

trait TellInt4 extends Tell4[Int, Int, Int, Int]
{ def unshow1: Show[Int] = Show.intPersistEv
  def unshow2: Show[Int] = Show.intPersistEv
  def unshow3: Show[Int] = Show.intPersistEv
  def unshow4: Show[Int] = Show.intPersistEv
  override def syntaxDepth: Int = 2
  override def paramNames: StrArr = StrArr(name1, name2, name3, name4)
  override def elemTypeNames: StrArr = StrArr("Int", "Int", "Int", "Int")
}

trait ShowTell4[A1, A2, A3, A4, R <: Tell4[A1, A2, A3, A4]] extends ShowTell[R]

object ShowTell4
{
  class ShowTell4Imp[A1, A2, A3, A4, R <: Tell4[A1, A2, A3, A4]](val typeStr: String) extends ShowTell4[A1, A2, A3, A4, R]
}

case class ShowTellInt4[R <: TellInt4](typeStr: String) extends ShowTell4[Int, Int, Int, Int, R]