/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import reflect.ClassTag

/** A Hex grid array class of [[Arr]]s. */
class HCenArrArr[A](val unsafeArray: Array[Array[A]])
{
  def apply(hc: HCen)(implicit grid: HGrid): Arr[A] = new Arr(unsafeArray(grid.arrIndex(hc)))
  def apply(r: Int, c: Int)(implicit grid: HGrid): Arr[A] = new Arr(unsafeArray(grid.arrIndex(r, c)))
  def prepend(r: Int, c: Int, value: A)(implicit grid: HGrid, ct: ClassTag[A]): Unit = prepend(HCen(r, c), value)
  def prepend(hc: HCen, value: A)(implicit grid: HGrid, ct: ClassTag[A]): Unit =
  {
    val oldElem =  unsafeArray(grid.arrIndex(hc))
    val newElem: Array[A] = new Array[A](oldElem.length + 1)
    newElem(0) = value
    oldElem.copyToArray(newElem, 1)
    unsafeArray(grid.arrIndex(hc)) = newElem
  }
  //    def prepends(value : A, roords: Roord*)(implicit grid: TileGridOld): Unit = roords.foreach{ r =>  thisRefs.unsafeArr(grid.arrIndex(r)) ::= value }

}
