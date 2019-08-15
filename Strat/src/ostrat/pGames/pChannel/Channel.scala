package ostrat
package pGames.pChannel
import pGrid._, pEarth._, pCanv._, Colour._

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
  val g1 = TGrid.rowMulti(460, MyGrid.apply,
    rm(180, sea, hills * 3, plain * 2, sea * 3),
    rm(178, sea, hills * 2, plain * 3, sea * 3),
    rm(180, sea * 2, plain * 4, Coast(dnLtA = - 2, dnRtA = 2, downA = -2, downB = -2), sea, plain),
    rm(178, sea , hills * 3, sea * 3, plain * 2),
    rm(180, sea * 6, plain * 3)
  )
  val scale = 48
  def stuff = g1.xyTilesMapAll {(x, y, tile) => tile match {
    case c: Coast => g1.tileDisplayPolygonReduce(x, y, scale, c.vertoffs).fill(tile.colour)
    case t => g1.tileFill(x, y, scale)(_.colour)
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