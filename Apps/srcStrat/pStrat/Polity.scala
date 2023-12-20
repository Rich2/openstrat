/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pStrat
import Colour._

trait Polity extends TellSimple with Coloured
{ override def typeStr: String = "Polity"
  //  def army(num: Int): BrArmy = BrArmy(this, num)
  // def armyNext()(implicit counters: ArrCounters[Polity]): BrArmy = army(counters(this))
  override def toString: String = str
}

object Polity
{
  implicit val showEv: ShowTellSimple[Nothing] = ShowTellSimple("Polity")
}

object Germany extends Polity
{ def str: String = "Deutschland"
  /** CadetBlue 60% shade. */
  def colour = Colour.fromInts(128, 177, 179)
}

object Britain extends Polity
{ def str: String = "Britain"
  def colour = Colour.fromInts(255, 232, 184)
}

object France extends Polity
{ def str: String = "France"
  def colour = Colour.fromInts(125, 255, 255)
}

object Japan extends Polity
{ def str: String = "Japan"
  def colour = Colour.fromInts(188, 0, 45)
}

object Soviet extends Polity
{ override def str: String = "Soviet Union"
  override def colour: Colour = Red
}
/** Historical nation state or other polity such as Kingdom or the Soviet Union. */
/*trait Polity extends  TellSimple with Coloured
{ override def typeStr: String = "Polity"
  def str: String
  override def toString: String = str
}*/

object Prussia extends Polity
{ override val str: String = "Preußen"
  override def colour: Colour = Navy
}

object Deutch extends Polity
{ override def str: String = "Deutsch"
  override def colour: Colour = Gray
}

/*object France extends Polity
{ override def str: String = "France"
  override def colour: Colour = LightBlue
}*/

object Soviets extends Polity
{ override def str: String = "Soviets"
  override def colour: Colour = Red
}
