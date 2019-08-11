package ostrat
package pGames.pSide

sealed trait Terr
object Land extends Terr
object Sea extends Terr
case class Coast(p1: Int, p2: Int = 0, p3: Int = 0, p4: Int = 0, p5: Int = 0, p6: Int = 0) extends Terr

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
  val a: Arr[Multiple[Terr]] = Arr(Sea * 5, Sea)
  println(a)
}