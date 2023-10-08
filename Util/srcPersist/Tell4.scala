/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait Tell4[A1, A2, A3, A4] extends PersistBase4[A1, A2, A3, A3] with TellN
{ override def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr = ???
  override def syntaxDepth: Int = ???
}

trait TellInt4 extends Tell4[Int, Int, Int, Int]
{ override def persist1: Persist[Int] = Show.intPersistEv
  override def persist2: Persist[Int] = Show.intPersistEv
  override def persist3: Persist[Int] = Show.intPersistEv
  override def persist4: Persist[Int] = Show.intPersistEv
  override def syntaxDepth: Int = 2
  override def paramNames: StrArr = StrArr(name1, name2, name3, name4)
  override def elemTypeNames: StrArr = StrArr("Int", "Int", "Int", "Int")
}

trait ShowTell4[A1, A2, A3, A4, R <: Tell4[A1, A2, A3, A4]] extends ShowTell[R]

object ShowTell4
{
  class ShowTell4Imp[A1, A2, A3, A4, R <: Tell4[A1, A2, A3, A4]](val typeStr: String) extends ShowTell4[A1, A2, A3, A4, R] {

  }
}

trait ShowTellInt4[R <: TellInt4] extends ShowTell4[Int, Int, Int, Int, R]

trait PersistTell4[A1, A2, A3, A4, R <: Tell4[A1, A2, A3, A4]] extends ShowTell[R] with Unshow4[A1, A2, A3, A4, R]

class PersistTellInt4[R <: TellInt4](val typeStr: String, val name1: String, val name2: String, val name3: String, val name4: String,
  val newT: (Int, Int, Int, Int) => R) extends PersistTell4[Int, Int, Int, Int, R] with ShowTellInt4[R] with UnshowInt4[R]
{ override def persist1: Unshow[Int] = Show.intPersistEv
  override def persist2: Unshow[Int] = Show.intPersistEv
  override def persist3: Unshow[Int] = Show.intPersistEv
  override def persist4: Unshow[Int] = Show.intPersistEv
}

object PersistTellInt4
{ def apply[R <: TellInt4](typeStr: String, name1: String, name2: String, name3: String, name4: String, newT: (Int, Int, Int, Int) => R):
    PersistTellInt4[R] = new PersistTellInt4[R](typeStr, name1, name2, name3, name4, newT: (Int, Int, Int, Int) => R)
}