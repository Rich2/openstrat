/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pzug
import prid._, phex._

/** Helper class for setting  [[LayerHcRefSys]][WTile], [[HSideLayer]][WSide] and [[HCornerLayer]] at the same time." */
abstract class ZugTerrSetter(gridIn: HGrid, val terrs: LayerHcRefSys[ZugTerr], val sTerrs: HSideLayer[HSideBoolLayer], val corners: HCornerLayer)
{
  sealed trait RowBase

  trait TRowElem extends ZugTerrHelper
}