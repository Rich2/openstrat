package ostrat
package pGames.pChannel
import pGrid._, pEarth._, geom._, pCanv._, Colour._

/*
case class Coast(p1: Int, p2: Int = 0, p3: Int = 0, p4: Int = 0, p5: Int = 0, p6: Int = 0) extends Terr
{ def str = "Coast"
  def colour = Blue
}*/

case class MyGrid(val tArr: Array[WTile], val indArr: Array[Int]) extends HGrid[WTile]
{
  override def toString = "MyGrid"
}

case class TGui(canv: CanvasPlatform)
{ import WTile._, RowMulti.{apply => rm}
  val v204463 = HVDnLt2(2, 2)
  val v206463 = HVUpRt2(2, 2)

  val g1 = TGrid.rowMulti(460, MyGrid.apply,
    rm(468, 180, sea, hills * 3, plain * 2, sea * 3),
    rm(466, 178, sea, hills * 2, plain * 3, sea * 3),
    rm(464, 180, sea * 2, plain * 4, Coast(dnRt = v206463, down = v204463), Coastal(dnLt = v206463), plain),
    rm(462, 178, sea , hills * 3, sea * 2, Coastal(upRt = v204463), Coast(up = HVUpRt2(2, 2), upLt = v204463), plain),
    rm(460, 180, sea * 6, plain * 3)
  )
  val scale = 48
  def stuff = g1.xyTilesDispAll {(x, y, tile, buff) => tile match {
    case c: CoastLike => buff.append(g1.tileDisplayPolygonReduce(x, y, scale, c.vertOffs).fill(tile.colour))
    case t => buff.append(g1.tileFill(x, y, scale)(_.colour))
    }
  }

  val multip = (9.power(6) + 1) / 2
  debvar(multip)
  val hn = 2 power 30
  val lo = hn / multip
  debvar(lo)
  canv.rendElems(stuff)
  canv.rendElems(g1.sideDrawsAll(scale)(2.0))
}