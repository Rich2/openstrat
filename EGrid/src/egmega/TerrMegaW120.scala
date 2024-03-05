/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 135° west to 105° west, centred on 120° west. Hex tile scale 1 Megametre or 1000km. */
object TerrMegaW120 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w120(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, tundra),
      TRow(116, taiga),
      VRow(115, MouthOld(8708, HVDL)),
      TRow(114, taiga),
      TRow(112, taiga * 2),
      VRow(111, BendIn(8700, HVDR, 13), MouthLt(8702, HVUR, 7)),
      TRow(110, hillyOce, hillySahel),
      VRow(109, BendIn(8700, HVUR, 13), BendOut(8702, HVDL, 7)),
      TRow(108, hillySub, hillySahel),
      VRow(107, BendIn(8702, HVUR, 13), BendOut(8704, HVDL, 7)),
      TRow(106, sea, hillySahel, hillySahel),
      VRow(105, MouthLt(8708, HVUL), Bend(8710, HVDL, 2, 4)),
      VRow(103, BendIn(8710, HVUR, 13), BendIn(8712, HVUp, 13)),
    )
  }
  help.run
}