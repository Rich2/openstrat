package ostrat
package pGames.pSide

sealed trait Terr extends ostrat.PersistSingleton
object Land extends Terr { override def str = "Land"}
object Sea extends Terr { override def str = "Sea"}
case class Coast(p1: Int, p2: Int = 0, p3: Int = 0, p4: Int = 0, p5: Int = 0, p6: Int = 0) extends Terr
{ def str = "Coast"}

trait HGrid[TileT]
{
  def ts: Array[TileT]
  def map[B](newGrid: HGrid[B], f: TileT => B): HGrid[B] =
  {
    val res = newGrid
    ts.iForeach((t, i) => res.ts(i) = f(t))
    res
  }
}
case class TGrid(ts: Array[Terr]) extends HGrid[Terr]

object Game extends App
{
  val a1: ArrMulti[Terr] = Arr(Sea * 6, Land * 3)
  val a2: ArrMulti[Terr] = Arr(Sea , Land * 3, Sea * 3, Land * 2)
  println(a2)
}