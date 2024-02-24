/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° east to 75° east, centred on 60° east. Hex tile scale 1 MegaMetre or 1000km. */
object TerrMegaE60 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e60(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, tundra),
      TRow(116, taiga),
      TRow(114, level),
      TRow(112, desert * 2),
      TRow(110, desert * 2),
      TRow(108, hillyDesert * 2),
      TRow(106, desert, sea, level),
      TRow(104, hillyDesert, sea * 2),
      VRow(97, BendIn(2554, HVDR)),
      TRow(96, hillySavannah),
      VRow(95, BendIn(2554, HVUL, 13)),
      TRow(82, ice)
    )
  }
  help.run
}