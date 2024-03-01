/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** Reference data layer for [[HGridSys]]. An [[HGridSys]] hex tile grid system of [[HCen]] or hex tile data. For efficiency the data is stored as a flat Array.
 *  No run time information distinguishes this from an ordinary linear sequence array of data. Whether in a game or a non game application the data of the grid
 *  tiles is likely to change much more frequently than the size, shape, structure of the grid. The compiler knows this is hex grid array and hence the data
 *  should be set and retrieved through the [[HGrid]] hex grid. So nearly all the methods take the [[HGrid]] as an implicit parameter. */
trait LayerHcRefSys[A <: AnyRef] extends Any with LayerHcRef[A] with LayerTcRef[A]
{  //override type KeyT <: HGridSys

  /** Completes the given row from the given starting c column value to the end of the row. An exception is
   *  thrown if the tile values don't match with the end of the row. */
  final def setRowEnd(row: Int, cStart: Int, tileMultis: Multiple[A]*)(implicit grid: HGrid): HCen =
  { val numTiles = tileMultis.numSingles
    val endValues = cStart + numTiles * 4 - 4
    val rowEnd = grid.rowRightCenC(row)
    if( rowEnd != endValues) debexc(s"Row $row last data column ${endValues} != $rowEnd the grid row end.")
    tileMultis.iForeachSingle { (i, e) =>  val c = cStart + i * 4; arrayUnsafe(grid.layerArrayIndex(row, c)) = e }
    HCen(row, cStart + (numTiles - 1) * 4)
  }

  final def setRowEndUnchecked(row: Int, tileMultis: Multiple[A]*)(implicit grid: HGrid): HCen =
  { val numTiles = tileMultis.numSingles
    val cStart = grid.rowRightCenC(row) - numTiles * 4 + 4
    val endValues = cStart + numTiles * 4 - 4
    val rowEnd = grid.rowRightCenC(row)
    if (rowEnd > endValues) debexc(s"Row $row last data column ${endValues} > $rowEnd the grid row end.")
    tileMultis.iForeachSingle { (i, e) => val c = cStart + i * 4; arrayUnsafe(grid.layerArrayIndex(row, c)) = e }
    HCen(row, cStart + (numTiles - 1) * 4)
  }

  /** Fills in the whole given row, with the same given value. This method has anme overload where the grid is passed explicitly as the first
   *  parameter. */
  inline def setRowSame(row: Int, value: A)(implicit grid: HGrid): Unit = setRowSame(grid, row, value)

  /** Fills in the whole given row, with the same given value. This method has anme overload where the grid is passed implicitly. */
  def setRowSame(grid: HGrid, row: Int, value: A): Unit =  grid.rowForeach(row){hc => arrayUnsafe(grid.layerArrayIndex(hc)) = value}

  /** Sets the given row from the given starting c column value, for the given number of tile centre values. An exception is thrown if the numOfCens
   * overflows the row end. */
  final def setRowPartSame(row: Int, cStart: Int, numOfCens: Int, tileValue: A)(implicit grid: HGrid): HCen =
  { val rightC = cStart + numOfCens * 4 - 4
    val rowEnd = grid.rowRightCenC(row)
    if( rowEnd < rightC) debexc(s"Row $row last data column ${rightC} > $rowEnd the grid row end.")
    iToForeach(cStart, rightC, 4) { c => arrayUnsafe(grid.layerArrayIndex(row, c)) = tileValue }
    HCen(row, rightC)
  }

  /** Sets the given row from the start of the row, for the given number of tile centre values. An exception is thrown if the numOfCens overflows the
   *  row end. */
  final def setRowStartSame(row: Int, numOfCens: Int, tileValue: A)(implicit grid: HGrid): HCen =
  { val rightC = grid.rowLeftCenC(row) + numOfCens * 4 - 4
    val rowEnd = grid.rowRightCenC(row)
    if (rowEnd < rightC) debexc(s"Row $row last data column ${rightC} > $rowEnd the grid row end.")
    iToForeach(grid.rowLeftCenC(row), rightC, 4) { c => arrayUnsafe(grid.layerArrayIndex(row, c)) = tileValue }
    HCen(row, rightC)
  }

  def rowsCombine(implicit grider: HGridSys): RArr[HCenRowPair[A]] = grider.rowsCombine(this, grider)

  def projRowsCombine(implicit proj: HSysProjection): RArr[HCenRowPair[A]] = proj.gChild.rowsCombine(this, proj.parent)

  def projRowsCombinePolygonHCs(implicit proj: HSysProjection, ct: ClassTag[A]): PolygonHCPairArr[A] = projRowsCombine.map(_.polygonHCTuple)

  def projRowsCombinePolygons(implicit proj: HSysProjection, ct: ClassTag[A]): PolygonGenPairArr[A] =
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

  def projPolyMap(proj: HSysProjection, corners: HCornerLayer)(f: (Polygon, A) => GraphicElem): GraphicElems = proj.hCenMap{hc =>
    val terr = apply(hc)(proj.parent)
    val poly2: Polygon = getPoly(hc, terr, corners, proj)
    f(poly2, terr)
  }

  def projHCenPolyMap(proj: HSysProjection, corners: HCornerLayer)(f: (HCen, Polygon, A) => GraphicElem): GraphicElems = proj.hCenMap { hc =>
    val terr = apply(hc)(proj.parent)
    val poly2: Polygon = getPoly(hc, terr, corners, proj)
    f(hc, poly2, terr)
  }

  def getPoly(hc: HCen, terr : Any, corners: HCornerLayer, proj: HSysProjection): Polygon =
  { val poly1 = terr match {
    //      case _: HIndent6 => hc.vertsIn(7)
    //      case hi: HIndent4 => iUntilPolygonLikeMap(6) { i => ife(i == hi.indentStartIndex | (i + 1) %% 6 == hi.indentStartIndex, hc.vExact(i), hc.vIn(i, 7)) }
    //
    //      case hi: HIndent3 => iUntilPolygonLikeMap(6) { i =>
    //        ife(i == hi.indentStartIndex | (i + 1) %% 6 == hi.indentStartIndex | (i + 2) %% 6 == hi.indentStartIndex, hc.vExact(i), hc.vIn(i, 7)) }
    //
    //      case hi: HIndent2 =>
    //        iUntilPolygonLikeMap(6) { i => ife((i + 4) %% 6 == hi.indentStartIndex | (i + 5) %% 6 == hi.indentStartIndex, hc.vIn(i, 7), hc.vExact(i)) }

    // case hi: HIndent1 => iUntilPolygonLikeMap(6) { i => ife((i + 5) %% 6 == hi.indentStartIndex, hc.vIn(i, 7), hc.vExact(i)) }

    case _ => corners.tilePoly(hc)(proj.parent)
  }
    proj.transPolygonHVOffset(poly1)
  }

  /** Spawns a new [[LayerHcRefSys]] data layer from this [[LayerHcRefSys]]'s [[HGridSys]] to the child [[HGridSys]], passed as implicit parameter. There is a
   * name overload for this method that passes the child [[HGridSys]] explicitly. */
  def spawn(parentGridSys: HGridSys)(implicit ct: ClassTag[A], childGridSys: HGridSys): LayerHcRefSys[A] = spawn(parentGridSys, childGridSys)(ct)

  /** Spawns a new [[LayerHcRefSys]] data layer from this [[LayerHcRefSys]]'s [[HGridSys]] to the child [[HGridSys]]. There is a name overload for this method
   *  that passes the child [[HGridSys]] implicitly. */
  def spawn(parentGridSys: HGridSys, childGridSys: HGridSys)(implicit ct: ClassTag[A]): LayerHcRefSys[A]/* =
  { val array: Array[A] = new Array[A](childGridSys.numTiles)
    childGridSys.foreach { hc => array(childGridSys.layerArrayIndex(hc)) = apply(hc)(parentGridSys) }
    new LayerHcRefMulti[A](array)
  }*/

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   *  [[HSep]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSep]] and the two adjacent hex
   *  tile data values. */
  def sideMap[B, BB <: Arr[B]](f1: (HSep, A) => B, f2: (HSep, A, A) => B)(implicit grid: HGrid, build: BuilderArrMap[B, BB]): BB =
    grid.sepsMap{ hs => hs.unsafeTiles match
      { case (c1, c2) if grid.hCenExists(c1) & grid.hCenExists(c2) =>f2(hs, apply(c1), apply(c2))
        case (c1, _) if grid.hCenExists(c1) => f1(hs, apply(c1))
        case (_, c2) => f1(hs, apply(c2))
      }
    }

  /** Maps the links or inner sides to an immutable Array, using the data of this HCenArr. It takes a function for the links or inner sides of the
   *  grid that takes the [[HSep]] and the two adjacent hex tile data values. */
  def linksMap[B, BB <: Arr[B]](f: (HSep, A, A) => B)(implicit grid: HGridSys, build: BuilderArrMap[B, BB]): BB =
    grid.linksMap{ hs => hs.unsafeTiles match
    { case (c1, c2)  => f(hs, apply(c1), apply(c2))
    }
    }

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   *  [[HSep]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSep]] and the two adjacent hex
   *  tile data values. */
  def sideFlatMap[BB <: Arr[?]](f1: (HSep, A) => BB, f2: (HSep, A, A) => BB)(implicit grid: HGridSys, build: BuilderArrFlat[BB]): BB =
    grid.sepsFlatMap{ hs => hs.unsafeTiles match
      { case (c1, c2) if grid.hCenExists(c1) & grid.hCenExists(c2) =>f2(hs, apply(c1), apply(c2))
        case (c1, _) if grid.hCenExists(c1) => f1(hs, apply(c1))
        case (_, c2) => f1(hs, apply(c2))
      }
    }

  /** FlatMaps the links / inner sides to an immutable Array, using the data of this HCenArr. It takes a function, that takes the [[HSep]] and the
   *  two adjacent hex tile data values. */
  def linksFlatMap[BB <: Arr[?]](f: (HSep, A, A) => BB)(implicit grid: HGridSys, build: BuilderArrFlat[BB]): BB =
    grid.linksFlatMap { hs => f(hs, apply(hs.tileLtReg), apply(hs.tileRtReg)) }

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   * [[HSep]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSep]] and the two adjacent hex
   * tile data values. */
  def projSideFlatMap[BB <: Arr[?]](proj: HSysProjection)(f1: (HSep, A) => BB, f2: (HSep, A, A) => BB)(implicit build: BuilderArrFlat[BB]): BB =
    proj.gChild.sepsFlatMap { hs =>
      hs.unsafeTiles match {
        case (c1, c2) if proj.gChild.hCenExists(c1) & proj.gChild.hCenExists(c2) => f2(hs, apply(proj.parent, c1), apply(proj.parent, c2))
        case (c1, _) if proj.gChild.hCenExists(c1) => f1(hs, apply(proj.parent, c1))
        case (_, c2) => f1(hs, apply(proj.parent, c2))
      }
    }

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   * [[HSep]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSep]] and the two adjacent hex
   * tile data values. */
  def projLinksFlatMap[BB <: Arr[?]](f2: (HSep, A, A) => BB)(implicit proj: HSysProjection, build: BuilderArrFlat[BB]): BB =
    projLinksFlatMap(proj)(f2)

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   * [[HSep]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSep]] and the two adjacent hex
   * tile data values. */
  def projLinksFlatMap[BB <: Arr[?]](proj: HSysProjection)(f: (HSep, A, A) => BB)(implicit build: BuilderArrFlat[BB]): BB =
    proj.gChild.linksFlatMap { hs => f(hs, apply(proj.parent, hs.tileLtReg), apply(proj.parent, hs.tileRtReg)) }

  /** Maps the sides to an immutable Array, using the data of this [[LayerHcRefSys]]. It takes two functions, one for the edges of the grid, that takes the
   * [[HSep]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSep]] and the two adjacent hex
   * tile data values. */
  def projLinksLineOptMap[B, BB <: Arr[B]](f: (LineSeg, A, A) => Option[B])(implicit proj: HSysProjection, build: BuilderArrMap[B, BB]): BB =
    projLinksLineOptMap(proj)(f)

  /** Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   * [[HSep]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSep]] and the two adjacent hex
   * tile data values. */
  def projLinksLineOptMap[B, BB <: Arr[B]](proj: HSysProjection)(f: (LineSeg, A, A) => Option[B])(implicit build: BuilderArrMap[B, BB]): BB =
    proj.gChild.linksOptMap { hs =>
      hs.unsafeTiles match {
        case (c1, c2) if proj.gChild.hCenExists(c1) & proj.gChild.hCenExists(c2) => f(hs.lineSegHC.map(proj.transCoord), apply(proj.parent, c1), apply(proj.parent, c2))
      }
    }

  /** Comment no correct, Maps the sides to an immutable Array, using the data of this [[LayerHcRefSys]]. It takes two functions, one for the edges of the grid, that takes the
   * [[HSep]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSep]] and the two adjacent hex
   * tile data values. */
  def projLinksHsLineOptMap[B, BB <: Arr[B]](f: (HSep, LineSeg, A, A) => Option[B])(implicit proj: HSysProjection, build: BuilderArrMap[B, BB]): BB =
    projLinksHsLineOptMap(proj)(f)

  /** implementation not correct, Comment not correct, Maps the sides to an immutable Array, using the data of this HCenArr. It takes two functions, one for the edges of the grid, that takes the
   * [[HSep]] and the single adjacent hex tile data value and one for the inner sides of the grid that takes the [[HSep]] and the two adjacent hex
   * tile data values. */
  def projLinksHsLineOptMap[B, BB <: Arr[B]](proj: HSysProjection)(f: (HSep, LineSeg, A, A) => Option[B])(implicit build: BuilderArrMap[B, BB]): BB =
    proj.gChild.linksOptMap { hs =>
      hs.unsafeTiles match {
        case (c1, c2) if proj.gChild.hCenExists(c1) & proj.gChild.hCenExists(c2) => f(hs, hs.lineSegHC.map(proj.transCoord), apply(proj.parent, c1), apply(proj.parent, c2))
      }
    }

  def ++(operand: LayerHcRefSys[A])(implicit ct: ClassTag[A]): LayerHcRefSys[A] =
  { val newArray = Array.concat(arrayUnsafe, operand.arrayUnsafe)
    new LayerHcRefMulti[A](newArray)
  }
}

/** Companion object for [[LayerHcRefSys]], contains factory apply methods. */
object LayerHcRefSys
{ /** Apply factory method for [[LayerHcRefSys]]. */
  def apply[A <: AnyRef](value: A)(implicit ct: ClassTag[A], gridSys: HGridSys): LayerHcRefSys[A] = apply(gridSys, value)(ct)

  /** Apply factory method for [[LayerHcRefSys]]. */
  def apply[A <: AnyRef](gridSys: HGridSys, value: A)(implicit ct: ClassTag[A]): LayerHcRefSys[A] =
  { val newArray = new Array[A](gridSys.numTiles)
    iUntilForeach(gridSys.numTiles)(i => newArray(i) = value)
    new LayerHcRefMulti[A](newArray)
  }

  implicit class RArrHCenLayerExtension[A <: AnyRef](val thisArr: RArr[LayerHcRefSys[A]])
  { /** Combines by appending the data grids to produce a single layer. */
    def combine(implicit ct: ClassTag[A]): LayerHcRefSys[A] =
    { val newLen = thisArr.sumBy(_.length)
      val newArray = new Array[A](newLen)
      var i = 0
      thisArr.foreach{ar => ar.arrayUnsafe.copyToArray(newArray, i); i += ar.length}
      new LayerHcRefMulti[A](newArray)
    }
  }
}

class LayerHcRefGrid[A <: AnyRef](val arrayUnsafe: Array[A]) extends AnyVal with LayerHcRefSys[A] //with LayerTcRef[A]
{
  //override type KeyT = HGrid

  /** Spawns a new [[LayerHcRefSys]] data layer from this [[LayerHcRefSys]]'s [[HGridSys]] to the child [[HGridSys]]. There is a name overload for this method
   *  that passes the child [[HGridSys]] implicitly. */
  override def spawn(parentGridSys: HGridSys, childGridSys: HGridSys)(implicit ct: ClassTag[A]): LayerHcRefSys[A] =
  { val array: Array[A] = new Array[A](childGridSys.numTiles)
    childGridSys.foreach { hc => array(childGridSys.layerArrayIndex(hc)) = apply(hc)(parentGridSys) }
    new LayerHcRefMulti[A](array)
  }

  /** Fills in the whole given row. An exception is thrown if the tile values don't match with the
   *  end of the row. */
  final def setRow(row: Int, tileMultis: Multiple[A]*)(implicit grid: HGrid): HCen =
  { val numTiles = tileMultis.numSingles
    val cStart: Int = grid.rowLeftCenC(row)
    val endValues = cStart + numTiles * 4 - 4
    val rowEnd = grid.rowRightCenC(row)
    if (endValues > rowEnd) debexc(s"Row $row last data column ${endValues} > $rowEnd the grid row end.")
    tileMultis.iForeachSingle { (i, e) => val c = cStart + i * 4; arrayUnsafe(grid.layerArrayIndex(row, c)) = e }
    HCen(row, cStart + (numTiles - 1) * 4)
  }
}

/** Companion object for [[LayerHcRefGrid]], contains factory apply methods. */
object LayerHcRefGrid
{
  /** Apply factory method for [[LayerHcRefSys]]. */
  def apply[A <: AnyRef]()(implicit ct: ClassTag[A], gridSys: HGrid, defaultValue: DefaultValue[A]): LayerHcRefGrid[A] = apply(gridSys, defaultValue.default)(ct)

  /** Apply factory method for [[LayerHcRefSys]]. */
  def apply[A <: AnyRef](value: A)(implicit ct: ClassTag[A], gridSys: HGrid): LayerHcRefGrid[A] = apply(gridSys, value)(ct)

  /** Apply factory method for [[LayerHcRefSys]]. */
  def apply[A <: AnyRef](gridSys: HGrid, value: A)(implicit ct: ClassTag[A]): LayerHcRefGrid[A] =
  {
    val newArray = new Array[A](gridSys.numTiles)
    iUntilForeach(gridSys.numTiles)(i => newArray(i) = value)
    new LayerHcRefGrid[A](newArray)
  }
}

class LayerHcRefMulti[A <: AnyRef](val arrayUnsafe: Array[A]) extends AnyVal with LayerHcRefSys[A] //with LayerTcRef[A]
{
  //override type KeyT = HGridSys

  /** Spawns a new [[LayerHcRefSys]] data layer from this [[LayerHcRefSys]]'s [[HGridSys]] to the child [[HGridSys]]. There is a name overload for this method
   *  that passes the child [[HGridSys]] implicitly. */
  override def spawn(parentGridSys: HGridSys, childGridSys: HGridSys)(implicit ct: ClassTag[A]): LayerHcRefSys[A] =
  { val array: Array[A] = new Array[A](childGridSys.numTiles)
    childGridSys.foreach { hc => array(childGridSys.layerArrayIndex(hc)) = apply(hc)(parentGridSys) }
    new LayerHcRefMulti[A](array)
  }
}