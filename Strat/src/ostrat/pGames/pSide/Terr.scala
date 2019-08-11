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
  val a: Arr[Multiple[Terr]] = Arr(Sea * 5, Land * 2)
  println(a)
}