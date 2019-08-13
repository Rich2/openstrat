package ostrat
package pGames.pChannel
import geom._, pGrid._, pEarth._, pCanv._, Colour._

/*
case class Coast(p1: Int, p2: Int = 0, p3: Int = 0, p4: Int = 0, p5: Int = 0, p6: Int = 0) extends Terr
{ def str = "Coast"
  def colour = Blue
}*/

case class MyGrid(val tArr: Array[Terrain], val indArr: Array[Int]) extends HGrid[Terrain]
{
  override def toString = "MyGrid"
}

case class TGui(canv: CanvasPlatform)
{ import Terrain._
  val g1 = TGrid.rowMulti(460, MyGrid.apply,
    RowMulti(180, sea * 2, plain * 5, sea * 2),
    RowMulti(178, sea , hills * 3, sea * 3, plain * 2),
    RowMulti(180, sea * 6, plain * 3)
  )

  canv.rendElems(g1.tilesFillAll(45)(_.colour))
}