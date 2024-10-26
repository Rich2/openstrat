/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] 120km scale terrain for 15° east to 45° east, centred on 30° east.
 * [[Isle13]] 7611.551km² => 8878.113km². Crete 8450km².
 * [[Isle6]] 1473.596km² => 2058.163km². Lesbos 1633km².
 * [[Isle5]] 986.457km² => 1473.596km². Andros for northern Cyclades 2572km²/2 = 1286km², Naxos for southern Cyclades = 1286km².
 * [[Isle3]] 304.462km² => 596.745km². */
object Terr120E30 extends Long120Terrs
{ override implicit val grid: EGrid120LongFull = EGrid120.e30(274, 286)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(286, hillySub, subtrop, mtainSub, mtainSubForest, mtainSavannah, mtainSavannah, sea),
    VRow(285, OrigMin(1498, HVUR), BendMax(1500, HVDn), BendIn(1504, HVUR, 13), Bend(1502, HVDL, 13, 6)),

    TRow(284, mtainSubForest, mtainSavannah, sea, mtainSub, mtainSubForest, hillySavannah, sea, Isle6(mtainSavannah), mtainSavannah * 2, hillySavannah,
      hillySahel * 4, mtainSahel, mtainSavannah * 2, mtainSahel * 3),

    VRow(283, BendOut(1500, HVDR, 7), BendIn(1502, HVUL,  13), OrigRt(1572, HVUR, 7, Lake), OrigLt(1574, HVDL, 7, Lake)),
    TRow(282, mtainSavannah, sea * 2, hillySub, mtainSubForest, hillySavannah, sea, hillySavannah * 2, hillySahel, mtainSahel, hillySahel, sahel,
      hillySahel * 2, mtainSahel, mtainSavannah, hillySavannah, mtainSavannah, mtainSahel * 2),

    VRow(281, OrigLt(1498, HVUR, 7), BendIn(1500, HVUL, 13)),

    TRow(280, sea * 4, mtainSub, mtainSavannah, Isle5(mtainSavannah), sea, hillySavannah, mtainSavannah * 3, savannah, hillySahel * 2, mtainSavannah,
      hillySavannah, savannah, mtainDeshot, mtainSahel * 3),

    TRow(278, sea * 5, mtainSavannah, sea, Isle5(mtainSavannah)),
    VRow(277, OrigRt(1518, HVUR, 7), BendIn(1520, HVDn, 13), Bend(1522, HVUp, 11, 7), BendIn(1524, HVDn, 13), BendIn(1526, HVDL, 13)),
    TRow(276, sea * 6, mtainSavannah * 2),
    VRow(275, OrigLt(1526, HVUp, 7)),
    TRow(274, sea * 8, sea * 5, sea, sea),
    )
  }
  help.run

  { import hexNames.{setRow => str}
    str(284, "" * 7, "Lesbos")
    str(280, "" * 6, "Andros")
    str(278, "" * 7, "Naxos")
  }
}