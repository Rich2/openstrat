package ostrat
package pGames.pSide

sealed trait Terr extends ostrat.PersistSingleton
object Land extends Terr { override def str = "Land"}
object Sea extends Terr { override def str = "Sea"}
case class Coast(p1: Int, p2: Int = 0, p3: Int = 0, p4: Int = 0, p5: Int = 0, p6: Int = 0) extends Terr
{ def str = "Coast"}

trait TGrid[TileT]
{
  type GridT[A] <: TGrid[A]
  def ts: Array[TileT]
  def arrRows: Array[Int]
  def numRows: Int = arrRows.length / 2 - 1
  def rowIndex(rowNum: Int): Int = arrRows(rowNum * 2)
  def xRowStart(rowNum: Int): Int = arrRows(rowNum * 2 + 1)
  //def getTile

  def map[B](mapGrid: Array[Int] => GridT[B], f: TileT => B): GridT[B] =
  { val res = mapGrid(arrRows)
    ts.iForeach((t, i) => res.ts(i) = f(t))
    res
  }
}

trait HGrid[TileT] extends TGrid[TileT]
{
  type GridT[A] = HGrid[A]
}

class MyGrid(val ts: Array[Terr], val arrRows: Array[Int]) extends HGrid[Terr]

object Game extends App
{
  val a1: ArrMulti[Terr] = Arr(Sea * 6, Land * 3)
  val a2: ArrMulti[Terr] = Arr(Sea , Land * 3, Sea * 3, Land * 2)
  println(a2)
}