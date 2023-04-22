/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** A [[HGridSys]] [[HCen]] data layer of [[RArr]]s. */
class HCenArrLayer[A](val unsafeArray: Array[Array[A]])
{
  def apply(hc: HCen)(implicit grider: HGridSys): RArr[A] = new RArr(unsafeArray(grider.layerArrayIndex(hc)))
  def apply(r: Int, c: Int)(implicit grider: HGridSys): RArr[A] = new RArr(unsafeArray(grider.layerArrayIndex(r, c)))

  def empty(r: Int, c: Int)(implicit grider: HGridSys): Boolean = apply(r, c).length == 0
  def empty(hc: HCen)(implicit grider: HGridSys): Boolean = apply(hc).length == 0

  def set1(r: Int, c: Int, value: A)(implicit grider: HGridSys, ct: ClassTag[A]): Unit = setSeq(HCen(r, c), value)

  def setSeq(hc: HCen, values: A*)(implicit grider: HGridSys, ct: ClassTag[A]): Unit =
  { val newElem: Array[A] = new Array[A](values.length)
    values.iForeach((i, v) => newElem(i) = v)
    unsafeArray(grider.layerArrayIndex(hc)) = newElem
  }

  def setSame(value: A, hcs: HCen*)(implicit grider: HGridSys, ct: ClassTag[A]): Unit = hcs.foreach{ hc => setSeq(hc, value) }

  def prepend(r: Int, c: Int, value: A)(implicit grider: HGridSys, ct: ClassTag[A]): Unit = prepend(HCen(r, c), value)

  def prepend(hc: HCen, value: A)(implicit grider: HGridSys, ct: ClassTag[A]): Unit =
  { val oldElem =  unsafeArray(grider.layerArrayIndex(hc))
    val newElem: Array[A] = new Array[A](oldElem.length + 1)
    newElem(0) = value
    oldElem.copyToArray(newElem, 1)
    unsafeArray(grider.layerArrayIndex(hc)) = newElem
  }
  //    def prepends(value : A, roords: Roord*)(implicit grid: TileGridOld): Unit = roords.foreach{ r =>  thisRefs.unsafeArr(grid.arrIndex(r)) ::= value }

  /** flatMaps over the the first element of each tile's data Array. Ignores empty arrays and subsequent elements. */
  def gridHeadsMap[B, BB <: Arr[B]](f: (HCen, A) => B)(implicit grider: HGridSys, build: ArrMapBuilder[B, BB]): BB =
  {
    val buff = build.newBuff()
    grider.foreach { r =>
      val el:RArr[A] = apply(r)
      if (el.length >= 1) build. buffGrow(buff, f(r, el(0)))
    }
    build.buffToSeqLike(buff)
  }

  /** flatMaps over the the first element of each tile's data Array. Ignores empty arrays and subsequent elements. */
  def gridHeadsFlatMap[BB <: Arr[_]](f: (HCen, A) => BB)(implicit grider: HGridSys, build: ArrFlatBuilder[BB]): BB =
  {
    val buff = build.newBuff()
    grider.foreach { r =>
      val el:RArr[A] = apply(r)
      if (el.length >= 1) build.buffGrowArr(buff, f(r, el(0)))
    }
    build.buffToSeqLike(buff)
  }

  /** Uses projection to map the head data value with the corresponding [[HCen]] and the projections corresponding [[Pt2]] to an element of type B. In
   * most cases B will be a [[GraphicElem]] or a subtype. */
  def projSomeHcPtMap[B, ArrB <: Arr[B]](f: (A, HCen, Pt2) => B)(implicit proj: HSysProjection, build: ArrMapBuilder[B, ArrB]): ArrB =
    projSomeHcPtMap(proj)(f)

  /** Uses projection to map the Some head value with the corresponding [[HCen]] and the projections corresponding [[Pt2]] to an element of type B. In
   * most cases B will be a [[GraphicElem]] or a subtype. */
  def projSomeHcPtMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (A, HCen, Pt2) => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB = {
    val buff = build.newBuff()
    proj.gChild.foreach { hc =>
      val array = unsafeArray(proj.parent.layerArrayIndex(hc))
      if (array.length > 0) {
        val res = f(array(0), hc, proj.transCoord(hc))
        build.buffGrow(buff, res)
      }
    }
    build.buffToSeqLike(buff)
  }

  def projEmptyHcPtMap[B, ArrB <: Arr[B]](f: (HCen, Pt2) => B)(implicit proj: HSysProjection, build: ArrMapBuilder[B, ArrB]): ArrB =
    projEmptyHcPtMap(proj)(f)

  def projEmptyHcPtMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (HCen, Pt2) => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.foreach { hc =>
      val array = unsafeArray(proj.parent.layerArrayIndex(hc))
      if (array.length == 0) {
        val res = f(hc, proj.transCoord(hc))
        build.buffGrow(buff, res)
      }
    }
    build.buffToSeqLike(buff)
  }

  def setFSomesMut(f: () => A, hCens: Int*)(implicit grider: HGridSys, ct: ClassTag[A]): Unit = {
    if (hCens.length.isOdd) excep(s"${hCens.length} odd number of int parameters for HCens.")
    iUntilForeach(0, hCens.length, 2) { i => prepend(hCens(i), hCens(i + 1), f())
//      val r = hCens(i)
//      val c = hCens(i + 1)
//      val oldArrA: RArr[A] = apply(r, c)
//      val newArrA: RArr[A] = oldArrA +% f()
//      set1(r, c, newArrA)// unsafeArray(grider.layerArrayIndex(hCens(i), hCens(i + 1))) = f()
    }
  }
}