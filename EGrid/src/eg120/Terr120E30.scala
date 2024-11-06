/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] 120km scale terrain for 15° east to 45° east, centred on 30° east.
 * [[Isle13]] 7611.551km² => 8878.113km². Crete 8450km².
 * [[Isle6]] 1473.596km² => 2058.163km². Lesbos 1633km².
 * [[Isle5]] 986.457km² => 1473.596km². Andros for northern Cyclades 2572km²/2 = 1286km², Naxos for southern Cyclades = 1286km².
 * [[Isle3]] 304.462km² => 596.745km². */
object Terr120E30 extends Long120Terrs
{ override implicit val grid: EGrid120LongFull = EGrid120.e30(274, 362)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(353, BendIn(1526, HVDR, 13), BendIn(1528, HVDn, 13), BendOut(1530, HVUp, 7), BendIn(1532, HVDn , 13), OrigLt(1534, HVUL, 7)),
    TRow(352, sea, fjordCont * 2),
    TRow(350, fjordCont * 2, mtainLakesCont, hillyCont *2),
    TRow(296, mtainContForest),
    TRow(294, hillySub, mtainContForest * 2, mtainCont * 2, continental * 2, hillySavannah * 2),
    TRow(292, mtainSub * 2, mtainCont * 3, hillyCont * 3, sea * 8, mtainSavannah * 3, hillySavannah),
    VRow(291, OrigRt(1496, HVUR, 7), ThreeUp(1498, 7, 13, 0), BendInLt(1500, HVDL, 13, 7)),
    TRow(290, mtainSub, sea, mtainSavannah * 4, hillySavannah * 2, mtainSub, sea * 3, mtainSub, hillySub, sea * 4, hillySub, mtainSavannah),
    VRow(289, BendOut(1500, HVUR, 7), BendIn(1502, HVDL, 13)),
    TRow(288, hillySub, sea, mtainSub, hillySavannah * 4, savannah, hillySavannah, mtainSub * 2, mtainSavannah * 7, hillySavannah * 2),
    VRow(287, ThreeDown(1504, 0, 13, 11), OrigRt(1528, HVUR, 7), Bend(1530, HVDn, 13, 3),  BendIn(1532, HVUp, 12), OrigMin(1534, HVDL)),

    TRow(286, hillySub, subtrop, mtainSub, mtainSubForest, mtainSavannah, mtainSavannah, sea, mtainSavannah, hillySavannah, mtainSavannah * 3, mtainSahel,
      mtainSavannah * 4, mtainSahel * 2, mtainSavannah * 2),

    VRow(285, OrigMin(1498, HVUR), BendMax(1500, HVDn), BendIn(1504, HVUR, 13), Bend(1502, HVDL, 13, 6)),

    TRow(284, mtainSubForest, mtainSavannah, sea, mtainSub, mtainSubForest, hillySavannah, sea, Isle6(mtainSavannah), mtainSavannah * 2, hillySavannah,
      hillySahel * 4, mtainSahel, mtainSavannah * 2, mtainSahel * 3),

    VRow(283, BendIn(1494, HVUR, 13), BendOut(1500, HVDR, 7), BendIn(1502, HVUL,  13), OrigRt(1572, HVUR, 7, Lake), OrigLt(1574, HVDL, 7, Lake)),
    
    TRow(282, mtainSavannah, sea * 2, hillySub, mtainSubForest, hillySavannah, sea, hillySavannah * 2, hillySahel, mtainSahel, hillySahel, sahel,
      hillySahel * 2, mtainSahel, mtainSavannah, hillySavannah, mtainSavannah, mtainSahel * 2),

    VRow(281, OrigLt(1498, HVUR, 7), BendIn(1500, HVUL, 13)),

    TRow(280, sea * 4, mtainSub, mtainSavannah, Isle5(mtainSavannah), sea, hillySavannah, mtainSavannah * 3, savannah, hillySahel * 2, mtainSavannah,
      hillySavannah, savannah, mtainDeshot, mtainSahel * 3),

    VRow(279, BendOut(1528, HVDL)),

    TRow(278, sea * 5, mtainSavannah, sea, Isle5(mtainSavannah), sea, mtainSavannah * 4, mtainSahel, hillySavannah, hillySavannah, sahel * 3, deshot, sahel,
      mtainSahel),

    VRow(277, OrigRt(1518, HVUR, 7), BendIn(1520, HVDn, 13), Bend(1522, HVUp, 11, 7), BendIn(1524, HVDn, 13), BendIn(1526, HVDL, 13), OrigRt(1528, HVUp, 7),
      OrigLt(1532, HVDR, 7), OrigRt(1534, HVUL, 7), OrigLt(1538, HVUR, 7), BendOut(1540, HVDn, 7), ThreeDown(1542, 13, 13, 0), BendIn(1544, HVDn, 13),
      Bend(1546, HVUp, 2, 7)),

    TRow(276, sea * 6, mtainSavannah * 2, sea * 4, mtainSavannah, hillySavannah, mtainSub, hillySavannah, deshot * 2, sahel, deshot, sahel, hillyDeshot),
    VRow(275, OrigLt(1526, HVUp, 7)),
    TRow(274, sea * 13, mtainSavannah),
    )
  }
  help.run

  { import hexNames.{setRow => str}
    str(284, "" * 7, "Lesbos")
    str(280, "" * 6, "Andros")
    str(278, "" * 7, "Naxos")
  }
}