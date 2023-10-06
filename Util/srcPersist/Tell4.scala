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

object ShowTell4{

  class ShowTell4Imp[A1, A2, A3, A4, R <: Tell4[A1, A2, A3, A4]](val typeStr: String) extends ShowTell4[A1, A2, A3, A4, R] {

  }
}

trait PersistTell4[A1, A2, A3, A4, R <: Tell4[A1, A2, A3, A4]] extends ShowTell[R] with Unshow4[A1, A2, A3, A4, R]

object PersistTell4{

  class PersistTell4Imp[A1, A2, A3, A4, R <: Tell4[A1, A2, A3, A4]]()(implicit override val persist1: Unshow[A1], override val persist2: Unshow[A2],
    override val persist3: Unshow[A3], override val persist4: Unshow[A4]) extends PersistTell4[A1, A2, A3, A4, R]
  {
    override def newT: (A1, A2, A3, A4) => R = ???

    /** 4th parameter name. */
    override def name4: String = ???

    /** 3rd parameter name. */
    override def name3: String = ???

    /** 1st parameter name. */
    override def name1: String = ???

    /** 2nd parameter name. */
    override def name2: String = ???

    /** The RSON type of T. This the only data that a ShowT instance requires, that can't be implemented through delegation to an object of type
     * Show. */
    override def typeStr: String = ???
  }
}

class PersistTellInt4[R <: TellInt4](val typeStr: String, val name1: String, val name2: String, val name3: String, val name4: String,
  val newT: (Int, Int, Int, Int) => R) extends PersistTell4[Int, Int, Int, Int, R]
{ override def persist1: Unshow[Int] = Show.intPersistEv
  override def persist2: Unshow[Int] = Show.intPersistEv
  override def persist3: Unshow[Int] = Show.intPersistEv
  override def persist4: Unshow[Int] = Show.intPersistEv
}