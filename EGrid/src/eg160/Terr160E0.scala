/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** 160km terrain for 0 degrees east. Majorca is big enough at this scale to qualify as Island. Lesbos is not. */
object Terr160E0 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e0(262)

  /** Terrain for 160km 30East. Zealand has been moved north. 94GG has been left as Sea. */
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)

  override val sTerrs: HSideOptLayer[WSide, WSideSome] = grid.newSideOptLayer[WSide, WSideSome]
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(310, sea * 6, Hland(3, 4, Mountains())),
      TRow(308, sea * 7, mtain),
      TRow(306, sea * 7, Hland(2, 4, Mountains())),
      TRow(304, sea * 7, mtain),
      TRow(302, sea * 6, Hland(2, 5, Hilly(Taiga)), taigaHills, taiga),
      TRow(300, sea * 6, Hland(1, 5,  Mountains()), taigaHills * 2),
      TRow(298, sea * 6, Hland(2, 4, Mountains()), mtain, taigaHills * 2),
      TRow(296, sea * 6, taigaHills * 2, taiga * 2),
      VRow(295, Mouth(528, HVUp)),
      TRow(294, sea * 2, Isle(Hilly()), Hland(3, 5, Hilly()), sea * 3, hills, Hland(2, 2, Hilly(Taiga)), taiga),
      TRow(292, sea * 3, hills, Hland(2, 1, Hilly()), sea * 4, Hland(4, 5), plain),
      TRow(290, sea * 2, Hland(2, 4, Hilly()), hills, sea * 4, plain, Isle(), plain),
      VRow(289, VertIn(502, HVDL)),
      TRow(288, sea * 2, plain, Hland(2, 3, Hilly()), plain, sea * 4, plain, sea),
      TRow(286, sea * 2, plain, Hland(2, 1), plain * 2, sea * 3, plain * 3),
      TRow(284, sea, plain * 2, Hland(2, 4, Hilly()), plain * 2, sea, plain * 5),
      TRow(282, sea * 4, hills, plain, Hland(3, 1), plain * 2, hills * 2, plain * 2),
      TRow(280, sea * 4, Hland(4, 2, Hilly()), sea * 2, plain, hills * 5),
      TRow(278, sea * 4, plain * 5, hills, plain * 2, hills),
      TRow(276, sea * 5, plain * 4, hills * 2, mtain * 3),
      TRow(274, sea * 6, plain * 3, hills, mtain * 4),
      VRow(273, Mouth(538, HVUp)),
      TRow(272, sea * 6, plain, hills * 2, mtain, hills, mtain, plain, hills),
      VRow(271, VertIn(538, HVUR), Mouth(540, HVDR)),
      TRow(270, sea * 2, Hland(3, 4), Hland(1, 0, Hilly()) * 3, plain * 2, hills * 2, mtain, sea, hills * 2, sea),
      TRow(268, sea * 3, hills, plain, desertHills * 2, mtain * 2, sea * 3, Isle(Hilly()), hills * 2),
      TRow(266, sea * 2, hills * 2, desert, desertHills * 2, hills, sea * 3, hills, sea * 2, hills),
      VRow(265, Mouth(514, HVUp)),
      TRow(264, sea * 3, hills * 2, desertHills, hills, Hland(1, 2, Hilly()), sea, Isle(Hilly()), sea * 2, hills, sea * 2),
      TRow(262, sea * 3, plain * 2, hills * 3)
    )
  }
  help.run
}

/** 16okm terrain scenario for Britain */
object Brit160
{ def britTerrs: HCenLayer[WTile] = EGrid160.britGrid.hCenLayerSpawn(Terr160E0.grid, Terr160E0.terrs)
  def britSTerrs: HSideOptLayer[WSide, WSideSome] = EGrid160.britGrid.sideOptLayerSpawn(Terr160E0.grid, Terr160E0.sTerrs)
  def britCorners: HCornerLayer = EGrid160.britGrid.cornerLayerSpawn(Terr160E0.grid, Terr160E0.corners)

  def britScen: EScenBasic = new EScenBasic
  { override implicit val gridSys: EGrid160LongPart = EGrid160.britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideOptLayer[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}