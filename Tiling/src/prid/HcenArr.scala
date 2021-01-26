/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import reflect.ClassTag

/** An array of hex tile or hex centre data. */
class HcenArr[A <: AnyRef](val unsafeArr: Array[A])
{
  def length: Int = unsafeArr.length
  def apply(hc: Hcen)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(hc))
  def mutSetAll(value: A): Unit = iUntilForeach(0, length){i => unsafeArr(i) = value }

  /** For each element in the underlying array performs the side effecting function. This method treats the [[HcenArr]] class like a standard Arr or
   *  Array. It does not utilise the grid [[HGrid]] from which this [[HcenArr]] was created. */
  def foreach[U](f: A => U): Unit = unsafeArr.foreach(f)

  /** Short for coordinate-foreach. For each element in the underlying array, with its respective Hcen coordinate performs the side effecting
   *  function. */
  def cForeach[U](f: (A, Hcen) => U)(implicit grid: HGrid): Unit = grid.iForeach{ (hc, i) => f(unsafeArr(i), hc); () }

  /** Each element in the underlying array is mapped by the parameter function to an element of type B. This method treat the HcenArr class like a
   *  standard Arr or Array. It does not utilise the grid HGrid from which this HcenArr was created. */
  def map[B, BB <: ArrImut[B]](f: A => B)(implicit build: ArrTBuilder[B, BB]): BB =
  { val res = build.newArr(length)
    var count = 0
    foreach{a => res.unsafeSetElem(count, f(a)); count += 1 }
    res
  }

  /** Short for coordinate-map. Each element in the underlying array, with its corresponding [[Hcen]] coordinate is mapped by the parameter function
   *  to an element of type B. */
  def cMap[B, BB <: ArrImut[B]](f: (A, Hcen) => B)(implicit grid: HGrid, build: ArrTBuilder[B, BB]): BB =
  { val res = build.newArr(length)
    grid.iForeach{ (hc, i) =>
      val newElem = f(apply(hc), hc)
      res.unsafeSetElem(i, newElem)
    }
    res
  }
}

/** Companion object for [[HcenArr]], contains an apply factory method. */
object HcenArr
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): HcenArr[A] = new HcenArr[A](new Array[A](length))
}
