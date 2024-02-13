/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(202, SeaIcePerm),
      TRow(200, SeaIcePerm),
      TRow(198, SeaIcePerm),
      TRow(178, sea * 4, Cape(4, 2, hillyTaiga)),
      TRow(176, sea * 5, hillyTaiga),
      TRow(174, sea * 5, hillyTaiga),
      TRow(172, sea, Isle3(mtain), sea * 2, mtain, hillyTaiga, taiga),
      VRow(171, BendIn(508, HVDR, 13), BendIn(510, HVDn, 13), BendIn(512, HVDL, 13)),
      TRow(170, sea * 2, Land(Hilly, Taiga, CivMix), sea, hillyTaiga, taiga * 2),
      VRow(169, BendIn(502, HVDR, 13), BendIn(504, HVDn, 13), ThreeDown(506, 0, 6, 6), ThreeUp(508, 7, 7, 0), ThreeDown(510, 7, 0, 7), BendIn(512, HVUL, 13)),
      TRow(168, sea, hillyTundra, hilly, sea * 2, Cape(3, 2, hillyTaiga), taiga),
      VRow(167, BendIn(502, HVUR, 13), ThreeDown(504, 10, 6, 0), BendIn(506, HVUL, 6), BendIn(512, HVDL, 13), BendAll(522, HVDn), BendAll(524, HVDL)),
      TRow(166, sea * 2, hilly * 2, sea * 2, land, Cape(4, 1)),
      VRow(165, ThreeUp(504, 6, 13, 0), BendIn(506, HVDL), Mouth(510, HVDL), BendIn(512, HVUL, 13), Mouth(526, HVDR), Mouth(528, HVUL)),
      TRow(164, sea, land, hilly, sea * 3, land, sea),
      VRow(163, BendIn(504, HVDR, 13), Mouth(508, HVDR)),
      TRow(162, sea, land, hilly, land, sea, Cape(5, 2), land * 3),
      VRow(161, MouthLt(498, HVUp), BendIn(504, HVUR, 13), Mouth(506, HVDR), BendOut(516, HVUL)),
      TRow(160, sea, land, sea, land * 2, Cape(5, 1), land, hilly, land),
      VRow(159, BendIn(498, HVUR, 13), BendIn(500, HVUp, 13), MouthRt(502, HVUR, 7), Mouth(506, HVUR), BendAll(512, HVUp), BendAll(514, HVUL)),
      TRow(158, sea * 2, Cape(2, 4, hilly), Cape(4, 3), land, hilly * 4),
      VRow(157, Mouth(510, HVDR)),
      TRow(156, sea * 3, Cape(0, 1), land * 2, hilly * 2, land, hilly),
      VRow(155, Mouth(506, HVUL), BendOut(508, HVDL)),
      TRow(154, sea * 4, land * 2, hilly, mtain * 3),
      VRow(153, BendIn(508, HVUR), Mouth(510, HVDR)),
      VRow(153, Mouth(530, HVUp)),
      TRow(152, sea * 4, land, hilly, mtain, hilly, land, Cape(4, 1, hilly)),
      VRow(151, Mouth(532, HVDR)),
      TRow(150, sea, Cape(4, 3, hilly), hilly * 4, hilly, Cape(2, 2, hilly), Cape(4, 5, hilly), hilly, sea),
      VRow(149, BendOut(518, HVDR), BendOut(520, HVDn), BendOut(526, HVUp)),
      TRow(148, sea * 2, land * 4, Cape(2, 2, hilly), sea, Cape(4, 3, hilly), sea, hilly),
      VRow(147, Mouth(514, HVUL), BendOut(524, HVDL)),
      TRow(146, sea, land * 4, sea * 3, Cape(1, 4, hilly), sea * 2),
      TRow(144, sea * 2, land * 3, sea * 5, Isle(hilly)),
      VRow(143, Mouth(512, HVDL), Mouth(516, HVDR)),
      TRow(142, sea * 3, Cape(2, 3, hilly), sea * 2, Cape(0, 1, hilly), hilly * 3, Cape(1, 2, hilly), sea),
      VRow(141, Mouth(530, HVDL)),
      TRow(140, sea * 2, Cape(5, 2, hilly), hilly, hillyDesert * 2, desert * 2, hillyDesert * 2, sea * 2),
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
  }
}