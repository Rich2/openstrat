/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** An [[HGridSys]] hex tile grid system of [[HCen]] or hex tile data. For efficiency the data is stored as a flat Array. No run time information
 *  distinguishes this from an ordinary linear sequence array of data. Whether in a game or a non game application the data of the grid tiles is
 *  likely to change much more frequently than the size, shape, structure of the grid. The compiler knows this is hex grid array and hence the data
 *  should be set and retrieved through the [[HGrid]] hex grid. So nearly all the methods take the [[HGrid]] as an implicit parameter. */
class HCenLayer[A <: AnyRef](val unsafeArray: Array[A]) extends AnyVal with TCenLayer[A]
{ override type ThisT = HCenLayer[A]
  override def typeStr: String = "HCenDGrid"

  /** Apply method returns a data element from this data layer for the given [[HCen]]. The appropriate index is found from the implicit [[HGridSys]].
   * There is an alterntive nme overload where the [[HGridSys]] is passed explicitly as the first paremter. */
  def apply(hc: HCen)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.arrIndex(hc))

  /** Apply method returns a data element from this data layer for the given [[HCen]]. */
  def apply(gridSys: HGridSys, hc: HCen): A = unsafeArray(gridSys.arrIndex(hc))

  def rc(r: Int, c: Int)(implicit grid: HGridSys): A = unsafeArray(grid.arrIndex(r, c))
  def set(hc: HCen, value: A)(implicit gridSys: HGridSys): Unit = { unsafeArray(gridSys.arrIndex(hc)) = value }
  def set(r: Int, c: Int, value: A)(implicit gridSys: HGridSys): Unit = { unsafeArray(gridSys.arrIndex(r, c)) = value }
  override def fromArray(array: Array[A]): HCenLayer[A] = new HCenLayer[A](array)

  /** The element String allows the composition of toString for the whole collection. The syntax of the output will be reworked. */
  override def elemsStr: String = "Not implemented"

  /** [[HCen]] with foreach. Applies the side effecting function to the [[HCen]] coordinate with its respective element. Note the function signature
   *  follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator, element)
   *  => B signature.  */
  def hcForeach[U](f: (HCen, A) => U)(implicit grid: HGrid): Unit = grid.iForeach{ (hc, i) => f(hc, unsafeArray(i)); () }

  /** [[HCen]] with map. Applies the function to each [[HCen]] coordinate with the corresponding element in the underlying array. Note the function
   *  signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator,
   *  element) => B signature. */
  def hcMap[B, BB <: Arr[B]](f: (HCen, A) => B)(implicit grid: HGridSys, build: ArrMapBuilder[B, BB]): BB =
  { val res = build.uninitialised(length)
    grid.iForeach{ (hc, i) =>
      val newElem = f(hc, apply(hc))
      res.unsafeSetElem(i, newElem)
    }
    res
  }

  /** Maps each data element with thw corresponding [[HCen]] to an [[Option]] of type B. Collects the [[Some]]'s values. The length of the returned
   * [[Arr]] will be between 0 and the lengthof this [[HCenLayer]]. */
  def hcOptMap[B, BB <: Arr[B]](f: (A, HCen) => Option[B])(implicit grid: HGridSys, build: ArrMapBuilder[B, BB]): BB =
  { val buff = build.newBuff()
    grid.iForeach { (hc, i) =>
      f(apply(hc), hc).foreach(build.buffGrow(buff, _))
    }
    build.buffToSeqLike(buff)
  }

  /** [[HCen]] with flatmap. Applies the function to each [[HCen]] coordinate with the corresponding element in the underlying array. Note the
   *  function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator,
   *  element) => B signature. */
  def hcFlatMap[BB <: Arr[_]](f: (HCen, A) => BB)(implicit grid: HGridSys, build: ArrFlatBuilder[BB]): BB =
  { val buff = build.newBuff()
    grid.iForeach{ (hc, i) =>
      val newElems = f(hc, apply(hc))
      build.buffGrowArr(buff, newElems)
    }
    build.buffToSeqLike(buff)
  }

  /** [[HCen]] with optFlatmap. Applies the function to each [[HCen]] coordinate with the corresponding element in the underlying array. Note the
   * function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator,
   * element) => B signature. */
  def hcOptFlatMap[BB <: Arr[_]](f: (HCen, A) => Option[BB])(implicit grid: HGridSys, build: ArrFlatBuilder[BB]): BB = {
    val buff = build.newBuff()
    grid.iForeach { (hc, i) =>
      f(hc, apply(hc)).foreach(build.buffGrowArr(buff, _))
    }
    build.buffToSeqLike(buff)
  }

  /** Completes the given row from the given starting c column value to the end of the row. An exception is
   *  thrown if the tile values don't match with the end of the row. */
  final def toEndRow(row: Int, cStart: Int, tileMultis: Multiple[A]*)(implicit grid: HGrid): HCen =
  { val numTiles = tileMultis.numSingles
    val endValues = cStart + numTiles * 4 - 4
    val rowEnd = grid.rowRightCenC(row)
    if( rowEnd != endValues) debexc(s"Row $row last data column ${endValues} != $rowEnd the grid row end.")
    tileMultis.iForeachSingle { (i, e) =>  val c = cStart + i * 4; unsafeArray(grid.arrIndex(row, c)) = e }
    HCen(row, cStart + (numTiles - 1) * 4)
  }

  /** Fills in the whole given row. An exception is thrown if the tile values don't match with the
   *  end of the row. */
  final def completeRow(row: Int, tileMultis: Multiple[A]*)(implicit grid: HGrid): HCen =
  { val numTiles = tileMultis.numSingles
    val cStart: Int = grid.rowLeftCenC(row)
    val endValues = cStart + numTiles * 4 - 4
    val rowEnd = grid.rowRightCenC(row)
    if (rowEnd != endValues) debexc(s"Row $row last data column ${endValues} != $rowEnd the grid row end.")
    tileMultis.iForeachSingle { (i, e) => val c = cStart + i * 4; unsafeArray(grid.arrIndex(row, c)) = e }
    HCen(row, cStart + (numTiles - 1) * 4)
  }

  /** Sets the given row from the given starting c column value, for the given number of tile centre values. An exception is thrown if the numOfCens
   * overflows the row end. */
  final def setRowPart(row: Int, cStart: Int, numOfCens: Int, tileValue: A)(implicit grid: HGrid): HCen =
  { val rightC = cStart + numOfCens * 4 - 4
    val rowEnd = grid.rowRightCenC(row)
    if( rowEnd < rightC) debexc(s"Row $row last data column ${rightC} > $rowEnd the grid row end.")
    iToForeach(cStart, rightC, 4) { c => unsafeArray(grid.arrIndex(row, c)) = tileValue }
    HCen(row, rightC)
  }

  def rowsCombine(implicit grider: HGridSys): RArr[HCenRowPair[A]] = grider.rowsCombine(this, grider)

  def projRowsCombine(implicit proj: HSysProjection): RArr[HCenRowPair[A]] = proj.gChild.rowsCombine(this, proj.parent)

  def projRowsCombinePolygonHCs(implicit proj: HSysProjection, ct: ClassTag[A]): PolygonHCPairArr[A] = projRowsCombine.map(_.polygonHCTuple)

  def projRowsCombinePolygons(implicit proj: HSysProjection, ct: ClassTag[A]): PolygonPairArr[A] =
    projRowsCombine.map(_.polygonHCTuple.polygonPair(proj.transCoord(_)))

  def projPtMap(proj: HSysProjection)(f: (Pt2, A) => GraphicElem): GraphicElems = proj.hCenPtMap{ (hc, pt2) => f(pt2, apply(hc)(proj.gChild)) }

  /** Maps the visible [[HCen]]s in the projection with their respective projection [[Pt2]] tile centre points and the data layer element form this collection to [[GraphicElems]]. This method name overload takes the [[HSysProjection]] as an implicit
   *  parameter. The other name overload takes it as an explicit first parameter list. In practice this method may be of limited utility. It may be better to use the the [[HSysProjection]] or another class as the dispatching object and access these
   *  data layer elements by the [[HCen]] apply methods. */
  def projHCenPtMap(f: (HCen, Pt2, A) => GraphicElem)(implicit proj: HSysProjection): GraphicElems = projHCenPtMap(proj)(f)

  /** Maps the visible [[HCen]]s in the projection with their respective projection [[Pt2]] tile centre points and the data layer element form this collection to [[GraphicElems]]. This method name overload takes the [[HSysProjection]] as an explicit
   *  first parameter  list. The other name overload takes it as an implicit parameter.In practice this method may be of limited utility. It may be better to use the the [[HSysProjection]] or another class as the dispatching object and access these
   * data layer elements by the [[HCen]] apply methods. */
  def projHCenPtMap(proj: HSysProjection)(f: (HCen, Pt2, A) => GraphicElem): GraphicElems = proj.hCenPtMap{ (hc, pt2) => f(hc, pt2, apply(hc)(proj.gChild)) }

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   *  [[HSide]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSide]] and the two adjacent hex
   *  tile data values. */
  def sideMap[B, BB <: Arr[B]](f1: (HSide, A) => B, f2: (HSide, A, A) => B)(implicit grid: HGrid, build: ArrMapBuilder[B, BB]): BB =
    grid.sidesMap{ hs => hs.unsafeTiles match
      {
        case (c1, c2) if grid.hCenExists(c1) & grid.hCenExists(c2) =>f2(hs, apply(c1), apply(c2))
        case (c1, _) if grid.hCenExists(c1) => f1(hs, apply(c1))
        case (_, c2) => f1(hs, apply(c2))
      }
    }

  /** Maps the links or inner sides to an immutable Array, using the data of this HCenArr. It takes a function for the links or inner sides of the
   *  grid that takes the [[HSide]] and the two adjacent hex tile data values. */
  def linksMap[B, BB <: Arr[B]](f: (HSide, A, A) => B)(implicit grid: HGridSys, build: ArrMapBuilder[B, BB]): BB =
    grid.linksMap{ hs => hs.unsafeTiles match
    { case (c1, c2)  => f(hs, apply(c1), apply(c2))
    }
  }

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   *  [[HSide]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSide]] and the two adjacent hex
   *  tile data values. */
  def sideFlatMap[BB <: Arr[_]](f1: (HSide, A) => BB, f2: (HSide, A, A) => BB)(implicit grid: HGridSys, build: ArrFlatBuilder[BB]): BB =
    grid.sidesFlatMap{ hs => hs.unsafeTiles match
    { case (c1, c2) if grid.hCenExists(c1) & grid.hCenExists(c2) =>f2(hs, apply(c1), apply(c2))
      case (c1, _) if grid.hCenExists(c1) => f1(hs, apply(c1))
      case (_, c2) => f1(hs, apply(c2))
    }
  }

  /** FlatMaps the links / inner sides to an immutable Array, using the data of this HCenArr. It takes a function, that takes the [[HSide]] and the
   *  two adjacent hex tile data values. */
  def linksFlatMap[BB <: Arr[_]](f: (HSide, A, A) => BB)(implicit grid: HGridSys, build: ArrFlatBuilder[BB]): BB =
    grid.linksFlatMap { hs => f(hs, apply(hs.tile1Reg), apply(hs.tile2Reg)) }

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   * [[HSide]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSide]] and the two adjacent hex
   * tile data values. */
  def projSideFlatMap[BB <: Arr[_]](proj: HSysProjection)(f1: (HSide, A) => BB, f2: (HSide, A, A) => BB)(implicit build: ArrFlatBuilder[BB]): BB =
    proj.gChild.sidesFlatMap { hs =>
      hs.unsafeTiles match {
        case (c1, c2) if proj.gChild.hCenExists(c1) & proj.gChild.hCenExists(c2) => f2(hs, apply(proj.parent, c1), apply(proj.parent, c2))
        case (c1, _) if proj.gChild.hCenExists(c1) => f1(hs, apply(proj.parent, c1))
        case (_, c2) => f1(hs, apply(proj.parent, c2))
      }
    }

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   * [[HSide]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSide]] and the two adjacent hex
   * tile data values. */
  def projLinksFlatMap[BB <: Arr[_]](f2: (HSide, A, A) => BB)(implicit proj: HSysProjection, build: ArrFlatBuilder[BB]): BB =
    projLinksFlatMap(proj)(f2)

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   * [[HSide]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSide]] and the two adjacent hex
   * tile data values. */
  def projLinksFlatMap[BB <: Arr[_]](proj: HSysProjection)(f: (HSide, A, A) => BB)(implicit build: ArrFlatBuilder[BB]): BB =
    proj.gChild.linksFlatMap { hs => f(hs, apply(proj.parent, hs.tile1Reg), apply(proj.parent, hs.tile2Reg)) }

  /** Maps the sides to an immutable Array, using the data of this [[HCenLayer]]. It takes two functions, one for the edges of the grid, that takes the
   * [[HSide]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSide]] and the two adjacent hex
   * tile data values. */
  def projLinksLineOptMap[B, BB <: Arr[B]](f: (LineSeg, A, A) => Option[B])(implicit proj: HSysProjection, build: ArrMapBuilder[B, BB]): BB =
    projLinksLineOptMap(proj)(f)

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   * [[HSide]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSide]] and the two adjacent hex
   * tile data values. */
  def projLinksLineOptMap[B, BB <: Arr[B]](proj: HSysProjection)(f: (LineSeg, A, A) => Option[B])(implicit build: ArrMapBuilder[B, BB]): BB =
    proj.gChild.linksOptMap { hs =>
      hs.unsafeTiles match {
        case (c1, c2) if proj.gChild.hCenExists(c1) & proj.gChild.hCenExists(c2) => f(hs.lineSegHC.map(proj.transCoord), apply(proj.parent, c1), apply(proj.parent, c2))
      }
    }

  /** Comment no correct, Maps the sides to an immutable Array, using the data of this [[HCenLayer]]. It takes two functions, one for the edges of the grid, that takes the
   * [[HSide]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSide]] and the two adjacent hex
   * tile data values. */
  def projLinksHsLineOptMap[B, BB <: Arr[B]](f: (HSide, LineSeg, A, A) => Option[B])(implicit proj: HSysProjection, build: ArrMapBuilder[B, BB]): BB =
    projLinksHsLineOptMap(proj)(f)

  /** implementation not correct, Comment not correct, Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   * [[HSide]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSide]] and the two adjacent hex
   * tile data values. */
  def projLinksHsLineOptMap[B, BB <: Arr[B]](proj: HSysProjection)(f: (HSide, LineSeg, A, A) => Option[B])(implicit build: ArrMapBuilder[B, BB]): BB =
    proj.gChild.linksOptMap { hs =>
      hs.unsafeTiles match {
        case (c1, c2) if proj.gChild.hCenExists(c1) & proj.gChild.hCenExists(c2) => f(hs, hs.lineSegHC.map(proj.transCoord), apply(proj.parent, c1), apply(proj.parent, c2))
      }
    }

  def ++(operand: HCenLayer[A])(implicit ct: ClassTag[A]): HCenLayer[A] = {
    val newArray = Array.concat(unsafeArray, operand.unsafeArray)
    new HCenLayer[A](newArray)
  }
}

/** Companion object for [[HCenLayer]], contains an apply factory method. */
object HCenLayer
{ /** Apply factory method for [[HCenLayer]]s. */
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): HCenLayer[A] = new HCenLayer[A](new Array[A](length))

  implicit class RArrHCenLayerExtension[A <: AnyRef](val thisArr: RArr[HCenLayer[A]])
  { /** Combines by appending the data grids to produce a single layer. */
    def combine(implicit ct: ClassTag[A]): HCenLayer[A] =
    { val newLen = thisArr.sumBy(_.length)
      val newArray = new Array[A](newLen)
      var i = 0
      thisArr.foreach{ar => ar.unsafeArray.copyToArray(newArray, i); i += ar.length}
      new HCenLayer[A](newArray)
    }
  }
}