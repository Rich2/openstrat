/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._, pGrid._

trait EGrid extends HexGridIrr
{
  def roodToLL(roord: Roord): LatLong
}
