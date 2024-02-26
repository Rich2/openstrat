/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 1300km or 1.3 Megametres.
 * Isle 463086.787km² => 966193.420km². Madagascar 587,041km².
 *  */
object Terr13E30 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e30(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(115, BendOut(1536, HVUp, 7), BendIn(1538, HVDn, 13, sea, SeaIceWinter), BendOut(1540, HVUp, 6, SeaIceWinter)),
      TRow(114, tundra),
      TRow(112, taiga),
      TRow(110, temperate),
      VRow(109, MouthLt(1534, HVUp), MouthLt(1538, HVUp, 7, lake)),
      TRow(108, hillyTemp),
      VRow(107, ThreeUp(1534, 6, 6, 0), MouthOld(1536, HVDR), MouthRt(1538, HVDn, 7, lake)),
      TRow(106, sahel, sahel),
      VRow(105, MouthLt(1536, HVUL), Bend(1538, HVDL, 2, 4), MouthLt(1540, HVUL), BendIn(1542, HVUp)),
      TRow(104, desert * 2),
      VRow(103, BendIn(1538, HVUR, 10), BendOut(1540, HVUp), BendIn(1542, HVDn, 12)),
      TRow(102, savannah, Hilly(Savannah)),
      VRow(101, BendOut(1542, HVDR)),
      TRow(100, jungle, hillySavannah),
      VRow(99, BendOut(1540, HVDR, 7), BendIn(1542, HVUL, 13)),
      TRow(98, jungle, jungle),
      VRow(97, BendIn(1538, HVDR), ThreeUp(1540, 0, 6, 6), BendIn(1542, HVDL)),
      TRow(96, hillyTemp, tempForest),
      VRow(95, BendIn(1538, HVUR), BendIn(1540, HVUp), BendIn(1542, HVUL)),
      TRow(94, temperate, sea),
      VRow(93, MouthRt(1534, HVDR)),
      TRow(88, SeaIceWinter),
      TRow(86, ice)
    )
  }
  help.run

  hexNames.setRow(108, "Turkey")
}