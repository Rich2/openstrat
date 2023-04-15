/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import Colour._

trait NationOpt extends Coloured
{ def altColour: Colour = colour
}

object NationLess extends NationOpt
{
  override val colour: Colour = DarkBlue
}

trait Nation extends NationOpt
{ val name: String

  override def toString: String = name
  def army(num: Int): Army = Army(this, num)
  def armyNext()(implicit counters: ArrCounters[Nation]): Army = army(counters(this))
}

object Britain extends Nation
{ override val name: String = "Britain"
  override val colour: Colour = Pink
}

object France extends Nation
{ override val name: String = "France"
  override val colour: Colour = DarkBlue
  override def altColour: Colour = LightBlue
}

object Germany extends Nation
{ override val name: String = "Germany"
  override val colour: Colour = Black
}

object Russia extends Nation
{ override val name: String = "Russia"
  override val colour: Colour = White
}

object Italy extends Nation
{ override val name: String = "Italy"
  override val colour: Colour = DarkRed
}

object Austria extends Nation
{ override val name: String = "Austro-Hungry"
  override val colour: Colour = Yellow
}

object Ottoman extends Nation
{ override val name: String = "Ottoman"
  override val colour: Colour = Red
}

object Spain extends Nation
{ override val name: String = "Spain"
  override val colour: Colour = Orange
}

object Neutral extends Nation
{ override val name: String = "Neutral"
  override val colour: Colour = Gray
}