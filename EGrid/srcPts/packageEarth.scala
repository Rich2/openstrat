/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import geom._, pglobe._

/** This package and module is for Earth maps. In particular the tiling of the whole world in Hex grids, defining the changes over the course of
 *  history. This will be a data orientated module. It will also include terrain types to model terrain, both real and imagined for local maps and
 *  higher scales right up to 0.5 metres per tile However it won't generally include the data for these. The data for the real world will be organised
 *  according to a number of levels, which are likely to change over increasingly shorter historical time frames.
 *
 *  1 Base elevation, relative to 1950 sea level, and relief.
 *  2 Climate.
 *  2 Sea level, shore lines, lake shore lines and river courses.
 *  3 Land-use, both natural and human. */
package object pEarth
{
   /** The North-South divide between Area1s and Grids at 45 degrees north approx. */
   val divN45 = 45.27369792435918.north

   /** Europe, Asia and North-and central Africa. */
   val oldWorldAreas: RArr[EArea1] = RArr(EuropeNW, EuropeSW, EuropeEast, MiddleEast, PolarNorth, MediterreaneanWest, MediterraneanEast, AfricaNorth,
      AsiaMain, AsiaEast, NorthAtantic, MalayArch)

   /** The areas discovered from the late 14th century. */
   val newWorldAreas: RArr[EArea1] = RArr(PolarSouth, Alaska, AmericasFarNorth, AmericasNearNorth, AmericasCentral, AmericasSouth, Australasia, PacificTop, AfricaSouth)

   def earthAllAreas: RArr[EArea1] = oldWorldAreas ++ newWorldAreas
}