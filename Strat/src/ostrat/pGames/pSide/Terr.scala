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
  def tileNum: Int = tArr.length
  /** The index Arr first value is the bottom row number followed by 2 values for each row. The index for the start of the row in the tile Arr
   * followed by the xCood for the start of the row. Hence its length is 2 * numRows + 1. */
  def indArr: Array[Int]

  /** The number of rows. */
  def numRows: Int = indArr.length / 2
  @inline def yMin: Int = indArr(0)
  @inline def yLen: Int = yMin + indArr.length / 2 - 1
  @inline def yMax: Int = yMin + indArr.length - 3

  @inline def xMin: Int =
  { var acc: Int = 10000000
    rowsForAll(y => acc = acc.min(xRowStart(y)))
    acc
  }

  @inline def xMax: Int =
  { var acc: Int = -10000000
    rowsForAll(y => acc = acc.max(xRowStart(y)))
    acc
  }

  def yInd(y: Int): Int = (y - indArr.last) / 2
  def rowIndex(y: Int): Int = indArr(y - yMin + 1)
  def xRowStart(y: Int): Int = indArr(y - yMin + 2)
  def xRowEnd(y: Int): Int = xRowStart(y) + xRowLen(y)
  def xRowLen(y: Int): Int = (rowTileNum(y) - 1 ) * 4
  def rowTileNum(y: Int): Int =  ife(y == yMax, tileNum - rowIndex(y), rowIndex(y + yStep) - rowIndex(y))
  @inline def xStep: Int

  def xyToInd(x: Int, y: Int): Int = (x - xRowStart(y)) / xStep + rowIndex(y)
  def getTile(x: Int, y: Int): TileT = tArr(xyToInd(x, y))

  def map[B](newGrid: Array[Int] => GridT[B], f: TileT => B): GridT[B] =
  { val res = newGrid(indArr)
    tArr.iForeach((t, i) => res.tArr(i) = f(t))
    res
  }
  def rowsForAll(f: Int => Unit) = iToForeach(yMin, yMax, yStep)(f)

  def yToRowI(y: Int): Int = (y - yMin) / 2

  def rowsMapAll[B](f: Int => B)(implicit ct: ClassTag[B]): Arr[B] =
  { val array: Array[B] = new Array(numRows)
    rowsForAll(y => array(yToRowI(y)) = f(y))
    array.toArr
  }

  def rowTilesForAll(y: Int)(f: TileT => Unit): Unit = iToForeach(xRowStart(y), xRowEnd(y), xStep)(x => f(getTile(x, y)))

  def rowTilesIForAll(y: Int)(f: (TileT, Int) => Unit): Unit =
  {
    var x = xRowStart(y)
    val xe = xRowEnd(y)
    var i = 0
    while(x <= xe)
    { f(getTile(x, y), i)
      x += xStep
      i += 1
    }
  }

  def rowTileArr(y: Int)(implicit ct: ClassTag[TileT]): Arr[TileT] =
  { val array: Array[TileT] = new Array(rowTileNum(y))
    rowTilesIForAll(y)((t, i) => array(i) = t)
    array.toArr
  }

  def fRow[B](y: Int, f: (Int, Int, Arr[TileT]) => B): B = f(y, xRowStart(y), ???)

  def rowsStr(len: Int = 3)(implicit ct: ClassTag[TileT]): String =
  { var acc = "TGrid\n"
    val xm = xMin
    debvar(xm)
    val strs: Arr[String] = rowsMapAll{y =>
        xRowStart(y).toString + ((xRowStart(y) - xm) * 2).spaces + rowTileArr(y).toStrsFold((len - 3).min(0).spaces, _.toString.lengthFix(len))
    }
    acc + strs.encurly
  }
}

object TGrid
{
  def rowMulti[TileT, GridT <: TGrid[TileT]](yMin: Int, fac:(Array[TileT], Array[Int]) => GridT,inp: RowMulti[TileT]*)(implicit ct: ClassTag[TileT]):
    GridT = rowMultis[TileT, GridT](inp.toArr, yMin: Int, fac)(ct)

  def rowMultis[TileT, GridT <: TGrid[TileT]](inp: Arr[RowMulti[TileT]], yMin: Int, fac:(Array[TileT], Array[Int]) => GridT)(
    implicit ct: ClassTag[TileT]): GridT =
  {
    val len = inp.sumBy(_.multis.singlesLen)
    val tiles = new Array[TileT](len)
    /* The index Arr first value is the bottom row number followed by 2 values for each row. The index for the start of the row in the tile Arr
     * followed by the xCood for the start of the row. Hence its length is 2 * numRows + 1. */
    val indArr = new Array[Int](inp.length * 2 + 1)
    indArr(0) = yMin
    var count = 0
    var rowCount = 1
    inp.reverseForeach{rm =>
      indArr(rowCount) = count
      indArr(rowCount + 1) = rm.xStart
      rowCount += 2
      rm.multis.foreach(_.foreach{t => tiles(count) = t; count += 1 })
    }

    fac(tiles, indArr)
  }
}

trait HGrid[TileT] extends TGrid[TileT]
{
  type GridT[A] = HGrid[A]
  @inline override def xStep: Int = 4
}

class RowMulti[TileT](val xStart: Int, val multis: ArrMulti[TileT])
{
  //def toPair(implicit ct: ClassTag[TileT]): (Int, Arr[TileT]) = (xStart, multis.flatSingles)
}

object RowMulti
{
  def apply[TileT](xStart: Int, multis: Multiple[TileT]*): RowMulti[TileT] = new RowMulti(xStart, multis.toArr)
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
  val g1 = TGrid.rowMulti(460, MyGrid.apply,
    RowMulti(178, Sea , Land * 3, Sea * 3, Land * 2),
    RowMulti(180, Sea * 6, Land * 3)
  )

  println(g1.rowsStr(4))
}