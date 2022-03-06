/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package pGrid
import geom._, pglobe._

trait EGridOld extends HexGridIrrOld
{
  def roodToLL(roord: Roord): LatLong
}
