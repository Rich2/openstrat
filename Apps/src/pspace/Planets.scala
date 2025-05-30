/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace
import geom._, Colour._

/** The planet <a href="https://en.wikipedia.org/wiki/Mercury_(planet)">Mercury</a>. */
object Mercury extends Planet
{ override val avSunDist: Gigametres = 57.909175.gigametres
  override val name: String = "Mercury"
  override def colour: Colour = Colour.LightGray
}

/** The planet <a href="https://en.wikipedia.org/wiki/Venus_(planet)">Venus</a>. */
object Venus extends Planet
{ override val avSunDist: Gigametres = 108.208930.gigametres
  override val name: String = "Venus"
  override def colour: Colour = White
}

object Earth extends Planet
{ override val avSunDist: Gigametres = 149.597890.gigametres// 93.01.megaMiles.toMetres
  override val name: String = "Earth"
  override def colour: Colour = Blue

  object TheMoon extends Moon
  { override val name: String = "The Moon"
  }

  override val moons: RArr[TheMoon.type] = RArr(TheMoon)
}

/** The planet <a href="https://en.wikipedia.org/wiki/Mars_(planet)">Mars</a>. */
object Mars extends Planet
{ override val avSunDist: Gigametres = 227.936640.gigametres
  override val name: String = "Mars"
  override def colour: Colour = Red
}

object Jupiter extends Planet
{ override val avSunDist: Gigametres = 778.412010.gigametres
  override val name: String = "Jupiter"
  override def colour: Colour = LightSalmon
}

object Saturn extends Planet
{ override val avSunDist: Metres = 886.7.megaMiles.toMetres
  override val name: String = "Saturn"
  override def colour: Colour = Colour.LightYellow
}

object Uranus extends Planet
{ override val avSunDist: Metres = 1784.0.megaMiles.toMetres
  override val name: String = "Uranus"
  override def colour: Colour = Colour.Khaki
}

object Neptune extends Planet
{ override val avSunDist: Metres = 2794.4.megaMiles.toMetres
  override val name: String = "Neptune"
  override def colour: Colour = Colour.Aquamarine
}

object Pluto extends Planet
{ override val avSunDist: Metres = 3674.5.megaMiles.toMetres
  override val name: String = "Pluto"
  override def colour: Colour = Colour.MediumVioletRed
}