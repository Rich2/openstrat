/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace
import geom._, pgui._, Colour._

class Planet(val dist: Length, val colour: Colour, val name: String)
{
  def posn(elapsed: Integer): PtM2 =
  { val auRatio: Double = dist / earthSunDist
    Pt2.circlePtClockwise(0.001 * elapsed / math.sqrt(auRatio.cubed)).toMetres(dist)
  }

  def size = 10
  override def toString = name
}

object Planet
{ def apply(metres: Length, colour: Colour, name: String): Planet = new Planet(metres, colour, name)
}

object Sun extends Planet(0.mMiles, Yellow, "Sun")
{ override val size = 14
}