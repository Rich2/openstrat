/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom.*, reflect.ClassTag

/** Hex grid system graphics projection. */
trait HSysProjection extends TSysProjection
{ type SysT <: HGridSys

  var gChild: HGridSys

  /** Maps the [[HCen]]s of this projection. */
  def hCenMap[B, ArrB <: Arr[B]](f: HCen => B)(using build: BuilderArrMap[B, ArrB]): ArrB = gChild.map(f)

  /** OptMaps the [[HCen]]s visible in this projection. */
  def hCenOptMap[B, ArrB <: Arr[B]](f: HCen => Option[B])(using build: BuilderArrMap[B, ArrB]): ArrB = gChild.optMap(f)

  /** IfMaps the [[HCen]]s visible in this projection. The mapping function is only applied if the first function condition is true. */
  def hCenIfMap[B, ArrB <: Arr[B]](f1: HCen => Boolean)(f2: HCen => B)(using build: BuilderArrMap[B, ArrB]): ArrB = gChild.ifMap(f1)(f2)

  /** FlatMaps the [[HCen]]s visible in this projection. */
  def hCenFlatMap[ArrB <: Arr[?]](f: HCen => ArrB)(using build: BuilderArrFlat[ArrB]): ArrB = gChild.flatMap(f)

  /** Maps the [[HCen]]s visible in this projection with their respective projected [[Pt2]]s. */
  def hCenPtMap(f: (HCen, Pt2) => Graphic2Elem): GraphicElems

  /** IfMaps the [[HCen]]s visible in this projection with their respective projected [[Pt2]]s. The mapping function is only applied if the first
   * function condition is true. */
  def hCensIfPtMap[B, ArrB <: Arr[B]](f1: HCen => Boolean)(f2: (HCen, Pt2) => B)(using build: BuilderArrMap[B, ArrB]): ArrB =
    gChild.ifMap(f1)(hc => f2(hc, transCoord(hc)))

  /** IfFlatMaps the [[HCen]]s visible in this projection with their respective projected [[Pt2]]s. The flatMapping function is only applied if the
   *  first function condition is true */
  def hCensIfPtFlatMap[ArrB <: Arr[?]](f1: HCen => Boolean)(f2: (HCen, Pt2) => ArrB)(using build: BuilderArrFlat[ArrB]): ArrB =
    gChild.ifFlatMap(hc => f1(hc) && transOptCoord(hc).nonEmpty)(hc => f2(hc, transCoord(hc)))

  def hCenSizedMap(hexScale: Double = 20)(f: (HCen, Pt2) => Graphic2Elem): GraphicElems

  /** Produces the hex tile polygons modified by the [[HCornerLayer]] parameter. Polygons not visible in the projection should be excluded. */
  def hCenPolygons(corners: HCornerLayer): HCenPairArr[Polygon] = gChild.optMapPair{hc =>
    val poly: PolygonHvOffset = corners.tilePoly(parent, hc)
    poly.optMap(transOptHVOffset(_))
  }

  /** Produces the [[HSep]] separator polygons from the [[HCornerLayer]] parameter. Polygons not visible in the projection should be excluded. */
  def hSepPolygons(f: HSep => Boolean, corners: HCornerLayer): HSepArrPair[Polygon] =
    gChild.sepOptMapPair { hs => if(f(hs)) corners.sepPoly(parent, hs).optMap(transOptHVOffset(_)) else None }

  /** transforms and filters out non-visible [[HSep]]s. */
  def transHSeps(inp: HSepArr): LSeg2Arr

  def transTile(hc: HCen): Option[Polygon]

  /** Optionally transforms an [[HCoord]] to a [[Pt2]]. This is safe unlike transCoord. */
  def transOptCoord(hc: HCoord): Option[Pt2]

  /** Generally unsafe. Transforms an [[HCoord]] to a [[Pt2]]. Only use for [[HCoord]]s that are guaranteed displayed by the projection. */
  def transCoord(hc: HCoord): Pt2

  def transHVOffset(hvo: HvOffset): Pt2 = hvo.toPt2(parent)(transCoord)
  def transOptHVOffset(hvo: HvOffset): Option[Pt2]

  def transPolygonHVOffset(inp: PolygonHvOffset): Polygon = inp.toPolygon(parent)(transCoord)
  def transOptPolygonHVOffset(inp: PolygonHvOffset): Option[Polygon] = inp.optMap(transOptHVOffset(_))

  def transOptLineSeg(seg: LSegHC): Option[LSeg2]
  def transLineSeg(seg: LSegHC): LSeg2

  def lineSeg(hs: HSep): LSeg2 = transLineSeg(hs.lineSegHC)

  /** Produces optional data about the HCoord. for example on a world projection it can give the latitude and longitude. */
  def hCoordOptStr(hc: HCoord): Option[String] = None

  /** Set the perspective, The position of the view. the rotation and the scale. */
  def setView(view: Any): Unit

  def transLineSegPairs[A2](inp: LineSegHCPairArr[A2])(using ct2: ClassTag[A2]): LineSegPairArr[A2] = inp.optMapOnA1(transOptLineSeg(_))

  def sidesOptMap[B, ArrB <: Arr[B]](f: HSep => Option[B])(using build: BuilderArrMap[B, ArrB]): ArrB = gChild.sepsOptMap(f)

  def linksOptMap[B, ArrB <: Arr[B]](f: HSep => Option[B])(using build: BuilderArrMap[B, ArrB]): ArrB = gChild.linksOptMap(f)

  def linkLineSegsOptMap[B, ArrB <: Arr[B]](f: (HSep, LSeg2) => Option[B])(using build: BuilderArrMap[B, ArrB]): ArrB =
    gChild.linksOptMap{hs => f(hs, lineSeg(hs)) }
}