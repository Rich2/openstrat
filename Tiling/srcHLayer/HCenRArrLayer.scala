/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** A [[HGridSys]] [[HCen]] data layer of [[RArr]]s. */
class HCenRArrLayer[A](val outerArrayUnsafe: Array[Array[A]], val gridSys: HGridSys)(implicit ct: ClassTag[A]) extends HCenArrLayer[A, RArr[A]]
{ override def apply(hc: HCen): RArr[A] = new RArr(outerArrayUnsafe(gridSys.layerArrayIndex(hc)))
  override def apply(r: Int, c: Int): RArr[A] = new RArr(outerArrayUnsafe(gridSys.layerArrayIndex(r, c)))
  override def iApply(index: Int): RArr[A] = new RArr(outerArrayUnsafe(index))
  def applyUnsafe(hc: HCen): Array[A] = outerArrayUnsafe(gridSys.layerArrayIndex(hc))

  def applyUnsafe(r: Int, c: Int): Array[A] = outerArrayUnsafe(gridSys.layerArrayIndex(r, c))

  /** Checks if the [[Arr]] is empty for the given [[HCen]]. */
  def emptyTile(r: Int, c: Int): Boolean = apply(r, c).length == 0

  /** Checks if the [[Arr]] is empty for the given [[HCen]]. */
  def emptyTile(hc: HCen): Boolean = apply(hc).length == 0

  def nonEmptyTile(r: Int, c: Int): Boolean = apply(r, c).length > 0

  def noneEmptyTile(hc: HCen): Boolean = apply(hc).length > 0

  def tileHeadGet(hCen: HCen): A = outerArrayUnsafe(gridSys.layerArrayIndex(hCen)).head

  def copy: HCenRArrLayer[A] = new HCenRArrLayer[A](outerArrayUnsafe.clone, gridSys)

  def set1(r: Int, c: Int, value: A): Unit = setArr(HCen(r, c), value)

  def setArray(hc: HCen, newArray: Array[A]): Unit = outerArrayUnsafe(gridSys.layerArrayIndex(hc)) = newArray

  def setArr(hc: HCen, values: A*): Unit =
  { val newElem: Array[A] = new Array[A](values.length)
    values.iForeach((i, v) => newElem(i) = v)
    outerArrayUnsafe(gridSys.layerArrayIndex(hc)) = newElem
  }

  def setArr(r: Int, c: Int, values: A*): Unit =
  { val newElem: Array[A] = new Array[A](values.length)
    values.iForeach((i, v) => newElem(i) = v)
    outerArrayUnsafe(gridSys.layerArrayIndex(r, c)) = newElem
  }
  def setSame(value: A, hcs: HCen*): Unit = hcs.foreach{ hc => setArr(hc, value) }

  /** Prepends to tile's [[Arr]]. */
  def prepend(r: Int, c: Int, value: A): Unit = prepend(HCen(r, c), value)

  /** Prepends to tile's [[Arr]]. */
  def prepend(hc: HCen, value: A): Unit =
  { val oldElem =  outerArrayUnsafe(gridSys.layerArrayIndex(hc))
    val newElem: Array[A] = new Array[A](oldElem.length + 1)
    newElem(0) = value
    oldElem.copyToArray(newElem, 1)
    outerArrayUnsafe(gridSys.layerArrayIndex(hc)) = newElem
  }

  /** Foreach's over the element of the  arrays with their respective [[HCen]]s. Applying the side effecting function. */
  override def foreachHcForeach(f: (HCen, A) => Unit)(implicit gSys: HGridSys): Unit = gSys.foreach{ hc => applyUnsafe(hc).foreach(a => f(hc, a)) }

  override def foreach(f: RArr[A] => Unit): Unit =
  { var i = 0
    outerArrayUnsafe.foreach{ array =>
      f(new RArr(array))
      i += 1
    }
  }

  override def iForeach(f: (Int, RArr[A]) => Unit): Unit =
  { var i = 0
    outerArrayUnsafe.foreach { array =>
      f(i, new RArr(array))
      i += 1
    }
  }

  /** Maps over the the first element of each tile's data Array. Ignores empty arrays and subsequent elements. */
  def headsMap[B, BB <: Arr[B]](f: (HCen, A) => B)(implicit gSys: HGridSys, build: MapBuilderArr[B, BB]): BB =
  { val buff = build.newBuff()
    gSys.foreach { r =>
      val el:RArr[A] = apply(r)
      if (el.length >= 1) build. buffGrow(buff, f(r, el(0)))
    }
    build.buffToSeqLike(buff)
  }

  /** flatMaps over the the first element of each tile's data Array. Ignores empty arrays and subsequent elements. */
  def headsFlatMap[BB <: Arr[_]](f: (HCen, A) => BB)(implicit build: FlatBuilderArr[BB]): BB =
  { val buff = build.newBuff()
    gridSys.foreach { r =>
      val el:RArr[A] = apply(r)
      if (el.length >= 1) build.buffGrowArr(buff, f(r, el(0)))
    }
    build.buffToSeqLike(buff)
  }

  /** FlatMaps the head values of the [[Arr]], if the [[Arr]] is none empty, with the corresponding [[HCen]] to a [[Seqimut]]. */
  def headsHcFlatMap[ArrT <: Arr[_]](f: (A, HCen) => ArrT)(implicit build: FlatBuilderArr[ArrT]): ArrT =
  { val buff = build.newBuff()
    gridSys.foreach { hc =>
      if (noneEmptyTile(hc)) {
        val newVal = f(tileHeadGet(hc), hc)
        build.buffGrowArr(buff, newVal)
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Uses projection to map the head data value with the corresponding [[HCen]] and the projections corresponding [[Pt2]] to an element of type B. In
   * most cases B will be a [[GraphicElem]] or a subtype. */
  def projHeadsHcPtMap[B, ArrB <: Arr[B]](f: (A, HCen, Pt2) => B)(implicit proj: HSysProjection, build: MapBuilderArr[B, ArrB]): ArrB =
    projHeadsHcPtMap(proj)(f)

  /** Uses projection to map the Some head value with the corresponding [[HCen]] and the projections corresponding [[Pt2]] to an element of type B. In
   * most cases B will be a [[GraphicElem]] or a subtype. */
  def projHeadsHcPtMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (A, HCen, Pt2) => B)(implicit build: MapBuilderArr[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.foreach { hc =>
      val array = outerArrayUnsafe(proj.parent.layerArrayIndex(hc))
      if (array.length > 0)
      { val res = f(array(0), hc, proj.transCoord(hc))
        build.buffGrow(buff, res)
      }
    }
    build.buffToSeqLike(buff)
  }

  def projSomesHcPtMap[B, ArrB <: Arr[B]](f: (RArr[A], HCen, Pt2) => B)(implicit proj: HSysProjection, build: MapBuilderArr[B, ArrB]): ArrB =
    projSomesHcPtMap(proj)(f)

  /** Uses projection to map the non empty ArrSome head value with the corresponding [[HCen]] and the projections corresponding [[Pt2]] to an element of type B. In
   * most cases B will be a [[GraphicElem]] or a subtype. */
  def projSomesHcPtMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (RArr[A], HCen, Pt2) => B)(implicit build: MapBuilderArr[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.foreach { hc =>
      val arr = apply(hc)
      if (arr.length > 0) proj.transOptCoord(hc).foreach { pt =>
        val res = f(arr, hc, pt)
        buff.grow(res)
      }
    }
    build.buffToSeqLike(buff)
  }

  def projEmptyHcPtMap[B, ArrB <: Arr[B]](f: (HCen, Pt2) => B)(implicit proj: HSysProjection, build: MapBuilderArr[B, ArrB]): ArrB =
    projEmptyHcPtMap(proj)(f)

  def projEmptyHcPtMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (HCen, Pt2) => B)(implicit build: MapBuilderArr[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.foreach { hc =>
      val array = outerArrayUnsafe(proj.parent.layerArrayIndex(hc))
      if (array.length == 0) {
        val res = f(hc, proj.transCoord(hc))
        build.buffGrow(buff, res)
      }
    }
    build.buffToSeqLike(buff)
  }

  def setFSomesMut(f: () => A, hCens: Int*)(implicit ct: ClassTag[A]): Unit =
  { if (hCens.length.isOdd) excep(s"${hCens.length} odd number of int parameters for HCens.")
    iUntilForeach(0, hCens.length, 2) { i => prepend(hCens(i), hCens(i + 1), f()) }
  }

  /** Takes an element that fulfills a predicate from one tile location and appends it to the Array at the target location. */
  def moveIfUnsafe(origCen: HCen, targetCen: HCen, pred: A => Boolean): Unit =
  { val origArray = applyUnsafe(origCen)
    val origIndex: Int = origArray.indexWhere(pred)
    val a = origArray(origIndex)
    val newOrigArray = origArray.removeAt(origIndex)
    setArray(origCen, newOrigArray)
    val targetArray = applyUnsafe(targetCen)
    val newTargetArray = targetArray :+ a
    setArray(targetCen, newTargetArray)
  }

  /** Takes an element from one tile location and appends it to the Array at the target location. */
  def moveUnsafe(origCen: HCen, targetCen: HCen, value: A): Unit =
  { val origArray = applyUnsafe(origCen)
    val origIndex: Int = origArray.indexOf(value)
    val a = origArray(origIndex)
    val newOrigArray = origArray.removeAt(origIndex)
    setArray(origCen, newOrigArray)
    val targetArray = applyUnsafe(targetCen)
    val newTargetArray = targetArray :+ a
    setArray(targetCen, newTargetArray)
  }


  /** Takes an element from one tile location, mutates it and then appends it to target location. */
  def mutateMoveUnsafe(origCen: HCen, targetCen: HCen, pred: A => Boolean)(f: A => A): Unit =
  { val origArray = applyUnsafe(origCen)
    val origIndex: Int = origArray.indexWhere(pred)
    val a = origArray(origIndex)
    val newOrigArray = origArray.removeAt(origIndex)
    setArray(origCen, newOrigArray)
    val targetArray = applyUnsafe(targetCen)
    val newTargetArray = targetArray :+ f(a)
    setArray(targetCen, newTargetArray)
  }
}

/** Companion object for the [[HCenRArrLayer]] class, contains factory apply methods that take the [[HGridSys]] implicitly or explicitly. */
object HCenRArrLayer
{  /** Factory apply method for an [[HGridSys]] [[HCen]] data layer of [[RArr]]s. There is a name overload of this method where the [[HGridSys]] is
   * passed explicitly as the sole paramter of the first parameter list. */
  def apply[A <: AnyRef]()(implicit ct: ClassTag[A], gridSys: HGridSys): HCenRArrLayer[A] = apply(gridSys)(ct)

  /** Factory apply method for an [[HGridSys]] [[HCen]] data layer of [[RArr]]s. There is a name overload of this method where the [[HGridSys]] is
   * passed implicitly. */
  def apply[A <: AnyRef](gridSys: HGridSys)(implicit ct: ClassTag[A]): HCenRArrLayer[A] =
  { val newArray = new Array[Array[A]](gridSys.numTiles)
    val init: Array[A] = Array()
    iUntilForeach(gridSys.numTiles)(newArray(_) = init)
    new HCenRArrLayer[A](newArray, gridSys)(ct)
  }
}