package ostrat
package pEarth
import pGrid._
case class MyGrid(val tArr: Array[WTile], val indArr: Array[Int]) extends HGrid[WTile]
{
  override def toString = "MyGrid"
}

object Channel
{
  def apply(): MyGrid =
  {
    import WTile._, RowMulti.{apply => rm}
    val v204463 = StraitsDnLt(2, 2)
    val v206463 = HVUpRt2(2, 2)

    TGrid.rowMulti(460, MyGrid.apply,
      rm(468, 180, sea, hills * 3, plain * 3, sea * 2),
      rm(466, 178, sea, hills * 2, plain * 4, sea * 2),
      rm(464, 180, sea * 2, plain * 4, Coast(dnRt = v206463, down = v204463), Coastal(dnLt = v206463), plain),
      rm(462, 178, sea , hills * 3, sea * 2, Coastal(upRt = v204463), Coast(up = HVUpRt2(2, 2), upLt = v204463), plain),
      rm(460, 180, sea * 6, plain * 3)
    )
  }
}

object ChannelApp extends App
{
  deb("Hello World")
  val multip = (9.power(6) + 1) / 2
  debvar(multip)
  val hn = 2 power 30
  val lo = hn / multip
  debvar(lo)
}
