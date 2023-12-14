/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

trait LayerHcOpt[A <: AnyRef] extends Any with LayerTcOpt[A]
{ type KeyT <: HCenStruct
}

/** [[HCen]] layer for a hex row. */
class LayerHcOptRow[A <: AnyRef](val row: Int, val arrayUnsafe: Array[A]) extends LayerHcOpt[A]
{ type ThisT = LayerHcOptSys[A]
  override type KeyT = HCenRow
  override def typeStr: String = "HRow"
  def numTiles: Int = arrayUnsafe.length

  override def equals(obj: Any): Boolean = obj match
  { case op: LayerHcOptRow[_] => row == op.row && arrayUnsafe.sameElements(op.arrayUnsafe)
    case _ => false
  }
}

object LayerHcOptRow
{
  def apply[A <: AnyRef](row: Int, elems: Multiple[A | None.type ]*)(implicit ct: ClassTag[A]): LayerHcOptRow[A] =
  { val array = new Array[A](elems.numSingles)
    var i = 0
    elems.foreach { m => m.foreach{ a =>
        if (a.isInstanceOf[None.type]) array(i) = null.asInstanceOf[A]
        else array(i) = a.asInstanceOf[A]
        i += 1
      }
    }
    new LayerHcOptRow[A](row, array)
  }

  def apply[A <: AnyRef](row: Int, array: Array[A]): LayerHcOptRow[A] = new LayerHcOptRow[A](row, array)

  implicit def showEv[A <: AnyRef](implicit evA: Show[A]): Show1OptRepeat[Int, A, LayerHcOptRow[A]] =
    Show1OptRepeat[Int, A, LayerHcOptRow[A]]("HRow", "row", _.row, "values", _.arrayUnsafe)

  implicit def unshow[A <: AnyRef](implicit evA: Unshow[A], ct: ClassTag[A]): Unshow1OptRepeat[Int, A, LayerHcOptRow[A]] =
    Unshow1OptRepeat[Int, A, LayerHcOptRow[A]]("HRow", "row", "values", (a1, ars) => new LayerHcOptRow[A](a1, ars))
}

/** [[HCen]] layer for a hex row. */
class LayerHcOptGrid[A <: AnyRef](val grid: HGrid, val arrayUnsafe: Array[A])(implicit ct: ClassTag[A]) extends LayerHcOpt[A]
{ type ThisT = LayerHcOptSys[A]
  override type KeyT = HCenRow
  override def typeStr: String = "HRow"
  def numTiles: Int = arrayUnsafe.length
  def apply(hCen: HCen): A = arrayUnsafe(grid.layerArrayIndex(hCen))
  def apply(r: Int, c: Int): A = arrayUnsafe(grid.layerArrayIndex(r, c))
  override def equals(obj: Any): Boolean = obj match
  { case op: LayerHcOptGrid[_] => grid == op.grid && arrayUnsafe.sameElements(op.arrayUnsafe)
    case _ => false
  }

  def rowsForeach(f: LayerHcOptRow[A] => Unit): Unit = grid.allRowsForeach{hcr =>
    val len = hcr.numTiles
    val array = new Array[A](len)
    val st = hcr.cStart
    iUntilForeach(len){i => array(i) = apply(hcr.r, st + i * 4) }
    val lhor: LayerHcOptRow[A] = LayerHcOptRow(hcr.r, array)
    f(lhor)
  }
}

object LayerHcOptGrid
{
  implicit def showEv[A <: AnyRef](implicit evA: Show[A]): ShowSeqLike[LayerHcOptRow[A], LayerHcOptGrid[A]] =
    ShowSeqLike[LayerHcOptRow[A], LayerHcOptGrid[A]]("LayerHcOptGrid", (obj, f) => obj.rowsForeach(f))
}

/** A [[HGridSys]] data layer of optional tile data. This is specialised for OptRef[A]. The tileGrid can map the [[HCen]] coordinate of the tile to
 *  the index of the Arr. Hence most methods take an implicit [[HGridSys]] hex grid parameter. */
class LayerHcOptSys[A <: AnyRef](val arrayUnsafe: Array[A]) extends AnyVal with LayerHcOpt[A] with LayerHcSys[Option[A]]
{ override type ThisT = LayerHcOptSys[A]
  override type KeyT = HGridSys
  override def typeStr: String = "HCenOptLayer"

  /** Maps this [[LayerHcOptSys]] to a new [[LayerHcOptSys]] of type B. [[None]] values are just mapped to [[None]]s. The [[HGridSys]] that encodes the
   *  layer is not required for this operation. */
  def map[B <: AnyRef](f: A => B)(implicit ct: ClassTag[B]): LayerHcOptSys[B] =
  { val newArray = new Array[B](flatLength)
    var i = 0
    while (i < flatLength) { if (arrayUnsafe(i) != null) newArray(i) = f(arrayUnsafe(i)); i += 1 }
    new LayerHcOptSys[B](newArray)
  }

  /** Maps the corresponding [[HCen]]s with the [[Some]] values to a new [[LayerHcOptSys]] to a new [[LayerHcOptSys]] of type B. [[None]] values are
   *  just mapped to [[None]]s. */
  def hcMap[B <: AnyRef](f: (HCen, A) => B)(implicit ct: ClassTag[B], gridSys: HGridSys): LayerHcOptSys[B] = {
    val newArray = new Array[B](flatLength)
    gridSys.foreach { hc =>
      val i = gridSys.layerArrayIndex(hc)
      val aUnsafe = arrayUnsafe(i)
      if (aUnsafe != null) newArray(i) = f(hc, aUnsafe)
    }
    new LayerHcOptSys[B](newArray)
  }

  /** Not sure what this does could perhaps be deleted. */
  def indexMap[B <: AnyRef](f: (Int, A) => B)(implicit ct: ClassTag[B], gridSys: HGridSys): LayerHcOptSys[B] =
  { val newArray = new Array[B](flatLength)
    gridSys.foreach { hc =>
      val i = gridSys.layerArrayIndex(hc)
      val aUnsafe = arrayUnsafe(i)
      if (aUnsafe != null) newArray(i) = f(i, aUnsafe)
    }
    new LayerHcOptSys[B](newArray)
  }

  /** Creates a shallow copy of this [[LayerHcOptSys]]. */
  def copy: LayerHcOptSys[A] = new LayerHcOptSys[A](arrayUnsafe.clone)

  /** Sets / mutates the Some value of the hex tile data at the specified row and column coordinate values. */
  def setSomeMut(r: Int, c: Int, value: A)(implicit gridSys: HGridSys): Unit = arrayUnsafe(gridSys.layerArrayIndex(r, c)) = value

  /** Sets / the Some value of the hex tile data at the specified [[HCen]] coordinate. This is an imperative mutating operation. */
  def setSomeMut(hc: HCen, value: A)(implicit gridSys: HGridSys): Unit = arrayUnsafe(gridSys.layerArrayIndex(hc)) = value

  /** Sets / mutates the Some values of the hex tile data at the specified row and column coordinate values. */
  def setSomesMut(triples: (Int, Int, A)*)(implicit gridSys: HGridSys): Unit =
    triples.foreach(t => arrayUnsafe(gridSys.layerArrayIndex(t._1, t._2)) = t._3)

  /** Sets / mutates the given hex tiles to the given value. */
  def setSamesUnsafe(value: A, hCens: Int*)(implicit gridSys: HGridSys): Unit =
    iUntilForeach(hCens.length / 2){ i => setSomeMut(hCens(i * 2), hCens(i * 2 + 1), value) }

  /** Sets / mutates the value ot the specified location to None. */
  def setNoneMut(hc: HCen)(implicit gridSys: HGridSys): Unit = arrayUnsafe(gridSys.layerArrayIndex(hc)) = null.asInstanceOf[A]

  /** Sets / mutates every element to the given value. */
  def setAllMut(value: A): Unit = iUntilForeach(flatLength)(arrayUnsafe(_) = value)

  /** Sets multiple [[HCen]] locations to [[Some]] values. */
  def setFSomesMut(f: () => A, hCens: Int*)(implicit gridSys: HGridSys): Unit =
  { if (hCens.length.isOdd) excep(s"${hCens.length} odd number of int parameters for HCens.")
    iUntilForeach(0, hCens.length, 2){i => arrayUnsafe(gridSys.layerArrayIndex(hCens(i), hCens(i + 1))) = f()
    }
  }

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(r: Int, c: Int, value: A)(implicit gridSys: HGridSys): LayerHcOptSys[A] =
  { val newArr = arrayUnsafe.clone()
    newArr(gridSys.layerArrayIndex(r, c)) = value
    new LayerHcOptSys[A](newArr)
  }

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(hc: HCen, value: A)(implicit gridSys: HGridSys): LayerHcOptSys[A] = {
    val newArr = arrayUnsafe.clone()
    newArr(gridSys.layerArrayIndex(hc)) = value
    new LayerHcOptSys[A](newArr)
  }

  /** Creates a new ArrOpt with the specified location set to NoRef. */
  def setNone(hc: HCen)(implicit gridSys: HGridSys): LayerHcOptSys[A] =
  { val newArr = arrayUnsafe.clone()
    newArr(gridSys.layerArrayIndex(hc)) = null.asInstanceOf[A]
    new LayerHcOptSys[A](newArr)
  }

  /** Moves the object in the array location given by the 1st [[HCen]] to the 2nd [[HCen]], by setting hc2 to the value of hc1 and setting hc1 to
   *  None. This mutates the data layer. */
  def moveUnsafe(hc1: HCen, hc2: HCen)(implicit gridSys: HGridSys): Unit =
  { arrayUnsafe(gridSys.layerArrayIndex(hc2)) = arrayUnsafe(gridSys.layerArrayIndex(hc1))
    arrayUnsafe(gridSys.layerArrayIndex(hc1)) = null.asInstanceOf[A]
  }

  /** Not sure if this is still needed. */
  def MoveModifyMut(hc1: HCen, hc2: HCen)(f: A => A)(implicit gridSys: HGridSys): Unit =
  { arrayUnsafe(gridSys.layerArrayIndex(hc2)) = f(arrayUnsafe(gridSys.layerArrayIndex(hc1)))
    arrayUnsafe(gridSys.layerArrayIndex(hc1)) = null.asInstanceOf[A]
  }

  /** Drops the [[None]] values. Foreach value of the [[Some]] with the corresponding [[HCen]] applies the side effecting parameter function. */
  def somesHcForeach(f: (A, HCen) => Unit)(implicit gridSys: HGridSys): Unit = gridSys.foreach { hc =>
    val a = arrayUnsafe(gridSys.layerArrayIndex(hc))
    if (a != null) f(a, hc)
  }

  /** Maps the option values with the corresponding [[HCen]] to type B. Hence it takes two functions as parameters one for the [[None]] values and one
   * for the [[Some]] values. */
  def hcMapArr[B, ArrT <: Arr[B]](fNone: => HCen => B)(fSome: (A, HCen) => B)(implicit gridSys: HGridSys, build: BuilderArrMap[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    gridSys.foreach { hc =>
      val a = arrayUnsafe(gridSys.layerArrayIndex(hc))
      if (a != null) build.buffGrow(buff, fSome(a, hc))
      else
      { val newVal = fNone(hc)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Maps from the [[Some]] values to an [[Arr]]. */
  def somesMap[B, ArrT <: Arr[B]](fSome: A => B)(implicit gridSys: HGridSys, build: BuilderArrMap[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    gridSys.foreach { hc =>
      val a = arrayUnsafe(gridSys.layerArrayIndex(hc))
      if (a != null) build.buffGrow(buff, fSome(a))
    }
    build.buffToSeqLike(buff)
  }

  /** Maps the [[Some]] values with the corresponding [[HCen]] to type B. The [[None]] values are dropped. */
  def somesHcMap[B, ArrT <: Arr[B]](f: (A, HCen) => B)(implicit gridSys: HGridSys, build: BuilderArrMap[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    gridSys.foreach { hc =>
      val a = arrayUnsafe(gridSys.layerArrayIndex(hc))
      if (a != null) build.buffGrow(buff, f(a, hc))
    }
    build.buffToSeqLike(buff)
  }

  /** Maps the [[Some]] values with the corresponding [[HCen]] to type B. The [[None]] values are dropped. */
  def somesHcPairMap[B1, B1Arr <: Arr[B1], B2, B <: PairElem[B1, B2], ArrT <: ArrPair[B1, B1Arr, B2, B]](f: (A, HCen) => B)(implicit gridSys: HGridSys,
    build: BuilderArrPairMap[B1, B1Arr, B2, B, ArrT]): ArrT =
  { val buff = build.newBuff()
    gridSys.foreach { hc =>
      val a = arrayUnsafe(gridSys.layerArrayIndex(hc))
      if (a != null) build. buffGrow(buff, f(a, hc))
    }
    build.buffToSeqLike(buff)
  }

  /** Maps the option values with the corresponding [[HCen]] to type B. Hence it takes two functions as parameters one for the [[None]] values and one
   * for the [[Some]] values. */
  def projHcMap(proj: HSysProjection)(fNone: (Pt2, HCen) => GraphicElem)(fSome: (A, Pt2, HCen) => GraphicElem): GraphicElems =
    proj.hCenPtMap{ (hc, pt) =>
      val a = arrayUnsafe(proj.parent.layerArrayIndex(hc))
      ife(a != null, fSome(a, pt, hc), fNone(pt, hc))
    }

  /** Indexes in to this [[LayerHcOptSys]] using the tile centre coordinate, either passed as an [[HCen]] or as row and column [[Int values]]. */
  def apply(hc: HCen)(implicit gridSys: HGridSys): Option[A] =
  { if (!gridSys.hCenExists(hc)) None else
      { val elem = arrayUnsafe(gridSys.layerArrayIndex(hc))
        if (elem == null) None else Some(elem)
      }
  }

  /** Indexes in to this [[LayerHcOptSys]] using the tile centre coordinate, either passed as an [[HCen]] or as row and column [[Int values]]. */
  def apply(r: Int, c: Int)(implicit gridSys: HGridSys): Option[A] = {
    if (!gridSys.hCenExists(r, c)) None else {
      val elem = arrayUnsafe(gridSys.layerArrayIndex(r, c))
      if (elem == null) None else Some(elem)
    }
  }

  /** Indexes in to this [[LayerHcOptSys]] using the tile centre coordinate, returns the raw value which might be a [[null]]. */
  def applyUnsafe(hc: HCen)(implicit gridSys: HGridSys): A = arrayUnsafe(gridSys.layerArrayIndex(hc))

  /** Indexes in to this [[LayerHcOptSys]] using the tile centre coordinate, returns the raw value which might be a [[null]]. */
  def applyUnsafe(r: Int, c: Int)(implicit gridSys: HGridSys): A = arrayUnsafe(gridSys.layerArrayIndex(r, c))

  /** Indexes in to this [[LayerHcOptSys]] using the tile centre coordinate, will return nulls for [[None]] values, throws exception if tile centre
   *  does not exist. */
  def getex(r: Int, c: Int)(implicit gridSys: HGridSys): A = arrayUnsafe(gridSys.layerArrayIndex(r, c))

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def getex(hc: HCen)(implicit gridSys: HGridSys): A = arrayUnsafe(gridSys.layerArrayIndex(hc))

  /** The tile is a None at the given hex grid centre coordinate [[HCen]]. */
  def emptyTile(hc: HCen)(implicit gridSys: HGridSys): Boolean = arrayUnsafe(gridSys.layerArrayIndex(hc)) == null

  /** Drops the [[None]] values. Maps the [[Some]]'s value with the corresponding [[HCen]] to value of type B. Returns a [[Seqimut]] of length between
   * 0 and the length of this [[LayerHcOptSys]]. */
  def someHCMapArr[B, ArrB <: Arr[B]](f: (A, HCen) => B)(implicit gridSys: HGridSys, build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()

    gridSys.foreach { hc =>
      val a: A = arrayUnsafe(gridSys.layerArrayIndex(hc))
      if(a != null)
      { val newVal = f(a, hc)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Drops the None values mapping the [[Some]]'s value with the [[HCen]] to an option value, collecting the values of the [[Some]]s returned by the
   *  function. Returns a [[Seqimut]] of length 0 to the length of this [[LayerHcOptSys]]. */
  def projSomeHCMap(f: (A, HCen) => GraphicElem)(implicit proj: HSysProjection): GraphicElems = projSomeHCMap(proj)(f)

  /** Uses projection to map the Some data value with the corresponding [[HCen]] and the projections corresponding [[Pt2]] to an element of type B. In
   * most cases B will be a [[GraphicElem]] or a subtype. */
  def projSomeHCMap(proj: HSysProjection)(f: (A, HCen) => GraphicElem): GraphicElems =
  {
    val buff = BuffGraphic()
    proj.gChild.foreach { hc =>
      val a: A = arrayUnsafe(proj.parent.layerArrayIndex(hc))
      if (a != null) {
        buff.append(f(a, hc))
      }
    }
    buff.toArr
  }

  /** Uses projection to map the Some data value with the projections corresponding [[Pt2]] to an element of type B. In most cases B will be a
   *  [[GraphicElem]] or a subtype. */
  def projSomesPtMap[B, ArrB <: Arr[B]](f: (A, Pt2) => B)(implicit proj: HSysProjection, build: BuilderArrMap[B, ArrB]): ArrB =
    projSomesPtMap(proj)(f)

  /** Uses projection to map the Some data value with the projections corresponding [[Pt2]] to an element of type B. In most cases B will be a
   *  [[GraphicElem]] or a subtype. */
  def projSomesPtMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (A, Pt2) => B)(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.foreach { hc =>
      val a: A = arrayUnsafe(proj.parent.layerArrayIndex(hc))
      if (a != null) proj.transOptCoord(hc).foreach { pt =>
        val res = f(a, pt)
        build.buffGrow(buff, res)
      }
    }
    build.buffToSeqLike(buff)
  }
  /** Uses projection to map the Some data value with the corresponding [[HCen]] and the projections corresponding [[Pt2]] to an element of type B. In
   *  most cases B will be a [[GraphicElem]] or a subtype. */
  def projSomesHcPtMap[B, ArrB <: Arr[B]](f: (A, HCen, Pt2) => B)(implicit proj: HSysProjection, build: BuilderArrMap[B, ArrB]): ArrB =
    projSomesHcPtMap(proj)(f)

  /** Uses projection to map the Some data value with the corresponding [[HCen]] and the projections corresponding [[Pt2]] to an element of type B. In
   * most cases B will be a [[GraphicElem]] or a subtype. */
  def projSomesHcPtMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (A, HCen, Pt2) => B)(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.foreach { hc =>
      val a: A = arrayUnsafe(proj.parent.layerArrayIndex(hc))
      if (a != null) proj.transOptCoord(hc).foreach { pt =>
        val res = f(a, hc, pt)
        build.buffGrow(buff, res)
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Uses this and a second [[LayerHcOptSys]] of type B. Drops all values where either or both [[LayerHcOptSys]] have [[None]] values. Maps the
   *  corresponding values of the [[Some]]s to type C. Returns a [[Arr]] of length bwteen 0 na d the length of the original [[LayerHcOptSys]]s. */
  def zipSomesMap[B <: AnyRef, C, ArrC <: Arr[C]](optArrB: LayerHcOptSys[B])(f: (A, B) => C)(implicit gridSys: HGridSys, build: BuilderArrMap[C, ArrC]): ArrC =
  { val buff = build.newBuff()

    gridSys.foreach { hc =>
      val a: A = arrayUnsafe(gridSys.layerArrayIndex(hc))
      val b: B = optArrB.arrayUnsafe(gridSys.layerArrayIndex(hc))
      if(a != null & b != null)
      { val newVal = f(a, b)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Uses this and a second [[LayerHcOptSys]] of type B, combining corresponding pairs of [[Some]] values with the corresponding [[HCen]] and apping
   * to a value of type C. Returns a [[Arr]] with a length between 0 and the length of the original [[HCenOptDGtid]] data grids. */
  def zipSomesHCMap[B <: AnyRef, C, ArrC <: Arr[C]](optArrB: LayerHcOptSys[B])(f: (A, B, HCen) => C)(
    implicit gridSys: HGridSys, build: BuilderArrMap[C, ArrC]): ArrC =
  { val buff = build.newBuff()

    gridSys.foreach { hc =>
      val a: A = arrayUnsafe(gridSys.layerArrayIndex(hc))
      val b: B = optArrB.arrayUnsafe(gridSys.layerArrayIndex(hc))
      if(a != null)
      { val newVal = f(a, b, hc)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Drops the [[Some]] values. Maps the corresponding [[HCen]] for the [[None]] to type B. Returns
   *  a [[Arr]] of length between 0 and the length of this [[LayerHcOptSys]]. */
  def noneHCMap[B, ArrB <: Arr[B]](f: HCen => B)(implicit gridSys: HGridSys, build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()

    gridSys.foreach { r =>
      val a: A = arrayUnsafe(gridSys.layerArrayIndex(r))
      if(a == null)
      { val newVal = f(r)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Uses a projection of the implicit [[HGridSys]] to map the [[HCen]] and the [[Pt2]] from the projection where this [[LayerHcOptSys]] contains
   *  [[None]] values. There is a name overload for this where the projection is passed explicitly as the first parameter list. */
  def projNoneHcPtMap[B, ArrB <: Arr[B]](f: (HCen, Pt2) => B)(implicit proj: HSysProjection, build: BuilderArrMap[B, ArrB]): ArrB =
    projNoneHcPtMap(proj)(f)

  /** Uses a projection of the implicit [[HGridSys]] to map the [[HCen]] and the [[Pt2]] from the projection where this [[LayerHcOptSys]] contains
   * [[None]] values. There is a name overload for this where the projection is passed implicitly with the [[BuilderArrMap]]. */
  def projNoneHcPtMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (HCen, Pt2) => B)(implicit build: BuilderArrMap[B, ArrB]): ArrB = {
    val buff = build.newBuff()

    proj.gChild.foreach { hc =>
      val a: A = arrayUnsafe(proj.parent.layerArrayIndex(hc))
      if (a == null) {
        build.buffGrow(buff, f(hc, proj.transCoord(hc)))
      }
    }
    build.buffToSeqLike(buff)
  }

  /** FlatMaps the value of the [[Some]] with the corresponding [[HCen]] to a [[Arr]]. Drops the [[None]] values . */
  def somesHcFlatMap[ArrT <: Arr[_]](f: (A, HCen) => ArrT)(implicit gridSys: HGridSys, build: BuilderArrFlat[ArrT]): ArrT =
  { val buff = build.newBuff()
    gridSys.foreach { hc =>
      val a = arrayUnsafe(gridSys.layerArrayIndex(hc))
      if(a != null)
      { val newVal = f(a, hc)
        build.buffGrowArr(buff, newVal)
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Drops the None values, flatMaps the [[Some]]'s value and the corresponding [[HCen]] to an [[option]] of a [[Arr]], collects only the
   *  [[Some]]'s values returned by the function. */
  def someHCOptFlatMap[ArrB <: Arr[_]](f: (A, HCen) => Option[ArrB])(implicit gridSys: HGridSys, build: BuilderArrFlat[ArrB]): ArrB =
  { val buff = build.newBuff()

    gridSys.foreach { hc =>
      val a: A = arrayUnsafe(gridSys.layerArrayIndex(hc))
      if (a != null) { f(a, hc).foreach(build.buffGrowArr(buff, _)) }
    }
    build.buffToSeqLike(buff)
  }

  /** Returns an [[HCenPairArr]] of the Some values. */
  def somePairs(implicit gridSys: HGridSys, build: HCenPairArrMapBuilder[A]): HCenPairArr[A] =
  { val buff = build.newBuff()
    somesHcForeach((p, hc) => buff.grow(hc, p))
    build.buffToSeqLike(buff)
  }

  /** [[Option]]ally finds the value returning the [[HCen]] location wrapped in a [[Some]] if found. */
  def find(value: A)(implicit gridSys: HGridSys): Option[HCen] =
  { var res: Option[HCen] = None
    somesHcForeach{ (a, hc) => if (value == a) res = Some(hc)}
    res
  }

  def findHCen(value: A)(implicit gridSys: HGridSys): Option[HCen] =
  { var res: Option[HCen] = None
    gridSys.foreach{hc => res.replaceNone(ife(value == getex(hc), Some(hc), None)) }
    res
  }

  def out(gridSys: HGridSys)(implicit showA: Show[A]): String =
  {
//    val rows = gridSys.mapRows{r => r.str + gridSys.r ""}
    ""
  }
}

object LayerHcOptSys
{ /** New hex tile data layer of optional data for this [[HGridSys]]. */
  def apply[A <: AnyRef]()(implicit ct: ClassTag[A], gSys: HGridSys): LayerHcOptSys[A] = new LayerHcOptSys(new Array[A](gSys.numTiles))

  /** New hex tile data layer of optional data for this [[HGridSys]]. */
  def apply[A <: AnyRef](gSys: HGridSys)(implicit ct: ClassTag[A]): LayerHcOptSys[A] = new LayerHcOptSys(new Array[A](gSys.numTiles))

  implicit def showEv[A <: AnyRef](gridSys: HGridSys, evA: Show[A]): Show[LayerHcOptSys[A]] = new Show[LayerHcOptSys[A]]
  {
    override def typeStr: String = "HCenOptLayer"

    override def show(obj: LayerHcOptSys[A], style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = gridSys match
    {
      case hg: HGrid => {
        val r1 = hg.allRsMap(r => s"Row($r" + hg.rowMap(r){hc => obj(hc)(hg).toString})
        r1.mkStr("\n")
     }
     case hm: HGridMulti => "Not implemented"
    }

    /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
     * rather than a method on the object being shown. */
    override def strT(obj: LayerHcOptSys[A]): String = ???

    /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
     * should always be determined at compile time or if sometimes it should be determined at runtime. */
    override def syntaxDepth(obj: LayerHcOptSys[A]): Int = ???
  }
}

/*class HCenRowOptLayer[A <: AnyRef](val r: Int, val unsafeArray: Array[A]) extends Tell
{
  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  override def str: String = ???

  override def tellDepth: Int = ???

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  override def tell(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???

  /** The RSON type of T. This the only data that a ShowT instance requires, that can't be implemented through delegation to an object of type
   * Show. */
  override def typeStr: String = ???
}*/