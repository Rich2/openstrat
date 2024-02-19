/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTiles._

/** 220km [[WTile]] terrain for 15° west to 15° east centred on 0° east. A tile area of 41915.629km². Sicily is ~75% of a tile.
 * A minimum island size of 27670.864km².
 *  Isle 13262.367km² <= 27670.864km².
 *  Isle8 8022.913km² <= 13262.367km²
 *  Isle6 4952.921km² <= 8022.913km² Zealand 7180km² is large enough to qualify, but shares its hex with Jutland.
 *  Isle5 3315.591km² <= 4952.921km² Mallorca 3640km².
 *  Isle4 2005.728km² <= 3315.591km² Outer Hebrides 3071km².
 *  Isle3 1023.330km² <= 2005.728km² Faroe Islands 1,399 km²
 *  Smaller Isle of Man. */
object Terr220E0 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e0(132, 202)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(202, SeaIcePerm),
      TRow(200, SeaIcePerm),
      TRow(198, SeaIcePerm),
      TRow(192, sea, mtain),
      VRow(179, MouthLt(520, HVUp, 7)),
      TRow(178, sea * 4, hillyTaiga),
      VRow(177, MouthRt(520, HVDn, 7)),
      TRow(176, sea * 5, hillyTaiga),
      VRow(175, BendIn(516, HVDR, 13), BendIn(518, HVDn, 13), MouthLt(520, HVDR)),
      TRow(174, sea * 4, mtain, hillyTaiga),
      VRow(173, BendIn(514, HVDR, 13), BendOut(516, HVUL, 7)),
      TRow(172, sea, Isle3(mtain), sea * 2, mtain, hillyTaiga, taiga),
      VRow(171, BendIn(508, HVDR, 13), BendIn(510, HVDn, 13), BendIn(512, HVDL, 13), BendIn(514, HVUR, 13), BendOut(516, HVDL)),
      TRow(170, sea * 2, Land(Hilly, Taiga, CivMix), sea, hillyTaiga, taiga * 2),

      VRow(169, BendIn(502, HVDR, 13), BendIn(504, HVDn, 13), ThreeDown(506, 0, 6, 6), ThreeUp(508, 7, 7, 0), ThreeDown(510, 7, 0, 7), BendIn(512, HVUL, 13),
        MouthRt(516, HVDn, 7)),

      TRow(168, sea, hillyTundra, hilly, sea * 2, hillyTaiga, taiga),

      VRow(167, BendIn(502, HVUR, 13), ThreeDown(504, 10, 6, 0), BendIn(506, HVUL, 6), BendIn(512, HVDL, 13), MouthLt(518, HVUL, 7), ThreeDown(520, 9, 10, 0),
        BendAllOld(522, HVDn), BendAllOld(524, HVDL)),

      TRow(166, sea * 2, hilly * 2, sea * 2, land, land),

      VRow(165, MouthRt(502, HVDL), ThreeUp(504, 6, 9, 0), BendIn(506, HVDL, 7), BendOut(512, HVUR, 7), BendIn(514, HVDL, 13), MouthRt(520, HVDn, 7),
        BendIn(524, HVUR, 13), MouthOld(526, HVDR), MouthOld(530, HVDL, 7)),

      TRow(164, sea, land, hilly, land, sea * 2, land, sea),

      VRow(163, BendIn(504, HVDR, 13), ThreeUp(506, 3, 13, 3), MouthOld(508, HVDR), BendOut(514, HVUR, 7), ThreeDown(516, 0, 13, 13), BendIn(518, HVDn, 13),
        Bend(520,HVUp, 3, 3), MouthLt(522, HVUR, 7)),

      TRow(162, sea, land, hilly, land * 2, land * 4),
      VRow(161, MouthLt(498, HVUp), BendIn(504, HVUR, 13), MouthOld(506, HVDR), BendIn(514, HVDR, 13), Bend(516, HVUL, 13, 7)),
      TRow(160, sea, land, sea, land * 2, land * 2, hilly, land),

      VRow(159, BendIn(498, HVUR, 13), BendIn(500, HVUp, 13), MouthRt(502, HVUR, 7), BendIn(504, HVDR, 6), MouthOld(506, HVUR), BendIn(508, HVDR, 12),
        BendIn(510, HVDn, 13), Bend(512, HVUp, 1, 7), Bend(514, HVUL, 3, 3)),

      TRow(158, sea * 2, hilly, land * 2, hilly * 4),
      VRow(157, BendIn(504, HVUR, 13), BendIn(506, HVUp, 13), Bend(508, HVUL, 13, 7)),
      TRow(156, sea * 3, land * 3, hilly * 2, land, hilly),
      VRow(155, MouthOld(506, HVUL), BendOut(508, HVDL)),
      TRow(154, sea * 4, land * 2, hilly, mtain * 3),
      VRow(153, BendIn(508, HVUR), MouthOld(510, HVDR)),
      VRow(153, MouthOld(530, HVUp)),
      TRow(152, sea * 4, land, hilly, mtain, hilly, land, hilly),
      VRow(151, BendIn(530, HVUR, 13), BendOut(532, HVDL, 7)),
      TRow(150, sea, Cape(4, 3, hilly), hilly * 4, hilly, Cape(2, 2, hilly), Cape(4, 5, hilly), hilly, mtain),
      VRow(149, BendOut(518, HVDR), BendOut(520, HVDn), BendOut(526, HVUp), BendIn(532, HVUR, 13), BendIn(534, HVUp, 13), Bend(536, HVDn, 6, 7)),
      TRow(148, sea * 2, mtain, sahel, hillySavannah * 3, sea, Cape(4, 3, hilly), sea, hilly),
      VRow(147, MouthLt(514, HVUL), ThreeDown(516, 13, 6, 0), ThreeUp(518, 0, 6, 13), BendOut(524, HVDL), MouthLt(530, HVUL, 7), MouthRt(532, HVDR, 7)),
      TRow(146, sea, hillySavannah, hilly, hillySavannah * 2, sea, hillySavannah, sea, Cape(1, 4, hilly), sea * 2),
      //VRow(145, BendIn(516, HVUR, 10), BendIn(518, HVUp, 10), BendIn(520, HVUL, 10)),
      TRow(144, sea * 2, savannah, hillySavannah, hillySahel, sea * 5, hillySavannah),
      VRow(143, MouthLt(500, HVUp, 7), BendIn(508, HVDR, 13), BendIn(510, HVDn, 13), BendOut(512, HVUp, 7), MouthLt(514, HVUR, 7)),
      TRow(142, sea * 3, hilly, sea, hillySavannah * 6, sea),
      VRow(141, ThreeUp(500, 13, 13, 0), BendIn(502, HVUp, 13), MouthRt(504, HVUR, 7), MouthRt(508, HVDn, 7), MouthOld(530, HVDL)),
      TRow(140, sea * 2, hillySavannah, hilly, hillyDesert * 2, desert * 2, hillyDesert * 2, sea * 2),
      TRow(138, sea * 2, land, desert, hillyDesert, desert * 7),
      TRow(136, sea * 2, mtain * 3, desert * 8),
      TRow(134, sea, hillyDesert * 2, desert * 10),
      TRow(132, sea, desert * 12)
    )
  }
  help.run
}

object BritReg220
{ def britGrid: EGrid220Long = EGrid220Long.reg(156, 170, 0, 500, 520)
  def britTerrs: LayerHcRefSys[WTile] = Terr220E0.terrs.spawn(Terr220E0.grid, britGrid)
  def britSTerrs: LayerHSOptSys[WSep, WSepSome] = Terr220E0.sTerrs.spawn(Terr220E0.grid, britGrid)
  def britCorners: HCornerLayer = Terr220E0.corners.spawn(Terr220E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  { override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid220Long = britGrid
    override val terrs: LayerHcRefSys[WTile] = britTerrs
    override val sTerrs: LayerHSOptSys[WSep, WSepSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
    override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
  }
}