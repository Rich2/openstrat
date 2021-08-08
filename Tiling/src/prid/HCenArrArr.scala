/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import reflect.ClassTag

/** A Hex grid array class of [[Arr]]s. */
class HCenArrArr[A](val unsafeArray: Array[Array[A]])
{
  def apply(hc: HCen)(implicit grid: HGrid): Arr[A] = new Arr(unsafeArray(grid.arrIndex(hc)))
  def apply(r: Int, c: Int)(implicit grid: HGrid): Arr[A] = new Arr(unsafeArray(grid.arrIndex(r, c)))

  def set(r: Int, c: Int, value: A)(implicit grid: HGrid, ct: ClassTag[A]): Unit = set(HCen(r, c), value)

  def set(hc: HCen, values: A*)(implicit grid: HGrid, ct: ClassTag[A]): Unit =
  { val newElem: Array[A] = new Array[A](values.length)
    values.iForeach((v ,i) => newElem(i) = v)
    unsafeArray(grid.arrIndex(hc)) = newElem
  }

  def setSame(value: A, hcs: HCen*)(implicit grid: HGrid, ct: ClassTag[A]): Unit = hcs.foreach{ hc => set(hc, value) }

  def prepend(r: Int, c: Int, value: A)(implicit grid: HGrid, ct: ClassTag[A]): Unit = prepend(HCen(r, c), value)

  def prepend(hc: HCen, value: A)(implicit grid: HGrid, ct: ClassTag[A]): Unit =
  { val oldElem =  unsafeArray(grid.arrIndex(hc))
    val newElem: Array[A] = new Array[A](oldElem.length + 1)
    newElem(0) = value
    oldElem.copyToArray(newElem, 1)
    unsafeArray(grid.arrIndex(hc)) = newElem
  }
  //    def prepends(value : A, roords: Roord*)(implicit grid: TileGridOld): Unit = roords.foreach{ r =>  thisRefs.unsafeArr(grid.arrIndex(r)) ::= value }

  /** flatMaps over the the first element of each tile's data Array. Ignores empty arrays and subsequent elements. */
  def gridHeadsMap[B, BB <: ArrBase[B]](f: (HCen, A) => B)(implicit grid: HGrid, build: ArrBuilder[B, BB]): BB =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val el:Arr[A] = apply(r)
      if (el.elemsNum >= 1) build. buffGrow(buff, f(r, el(0)))
    }
    build.buffToBB(buff)
  }

  /** flatMaps over the the first element of each tile's data Array. Ignores empty arrays and subsequent elements. */
  def gridHeadsFlatMap[BB <: ArrBase[_]](f: (HCen, A) => BB)(implicit grid: HGrid, build: ArrFlatBuilder[BB]): BB =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val el:Arr[A] = apply(r)
      if (el.elemsNum >= 1) build.buffGrowArr(buff, f(r, el(0)))
    }
    build.buffToBB(buff)
  }
}