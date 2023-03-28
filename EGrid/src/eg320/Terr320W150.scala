/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

object Terr320W150 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w150(128, 164)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }

    wr(156, tundra * 2, sea)
    wr(154, taigaHills * 3, taiga)
    wr(152, taiga * 3, taigaHills)
    wr(150, tundraHills, mtain * 2, taiga)
    wr(148, taigaHills * 2, sea * 2, mtain)
    wr(146, tundraHills, sea * 3, mtain)
    wr(144, taigaHills, sea * 4)
    res
  }

  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)

    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer
    res.setMouth2Corner(158, 7686)
    res
  }

  val help = new WTerrSetter(grid, terrs, sTerrs, corners) {
    override val rowDatas: RArr[RowBase] = RArr(

    )
  }

  help.run
}