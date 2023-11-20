/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° west to 15° east centred on 0° east. A tile area of 34294.605km². Sicily is ~75% of a tile. A minimum island size of 1/6
 *  5715.767km². At 3071km² the Outer Hebrides are too small. Zealand is large enough to qualify, but shares its hex with Jutland. Mallorca is too
 *  small. */
object Terr220E0 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e0(132, 202)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(202, sice),
      TRow(200, sice),
      TRow(198, sice),
      TRow(178, sea * 4, Cape.a(4, 2, hillyTaiga)),
      TRow(176, sea * 5, hillyTaiga),
      TRow(174, sea * 5, hillyTaiga),
      TRow(172, sea * 5, hillyTaiga, taiga),
      TRow(170, sea * 4, hillyTaiga, taiga * 2),
      VRow(169, Mouth(506, HVDL)),
      TRow(168, sea * 2, Cape.a(0, 2, hilly), sea * 2, Cape.a(3, 2, hillyTaiga), taiga),
      VRow(167, Mouth(510, HVDn), BendAll(522, HVDn), BendAll(524, HVDL)),
      TRow(166, sea * 2, hilly, sea, sea * 2, land, Cape(1, 4)),
      VRow(165, Mouth(526, HVDR), Mouth(528, HVUL)),
      TRow(164, sea, Cape(3, 5), Cape(1, 4, hilly), sea * 3, land, sea),
      VRow(163, Mouth(508, HVDR)),
      TRow(162, sea, land, Cape(3, 4, hilly), land, sea, Cape(2, 5), land * 3),
      VRow(161, Mouth(506, HVDR), BendOut(516, HVUL)),
      TRow(160, sea, Cape(4, 2), sea, land * 2, Cape(1, 5), land, hilly, land),
      VRow(159, Mouth(506, HVUR), BendAll(512, HVUp), BendAll(514, HVUL)),
      TRow(158, sea * 2, Cape(4, 2, hilly), Cape(3, 4), land, hilly * 4),
      VRow(157, Mouth(510, HVDR)),
      TRow(156, sea * 3, Cape(1, 0), land * 2, hilly * 2, land, hilly),
      VRow(155, Mouth(506, HVUL), BendOut(508, HVDL)),
      TRow(154, sea * 4, land * 2, hilly, mtain * 3),
      VRow(153, BendIn(508, HVUR), Mouth(510, HVDR)),
      VRow(153, Mouth(530, HVUp)),
      TRow(152, sea * 4, land, hilly, mtain, hilly, land, Cape(1, 4, hilly)),
      VRow(151, Mouth(532, HVDR)),
      TRow(150, sea, Cape(3, 4, hilly), hilly * 4, hilly, Cape(2, 2, hilly), Cape(5, 4, hilly), hilly, sea),
      VRow(149, BendOut(518, HVDR), BendOut(520, HVDn), BendOut(526, HVUp)),
      TRow(148, sea * 2, land * 4, Cape(2, 2, hilly), sea, Cape(3, 4, hilly), sea, hilly),
      VRow(147, Mouth(514, HVUL), BendOut(524, HVDL)),
      TRow(146, sea, land * 4, sea * 3, Cape(4, 1, hilly), sea * 2),
      TRow(144, sea * 2, land * 3, sea * 5, Isle(hilly)),
      VRow(143, Mouth(512, HVDL), Mouth(516, HVDR)),
      TRow(142, sea * 3, Cape(3, 2, hilly), sea * 2, Cape(1, 0, hilly), hilly * 3, Cape(2, 1, hilly), sea),
      VRow(141, Mouth(530, HVDL)),
      TRow(140, sea * 2, Cape(2, 5, hilly), hilly, hillyDesert * 2, desert * 2, hillyDesert * 2, sea * 2),
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
  def britTerrs: HCenLayer[WTile] = Terr220E0.terrs.spawn(Terr220E0.grid, britGrid)
  def britSTerrs: HSideOptLayer[WSide, WSideSome] = Terr220E0.sTerrs.spawn(Terr220E0.grid, britGrid)
  def britCorners: HCornerLayer = Terr220E0.corners.spawn(Terr220E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  { override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid220Long = britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideOptLayer[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}