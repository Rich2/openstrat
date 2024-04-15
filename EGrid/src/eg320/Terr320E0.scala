/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** 320km [[WTile]] terrain for 15° west to 15° east, centred on 0° east. Hex tile scale of 320km.
 * [[Tile9]] 25028.134km² => 31263.517km². Sicily 25711km².
 * [[Tile8]] 19485.571km² => 25028.134km². Sardinia 24090km².
 * [[Tile5]] 7014.805km² => 10478.907km². Canaries 7492km², Corsica 8722km².
 * [[Tile4]] 4243.524km² => 7014.805km². Balearic Islands 5040km². */
object Terr320E0 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e0(118)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(166, SeaIcePerm),
    TRow(164, SeaIceWinter),
    TRow(162, SeaIceWinter * 2),
    TRow(160, SeaIceWinter),
    VRow(157, OrigRt(518, HVUR, 7), OrigLt(520, HVDL, 7)),
    TRow(156, sea * 3),
    TRow(154, sea * 4),
    TRow(152, sea * 3, hillyTaiga),
    VRow(151, BendIn(506, HVUL)),
    TRow(150, sea * 3, hillyTaiga),
    VRow(149, OrigLt(514, HVDn, 7)),
    TRow(148, sea * 3, hillyTaiga, taiga),

    VRow(147, BendIn(504, HVDR, 13), OrigLt(506, HVDL, 7), OrigRt(512, HVDn, 7), OrigRt(514, HVUp, 7), BendIn(516, HVDR, 13), BendIn(518, HVDn, 13),
      Bend(520, HVDL, 5, 1), Bend(524, HVDR, 13, 3)),

    TRow(146, mtainDepr, hillyOce, sea, oceanic, oceForest),

    VRow(145, BendIn(502, HVDR, 13), ThreeUp(504, 13, 13, 0), BendOut(506, HVDL, 7), BendOut(512, HVUR, 7), BendIn(514, HVDL, 13), BendIn(516, HVUR, 13),
      OrigMin(518, HVUL, 4), BendIn(520, HVUR), BendOut(522, HVUp, 7), Bend(524, HVUL, 2, 7)),

    TRow(144, hillyOce, oceanic, oceanic, sea, oceanic),
    VRow(143, OrigRt(502, HVUp, 7), BendIn(506, HVUR, 13), BendIn(508, HVDL, 7), BendOut(514, HVUR, 7), BendIn(516, HVDL, 13)),
    TRow(142, sea, oceanic, oceanic, oceanic, oceanic * 2),
    VRow(141, Orig(506, HVUR, 7, 7), BendIn(508, HVUL, 11), BendIn(510, HVDR, 13), BendIn(512, HVDn), BendIn(514, HVUp), BendIn(516, HVUL, 13)),
    TRow(140, sea, hillyOce, oceanic, oceanic * 3),
    VRow(139, BendIn(506, HVUR, 13), BendIn(508, HVUp, 13), BendIn(510, HVUL, 13)),
    TRow(138, sea * 2, oceanic * 2, hillyOce, mtainDepr * 2),
    VRow(137, OrigRt(526, HVDn)),
    TRow(136, sea * 3, oceanic, hillyOce, mtainDepr, oceanic),

    VRow(135, BendIn(500, HVDR, 13), OrigLt(502, HVDL, 7), OrigMin(516, HVDn), Bend(520, HVDR, 11, 5), BendIn(522, HVDn, 11), BendIn(524, HVDL, 13),
      BendOut(526, HVUR, 7), BendIn(528, HVDL, 13)),

    TRow(134, hillyOce * 3, hillySub, mtainSavannah, mtainSavannah, hillySavannah),

    VRow(133, BendIn(500, HVUR, 13), Bend(514, HVDR, 12, 6), ThreeUp(516, 13, 12, 6), ThreeDown(518, 13, 8, 12), ThreeUp(520, 11, 8, 13),
      ThreeDown(522, 11, 0, 8), BendIn(524, HVUL, 11)),

    TRow(132, sea, hillySub, hillySavannah * 2, hillySavannah, mtainSavannah, sea),

    VRow(131, OrigLt(500, HVDn, 7), BendIn(512, HVDR, 13), ThreeUp(514, 12, 13, 13), Bend(516, HVUp, 12, 7), ThreeUp(518, 8, 13, 12), Bend(520, HVUp, 8, 7),
      ThreeUp(522, 0, 13, 8), BendInRt(524, HVDL, 11, 7)),// OrigSpecRevDepr(526, HVUL, HVRt, HVLt)),

    TRow(130, sea, hillyTrop, hillySavannah * 2, hillySavannah, mtainSavannah, hillySavannah, hillySavannah),

    VRow(129, BendIn(500, HVUR, 13), ThreeDown(502, 13, 13, 0), Bend(504, HVDn, 13, 1), Bend(506, HVUp, 4, 2), Bend(508, HVDn, 13, 1), Bend(510, HVUp, 9, 6),
      Bend(512, HVUL, 11, 4), BendIn(524, HVUR, 13), ThreeDown(526, 13, 0, 11), ThreeUp(528, 10, 6, 0)),

    TRow(128, sea, hillySub * 4, hillySahel, hillySavannah, sea),
    VRow(127, BendIn(500, HVDR, 10), BendOut(502, HVUL), BendOut(526, HVUR, 7), OrigLt(528, HVUL, 7)),
    TRow(126, sea, sahel, mtainDepr, hillyDeshot, sahel * 4),
    VRow(125, BendIn(498, HVDR, 13), BendMin(500, HVUL)),
    TRow(124, sea, hillyDeshot, hillyDeshot, deshot * 6),
    VRow(123, BendIn(494, HVDn, 11), ThreeDown(496, 0, 7, 11), BendOut(498, HVUL)),
    TRow(122, SepB(), deshot, deshot * 8),
    VRow(121, BendIn(494, HVUp, 11), BendIn(496, HVUL, 11)),
    TRow(120, deshot * 5, hillyDeshot * 2, deshot * 2),
    TRow(118, deshot * 9),
    )
  }
  help.run

  { import hexNames.{setRow => str}
    str(140, "", "English West Country", "" * 4)
    str(136, "" * 3, "Gascogne")
    str(134, "" * 4, "Provence", "Corsica")
    str(132, "" * 4, "Balearics", "Sardinia")
    str(130, "" * 7, "Sicily")
  }
}

object BritReg320
{ def britGrid: EGrid320Long = EGrid320Long.reg(138, 148, 0, 504, 520)
  def britTerrs: LayerHcRefSys[WTile] = Terr320E0.terrs.spawn(Terr320E0.grid, britGrid)
  def britSTerrs: LayerHSOptSys[WSep, WSepSome] =Terr320E0.sTerrs.spawn(Terr320E0.grid, britGrid)
  def britCorners: HCornerLayer =Terr320E0.corners.spawn(Terr320E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid320Long = britGrid
    override val terrs: LayerHcRefSys[WTile] = britTerrs
    override val sTerrs: LayerHSOptSys[WSep, WSepSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
    override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
  }
}