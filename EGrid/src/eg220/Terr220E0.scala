/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15° west to 15° east centred on 0° east. A minimum island size of 1/6 5715.767km2. At 3071km² the Outer Hebrides are too
 * small. Zealand is large enough to qualify, but shares its hex with Jutland. Mallorca is too small. */
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
      TRow(178, sea * 4, Hland(2, 4, hillyTaiga)),
      TRow(176, sea * 5, hillyTaiga),
      TRow(174, sea * 5, hillyTaiga),
      TRow(172, sea * 5, hillyTaiga, taiga),
      TRow(170, sea * 4, hillyTaiga, taiga * 2),
      VRow(169, Mouth(506, HVDL)),
      TRow(168, sea * 2, Hland(2, 0, hills), sea * 2, Hland(2, 3, hillyTaiga), taiga),
      VRow(167, Mouth(510, HVDn), BendAll(522, HVDn), BendAll(524, HVDL)),
      TRow(166, sea * 2, hills, sea, sea * 2, plain, Hland(1, 4)),
      VRow(165, Mouth(526, HVDR), Mouth(528, HVUL)),
      TRow(164, sea, Hland(3, 5), Hland(1, 4, hills), sea * 3, plain, sea),
      VRow(163, Mouth(508, HVDR)),
      TRow(162, sea, plain, Hland(3, 4, hills), plain, sea, Hland(2, 5), plain * 3),
      VRow(161, Mouth(506, HVDR), BendOut(516, HVUL)),
      TRow(160, sea, Hland(4, 2), sea, plain * 2, Hland(1, 5), plain, hills, plain),
      VRow(159, Mouth(506, HVUR), BendAll(512, HVUp), BendAll(514, HVUL)),
      TRow(158, sea * 2, Hland(4, 2, hills), Hland(3, 4), plain, hills * 4),
      VRow(157, Mouth(510, HVDR)),
      TRow(156, sea * 3, Hland(1, 0), plain * 2, hills * 2, plain, hills),
      VRow(155, Mouth(506, HVUL), BendOut(508, HVDL)),
      TRow(154, sea * 4, plain * 2, hills, mtain * 3),
      VRow(153, BendIn(508, HVUR), Mouth(510, HVDR)),
      VRow(153, Mouth(530, HVUp)),
      TRow(152, sea * 4, plain, hills, mtain, hills, plain, Hland(1, 4, hills)),
      VRow(151, Mouth(532, HVDR)),
      TRow(150, sea, Hland(3, 4, hills), hills * 4, hills, Hland(2, 2, hills), Hland(5, 4, hills), hills, sea),
      VRow(149, BendOut(518, HVDR), BendOut(520, HVDn), BendOut(526, HVUp)),
      TRow(148, sea * 2, plain * 4, Hland(2, 2, hills), sea, Hland(3, 4, hills), sea, hills),
      VRow(147, Mouth(514, HVUL), BendOut(524, HVDL)),
      TRow(146, sea, plain * 4, sea * 3, Hland(4, 1, hills), sea * 2),
      TRow(144, sea * 2, plain * 3, sea * 5, Hland(3, 3, hills)),
      VRow(143, Mouth(512, HVDL), Mouth(516, HVDR)),
      TRow(142, sea * 3, Hland(3, 2, hills), sea * 2, Hland(1, 0, hills), hills * 3, Hland(2, 1, hills), sea),
      TRow(140, sea * 2, Hland(2, 5, hills), hills, hillyDesert * 2, desert * 2, hillyDesert * 2, sea * 2),
      TRow(138, sea * 2, plain, desert, hillyDesert, desert * 7),
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