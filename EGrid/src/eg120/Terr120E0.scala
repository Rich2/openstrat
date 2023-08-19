/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid.phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. The Faroe Islands and the Shetland Islands are large enough to qualify as an island. The Orkney's are
 *  probably not, even with the mainland that comes into the hex, but for the sake of Scapa FLow they will be an [[Isle]].  */
object Terr120E0 extends Long120Terrs
{ override implicit val grid: EGrid120LongFull = EGrid120.e0(300)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(344, sea * 9, Hland(2, 4, Mountains())),
      TRow(342, sea * 10, mtain) ,
      TRow(340, sea * 10, mtain),
      TRow(338, sea * 9, hillyTundra * 2),
      TRow(336, sea * 9, hillyTaiga * 2, taiga),
      TRow(334, sea * 8, Hland(2, 5, Mountains()), hillyTaiga * 3),
      TRow(332, sea * 3, Isle(Hilly), sea * 4, Hland(2, 4, Mountains()), mtain, hillyTaiga, taiga * 2),
      TRow(330, sea * 5, Isle(Hilly), sea * 2, mtain * 4, hillyTaiga),
      TRow(328, sea * 6, sea * 3, mtain, hillyTaiga * 3),
      TRow(326, sea * 5, Isle(Hilly), sea * 3, Hland(2, 4, Hilly(Taiga)), hillyTaiga, taiga * 3),
      TRow(324, sea * 3, plain, hills, sea * 4, Hland(2, 3, Hilly(Taiga)), Hland(2, 2, Hilly(Taiga)), sea, plain * 2),
      TRow(322, sea * 3, hills, hills * 2, sea * 5, plain, sea, plain * 2),
      TRow(320, sea * 4, plain * 2, sea * 5, plain * 2, sea, plain),
      VRow(319, Mouth(536, HVUp), Mouth(538, HVUL), Mouth(540, HVDR)),
      TRow(318, sea * 3, Hland(3, 5), Hland(1, 4, Hilly()), hills, sea * 5, plain, plain, plain, sea),
      VRow(317, Mouth(536, HVDn)),
      TRow(316, sea * 2, Hland(2, 5), plain, Hland(3, 1), Hland(2, 3, Hilly()), plain, sea * 5, plain, sea * 3),
      VRow(315, Mouth(498, HVUL), Mouth(506, HVUR)),
      TRow(314, sea * 2, Hland(2, 4), plain, Hland(2, 1), Hland(3, 4, Hilly()), plain * 2, sea * 3, Hland(2, 5), plain * 4),
      TRow(312, sea * 2, plain * 2, sea, hills, plain * 2, Hland(3, 0), sea, plain * 6),
      TRow(310, sea * 2, Hland(4, 2), sea * 2, Hland(2, 3, Hilly()), plain * 2, Hland(1, 2), sea, plain * 3, hills * 2, plain * 2),
      VRow(309, Mouth(504, HVUR)),
      TRow(308, sea * 5, Hland(3, 4, Hilly()), Hland(1, 3, Hilly()), Hland(1, 3), Hland(2, 2), Hland(2, 5), plain * 2, hills * 5),
      TRow(306, sea * 8, Hland(3, 5), Hland(1, 5), plain * 2, hills * 6),
      VRow(305, Mouth(512, HVDn)),
      TRow(304, sea * 6, plain * 5, hills * 4, plain, hills * 2),
      TRow(302, sea * 6, Hland(3, 3), plain * 4, hills * 2, hillyForest, hills * 2, mtain * 2),
      TRow(300, sea * 8, plain * 4, hills * 2, mtain * 4, hills),
    )
  }
  help.run
}

object BritReg120
{ implicit def britGrid: EGrid120Long = EGrid120Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = Terr120E0.terrs.spawn(Terr120E0.grid)
  def britSTerrs: HSideOptLayer[WSide, WSideSome] = Terr120E0.sTerrs.spawn(Terr120E0.grid, britGrid)
  def britCorners: HCornerLayer = Terr120E0.corners.spawn(Terr120E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid120Long = britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideOptLayer[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}