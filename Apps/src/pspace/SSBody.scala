/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace
import geom._, Colour._

/** Solar System body includes the Sun, the Planets, Dwarf planets and their moons. */
sealed trait SSBody extends Coloured
{ /** The name of this body. */
  val name: String

  override def toString = name
  def colour: Colour = White
}

/** The Sun or a body that directly orbits the Sun. */
trait SSPrimaryBody extends SSBody

trait Planet extends SSPrimaryBody
{ val avSunDist: Metres
  trait Moon extends SSBody
  def moons: RArr[Moon] = RArr()
}

/** The Sun, the Star of our solar system. */
object Sun extends SSPrimaryBody
{ override val name: String = "Sun"
  override def colour: Colour = Yellow
}