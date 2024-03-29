/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15 West to 15 East. The Faroe Islands and the Shetland Islands are large enough to qualify as an island. The Orkney's are
 *  probably not, even with the mainland that comes into the hex, but for the sake of Scapa FLow they will be an [[Isle]].  */
object Terr120E0 extends Long120Terrs
{ override implicit val grid: EGrid120LongFull = EGrid120.e0(300)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()


  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(344, sea * 9, mtainOld),
    TRow(342, sea * 10, mtainOld) ,
    TRow(340, sea * 10, mtainOld),
    TRow(338, sea * 9, hillyTundra * 2),
    TRow(336, sea * 9, hillyTaiga * 2, taiga),
    TRow(334, sea * 8, mtainOld, hillyTaiga * 3),
    TRow(332, sea * 3, Isle10(hillyOce), sea * 4, mtainOld, mtainOld, hillyTaiga, taiga * 2),
    TRow(330, sea * 5, Isle10(hillyOce), sea * 2, mtainOld * 4, hillyTaiga),
    TRow(328, sea * 6, sea * 3, mtainOld, hillyTaiga * 3),
    TRow(326, sea * 5, Isle10(hillyOce), sea * 3, hillyTaiga, hillyTaiga, taiga * 3),
    VRow(325, BendOut(530, HVDR)),
    TRow(324, sea * 3, oceanic, hillyOce, sea * 4, hillyTaiga, hillyTaiga, sea, oceanic * 2),
    VRow(323, ThreeDown(528, 13, 10, 0), Bend(530, HVUL, 13, 7), MouthRt(534, HVUL), BendIn(536, HVDL, 13)),
    TRow(322, sea * 3, hillyOce, hillyOce * 2, sea * 5, oceanic * 2, oceanic * 2),
    TRow(320, sea * 4, oceanic * 2, sea * 5, oceanic, oceanic, sea, oceanic),
    VRow(319, BendIn(492, HVDR, 13), BendIn(494, HVDn, 13), BendOut(496, HVUp), BendIn(498, HVDn, 13), BendIn(500, HVDL, 13)),
    TRow(318, sea * 2, Land(MountLakes, Oceanic), oceanic, hillyOce, hillyOce, sea * 5, oceanic, oceanic, oceanic, sea),
    VRow(317, SourceRt(490, HVUR, 7), BendOut(492, HVUL, 7), Bend(500, HVUR, 4, 2), BendIn(502, HVDL, 13)),
    TRow(316, sea * 2, oceanic, oceanic * 2, hillyOce, oceanic, sea * 5, oceanic, sea * 3),
    VRow(315, BendMax(500, HVDR), ThreeUp(502, 13, 13, 13), SourceMax(504, HVUL)),
    TRow(314, sea * 2, oceanic, oceanic * 2, hillyOce, oceanic * 2, sea * 3, CapeOld(5, 2), oceanic * 4),
    VRow(313, SourceMax(500, HVUp)),
    TRow(312, sea, hillyOce, oceanic * 2, sea, hillyOce, oceanic * 3, sea, oceanic * 6),
    VRow(311, SourceRt(486, HVUp, 7), SourceRt(496, HVDn, 7)),
    TRow(310, sea * 2, hillyOce * 2, sea, hillyOce, oceanic * 3, sea, oceanic * 3, hillyOce * 2, oceanic * 2),

    VRow(309, SourceLt(488, HVDR, 7), BendIn(490, HVUp, 13), BendOut(492, HVDn, 7), BendIn(494, HVUp, 13), BendIn(496, HVUL, 13), BendIn(498, HVDR, 13),
      BendInRt(500, HVDn, 13, 7), BendIn(502, HVUp, 13), SourceRt(504, HVDL)),

    TRow(308, sea * 5, hillyOce * 2, CapeOld(3, 1), CapeOld(2, 2), CapeOld(5, 2), oceanic * 2, hillyOce * 5),
    VRow(307, SourceRt(498, HVUp)),
    TRow(306, sea * 8, CapeOld(5, 3), CapeOld(5, 1), oceanic * 2, hillyOce * 6),
    VRow(305, MouthOld(512, HVDn)),
    TRow(304, sea * 6, oceanic * 5, hillyOce * 4, oceanic, hillyOce * 2),
    TRow(302, sea * 6, CapeOld(3, 3), oceanic * 4, hillyOce * 2, hillyOceForest, hillyOce * 2, mtainOld * 2),
    TRow(300, sea * 8, oceanic * 4, hillyOce * 2, mtainOld * 4, hillyOce),
    )
  }
  help.run
}

object BritReg120
{ implicit def britGrid: EGrid120Long = EGrid120Long.reg(138, 148, 0, 504, 520)
  def britTerrs: LayerHcRefSys[WTile] = Terr120E0.terrs.spawn(Terr120E0.grid)
  def britSTerrs: LayerHSOptSys[WSep, WSepSome] = Terr120E0.sTerrs.spawn(Terr120E0.grid, britGrid)
  def britCorners: HCornerLayer = Terr120E0.corners.spawn(Terr120E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid120Long = britGrid
    override val terrs: LayerHcRefSys[WTile] = britTerrs
    override val sTerrs: LayerHSOptSys[WSep, WSepSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
    override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
  }
}