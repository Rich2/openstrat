/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France. Majorca is still just too small in area at this scale.  */
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
      TRow(168, sea * 2, Hland(3, 5, hills), sea * 2, Hland(2, 3, hillyTaiga), taiga),
      VRow(167, BendAll(522, HVDn), BendAll(524, HVDL)),
      TRow(166, sea * 2, hills, Hland(2, 1, hills), sea * 2, plain, Hland(1, 4)),
      VRow(165, Mouth(526, HVDR), Mouth(528, HVUL)),
      TRow(164, sea, Hland(3, 5), Hland(1, 4, hills), sea * 3, plain, sea),
      VRow(163, Mouth(508, HVDR)),
      TRow(162, sea, plain, sea, plain, sea, Hland(2, 5), plain * 3),
      VRow(161, BendOut(516, HVUL)),
      TRow(160, sea, Hland(4, 2), sea, plain * 2, Hland(1, 5), plain, hills, plain),
      VRow(159, BendAll(512, HVUp), BendAll(514, HVUL)),
      TRow(158, sea * 2, Hland(4, 2, hills), Hland(3, 4), plain, hills * 4),
      VRow(157, Mouth(510, HVDR)),
      TRow(156, sea * 3, Hland(1, 0), plain * 2, hills * 2, plain, hills),
      VRow(155, Mouth(506, HVUL), BendOut(508, HVDL)),
      TRow(154, sea * 4, plain * 2, hills, mtain * 3),
      VRow(153, BendIn(508, HVUR), Mouth(510, HVDR)),
      VRow(153, Mouth(530, HVUp)),
      TRow(152, sea * 4, plain, hills, mtain, hills, plain, hills),
      VRow(151, BendAll(530, HVUR), Mouth(532, HVDR)),
      TRow(150, sea, Hland(3, 4, hills), hills * 4, sea * 2, Isle(hills), hills, sea),
      TRow(148, sea * 2, plain * 4, sea * 2, Hland(4, 4, hills), sea, hills),
      TRow(146, sea, plain * 4, sea * 3, Hland(4, 1, hills), sea * 2),
      TRow(144, sea * 2, plain * 3, sea * 5, hills),
      TRow(142, sea * 3, Hland(3, 2, hills), sea * 2, hills * 4, Hland(3, 0, hills), sea),
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
{ def britGrid: EGrid220Long = EGrid220Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = Terr220E0.terrs.spawn(Terr220E0.grid, britGrid)
  def britSTerrs: HSideOptLayer[WSide, WSideSome] =Terr220E0.sTerrs.spawn(Terr220E0.grid, britGrid)
  def britCorners: HCornerLayer = Terr220E0.corners.spawn(Terr220E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  { override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid220Long = britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideOptLayer[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}