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
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(344, sea * 9, mtainOld),
      TRow(342, sea * 10, mtainOld) ,
      TRow(340, sea * 10, mtainOld),
      TRow(338, sea * 9, hillyTundra * 2),
      TRow(336, sea * 9, hillyTaiga * 2, taiga),
      TRow(334, sea * 8, mtainOld, hillyTaiga * 3),
      TRow(332, sea * 3, Isle10(hilly), sea * 4, mtainOld, mtainOld, hillyTaiga, taiga * 2),
      TRow(330, sea * 5, Isle10(hilly), sea * 2, mtainOld * 4, hillyTaiga),
      TRow(328, sea * 6, sea * 3, mtainOld, hillyTaiga * 3),
      TRow(326, sea * 5, Isle10(hilly), sea * 3, hillyTaiga, hillyTaiga, taiga * 3),
      VRow(325, BendOut(530, HVDR)),
      TRow(324, sea * 3, level, hilly, sea * 4, hillyTaiga, hillyTaiga, sea, level * 2),
      VRow(323, ThreeDown(528, 13, 10, 0), Bend(530, HVUL, 13, 7), MouthRt(534, HVUL), BendIn(536, HVDL, 13)),
      TRow(322, sea * 3, hilly, hilly * 2, sea * 5, level * 2, level * 2),
      TRow(320, sea * 4, level * 2, sea * 5, level, level, sea, level),
      VRow(319, MouthOld(536, HVUp), MouthOld(538, HVUL), MouthOld(540, HVDR)),
      TRow(318, sea * 3, Cape(5, 3), Cape(4, 1, hilly), hilly, sea * 5, level, level, level, sea),
      VRow(317, MouthOld(536, HVDn)),
      TRow(316, sea * 2, Cape(5, 2), level, Cape(1, 3), Cape(3, 2, hilly), level, sea * 5, level, sea * 3),
      VRow(315, MouthOld(498, HVUL), MouthOld(506, HVUR)),
      TRow(314, sea * 2, Cape(4, 2), level, Cape(1, 2), Cape(4, 3, hilly), level * 2, sea * 3, Cape(5, 2), level * 4),
      TRow(312, sea * 2, level * 2, sea, hilly, level * 2, Cape(0, 3), sea, level * 6),
      TRow(310, sea * 2, Cape(2, 4), sea * 2, Cape(3, 2, hilly), level * 2, Cape(2, 1), sea, level * 3, hilly * 2, level * 2),
      VRow(309, MouthOld(504, HVUR)),
      TRow(308, sea * 5, Cape(4, 3, hilly), Cape(3, 1, hilly), Cape(3, 1), Cape(2, 2), Cape(5, 2), level * 2, hilly * 5),
      TRow(306, sea * 8, Cape(5, 3), Cape(5, 1), level * 2, hilly * 6),
      VRow(305, MouthOld(512, HVDn)),
      TRow(304, sea * 6, level * 5, hilly * 4, level, hilly * 2),
      TRow(302, sea * 6, Cape(3, 3), level * 4, hilly * 2, hillyForest, hilly * 2, mtainOld * 2),
      TRow(300, sea * 8, level * 4, hilly * 2, mtainOld * 4, hilly),
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