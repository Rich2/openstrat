/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace
import geom._, Colour._

class Planet(val avSunDist: Length, val name: String)
{
  def posn(elapsed: Integer): PtM2 =
  { val auRatio: Double = avSunDist / Earth.avSunDist
    Pt2.circlePtClockwise(0.001 * elapsed / math.sqrt(auRatio.cubed)).toMetres(avSunDist)
  }

  override def toString = name
}

object Planet
{ def apply(metres: Length, name: String): Planet = new Planet(metres, name)
}

object Sun extends Planet(0.mMiles, "Sun")