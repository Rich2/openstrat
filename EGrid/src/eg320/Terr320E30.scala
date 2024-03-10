/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale of 320km.
 * [[Tile9]] 25028.134km² => 31263.517km². Sicily 25711km².
 * [[Tile5]] 7014.805km² => 10478.907km². Crete 8450 km². */
object Terr320E30 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e30(118)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, SeaIcePerm),
      TRow(164, Isle10(Plain, IceCap, LandFree, SeaIceWinter)),
      TRow(162, Land(Mountains, IceCap, LandFree), SeaIceWinter),
      VRow(157, MouthRt(1530, HVDL, 7), MouthLt(1532, HVUR, 7), MouthRt(1536, HVUL, 7), MouthLt(1538, HVDR, 7)),
      TRow(156, hillyTundra, hillyTundra, sea),
      VRow(155, MouthRt(1542, HVUL, 7, SeaIceWinter), BendIn(1544, HVDL, 11, SeaIceWinter)),
      TRow(154, hillyTaiga, taiga, hillyLakesTaiga, tundra),

      VRow(153, MouthRt(1534, HVUp, 7), MouthMin(1538, HVDL, SeaIceWinter), BendIn(1540, HVDn, 13, SeaIceWinter), ThreeDown(1542, 3, 3, 3, SeaIceWinter),
        BendIn(1544, HVUL, 9, SeaIceWinter)),

      TRow(152, taiga, taiga, lakesTaiga, taiga),
      VRow(151, BendOut(1532, HVDR, 7), BendIn(1534, HVUL, 13), MouthLt(1542, HVDn, 7, SeaIceWinter)),
      TRow(150, taiga, lakesTaiga * 3),
      VRow(149, BendOut(1530, HVDR, 7), BendIn(1532, HVUL, 13), MouthOld(1536, HVUL, 3, Lake), MouthOld(1538, HVDR, 3, Lake)),
      VRow(147, Bend(1528, HVDR, 13, 3), ThreeUp(1530, 13, 0, 10), BendIn(1532, HVUp, 13), MouthRt(1534, HVUR, 7)),
      TRow(148, taiga, taiga, taiga * 3),
      TRow(146, oceanic, taiga * 4),
      VRow(145, BendOut(1526, HVUp, 7), Bend(1528, HVUL, 2, 7)),
      TRow(144, oceanic * 5),
      TRow(142, oceanic * 6),
      TRow(140, oceanic * 6),
      TRow(138, mtainOld * 2, hillyOce, oceanic * 3, desert),
      VRow(137, MouthOld(1522, HVUp), Mouth(1538, HVUp, 3, 7), BendAllOld(1542, HVDR), MouthOld(1544, HVUR)),
      TRow(136, hillyCont, continental * 3, steppe, steppe * 2),
      VRow(135, BendIn(1524, HVDL, 13), BendMax(1536, HVDR), ThreeUp(1538, 7, 13, 13), Bend(1540, HVUp, 10, 7), ThreeUp(1542, 7, 13, 7)),
      TRow(134, hillyOce, hillyOce, hillyOce, mtainSubForest, mtainSavannah, mtainSubForest, mtainSavannah),
      VRow(133, BendIn(1524, HVUR, 8), BendIn(1526, HVDL, 13), MouthOld(1530, HVUp), MouthMax(1536, HVDn)),
      TRow(132, hillySavannah, hillyOce, CapeOld(4, 1, hillyOce), hillyOce * 4),
      VRow(131, VertLeftsRight(1522), BendMax(1524, HVDR), BendIn(1526, HVUL, 13), BendIn(1532, HVDL, 13)),
      TRow(130, hillyOce, hillyOce, hillyOce, hillyOce, hillyOce * 4),
      VRow(129, ThreeUp(1524, 13, 0, 13), MouthOld(1536, HVUR)),
      TRow(128, sea * 2, Isle10(hillySavannah), sea, Isle10(hillyOce), hillyOce, desert * 2),
      VRow(127, BendAllOld(1528, HVUp)),
      TRow(126, sea, CapeOld(5, 2, sahel), CapeOld(0, 2, sahel), sea * 2, CapeOld(5, 1, hillyOce), desert * 2),
      VRow(125, MouthOld(1524, HVDn), MouthOld(1532, HVDn), MouthOld(1540, HVDn)),
      TRow(124, sahel * 4, savannah, desert * 4),
      VRow(123, MouthOld(1538, HVUL), MouthOld(1540, HVDR)),
      TRow(122, desert * 4, savannah, sea, desert * 3),
      TRow(120, desert * 6, sea, desert * 2),
      TRow(118, desert * 5, hillyDesert, sea, hillyDesert, desert),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(132, "Italy south")
  }
}