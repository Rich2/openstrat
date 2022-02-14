/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

package object pspace
{ /** The average distance from the Sun to the Earth */
  //val earthSunDist: Length = 93.01.mMiles

  val Mercury: Planet = Planet(36.mMiles, "Mercury")
  val Venus: Planet = Planet(67.2.mMiles, "Venus" )
  val Earth: Planet = Planet(93.01.mMiles, "Earth")
  val Mars: Planet = Planet(141.6.mMiles, "Mars")
  val Jupiter: Planet = Planet(483.6.mMiles, "Jupiter")
  val Saturn: Planet = Planet(886.7.mMiles, "Saturn")
  val Uranus: Planet = Planet(1784.0.mMiles, "Uranus")
  val Neptune: Planet = Planet(2794.4.mMiles, "Neptune")
  val Pluto: Planet = Planet(3674.5.mMiles, "Pluto")

  val sunPlus9: Arr[Planet] = Arr(Sun, Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto)
}
