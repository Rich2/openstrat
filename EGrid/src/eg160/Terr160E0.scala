/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** 160km terrain for 0 degrees eat. */
object Terr160E0 extends Long160Terrs
{
  override implicit val grid: EGrid160LongFull = EGrid160.e0(262)

  /** Terrain for 160km 30East. Zealand has been moved north. 94GG has been left as Sea. */
  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }

    wr(310, sea * 6, mtain)
    wr(308, sea * 7, mtain)
    wr(306, sea * 7, mtain)
    wr(304, sea * 7, mtain)
    wr(302, sea * 7, taigaHills, taiga)
    wr(300, sea * 6, mtain, taigaHills * 2)
    wr(298, sea * 6, mtain * 2, taigaHills * 2)
    wr(296, sea * 6, taigaHills * 2, taiga * 2)
    wr(294, sea * 3, hills, sea * 3, hills, sea, taiga)
    wr(292, sea * 3, hills * 2, sea * 5, plain)
    wr(290, sea * 2, hills * 2, sea * 4, plain * 3)
    wr(288, sea * 2, plain, hills, plain, sea * 4, plain, sea)
    wr(286, sea * 2, plain * 4, sea * 3, plain * 3)
    wr(284, sea, plain * 2, hills, plain * 2, sea, plain * 5)
    wr(282, sea * 4, hills, plain * 4, hills * 2, plain * 2)
    wr(280, sea * 4, hills, sea * 2, plain, hills * 5)
    wr(278, sea * 4, plain * 5, hills, plain * 2, hills)
    wr(276, sea * 5, plain * 4, hills * 2, mtain * 3)
    wr(274, sea * 6, plain * 3, hills, mtain * 4)
    wr(272, sea * 6, plain, hills * 2, mtain, hills, mtain, plain, hills)
    wr(270, sea * 2, plain, hills * 3, plain * 2, hills * 2, mtain, sea, hills * 2, sea)
    wr(268, sea * 3, hills, plain, desertHills * 2, mtain * 2, sea * 3, SeaIsland(Hilly, OpenTerrain), hills * 2)
    wr(266, sea * 2, hills * 2, desert, desertHills * 2, hills, sea * 3, hills, sea * 2, hills)
    wr(264, sea * 3, hills * 2, desertHills, sea * 2, sea, sea, sea * 2, hills, sea * 2)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOptLayer[WSide]
    res.setSomeInts(SCSea, 279,505,  281,515,  282,516,  284,502,  285,503,  286,504,  287,503,  288,502,  289,501,  289,529,  290,528,  290,532,  291,531)
    res.setSomeInts(SLSea, 269,533,  268,534)
    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res.setMouth2(290, 498)//Northern Ireland -Scotland north west
    res.setVert1In(288, 500)//Northern Ireland - Scotland
    res.setVert4In(288, 504)//Northern Ireland - Scotland
    res.setVert1In(286, 502)//Ireland - England
    res.setVert2In(286, 502)//Ireland - Anglesey
    res.setVert5In(284, 504)//Ireland - Wales
    res.setMouth0(282, 502)//Ireland - Wales south

    res.setMouth1(278, 502)//Cornwall - Britany
    res.setMouth4(280, 508)//Britany - Devon

    res.setMouth1(280, 512)//Straits of Dover south west
    res.setVert2In(282, 514)//Straits of Dover
    res.setMouth3(284, 516)//Straits of Dover north east

    res
  }
}

/** 16okm terrain scenario for Britain */
object Brit160
{ def britTerrs: HCenLayer[WTile] = EGrid160.britGrid.hCenLayerSpawn(Terr160E0.grid, Terr160E0.terrs)
  def britSTerrs: HSideOptLayer[WSide] = EGrid160.britGrid.sideOptLayerSpawn(Terr160E0.grid, Terr160E0.sTerrs)
  def britCorners: HCornerLayer = EGrid160.britGrid.cornerLayerSpawn(Terr160E0.grid, Terr160E0.corners)

  def britScen: EScenBasic = new EScenBasic
  { override implicit val gridSys: EGrid160LongPart = EGrid160.britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideOptLayer[WSide] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}