/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import Colour._

trait Polity extends ShowSimple
{ override def typeStr: String = "Polity"
  def colour: Colour
  //def ar: Army = Army(this)

  def army(num: Int): Army = Army(this, num)

  def armyNext()(implicit counters: ArrCounters[Polity]): Army = army(counters(this))
}

object Polity
{
  object PolityPersist extends PersistSingletons[Polity]("Polity")
  { override val singletonList = List(Germany, Britain, France)
  }
}

object Germany extends Polity
{ def str: String = "Germany"
  def colour = Colour.fromInts(128, 177, 179)//CadetBlue 60% shade


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