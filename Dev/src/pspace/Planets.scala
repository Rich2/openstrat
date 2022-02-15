/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace

object Mercury extends Planet
{ override val avSunDist: Length = 36.mMiles
  override val name: String = "Mercury"}

object Venus extends Planet
{ override val avSunDist: Length = 67.2.mMiles
  override val name: String = "Venus"
}

object Earth extends Planet
{ override val avSunDist: Length = 93.01.mMiles
  override val name: String = "Earth"

  object TheMoon extends Moon

  override val moons: Arr[TheMoon.type] = Arr(TheMoon)
}

object Mars extends Planet
{ override val avSunDist: Length = 141.6.mMiles
  override val name: String = "Mars"
}

object Jupiter extends Planet
{ override val avSunDist: Length = 483.6.mMiles
  override val name: String = "Jupiter"
}

object Saturn extends Planet
{ override val avSunDist: Length = 886.7.mMiles
  override val name: String = "Saturn"
}

object Uranus extends Planet
{ override val avSunDist: Length = 1784.0.mMiles
  override val name: String = "Uranus"
}

object Neptune extends Planet
{ override val avSunDist: Length = 2794.4.mMiles
  override val name: String = "Neptune"
}

object Pluto extends Planet
{ override val avSunDist: Length = 3674.5.mMiles
  override val name: String = "Pluto"
}