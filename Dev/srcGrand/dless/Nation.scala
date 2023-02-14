/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import Colour._

trait Nation
{ val name: String
  val colour: Colour
  override def toString: String = name
}

object Britain extends Nation
{ override val name: String = "Britain"
  override val colour: Colour = Pink
}

object France extends Nation
{ override val name: String = "France"
  override val colour: Colour = DarkBlue
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