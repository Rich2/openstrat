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
      TRow(114, plain),
      TRow(112, desert * 2),
      TRow(110, desert * 2),
      TRow(108, hillyDesert * 2),
      VRow(107, MouthLt(2558, HVUL), BendIn(2560, HVDL, 6)),
      TRow(106, desert, mtainDesert, hillySavannah),
      VRow(105, BendIn(2554, HVDL, 13), BendIn(2560, HVUR, 13), MouthRt(2562, HVDR, 7)),
      TRow(104, hillyDesert, sea * 2),
      VRow(103, BendOut(2554, HVUR), BendIn(2556, HVUp, 13), MouthRt(2558, HVUR, 7), BendIn(2564, HVDR, 13), MouthRt(2566, HVUR), BendIn(2568, HVDR, 12)),
      TRow(102, sea * 2, hillySavannah),
      VRow(101, BendIn(2564, HVUR, 13), BendIn(2566, HVUp, 13), ThreeUp(2568, 12, 0, 8)),
      VRow(97, BendMouth(2554, HVDR, 6, 7), BendIn(2556, HVDn), BendIn(2558, HVDL, 13)),
      TRow(96, hillySavannah),
      VRow(95, BendIn(2554, HVUL, 13), BendIn(2556, HVDR), BendIn(2558, HVUL, 13)),
      VRow(93, SetSep(2557)),
      TRow(82, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(102, "" * 2, "Kerala")
  }
}