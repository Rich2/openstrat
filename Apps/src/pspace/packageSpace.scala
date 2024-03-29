/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Package offering some code for Space. */
package object pspace
{ /** The planets including Pluto from nearest to furthest  form the Sun, */
  val planets: RArr[Planet] = RArr(Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto)

  /** The Sun plus the planets including Pluto from nearest to furthest from the Sun, */
  val sunPlus9: RArr[SSPrimaryBody] = RArr(Sun, Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto)
}
