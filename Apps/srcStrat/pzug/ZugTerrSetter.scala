/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pzug
import prid.*, phex.*

/** Helper class for setting  [[LayerHcRefSys]][WTile], [[HSepLayer]][WSide] and [[HCornerLayer]] at the same time." */
abstract class ZugTerrSetter(gridIn: HGrid, val terrs: LayerHcRefSys[ZugTerr], val sTerrs: HSepLayer[HSideBoolLayer], val corners: HCornerLayer)
{
  sealed trait RowBase

  trait TRowElem extends ZugTerrHelper
}