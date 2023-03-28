/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

object Terr320E60 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e60(120, 166)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }

    wr(140, plain * 2, desert * 3, plain)
    wr(138, plain, desert * 6)
    wr(136, sea, desert * 6)
    wr(134, sea, desert * 5, mtain)
    wr(132, mtain, sea, desert * 3, mtain * 2)
    wr(130, mtain, sea, desert * 4, mtain * 2)
    wr(128, desertHills, desert * 5, mtain * 2)
    wr(126, desert, mtain, desert * 5, plain)
    wr(124, desert, plain, mtain, desert * 4, plain * 2)
    wr(122, desert, sea, mtain, desert * 3, plain, desert * 2)
    wr(120, desert * 2, sea, desert, sea * 3, plain, desert)
    res
  }

  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
    res.setSomeInts(WSideMid(), 137, 2551)
    res.setSomeInts(WSideMid(), 154,2552, 155,2553)
    res.setSomeInts(WSideMid(), 121,2555,  121,2557,  123,2547)
    res
  }

  override val corners: HCornerLayer =
  { val res: HCornerLayer = grid.newHVertOffsetLayer

    res.setCorner(154, 2554, 5, HVDR)//White Sea mouth
    res.setCorner(154, 2554, 4, HVDR)//White Sea mouth

    res.setMouth1(136, 2548)//Caspian Sea north east
    res.setMouth4(138, 2554)//Caspian Sea north east
    res
  }

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(160, Headland(1, 5, Level, Tundra), sea),
      TRow(158, Headland(4, 2, Level, Tundra), sea, tundra),
      TRow(156, sea * 2, tundra),
      TRow(154, tundra * 4),
      TRow(152, taiga * 4),
      TRow(150, taiga * 4),
      TRow(148, forest, taiga * 4),
      TRow(146, forest * 2, taiga * 3),
      TRow(144, forest * 5),
      TRow(142, plain * 6),
    )
  }

  help.run
}