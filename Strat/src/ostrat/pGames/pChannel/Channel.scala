package ostrat
package pGames.pChannel
import geom._, pGrid._,  pCanv._, Colour._

sealed trait Terr extends ostrat.PersistSingleton
{ def colour: Colour
}

object Land extends Terr
{ override def str = "Land"
  def colour = LightGreen
}

object Sea extends Terr
{ override def str = "Sea"
  def colour = Blue
}

case class Coast(p1: Int, p2: Int = 0, p3: Int = 0, p4: Int = 0, p5: Int = 0, p6: Int = 0) extends Terr
{ def str = "Coast"
  def colour = Blue
}

case class MyGrid(val tArr: Array[Terr], val indArr: Array[Int]) extends HGrid[Terr]
{
  override def toString = "MyGrid"
}

object MyGrid
{

}

case class TGui(canv: CanvasPlatform)
{
  val g1 = TGrid.rowMulti(460, MyGrid.apply,
    RowMulti(180, Sea * 2, Land * 5, Sea * 2),
    RowMulti(178, Sea , Land * 3, Sea * 3, Land * 2),
    RowMulti(180, Sea * 6, Land * 3)
  )
  canv.rendElem(TextGraphic(g1.rowsStr(4), 18))
  canv.rendElem(g1.tileFill(182, 462, 120)(_.colour))
  println(g1.rowsStr(4))
}