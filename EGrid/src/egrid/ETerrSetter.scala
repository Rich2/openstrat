/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTile._

class ETerrSetter(val terrs: HCenLayer[WTile], val sTerrs: HSideLayer[WSide], val corners: HCornerLayer)
{
  case class TRow(row: Int)
}