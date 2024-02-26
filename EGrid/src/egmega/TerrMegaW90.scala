/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** 1 Mm [[WTile]] terrain for 105° west to 75° west, centred on 90° west. Hex tile scale 1 Megametre or 1000km. Hex tile area of 866025.403 km².
 *  Isle6 102333.079km² => 142928.020km². Cuba 109884km².
 *  Isle3 21143.198km² => 41440.668km².
 *  Below 21143.198km², Jamaica 10991km². */
object TerrMegaW90 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w90(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, CapeOld(1, 1, tundra)),
      TRow(116, CapeOld(1, 2, taiga)),
      VRow(115, MouthOld(9728, HVDL)),
      TRow(114, taiga),
      TRow(112, taiga, taiga),
      TRow(110, savannah, hillyOce),
      VRow(109, MouthOld(9732, HVUL)),
      TRow(108, CapeOld(3, 1), CapeOld(1, 2)),
      VRow(107, MouthOld(9726, HVUL), MouthOld(9730, HVUR), MouthOld(9732, HVDL)),
      TRow(106, CapeOld(1, 2, sahel), sea, hillyJungle),
      VRow(105, BendOut(9722, HVDL), MouthOld(9726, HVDL)),
      TRow(104, CapeOld(3, 2, hillyJungle), jungle, sea),
      VRow(103, BendOut(9726, HVDn), BendOut(9728, HVDL)),
      TRow(102, sea, CapeOld(3, 2, jungle), jungle),
      VRow(101, MouthOld(9732, HVUR)),
      TRow(100, sea * 2, CapeOld(4, 2, hillyJungle)),
      VRow(99, MouthOld(9732, HVDR)),
      TRow(98, sea * 2, hillyJungle),
      VRow(89, MouthOld(9732, HVUR)),
      TRow(88, sea, CapeOld(5, 1, hillyTaiga)),
      VRow(87, MouthOld(9730, HVDn)),
    )
  }
  help.run
}