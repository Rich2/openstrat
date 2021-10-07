/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth;
import geom._

/** North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object AsiaEast extends EarthLevel1("Asia", 60 ll 100)
{ import AsiaEastPts._
  //override val gridMaker = E80Empty
  override val a2Arr = Arr(seAsia, ceAsia, neAsia, feAsia, japan)
}