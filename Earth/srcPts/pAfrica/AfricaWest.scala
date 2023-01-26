/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._

object AfricaWest extends EArea1("WAfrica", 20 ll 40)
{ type A2Type = EArea2
  override val a2Arr: RArr[EArea2] = RArr(Canarias, Sicily, Majorca, SaharaWest, AfricaWestPts.westAfricaSouth)
}
