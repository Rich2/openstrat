/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

/** 160km terrain for 0 degrees east. Majorca is big enough at this scale to qualify as Island. Lesbos is not. */
object Terr160E0 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e0(262)

  /** Terrain for 160km 30East. Zealand has been moved north. 94GG has been left as Sea. */
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)

  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(310, sea * 6, Hland(3, 4, mtain)),
      TRow(308, sea * 7, mtain),
      TRow(306, sea * 7, Hland(2, 4, mtain)),
      TRow(304, sea * 7, mtain),
      TRow(302, sea * 6, Hland(2, 5, hillyTaiga), hillyTaiga, taiga),
      TRow(300, sea * 6, Hland(1, 5,  mtain), hillyTaiga * 2),
      TRow(298, sea * 6, Hland(2, 4, mtain), mtain, hillyTaiga * 2),
      TRow(296, sea * 6, hillyTaiga * 2, taiga * 2),
      VRow(295, Mouth(528, HVUp)),
      TRow(294, sea * 2, Isle(hilly), Hland(3, 5, hilly), sea * 3, hilly, Hland(2, 2, hillyTaiga), taiga),
      TRow(292, sea * 3, hilly, Hland(2, 1, hilly), sea * 4, Hland(4, 5), land),
      TRow(290, sea * 2, Hland(2, 4, hilly), hilly, sea * 4, land, Isle(), land),
      VRow(289, BendAll(502, HVDL)),
      TRow(288, sea * 2, land, Hland(2, 3, hilly), land, sea * 4, land, sea),
      TRow(286, sea * 2, land, Hland(2, 1), land * 2, sea * 3, land * 3),
      TRow(284, sea, land * 2, Hland(2, 4, hilly), land * 2, sea, land * 5),
      TRow(282, sea * 4, hilly, land, Hland(3, 1), land * 2, hilly * 2, land * 2),
      TRow(280, sea * 4, Hland(4, 2, hilly), sea * 2, land, hilly * 5),
      TRow(278, sea * 4, land * 5, hilly, land * 2, hilly),
      TRow(276, sea * 5, land * 4, hilly * 2, mtain * 3),
      TRow(274, sea * 6, land * 3, hilly, mtain * 4),
      VRow(273, Mouth(538, HVUp)),
      TRow(272, sea * 6, land, hilly * 2, mtain, hilly, mtain, land, hilly),
      VRow(271, BendAll(538, HVUR), Mouth(540, HVDR)),
      TRow(270, sea * 2, Hland(3, 4), Hland(1, 0, hilly) * 3, land * 2, hilly * 2, mtain, sea, hilly * 2, sea),
      TRow(268, sea * 3, hilly, land, hillyDesert * 2, mtain * 2, sea * 3, Isle(hilly), hilly * 2),
      TRow(266, sea * 2, hilly * 2, desert, hillyDesert * 2, hilly, sea * 3, hilly, sea * 2, hilly),
      VRow(265, Mouth(514, HVUp)),
      TRow(264, sea * 3, hilly * 2, hillyDesert, hilly, Hland(1, 2, hilly), sea, Isle(hilly), sea * 2, hilly, sea * 2),
      TRow(262, sea * 3, land * 2, hilly * 3)
    )
  }
  help.run
}

/** 16okm terrain scenario for Britain */
object Brit160
{ def britTerrs: HCenLayer[WTile] = Terr160E0.terrs.spawn(Terr160E0.grid, EGrid160.britGrid)
  def britSTerrs: HSideOptLayer[WSide, WSideSome] =Terr160E0.sTerrs.spawn(Terr160E0.grid, EGrid160.britGrid)
  def britCorners: HCornerLayer = Terr160E0.corners.spawn(Terr160E0.grid, EGrid160.britGrid)

  def britScen: EScenBasic = new EScenBasic
  { override implicit val gridSys: EGrid160LongPart = EGrid160.britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideOptLayer[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}