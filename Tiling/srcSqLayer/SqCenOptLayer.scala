/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, reflect.ClassTag

/** A layer of immutable optional [[SqCen]] data for a [[SqGridSys]] square grid system, This is specialised for OptRef[A]. The tileGrid can map the
 *  [[SqCen]] coordinate of the tile to the index of the Arr. Hence most methods take an implicit [[SqGridSys]] square grid system parameter. */
class SqCenOptLayer[A <: AnyRef](val unsafeArray: Array[A]) extends AnyVal with TCenOptLayer[A]
{override type ThisT = SqCenOptLayer[A]
  override def typeStr: String = "SqCenOptLayer"

  /** Indexes in to this [[SqCenOptLayer]] using the tile centre coordinate, either passed as an [[SqCen]] or as row and column [[Int values]]. */
  def apply(hc: SqCen)(implicit gridSys: SqGridSys): Option[A] =
  { if (!gridSys.sqCenExists(hc)) None
    else
    { val elem = unsafeArray(gridSys.layerArrayIndex(hc))
      if (elem == null) None else Some(elem)
    }
  }

  /** Indexes in to this [[SqCenOptLayer]] using the tile centre coordinate, either passed as an [[SqCen]] or as row and column [[Int values]]. */
  def apply(r: Int, c: Int)(implicit gridSys: SqGridSys): Option[A] =
  { if (!gridSys.sqCenExists(r, c)) None else {
      val elem = unsafeArray(gridSys.layerArrayIndex(r, c))
      if (elem == null) None else Some(elem)
    }
  }
  /** Indexes in to this [[SqCenOptLayer]] using the tile centre coordinate, returns the raw value which might be a null. */
  def applyUnsafe(hc: SqCen)(implicit gridSys: SqGridSys): A = unsafeArray(gridSys.layerArrayIndex(hc))

  /** Indexes in to this [[SqCenOptLayer]] using the tile centre coordinate, returns the raw value which might be a null. */
  def applyUnsafe(r: Int, c: Int)(implicit gridSys: SqGridSys): A = unsafeArray(gridSys.layerArrayIndex(r, c))


  def copy: SqCenOptLayer[A] = new SqCenOptLayer[A](unsafeArray.clone)

  /** Sets / mutates the Some value of the hex tile data at the specified row and column coordinate values. */
  def setSomeMut(r: Int, c: Int, value: A)(implicit gridSys: SqGridSys): Unit = unsafeArray(gridSys.layerArrayIndex(r, c)) = value

  /** Sets / the Some value of the hex tile data at the specified [[SqCen]] coordinate. This is an imperative mutating operation. */
  def setSomeMut(hc: SqCen, value: A)(implicit gridSys: SqGridSys): Unit = unsafeArray(gridSys.layerArrayIndex(hc)) = value

  /** Sets / mutates the value ot the specified location to None. */
  def setNoneMut(hc: SqCen)(implicit gridSys: SqGridSys): Unit = unsafeArray(gridSys.layerArrayIndex(hc)) = null.asInstanceOf[A]

  /** Sets the Some value of the square tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSome(r: Int, c: Int, value: A)(implicit gridSys: SqGridSys): Unit = unsafeArray(gridSys.layerArrayIndex(r, c)) = value

  /** Sets the Some value of the hex tile data at the specified [[SqCen]] coordinate. This is an imperative mutating operation. */
  def unsafeSetSome(sc: SqCen, value: A)(implicit gridSys: SqGridSys): Unit = unsafeArray(gridSys.layerArrayIndex(sc)) = value

  /** Sets the Some values of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSomes(triples: (Int, Int, A)*)(implicit gridSys: SqGridSys): Unit = triples.foreach(t => unsafeArray(gridSys.layerArrayIndex(t._1, t._2)) = t._3)

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(r: Int, c: Int, value: A)(implicit gridSys: SqGridSys): SqCenOptLayer[A] = setSome(SqCen(r, c), value)

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(sc: SqCen, value: A)(implicit gridSys: SqGridSys): SqCenOptLayer[A] =
  { val newArr = unsafeArray.clone()
    newArr(gridSys.layerArrayIndex(sc)) = value
    new SqCenOptLayer[A](newArr)
  }

  /** Creates a new ArrOpt with the specified location set to NoRef. */
  def setNone(hc: SqCen)(implicit gridSys: SqGridSys): SqCenOptLayer[A] =
  { val newArr = unsafeArray.clone()
    newArr(gridSys.layerArrayIndex(hc)) = null.asInstanceOf[A]
    new SqCenOptLayer[A](newArr)
  }

  def somesMap[B, ArrT <: Arr[B]](fSome: A => B)(implicit gridSys: SqGridSys, build: BuilderArrMap[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    gridSys.foreach { hc =>
      val a = unsafeArray(gridSys.layerArrayIndex(hc))
      if (a != null) build.buffGrow(buff, fSome(a))
    }
    build.buffToSeqLike(buff)
  }

  /** Drops the [[None]] values. Foreach value of the [[Some]] with the corresponding [[SqCen]] applies the side effecting parameter function. */
  def somesScForeach(f: (A, SqCen) => Unit)(implicit gridSys: SqGridSys): Unit = gridSys.foreach { hc =>
    val a = unsafeArray(gridSys.layerArrayIndex(hc))
    if (a != null) f(a, hc)
  }
  /** The tile is a None at the given hex grid centre coordinate [[SqCen]]. */
  def emptyTile(sc: SqCen)(implicit gridSys: SqGridSys): Boolean = unsafeArray(gridSys.layerArrayIndex(sc)) == null

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def unSafeApply(sc: SqCen)(implicit gridSys: SqGridSys): A = unsafeArray(gridSys.layerArrayIndex(sc))

  def somesForeach(f: A => Unit)(implicit gridSys: SqGridSys): Unit = gridSys.foreach { sc =>
    val a: A = unsafeArray(gridSys.layerArrayIndex(sc))
    if (a != null) f(a)
  }

  def somesLen: Int =
  { var res = 0
    unsafeArray.foreach(a => if (a != null) res += 1)
    res
  }

  /** Maps this [[SqCenOptLayer]] to a new [[SqCenOptLayer]] of type B. The None values are just mapped to Nones. The [[SqGridSys]] that encodes the
   *  layer is not required for this operation. */
  def map[B <: AnyRef](f: A => B)(implicit ct: ClassTag[B]): SqCenOptLayer[B] =
  { val newArray = new Array[B](flatLength)
    var i = 0
    while (i < flatLength) {
      if (unsafeArray(i) != null) newArray(i) = f(unsafeArray(i)); i += 1
    }
    new SqCenOptLayer[B](newArray)
  }

  /** Maps the [[SqCen]]s with the corresponding Some values to a new [[SqCenOptLayer]] of type B. The None values are just mapped to Nones. */
  def scMap[B <: AnyRef](f: (SqCen, A) => B)(implicit ct: ClassTag[B], gridSys: SqGridSys): SqCenOptLayer[B] =
  { val newArray = new Array[B](flatLength)
    gridSys.foreach { hc =>
      val i = gridSys.layerArrayIndex(hc)
      val aUnsafe = unsafeArray(i)
      if (aUnsafe != null) newArray(i) = f(hc, aUnsafe)
    }
    new SqCenOptLayer[B](newArray)
  }

  /** [[SqCen]] with Some map. Maps the Some values of this [[SqCenArrOpt]], with the respective [[SqCen]] coordinate to type B, the first type
   *  parameter B.  Returns an immutable Array based collection of type ArrT, the second type parameter. */
  def scSomesMap[B, ArrB <: Arr[B]](f: (SqCen, A) => B)(implicit gridSys: SqGridSys, build: BuilderArrMap[B, ArrB]): ArrB =
  { val len = somesLen
    val res = build.uninitialised(len)
    var i = 0

    gridSys.foreach { sc =>
      val a: A = unsafeArray(gridSys.layerArrayIndex(sc))
      if(a != null)
      { val newVal = f(sc, a)
        res.setElemUnsafe(i, newVal)
        i += 1
      }

    }
    res
  }

  /** [[SqCen]] with Some flatMap. FlatMaps the Some values of this [[SqCenArrOpt]], with the respective [[SqCen]] coordinate to type ArrB. Returns an
   *  immutable Array based collection of type ArrB. */
  def scSomesFlatMap[ArrB <: Arr[_]](f: (SqCen, A) => ArrB)(implicit gridSys: SqGridSys, build: BuilderArrFlat[ArrB]): ArrB =
  { var buff = build.newBuff()

    gridSys.foreach { sc =>
      val a: A = unsafeArray(gridSys.layerArrayIndex(sc))
      if (a != null) {
        val newArr = f(sc, a)
        build.buffGrowArr(buff, newArr)
      }

    }
    build.buffToSeqLike(buff)
  }

  def scSomesMapPair[B1, ArrB1 <: Arr[B1], B2, B <: PairNoA1ParamElem[B1, B2], ArrB <: PairNoA1PramArr[B1, ArrB1, B2, B]](f1: (SqCen, A) => B1)(f2: (SqCen, A) => B2)(
  implicit gridSys: SqGridSys, build: PairArrMapBuilder[B1, ArrB1, B2, B, ArrB]): ArrB =
  { val len = somesLen
    val res1 = build.b1Uninitialised(len)
    val res2 = new Array[B2](len)(build.b2ClassTag)
    var i = 0

    gridSys.foreach { sc =>
      val a: A = unsafeArray(gridSys.layerArrayIndex(sc))
      if (a != null) {
        val new1 = f1(sc, a)
        res1.setElemUnsafe(i, new1)
        res2(i) = f2(sc, a)
        i += 1
      }
    }
    build.arrFromArrAndArray(res1, res2)
  }

  /** Drops the None values, flatMaps the [[Some]]'s value and the corresponding [[SqCen]] to an [[option]] of a [[Arr]], collects only the
   * [[Some]]'s values returned by the function. */
  def someSCOptFlatMap[ArrB <: Arr[_]](f: (A, SqCen) => Option[ArrB])(implicit gridSys: SqGridSys, build: BuilderArrFlat[ArrB]): ArrB = {
    val buff = build.newBuff()

    gridSys.foreach { hc =>
      val a: A = unsafeArray(gridSys.layerArrayIndex(hc))
      if (a != null) {
        f(a, hc).foreach(build.buffGrowArr(buff, _))
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Drops the None values mapping the [[Some]]'s value with the [[SqCen]] to an option value, collecting the values of the [[Some]]s returned by the
   * function. Returns a [[Seqimut]] of length 0 to the length of this [[SqCenOptLayer]]. */
  def projSomeScPtMap[B, ArrB <: Arr[B]](f: (A, SqCen, Pt2) => B)(implicit proj: SqSysProjection, build: BuilderArrMap[B, ArrB]): ArrB =
    projSomeScPtMap(proj)(f)(build)

  def projSomeScPtMap[B, ArrB <: Arr[B]](proj: SqSysProjection)(f: (A, SqCen, Pt2) => B)(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.foreach { sc =>
      val a: A = unsafeArray(proj.parent.layerArrayIndex(sc))
      if (a != null) {
        build.buffGrow(buff, f(a, sc, proj.transCoord(sc)))
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Coordinate map Nones. Map the None values respective [[SqCen]] coordinates of this [[SqCenOptLayer]] to type B, the first type parameter. Returns
   *  an immutable Array based collection of type ArrT, the second type parameter. */
  def scNoneMap[B, ArrT <: Arr[B]](f: SqCen => B)(implicit gridSys: SqGridSys, build: BuilderArrMap[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    gridSys.foreach { r =>
      val a: A = unsafeArray(gridSys.layerArrayIndex(r))
      if(a == null)
      { val newVal = f(r)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Coordinate map Nones. Map the None values respective [[SqCen]] coordinates of this [[SqCenOptLayer]] to type B, the first type parameter. Returns
   * an immutable Array based collection of type ArrT, the second type parameter. */
  def projNoneScPtMap[B, ArrB <: Arr[B]](f: (SqCen, Pt2) => B)(implicit proj: SqSysProjection, build: BuilderArrMap[B, ArrB]): ArrB = projNoneScPtMap(proj)(f)

  /** Coordinate map Nones. Map the None values respective [[SqCen]] coordinates of this [[SqCenOptLayer]] to type B, the first type parameter. Returns
   * an immutable Array based collection of type ArrT, the second type parameter. */
  def projNoneScPtMap[B, ArrB <: Arr[B]](proj: SqSysProjection)(f: (SqCen, Pt2) => B)(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.foreach { sc =>
      val a: A = unsafeArray(proj.parent.layerArrayIndex(sc))
      if (a == null) {
        val newVal = f(sc, proj.transCoord(sc))
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Moves the object in the array location given by SqCen1 to SqCen2, by setting s2 to the value of s1 and setting s1 to null. */
  def moveUnsafe(s1: SqCen, s2: SqCen)(implicit gridSys: SqGridSys): Unit =
  { unsafeArray(gridSys.layerArrayIndex(s2)) = unsafeArray(gridSys.layerArrayIndex(s1))
    unsafeArray(gridSys.layerArrayIndex(s1)) = null.asInstanceOf[A]
  }

  /** coordinate-foreach-Some. Foreach Some element and its associated [[SqCen]] coordinate applies the side effecting parameter function. It ignores
   *  the None values. */
  def someScForeach(f: (SqCen, A) => Unit)(implicit gridSys: SqGridSys): Unit = gridSys.foreach { sc =>
    val a = unsafeArray(gridSys.layerArrayIndex(sc))
    if (a != null) f(sc, a)
  }

  /** Maps the Somes of this [[SqCenArrOpt]] and the Some values of a second [[SqCenOptLayer]]. Returns an immutable Array based collection of type
   *  ArrC, the second type parameter. */
  def some2sMap[B <: AnyRef, C, ArrC <: Arr[C]](optArrB: SqCenOptLayer[B])(f: (A, B) => C)(implicit gridSys: SqGridSys, build: BuilderArrMap[C, ArrC]): ArrC =
  { val buff = build.newBuff()

    gridSys.foreach { hc =>
      val a: A = unsafeArray(gridSys.layerArrayIndex(hc))
      val b: B = optArrB.unsafeArray(gridSys.layerArrayIndex(hc))
      if(a != null & b != null)
      { val newVal = f(a, b)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToSeqLike(buff)
  }

  def keyMap(implicit gridSys: SqGridSys): Map[A, SqCen] =
  { val build = Map.newBuilder[A, SqCen]
    someScForeach((p, hc) => build.addOne(hc, p))
    build.result
  }

  /** Moves the object in the array location given by the 1st [[SqCen]] to the 2nd [[SqCen]], by setting sc2 to the value of sc1 and setting sc1 to
   *  None. */
  def unsafeMove(sc1: SqCen, sc2: SqCen)(implicit gridSys: SqGridSys): Unit =
  { unsafeArray(gridSys.layerArrayIndex(sc2)) = unsafeArray(gridSys.layerArrayIndex(sc1))
    unsafeArray(gridSys.layerArrayIndex(sc1)) = null.asInstanceOf[A]
  }
}