/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** A [[HGridSys]] data layer of optional tile data. This is specialised for OptRef[A]. The tileGrid can map the [[HCen]] coordinate of the tile to
 *  the index of the Arr. Hence most methods take an implicit [[HGridSys]] hex grid parameter. */
class HCenOptLayer[A <: AnyRef](val unsafeArray: Array[A]) extends AnyVal with TCenOptDGrid[A]
{
  def map[B <: AnyRef](f: A => B)(implicit ct: ClassTag[B]): HCenOptLayer[B] = {
    val newArray = new Array[B](length)
    var i = 0
    while (i < length) { if (unsafeArray(i) != null) newArray(i) = f(unsafeArray(i)); i += 1 }
    new HCenOptLayer[B](newArray)
  }

  def clone: HCenOptLayer[A] = new HCenOptLayer[A](unsafeArray.clone)

  /** Sets the Some value of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSome(r: Int, c: Int, value: A)(implicit grider: HGridSys): Unit = unsafeArray(grider.arrIndex(r, c)) = value

  /** Sets the Some value of the hex tile data at the specified [[HCen]] coordinate. This is an imperative mutating operation. */
  def unsafeSetSome(hc: HCen, value: A)(implicit grider: HGridSys): Unit = unsafeArray(grider.arrIndex(hc)) = value

  /** Sets the Some values of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSomes(triples: (Int, Int, A)*)(implicit grider: HGridSys): Unit = triples.foreach(t => unsafeArray(grider.arrIndex(t._1, t._2)) = t._3)

  /** Mutates the value ot the specified location to None. */
  def unsafeSetNone(hc: HCen)(implicit grider: HGridSys): Unit = unsafeArray(grider.arrIndex(hc)) = null.asInstanceOf[A]

  def unsafeSetAll(value: A): Unit = iUntilForeach(length)(unsafeArray(_) = value)

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(hc: HCen, value: A)(implicit grider: HGridSys): HCenOptLayer[A] =
  { val newArr = unsafeArray.clone()
    newArr(grider.arrIndex(hc)) = value
    new HCenOptLayer[A](newArr)
  }

  /** Creates a new ArrOpt with the specified location set to NoRef. */
  def setNone(hc: HCen)(implicit grider: HGridSys): HCenOptLayer[A] =
  { val newArr = unsafeArray.clone()
    newArr(grider.arrIndex(hc)) = null.asInstanceOf[A]
    new HCenOptLayer[A](newArr)
  }

  /** Moves the object in the array location given by the 1st [[HCen]] to the 2nd [[HCen]], by setting hc2 to the value of hc1 and setting hc1 to
   *  None. */
  def unsafeMove(hc1: HCen, hc2: HCen)(implicit grider: HGridSys): Unit =
  { unsafeArray(grider.arrIndex(hc2)) = unsafeArray(grider.arrIndex(hc1))
    unsafeArray(grider.arrIndex(hc1)) = null.asInstanceOf[A]
  }

  def unsafeMoveMod(hc1: HCen, hc2: HCen)(f: A => A)(implicit grider: HGridSys): Unit =
  { unsafeArray(grider.arrIndex(hc2)) = f(unsafeArray(grider.arrIndex(hc1)))
    unsafeArray(grider.arrIndex(hc1)) = null.asInstanceOf[A]
  }

  /** Drops the [[None]] values. Foreach value of the [[Some]] with the corresponding [[HCen]] applies the side effecting parameter function. */
  def someHCForeach(f: (A, HCen) => Unit)(implicit grider: HGridSys): Unit = grider.foreach { hc =>
    val a = unsafeArray(grider.arrIndex(hc))
    if (a != null) f(a, hc)
  }

  /** Maps the option values with the corresponding [[HCen]] to type B. Hence it takes two functions as parameters one for the [[None]] values and one
   * for the [[Some]] values. */
  def hcMap[B, ArrT <: Arr[B]](fNone: => HCen => B)(fSome: (A, HCen) => B)(implicit grider: HGridSys, build: ArrMapBuilder[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    grider.foreach { hc =>
      val a = unsafeArray(grider.arrIndex(hc))
      if (a != null) build.buffGrow(buff, fSome(a, hc))
      else { val newVal = fNone(hc); build.buffGrow(buff, newVal) }
    }
    build.buffToBB(buff)
  }

  /** Maps the option values with the corresponding [[HCen]] to type B. Hence it takes two functions as parameters one for the [[None]] values and one
   * for the [[Some]] values. */
  def projHcMap(proj: HSysProjection)(fNone: (Pt2, HCen) => GraphicElem)(fSome: (A, Pt2, HCen) => GraphicElem): GraphicElems =
    proj.hCenMap{ (pt, hc) =>
      val a = unsafeArray(proj.parent.arrIndex(hc))
      ife(a != null, fSome(a, pt, hc), fNone(pt, hc))
    }

  /** Indexes in to this [[HCenOptLayer]] using the tile centre coordinate, either passed as an [[HCen]] or as row and column [[Int values]]. */
  def apply(hc: HCen)(implicit grider: HGridSys): Option[A] =
  { if (!grider.hCenExists(hc)) None else
      { val elem = unsafeArray(grider.arrIndex(hc))
        if (elem == null) None else Some(elem)
      }
  }

  /** Indexes in to this [[HCenOptLayer]] using the tile centre coordinate, either passed as an [[HCen]] or as row and column [[Int values]]. */
  def apply(r: Int, c: Int)(implicit grider: HGridSys): Option[A] = {
    if (!grider.hCenExists(r, c)) None else {
      val elem = unsafeArray(grider.arrIndex(r, c))
      if (elem == null) None else Some(elem)
    }
  }

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def unSafeApply(hc: HCen)(implicit grider: HGridSys): A = unsafeArray(grider.arrIndex(hc))

  /** The tile is a None at the given hex grid centre coordinate [[HCen]]. */
  def tileNone(hc: HCen)(implicit grider: HGridSys): Boolean = unsafeArray(grider.arrIndex(hc)) == null

  /** Drops the [[None]] values. Maps the [[Some]]'s value with the corresponding [[HCen]] to value of type B. Returns a [[Seqimut]] of length between
   * 0 and the length of this [[HCenOptLayer]]. */
  def someHCMap[B, ArrB <: Arr[B]](f: (A, HCen) => B)(implicit grider: HGridSys, build: ArrMapBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()

    grider.foreach { hc =>
      val a: A = unsafeArray(grider.arrIndex(hc))
      if(a != null)
      { val newVal = f(a, hc)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Drops the None values mapping the [[Some]]'s value with the [[HCen]] to an option value, collecting the values of the [[Some]]s returned by the
   *  function. Returns a [[Seqimut]] of length 0 to the length of this [[HCenOptLayer]]. */
  def projSomeHCMap(f: (A, HCen) => GraphicElem)(implicit proj: HSysProjection): GraphicElems = projSomeHCMap(proj)(f)

  def projSomeHCMap(proj: HSysProjection)(f: (A, HCen) => GraphicElem): GraphicElems =
  {
    val buff = BuffGraphic()
    proj.gChild.foreach { hc =>
      val a: A = unsafeArray(proj.parent.arrIndex(hc))
      if (a != null) {
        buff.append(f(a, hc))
      }
    }
    buff.toArr
  }

  /** Uses projection to map the Some data value with the corresponing [[HCen]] and the projections corresponding [[Pt2]] to an element of type B. In
   *  most cases B will be a [[GraphicElem]] or a subtype. */
  def projSomeHcPtMap[B, ArrB <: Arr[B]](f: (A, HCen, Pt2) => B)(implicit proj: HSysProjection, build: ArrMapBuilder[B, ArrB]): ArrB =
    projSomeHcPtMap(proj)(f)

  /** Uses projection to map the Some data value with the corresponing [[HCen]] and the projections corresponding [[Pt2]] to an element of type B. In
   * most cases B will be a [[GraphicElem]] or a subtype. */
  def projSomeHcPtMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (A, HCen, Pt2) => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.foreach { hc =>
      val a: A = unsafeArray(proj.parent.arrIndex(hc))
      if (a != null) {
        val res = f(a, hc, proj.transCoord(hc))
        build.buffGrow(buff, res)
      }
    }
    build.buffToBB(buff)
  }

  /** Uses this and a second [[HCenOptLayer]] of type B. Drops all values where either or both [[HCenOptLayer]] have [[None]] values. Maps the
   *  corresponding values of the [[Some]]s to type C. Returns a [[Arr]] of length bwteen 0 na d the length of the original [[HCenOptLayer]]s. */
  def zipSomesMap[B <: AnyRef, C, ArrC <: Arr[C]](optArrB: HCenOptLayer[B])(f: (A, B) => C)(implicit gridSys: HGridSys, build: ArrMapBuilder[C, ArrC]): ArrC =
  { val buff = build.newBuff()

    gridSys.foreach { hc =>
      val a: A = unsafeArray(gridSys.arrIndex(hc))
      val b: B = optArrB.unsafeArray(gridSys.arrIndex(hc))
      if(a != null & b != null)
      { val newVal = f(a, b)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Uses this and a second [[HCenOptLayer]] of type B, combining corresponding pairs of [[Some]] values with the corresponding [[HCen]] and apping
   * to a value of type C. Returns a [[Arr]] with a length between 0 and the length of the original [[HCenOptDGtid]] data grids. */
  def zipSomesHCMap[B <: AnyRef, C, ArrC <: Arr[C]](optArrB: HCenOptLayer[B])(f: (A, B, HCen) => C)(
    implicit grider: HGridSys, build: ArrMapBuilder[C, ArrC]): ArrC =
  { val buff = build.newBuff()

    grider.foreach { hc =>
      val a: A = unsafeArray(grider.arrIndex(hc))
      val b: B = optArrB.unsafeArray(grider.arrIndex(hc))
      if(a != null)
      { val newVal = f(a, b, hc)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Drops the [[Some]] values. Maps the corresponding [[HCen]] for the [[None]] to type B. Returns
   *  a [[Arr]] of length between 0 and the length of this [[HCenOptLayer]]. */
  def noneHCMap[B, ArrB <: Arr[B]](f: HCen => B)(implicit grider: HGridSys, build: ArrMapBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()

    grider.foreach { r =>
      val a: A = unsafeArray(grider.arrIndex(r))
      if(a == null)
      { val newVal = f(r)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  def projNoneHcPtMap[B, ArrB <: Arr[B]](f: (HCen, Pt2) => B)(implicit proj: HSysProjection, build: ArrMapBuilder[B, ArrB]): ArrB =
    projNoneHcPtMap(proj)(f)

  def projNoneHcPtMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (HCen, Pt2) => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB = {
    val buff = build.newBuff()

    proj.gChild.foreach { hc =>
      val a: A = unsafeArray(proj.parent.arrIndex(hc))
      if (a == null) {
        build.buffGrow(buff, f(hc, proj.transCoord(hc)))
      }
    }
    build.buffToBB(buff)
  }

  /** Drops the [[None]] values flatMaps the value of the [[Some]] with the corresponding [[HCen]] to a [[Seqimut]]. */
  def someHCFlatMap[ArrT <: Arr[_]](f: (A, HCen) => ArrT)(implicit grider: HGridSys, build: ArrFlatBuilder[ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grider.foreach { hc =>
      val a = unsafeArray(grider.arrIndex(hc))
      if(a != null)
      { val newVal = f(a, hc)
        build.buffGrowArr(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Drops the None values, flatMaps the [[Some]]'s value and the corresponding [[HCen]] to an [[option]] of a [[Arr]], collects only the
   *  [[Some]]'s values returned by the function. */
  def someHCOptFlatMap[ArrB <: Arr[_]](f: (A, HCen) => Option[ArrB])(implicit grider: HGridSys, build: ArrFlatBuilder[ArrB]): ArrB = {
    val buff = build.newBuff()

    grider.foreach { hc =>
      val a: A = unsafeArray(grider.arrIndex(hc))
      if (a != null) {
        f(a, hc).foreach(build.buffGrowArr(buff, _))
      }
    }
    build.buffToBB(buff)
  }

  def SomesKeyMap(implicit grider: HGridSys): Map[A, HCen] =
  { val build = Map.newBuilder[A, HCen]
    someHCForeach((p, hc) => build.addOne(p, hc))
    build.result
  }

  /** Returns an [[HCenPairArr]] of the Some values. */
  def somePairArr(implicit grider: HGridSys, build: HCenPairArrMapBuilder[A]): HCenPairArr[A] =
  { val buff = build.newBuff()
    someHCForeach((p, hc) => buff.grow(hc, p))
    build.buffToBB(buff)
  }

  def find(value: A)(implicit grider: HGridSys): Option[HCen] =
  { var res: Option[HCen] = None
    someHCForeach{ (a, hc) => if (value == a) res = Some(hc)}
    res
  }

  def get(value: A)(implicit grider: HGridSys): HCen = find(value).get
}