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