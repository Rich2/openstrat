/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

/** 160km terrain for 0 degrees east. Majorca is big enough at this scale to qualify as Island. Lesbos is not. */
object Terr160E0 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e0(262)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(310, sea * 6, CapeOld(4, 3, mtainOld)),
      TRow(308, sea * 7, mtainOld),
      TRow(306, sea * 7, CapeOld(4, 2, mtainOld)),
      TRow(304, sea * 7, mtainOld),
      TRow(302, sea * 6, CapeOld(5, 2, hillyTaiga), hillyTaiga, taiga),
      TRow(300, sea * 6, CapeOld(5, 1,  mtainOld), hillyTaiga * 2),
      TRow(298, sea * 6, CapeOld(4, 2, mtainOld), mtainOld, hillyTaiga * 2),
      TRow(296, sea * 6, hillyTaiga * 2, taiga * 2),
      VRow(295, MouthOld(528, HVUp)),
      TRow(294, sea * 2, Isle10(hilly), CapeOld(5, 3, hilly), sea * 3, hilly, CapeOld(2, 2, hillyTaiga), taiga),
      TRow(292, sea * 3, hilly, CapeOld(1, 2, hilly), sea * 4, CapeOld(5, 4), plain),
      TRow(290, sea * 2, CapeOld(4, 2, hilly), hilly, sea * 4, plain, Isle10(), plain),
      VRow(289, BendAllOld(502, HVDL)),
      TRow(288, sea * 2, plain, CapeOld(3, 2, hilly), plain, sea * 4, plain, sea),
      VRow(287, BendIn(520, HVDR, 13),  BendIn(522, HVDn, 13), MouthLt(524, HVDR, 7)),
      TRow(286, sea * 2, plain, CapeOld(1, 2), plain * 2, sea * 2, plain * 4),
      TRow(284, sea, plain * 2, CapeOld(4, 2, hilly), plain * 2, sea, plain * 5),
      TRow(282, sea * 4, hilly, plain, CapeOld(1, 3), plain * 2, hilly * 2, plain * 2),
      TRow(280, sea * 4, CapeOld(2, 4, hilly), sea * 2, plain, hilly * 5),
      TRow(278, sea * 4, plain * 5, hilly, plain * 2, hilly),
      TRow(276, sea * 5, plain * 4, hilly * 2, mtainOld * 3),
      TRow(274, sea * 6, plain * 3, hilly, mtainOld * 4),
      VRow(273, MouthOld(538, HVUp)),
      TRow(272, sea * 6, plain, hilly * 2, mtainOld, hilly, mtainOld, plain, hilly),
      VRow(271, BendAllOld(538, HVUR), MouthOld(540, HVDR)),
      TRow(270, sea * 2, CapeOld(4, 3), CapeOld(0, 1, hilly) * 3, plain * 2, hilly * 2, mtainOld, sea, hilly * 2, sea),
      TRow(268, sea * 3, hilly, plain, hillyDesert * 2, mtainOld * 2, sea * 3, Isle10(hilly), hilly * 2),
      TRow(266, sea * 2, hilly * 2, desert, hillyDesert * 2, hilly, sea * 3, hilly, sea * 2, hilly),
      VRow(265, MouthOld(514, HVUp)),
      TRow(264, sea * 3, hilly * 2, hillyDesert, hilly, CapeOld(2, 1, hilly), sea, Isle10(hilly), sea * 2, hilly, sea * 2),
      TRow(262, sea * 3, plain * 2, hilly * 3)
    )
  }
  help.run
}

/** 16okm terrain scenario for Britain */
object Brit160
{ def britTerrs: LayerHcRefSys[WTile] = Terr160E0.terrs.spawn(Terr160E0.grid, EGrid160.britGrid)
  def britSTerrs: LayerHSOptSys[WSep, WSepSome] =Terr160E0.sTerrs.spawn(Terr160E0.grid, EGrid160.britGrid)
  def britCorners: HCornerLayer = Terr160E0.corners.spawn(Terr160E0.grid, EGrid160.britGrid)

  def britScen: EScenBasic = new EScenBasic
  { override implicit val gridSys: EGrid160LongPart = EGrid160.britGrid
    override val terrs: LayerHcRefSys[WTile] = britTerrs
    override val sTerrs: LayerHSOptSys[WSep, WSepSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
    override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
  }
}