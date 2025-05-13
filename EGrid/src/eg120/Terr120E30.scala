/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] 120km scale terrain for 15° east to 45° east, centred on 30° east.
 * [[Isle13]] 7611.551km² => 8878.113km². Crete 8450km².
 * [[Isle10]] 4396.432km² => 5370.710km². Edgeøya 5,073km².
 * [[Isle6]] 1473.596km² => 2058.163km². Lesbos 1633km².
 * [[Isle5]] 986.457km² => 1473.596km². Andros for northern Cyclades 2572km²/2 = 1286km², Naxos for southern Cyclades = 1286km².
 * [[Isle3]] 304.462km² => 596.745km². */
object Terr120E30 extends Long120Terrs
{ override implicit val grid: EGrid120LongFull = EGrid120.e30(274)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(386, SeaIcePerm),
    TileRow(384, SeaIcePerm),
    TileRow(382, SeaIcePerm * 2),
    TileRow(380, SeaIcePerm * 2),
    TileRow(378, SeaIcePerm * 2, SeaIceWinter),
    TileRow(376, SeaIcePerm, SeaIceWinter * 2),
    TileRow(374, SeaIceWinter * 4),
    VertRow(373, BendIn(1530, HVDR, 13, SeaIceWinter),  BendIn(1532, HVDn, 13, SeaIceWinter), BendIn(1534, HVDL, 13, SeaIceWinter)),
    TileRow(372, hillyTundra, SeaIceWinter * 3),

    VertRow(371, BendMin(1528, HVDR, 3, SeaIceWinter), BendOut(1534, HVUR, 6, SeaIceWinter),
      BendIn(1536, HVDL, 13, SeaIceWinter)),

    TileRow(370, hillyIce * 2, SeaIceWinter * 3),

    VertRow(369, BendIn(1528, HVUR, 9, SeaIceWinter), Orig(1530, HVUL, 1, 7, SeaIceWinter), OrigLt(1532, HVDR, 6, SeaIceWinter),
      BendIn(1534, HVUp, 13, SeaIceWinter), BendIn(1536, HVUL, 13, SeaIceWinter)),
    
    TileRow(368, hillyIce, SeaIceWinter * 4),
    TileRow(366, Isle10(hillyTundra, SeaIceWinter), SeaIceWinter),

    VertRow(353, BendIn(1526, HVDR, 13), BendIn(1528, HVDn, 13), BendOut(1530, HVUp, 7), BendIn(1532, HVDn , 13), OrigLt(1534, HVUL, 7)),
    TileRow(352, sea, fjordCont * 2),
    TileRow(350, fjordCont * 2, mtainLakesCont, hillyCont *2),
    TileRow(296, mtainContForest),
    TileRow(294, hillySub, mtainContForest * 2, mtainCont * 2, continental * 2, hillySavannah * 2),
    TileRow(292, mtainSub * 2, mtainCont * 3, hillyCont * 3, sea * 8, mtainSavannah * 3, hillySavannah),
    VertRow(291, OrigRt(1496, HVUR, 7), ThreeUp(1498, 7, 13, 0), BendInLt(1500, HVDL, 13, 7)),
    TileRow(290, mtainSub, sea, mtainSavannah * 4, hillySavannah * 2, mtainSub, sea * 3, mtainSub, hillySub, sea * 4, hillySub, mtainSavannah),
    VertRow(289, BendOut(1500, HVUR, 7), BendIn(1502, HVDL, 13)),
    TileRow(288, hillySub, sea, mtainSub, hillySavannah * 4, savannah, hillySavannah, mtainSub * 2, mtainSavannah * 7, hillySavannah * 2),
    VertRow(287, ThreeDown(1504, 0, 13, 11), OrigRt(1528, HVUR, 7), Bend(1530, HVDn, 13, 3),  BendIn(1532, HVUp, 12), OrigMin(1534, HVDL)),

    TileRow(286, hillySub, subtrop, mtainSub, mtainSubForest, mtainSavannah, mtainSavannah, sea, mtainSavannah, hillySavannah, mtainSavannah * 3, mtainSahel,
      mtainSavannah * 4, mtainSahel * 2, mtainSavannah * 2),

    VertRow(285, OrigMin(1498, HVUR), BendMax(1500, HVDn), BendIn(1504, HVUR, 13), Bend(1502, HVDL, 13, 6)),

    TileRow(284, mtainSubForest, mtainSavannah, sea, mtainSub, mtainSubForest, hillySavannah, sea, Isle6(mtainSavannah), mtainSavannah * 2, hillySavannah,
      hillySahel * 4, mtainSahel, mtainSavannah * 2, mtainSahel * 3),

    VertRow(283, BendIn(1494, HVUR, 13), BendOut(1500, HVDR, 7), BendIn(1502, HVUL,  13), OrigRt(1572, HVUR, 7, Lake), OrigLt(1574, HVDL, 7, Lake)),
    
    TileRow(282, mtainSavannah, sea * 2, hillySub, mtainSubForest, hillySavannah, sea, hillySavannah * 2, hillySahel, mtainSahel, hillySahel, sahel,
      hillySahel * 2, mtainSahel, mtainSavannah, hillySavannah, mtainSavannah, mtainSahel * 2),

    VertRow(281, OrigLt(1498, HVUR, 7), BendIn(1500, HVUL, 13), OrigLt(1510, HVUR, 7), BendOut(1512, HVDn , 7), BendIn(1514, HVUp, 12), OrigLt(1516, HVDL)),

    TileRow(280, sea * 4, mtainSub, mtainSavannah, Isle5(mtainSavannah), sea, hillySavannah, mtainSavannah * 3, savannah, hillySahel * 2, mtainSavannah,
      hillySavannah, savannah, mtainDeshot, mtainSahel * 3),

    VertRow(279, BendOut(1528, HVDL)),

    TileRow(278, sea * 5, mtainSavannah, sea, Isle5(mtainSavannah), sea, mtainSavannah * 4, mtainSahel, hillySavannah, hillySavannah, sahel * 3, deshot, sahel,
      mtainSahel),

    VertRow(277, OrigRt(1518, HVUR, 7), BendIn(1520, HVDn, 13), Bend(1522, HVUp, 11, 7), BendIn(1524, HVDn, 13), BendIn(1526, HVDL, 13), OrigRt(1528, HVUp, 7),
      OrigLt(1532, HVDR, 7), OrigRt(1534, HVUL, 7), OrigLt(1538, HVUR, 7), BendOut(1540, HVDn, 7), ThreeDown(1542, 13, 13, 0), BendIn(1544, HVDn, 13),
      Bend(1546, HVUp, 2, 7)),

    TileRow(276, sea * 6, mtainSavannah * 2, sea * 4, mtainSavannah, hillySavannah, mtainSub, hillySavannah, deshot * 2, sahel, deshot, sahel, hillyDeshot),
    VertRow(275, OrigLt(1526, HVUp, 7)),
    TileRow(274, sea * 13, mtainSavannah),
    )
  }
  help.run

  { import hexNames.{setRow => str}
    str(366, "Edgeøya")
    str(284, "" * 7, "Lesbos")
    str(280, "" * 6, "Andros")
    str(278, "" * 7, "Naxos")
  }
}