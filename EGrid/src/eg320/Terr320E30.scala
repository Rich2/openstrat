/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex._, egrid._, WTile._

object Terr320E30 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e30(124)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(156, Head2Land(5, Hilly, Tundra), Head2Land(0, Hilly, Tundra), sea),
      TRow(154, taigaHills, taiga * 2, Head4Land(0, Plains, Tundra)),
      VRow(153, MouthUp(1534)),
      TRow(152, Head1Land(2, Plains, Taiga), taiga, Head2Land(1, Plains, Taiga), taiga),
      VRow(151, VertInDR(1532), MouthDL(1540)),
      TRow(150, Head1Land(2, Plains, Taiga), taiga * 3),
      VRow(149, MouthUL(1536, Lake), MouthDR(1538, Lake)),
      VRow(147, MouthUR(1534)),
      TRow(148, Head1Land(2, Plains, Taiga), Head3Land(3, Plains, Taiga), taiga * 3),
      TRow(146, Head2Land(5), taiga * 4),
      TRow(144, plain * 5),
      TRow(142, plain * 6),
      TRow(140, plain * 6),
      TRow(138, mtain * 2, hills, plain * 3, desert),
      VRow(137, MouthUp(1522), MouthUR(1540), MouthUR(1544)),
      TRow(136, hills, plain * 2, Head1Land(2), Head4Land(2), Head3Land(3), plain),
      VRow(135, VertInUR(1522)),
      TRow(134, Head1Land(4, Hilly), hills, Head2Land(2, Hilly), sea * 3, mtain),
      TRow(132, Head2Land(1, Hilly), hills, Head3Land(4, Hilly), hills * 4),
      VRow(131, MouthDR(1528)),
      TRow(130, Head4Land(2, Hilly), Head4Land(3, Hilly), Head3Land(1, Hilly), Head2Land(3, Hilly), hills * 4),
      VRow(129, MouthUR(1536)),
      TRow(128, sea * 2, Island(Hilly), sea, Island(Hilly), hills, desert * 2),
      VRow(127, VertInUp(1528)),
      TRow(126, sea, Head2Land(5, Plains, Desert), Head2Land(0, Plains, Desert), sea * 2, Head1Land(5, Hilly), desert * 2),
      VRow(125, MouthDn(1524), MouthDn(1532), MouthDn(1540)),
      TRow(124, desert * 4, plain, desert * 4),
    )
  }

  help.run
}