package ostrat
package pGames.pSide
import reflect.ClassTag

sealed trait Terr extends ostrat.PersistSingleton
object Land extends Terr { override def str = "Land"}
object Sea extends Terr { override def str = "Sea"}
case class Coast(p1: Int, p2: Int = 0, p3: Int = 0, p4: Int = 0, p5: Int = 0, p6: Int = 0) extends Terr
{ def str = "Coast"}

trait TGrid[TileT]
{
  type GridT[A] <: TGrid[A]
  @inline final def yStep: Int = 2
  def tArr: Array[TileT]
  def indArr: Array[Int]
  def numRows: Int = indArr.length / 2 - 1
  @inline def yMin: Int = indArr(0)
  @inline def yLen: Int = yMin + indArr.length / 2 - 1
  @inline def yMax: Int = yMin + indArr.length - 3
  def yInd(y: Int): Int = (y - indArr.last) / 2
  def rowIndex(rowNum: Int): Int = indArr(rowNum * 2)
  def xRowStart(rowInd: Int): Int = indArr(rowInd * 2 + 1)
  @inline def xStep: Int

  def xyToInd(x: Int, y: Int): Int = (x - xRowStart(y)) / xStep + rowIndex(y)
  def getTile(x: Int, y: Int): TileT = tArr(xyToInd(x, y))

  def map[B](mapGrid: Array[Int] => GridT[B], f: TileT => B): GridT[B] =
  { val res = mapGrid(indArr)
    tArr.iForeach((t, i) => res.tArr(i) = f(t))
    res
  }
}

object TGrid
{
  def rowMultis[TileT, GridT <: TGrid[TileT]](inp: Arr[RowMulti[TileT]], yMin: Int, fac:(Array[TileT], Array[Int]) => GridT)(
    implicit ct: ClassTag[TileT]): GridT =
  {
    val len = inp.sumBy(_.multis.singlesLen + 10)
    val tiles = new Array[TileT](len)
    val indArr = new Array[Int](inp.length * 2 + 2)
    indArr(0) = yMin
    var count = 1
    var rowCount = 0
    inp.foreach{rm =>
      indArr(rowCount * 2 + 1) = count
      indArr(rowCount * 2 + 2) = rm.xStart
      rm.multis.foreach(_.foreach{t => tiles(count) = t; count += 1 })
      rowCount += 1
    }

    //assert((rowCount * 2) == indArr.length)
    fac(tiles, indArr)
  }
}

trait HGrid[TileT] extends TGrid[TileT]
{
  type GridT[A] = HGrid[A]
  @inline override def xStep: Int = 4
}



case class RowMulti[TileT](xStart: Int, multis: ArrMulti[TileT])
{
  //def toPair(implicit ct: ClassTag[TileT]): (Int, Arr[TileT]) = (xStart, multis.flatSingles)
}

case class MyGrid(val tArr: Array[Terr], val indArr: Array[Int]) extends HGrid[Terr]
{
  override def toString = "MyGrid"
}

object MyGrid
{

}

object Game extends App
{
  val a1: ArrMulti[Terr] = Arr(Sea * 6, Land * 3)
  val a2: ArrMulti[Terr] = Arr(Sea , Land * 3, Sea * 3, Land * 2)
  val a3: ArrMulti[Terr] = Arr(Sea)
  val g1 = TGrid.rowMultis(Arr(RowMulti(0, a3)), 4, MyGrid.apply)
  debvar(g1.yMin)
  debvar(g1.numRows)
}