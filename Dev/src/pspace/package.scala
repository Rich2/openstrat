/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import Colour._

package object pspace
{ /** The average distance from the Sun to the Earth */
  val earthSunDist: Length = 93.01.mMiles

  val Mercury: Planet = Planet(36.mMiles, Gray, "Mercury")
  val Venus: Planet = Planet(67.2.mMiles , White, "Venus" )
  val Earth: Planet = Planet(earthSunDist, Blue, "Earth")
  val Mars: Planet = Planet(141.6.mMiles , Red, "Mars")
  val Jupiter: Planet = Planet(483.6.mMiles , Orange, "Jupiter")
  val Saturn: Planet = Planet(886.7.mMiles , Gold, "Saturn")
  val Uranus: Planet = Planet(1784.0.mMiles , BrightSkyBlue, "Uranus")
  val Neptune: Planet = Planet(2794.4.mMiles , LightGreen, "Neptune")
  val Pluto: Planet = Planet(3674.5.mMiles, SandyBrown, "Pluto")

  val sunPlus9: Arr[Planet] = Arr(Sun, Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto)
}
