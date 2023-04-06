/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTile._

object Terr220E30 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e30(132)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = grid.newSideOptLayer[WSide, WSideSome]
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(182, Hland(2, 5, Hilly(Tundra)), Hland(2, 0, Hilly(Tundra)), sea * 3),
      TRow(180, tundraHills, taiga, tundra, Hland(2, 0, Hilly(Tundra)), sea),
      TRow(178, taiga * 4, Hland(3, 5, Level(Tundra))),
      VRow(177, MouthUp(1530), MouthDL(1538), VertInUL(1544)),
      TRow(176, Hland(1, 2, Level(Taiga)), taiga * 2, Hland(3, 0, Level(Taiga)), Hland(2, 4, Level(Taiga)), taiga),
      VRow(175, MouthDL(1540), MouthDR(1544)),
      TRow(174, Hland(1, 2, Level(Taiga)), Hland(1, 5, Level(Taiga)), taiga * 4),
      VRow(173, MouthUL(1540, Lake), VertInDL(1542, Lake, Lake)),
      TRow(172, Hland(1, 2, Level(Taiga)), Hland(1, 5, Level(Taiga)), taiga * 5),
      VRow(171, MouthDL(1536, Lake), MouthUR(1538, Lake), MouthDn(1542, Lake)),
      TRow(170, sea, Hland(2, 3, Level(Taiga)), Hland(1, 3, Level(Taiga)), taiga * 4),
      VRow(169, MouthUR(1536)),
      TRow(168, Hland(2, 1), Hland(2, 5), Hland(1, 0), forest * 2, taiga * 2),
      TRow(166, Hland(2, 2), Hland(1, 5), plain * 2, forest * 2, plain, forest),
      TRow(164, Hland(1, 5), plain * 7),
      TRow(162, plain * 9),
      TRow(160, plain * 3, forest, plain * 5),
      TRow(158, hills * 2, plain * 7),
      TRow(156, plain * 2, mtain, plain * 6, desert),
      VRow(155, MouthUp(1544), MouthUp(1548)),
      TRow(154, plain * 2, hills * 2, plain * 3, Hland(3, 2), plain * 2),
      VRow(153, MouthUR(1540)),
      TRow(152, hills * 2, plain * 2, sea, Hland(3, 3), Hland(3, 1), Hland(2, 3, Hilly()), plain * 2),
      TRow(150, Hland(1, 4, Hilly()), hills * 3, sea * 5, Hland(1, 4, Mountains()), hills),
      VRow(149, MouthDR(1554)),
      TRow(148, Hland(2, 0, Hilly()), Hland(1, 4, Hilly()), plain, hills * 2, Hland(1, 0, Hilly()), mtain, hills * 2, Hland(1, 0, Mountains()), hills),
      VRow(147, MouthUL(1526)),
      TRow(146, Hland(2, 1, Hilly()), Hland(1, 4, Hilly()), Hland(2, 1, Hilly()), hills * 2, desertHills * 3, mtain, desertHills * 2),
      VRow(145, MouthDL(1522), MouthUR(1524)),
      TRow(144, Hland(4, 2, Hilly()), sea, hills, sea, hills * 4, desertHills, plain, mtain),
      VRow(143, MouthUp(1544)),
      TRow(142, sea * 5, Hland(3, 2, Hilly()), sea, Hland(3, 2, Hilly()), Hland(1, 4, Hilly()), desert * 3),
      TRow(140, sea * 3, Isle(Hilly()), sea * 2, Isle(Hilly()), sea, hills, desert * 3),
      TRow(138, sea * 2, Hland(3, 5, Level(Desert)), sea * 5, hills, desert * 3),
      TRow(136, desert, sea, desert * 4, plain, desertHills, desert * 5),
      VRow(135, MouthUp(1540), MouthUp(1544)),
      TRow(134, desert * 6, plain, desert, desertHills, desertHills, desert * 3),
      VRow(133, VertInUR(1540), MouthDR(1542), MouthDn(1544)),
      TRow(132, desert * 6, plain, desert, sea, desert * 4),
    )
  }
  help.run
}