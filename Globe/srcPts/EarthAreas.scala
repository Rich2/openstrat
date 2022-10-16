/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
//import pGrid._

/** Object for irregular areas and hexagonal tile grids to represent the Earths surface. */
object EarthAreas
{ import pPts._, pEurope._

  /** Europe, Asia and North-and central Africa. */
  val oldWorld: RArr[EArea1] = RArr(EuropeNW, EuropeSW, EuropeEast, AsiaWest, PolarNorth, AfricaWest, AfricaEast, AsiaEast, NorthAtantic)

  /** The areas discovered from the late 14th century. */
  val newWorld: RArr[EArea1] = RArr(PolarSouth, AmericasNorth, AmericasSouth, Australasia, PacificTop, AfricanTrangle)

  def allTops: RArr[EArea1] =  oldWorld ++ newWorld
}