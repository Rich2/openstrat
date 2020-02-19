/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug
import pGrid._

object Zug2  extends ZugGrid(4, 38, 2, 10, 0)
{ 
  setSideCollection(hSidesHorr(7, 5, 37), true)
  placeSquads((Germany, 18, 6), (Germany, 30, 6), (France, 14, 10),  (France, 22, 10), (France, 30, 10))
}