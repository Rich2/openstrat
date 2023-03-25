/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** 160km terrain for 0 degrees east. Majorca is big enough at this scale to qualify as Island. Lesbos is not. */
object Terr160E0 extends Long160Terrs
{
  override implicit val grid: EGrid160LongFull = EGrid160.e0(262)

  /** Terrain for 160km 30East. Zealand has been moved north. 94GG has been left as Sea. */
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(310, sea * 6, Head3Land(4, Mountains)),
      TRow(308, sea * 7, mtain),
      TRow(306, sea * 7, Head2Land(4, Mountains)),
      TRow(304, sea * 7, mtain),
      TRow(302, sea * 6, Head2Land(5, Hilly, Taiga), taigaHills, taiga),
      TRow(300, sea * 6, Head1Land(5,  Mountains), taigaHills * 2),
      TRow(298, sea * 6, Head2Land(4, Mountains), mtain, taigaHills * 2),
      TRow(296, sea * 6, taigaHills * 2, taiga * 2),
      VRow(295, MouthUp(528)),
      TRow(294, sea * 2, Island(Hilly), Head3Land(5, Hilly), sea * 3, hills, Head2Land(2, Hilly, Taiga), taiga),
      TRow(292, sea * 3, hills, Head2Land(1, Hilly), sea * 4, Head4Land(5), plain),
      TRow(290, sea * 2, Head2Land(4, Hilly), hills, sea * 4, plain, Island(), plain),
      VRow(289, VertInDL(502)),
      TRow(288, sea * 2, plain, Head2Land(3, Hilly), plain, sea * 4, plain, sea),
      TRow(286, sea * 2, plain, Head2Land(1), plain * 2, sea * 3, plain * 3),
      TRow(284, sea, plain * 2, Head2Land(4, Hilly), plain * 2, sea, plain * 5),
      TRow(282, sea * 4, hills, plain, Head3Land(1), plain * 2, hills * 2, plain * 2),
      TRow(280, sea * 4, Head4Land(2, Hilly), sea * 2, plain, hills * 5),
      TRow(278, sea * 4, plain * 5, hills, plain * 2, hills),
      TRow(276, sea * 5, plain * 4, hills * 2, mtain * 3),
      TRow(274, sea * 6, plain * 3, hills, mtain * 4),
      VRow(273, MouthUp(538)),
      TRow(272, sea * 6, plain, hills * 2, mtain, hills, mtain, plain, hills),
      VRow(271, VertInUR(538), MouthDR(540)),
      TRow(270, sea * 2, Head3Land(4), Head1Land(0, Hilly) * 3, plain * 2, hills * 2, mtain, sea, hills * 2, sea),
      TRow(268, sea * 3, hills, plain, desertHills * 2, mtain * 2, sea * 3, Island(Hilly), hills * 2),
      TRow(266, sea * 2, hills * 2, desert, desertHills * 2, hills, sea * 3, hills, sea * 2, hills),
      VRow(265, MouthUp(514)),
      TRow(264, sea * 3, hills * 2, desertHills, hills, Head1Land(2, Hilly), sea, Island(Hilly), sea * 2, hills, sea * 2),
      TRow(262, sea * 3, plain * 2, hills * 3)
    )
  }
  help.run
}

/** 16okm terrain scenario for Britain */
object Brit160
{ def britTerrs: HCenLayer[WTile] = EGrid160.britGrid.hCenLayerSpawn(Terr160E0.grid, Terr160E0.terrs)
  def britSTerrs: HSideLayer[WSide] = EGrid160.britGrid.sideLayerSpawn(Terr160E0.grid, Terr160E0.sTerrs)
  def britCorners: HCornerLayer = EGrid160.britGrid.cornerLayerSpawn(Terr160E0.grid, Terr160E0.corners)

  def britScen: EScenBasic = new EScenBasic
  { override implicit val gridSys: EGrid160LongPart = EGrid160.britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideLayer[WSide] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}