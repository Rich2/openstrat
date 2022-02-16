/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace
import Colour._

object Mercury extends Planet
{ override val avSunDist: Length = 36.mMiles
  override val name: String = "Mercury"
  override def colour: Colour = Colour.LightGray
}

object Venus extends Planet
{ override val avSunDist: Length = 67.2.mMiles
  override val name: String = "Venus"
  override def colour: Colour = White
}

object Earth extends Planet
{ override val avSunDist: Length = 93.01.mMiles
  override val name: String = "Earth"
  override def colour: Colour = Blue

  object TheMoon extends Moon
  { override val name: String = "The Moon"
  }

  override val moons: Arr[TheMoon.type] = Arr(TheMoon)
}

object Mars extends Planet
{ override val avSunDist: Length = 141.6.mMiles
  override val name: String = "Mars"
  override def colour: Colour = Red
}

object Jupiter extends Planet
{ override val avSunDist: Length = 483.6.mMiles
  override val name: String = "Jupiter"
  override def colour: Colour = LightSalmon
}

object Saturn extends Planet
{ override val avSunDist: Length = 886.7.mMiles
  override val name: String = "Saturn"
  override def colour: Colour = Colour.LightYellow
}

object Uranus extends Planet
{ override val avSunDist: Length = 1784.0.mMiles
  override val name: String = "Uranus"
  override def colour: Colour = Colour.Khaki
}

object Neptune extends Planet
{ override val avSunDist: Length = 2794.4.mMiles
  override val name: String = "Neptune"
  override def colour: Colour = Colour.Aquamarine
}

object Pluto extends Planet
{ override val avSunDist: Length = 3674.5.mMiles
  override val name: String = "Pluto"
  override def colour: Colour = Colour.MediumVioletRed
}