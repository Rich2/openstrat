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
  { override val rows: RArr[DateRow] = RArr(
    TileRow(118, tundra),
    TileRow(116, taiga),
    TileRow(114, steppe),
    TileRow(112, sahel, steppe),
    TileRow(110, deshot * 2),
    TileRow(108, hillyDeshot * 2),
    VertRow(107, OrigRt(2558, HVDR, 7), BendIn(2560, HVDL)),
    TileRow(106, deshot, mtainDeshot, hillySavannah),
    VertRow(105, BendIn(2554, HVDL, 13), BendIn(2560, HVUR, 13), OrigRt(2562, HVUL, 7)),
    TileRow(104, hillyDeshot, sea * 2),
    VertRow(103, BendOut(2554, HVUR), BendIn(2556, HVUp, 13), OrigRt(2558, HVDL, 7), BendIn(2564, HVDR, 13), BendIn(2568, HVDR, 12)),
    TileRow(102, sea * 2, hillySavannah),
    VertRow(101, BendIn(2564, HVUR, 13), BendIn(2566, HVUp, 13), ThreeUp(2568, 12, 0, 8)),
    VertRow(97, BendInLt(2554, HVDR, 6, 7), BendIn(2556, HVDn), BendIn(2558, HVDL, 13)),
    TileRow(96, hillySavannah),
    VertRow(95, BendIn(2554, HVUL, 13), BendIn(2556, HVDR), BendIn(2558, HVUL, 13)),
    VertRow(93, SetSep(2557)),
    TileRow(82, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(102, "" * 2, "Kerala")
    str(96, "Madagascar north")
  }
}