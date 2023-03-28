/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320km terrain for 60 west. */
object Terr320W60 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w60(128, 164)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.setRowEnd(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }


    wr(148, taiga, mtain, sea * 3)
    wr(146, taiga, taigaHills, sea * 3)
    gs(144, 10744, taiga * 3, sea * 2)
    gs(142, 10742, taiga * 4, sea * 2)
    gs(140, 10744, taiga, sea * 2, taiga, sea * 2)
    gs(138, 10742, taiga * 2, sea, taiga * 2, sea * 2)
    wr(136, taigaHills, taiga * 2, sea * 4)
    wr(134, forestHills, sea * 6)
    wr(132, sea * 7)

    res
  }

  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
    res.setSomeInts(WSideMid(), 141,10755,  148,10746)
    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res.setMouth3(150, 10746)//Ungava Bay north
    res.setMouth0(146, 10746)//Ungava Bay south

    res.setMouth4(142, 10758)//Newfoundland Gulf of St Lawrence north
    res.setMouth1(140, 10752)//Newfoundland Gulf of St Lawrence

    res.setMouth4Corner(136, 10740)//Lake Ontario east
    res

  }
  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(164, ice),
      TRow(162, Headland(1, 4, Plains, IceCap), ice),
      TRow(160, sea, ice),
      TRow(158, sea, ice * 2),
      TRow(156, tundra, sea, ice),
      TRow(154, tundra * 2, sea, ice),
      TRow(152, tundra, sea * 2, ice),
      TRow(150, tundra),
    )
  }

  help.run
}