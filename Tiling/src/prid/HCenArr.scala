/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import reflect.ClassTag

/** An array of hex tile or hex centre data. For efficiency the data is stored as a flat Array. No run time information distinguishes this from an
 * ordinary linear sequence array of data. Whether in a game or a non game application the data of the grid tiles is likely to change much more
 * frequently than the size, shape, structure of the grid. The compiler knows this is hex grid array and hence the data should be set and retrieved
 * through the [[HGrid]] hex grid. So nearly all the methods take the [[HGrid]] as an implicit parameter. */
class HCenArr[A <: AnyRef](val unsafeArr: Array[A])
{
  def length: Int = unsafeArr.length
  def apply(hc: HCen)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(hc))
  def rc(r: Int, c: Int)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(r, c))
  def mutSetAll(value: A): Unit = iUntilForeach(0, length){i => unsafeArr(i) = value }

  /** For each element in the underlying array performs the side effecting function. This method treats the [[HCenArr]] class like a standard Arr or
   *  Array. It does not utilise the grid [[HGrid]] from which this [[HCenArr]] was created. */
  def foreach[U](f: A => U): Unit = unsafeArr.foreach(f)

  /** Short for coordinate-foreach. For each element in the underlying array, with its respective [[HCen]] coordinate performs the side effecting
   *  function. */
  def cForeach[U](f: (A, HCen) => U)(implicit grid: HGrid): Unit = grid.iForeach{ (hc, i) => f(unsafeArr(i), hc); () }

  /** Each element in the underlying array is mapped by the parameter function to an element of type B. This method treat the [[HCenArr]] class like a
   *  standard Arr or Array. It does not utilise the grid HGrid from which this HCenArr was created. */
  def map[B, BB <: ArrBase[B]](f: A => B)(implicit build: ArrBuilder[B, BB]): BB =
  { val res = build.newArr(length)
    var count = 0
    foreach{a => res.unsafeSetElem(count, f(a)); count += 1 }
    res
  }

  /** Short for map with hex centre coordinate-map. Each element in the underlying array, with its corresponding [[HCen]] coordinate is mapped by the
   *  parameter function to an element of type B. */
  def mapHC[B, BB <: ArrBase[B]](f: (A, HCen) => B)(implicit grid: HGrid, build: ArrBuilder[B, BB]): BB =
  { val res = build.newArr(length)
    grid.iForeach{ (hc, i) =>
      val newElem = f(apply(hc), hc)
      res.unsafeSetElem(i, newElem)
    }
    res
  }

  /** Note set Row starts with the r (row) parameter. */
  final def setRow(row: Int, cStart: Int, tileValues: Multiple[A]*)(implicit grid: HGrid): HCen =
  {
    val tiles: List[A] = tileValues.toSingles
    tiles.iForeach { (e, i) =>
      val c = cStart + i * 4

      unsafeArr(grid.arrIndex(row, c)) = e
    }
    HCen(row, cStart + (tiles.length - 1) * 4)
  }

  def rowCombine(implicit grid: HGrid): Arr[HCenRowValue[A]] =
  {
    grid.flatMapRows[Arr[HCenRowValue[A]]]{ r => if (grid.cenRowEmpty(r)) Arr()
      else
      { var currStart: Int = grid.rowCenStart(r)
        var currC: Int = currStart
        var currVal: A = rc(r, currStart)
        var list: List[HCenRowValue[A]] = Nil
        grid.rowForeach(r){hc =>
          currC = hc.c
          if (apply(hc) != currVal) {
            val newHCenRowValue = HCenRowValue(r, currStart, (currC - currStart + 4) / 4, currVal)
            list :+= newHCenRowValue
            currVal = apply(hc)
            currStart = hc.c
          }

        }
        val newHCenRowValue = HCenRowValue(r, currStart, (currC - currStart + 4) / 4, currVal)
        list :+= newHCenRowValue
        list.toArr
      }
    }
  }

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that tkaes the
   *  [[HSide]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSide]] and the two adjacent hex
   *  tile data values. */
  def sideMap[B, BB <: ArrBase[B]](f1: (HSide, A) => B, f2: (HSide, A, A) => B)(implicit grid: HGrid, build: ArrBuilder[B, BB]): BB =
    grid.sidesMap{ hs => hs.tiles match
      {
        case (c1, c2) if grid.hCenExists(c1) & grid.hCenExists(c2) =>f2(hs, apply(c1), apply(c2))
        case (c1, _) if grid.hCenExists(c1) => f1(hs, apply(c1))
        case (_, c2) => f1(hs, apply(c2))
      }
    }

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   *  [[HSide]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSide]] and the two adjacent hex
   *  tile data values. */
  def sideFlatMap[BB <: ArrBase[_]](f1: (HSide, A) => BB, f2: (HSide, A, A) => BB)(implicit grid: HGrid, build: ArrFlatBuilder[BB]): BB =
    grid.sidesFlatMap{ hs => hs.tiles match
    { case (c1, c2) if grid.hCenExists(c1) & grid.hCenExists(c2) =>f2(hs, apply(c1), apply(c2))
      case (c1, _) if grid.hCenExists(c1) => f1(hs, apply(c1))
      case (_, c2) => f1(hs, apply(c2))
    }
  }
}

/** Companion object for [[HCenArr]], contains an apply factory method. */
object HCenArr
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): HCenArr[A] = new HCenArr[A](new Array[A](length))
}