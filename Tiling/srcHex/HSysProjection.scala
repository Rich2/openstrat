/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** Hex grid system graphics projection. */
trait HSysProjection extends TSysProjection
{ type SysT <: HGridSys

  var gChild: HGridSys

  /** Maps the [[HCen]]s of this projection. */
  def hCenMap[B, ArrB <: Arr[B]](f: HCen => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB = gChild.map(f)

  /** OptMaps the [[HCen]]s visible in this projection. */
  def hCenOptMap[B, ArrB <: Arr[B]](f: HCen => Option[B])(implicit build: ArrMapBuilder[B, ArrB]): ArrB = gChild.optMap(f)

  /** IfMaps the [[HCen]]s visible in this projection. The mapping function is only applied if the first function condition is true. */
  def hCenIfMap[B, ArrB <: Arr[B]](f1: HCen => Boolean)(f2: HCen => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB = gChild.ifMap(f1)(f2)

  /** FlatMaps the [[HCen]]s visible in this projection. */
  def hCenFlatMap[ArrB <: Arr[_]](f: HCen => ArrB)(implicit build: ArrFlatBuilder[ArrB]): ArrB = gChild.flatMap(f)

  /** Maps the [[HCen]]s visible in this projection with their respective projected [[Pt2]]s. */
  def hCenPtMap(f: (HCen, Pt2) => GraphicElem): GraphicElems

  /** IfMaps the [[HCen]]s visible in this projection with their respective projected [[Pt2]]s. The mapping function is only applied if the first
   * function condition is true. */
  def hCensIfPtMap[B, ArrB <: Arr[B]](f1: HCen => Boolean)(f2: (HCen, Pt2) => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
    gChild.ifMap(f1)(hc => f2(hc, transCoord(hc)))

  /** IfFlatMaps the [[HCen]]s visible in this projection with their respective projected [[Pt2]]s. The flatMapping function is only applied if the
   *  first function condition is true */
  def hCenIfPtFlatMap[ArrB <: Arr[_]](f1: HCen => Boolean)(f2: (HCen, Pt2) => ArrB)(implicit build: ArrFlatBuilder[ArrB]): ArrB =
    gChild.ifFlatMap(f1)(hc => f2(hc, transCoord(hc)))

  def hCenSizedMap(hexScale: Double = 20)(f: (HCen, Pt2) => GraphicElem): GraphicElems

  def hCenPolygons(corners: HCornerLayer): HCenPairArr[Polygon] =
    gChild.mapPair{hc => corners.tilePoly(hc)(parent).map { hvo => hvo.toPt2(transCoord(_))(parent) } }

  /** transforms and filters out non visible [[HSide]]s. */
  def transHSides(inp: HSideArr): LineSegArr

  def transTile(hc: HCen): Option[Polygon]
  def transOptCoord(hc: HCoord): Option[Pt2]

  /** only use for projection's known [[HCoord]]s. */
  def transCoord(hc: HCoord): Pt2
  def transHVOffset(hvo: HVOffset): Pt2 = hvo.toPt2(transCoord)(parent)
  def transPolygonHVOffset(inp: PolygonHVOffset): Polygon = inp.toPolygon(transCoord)(parent)


  def transOptLineSeg(seg: LineSegHC): Option[LineSeg]
  def transLineSeg(seg: LineSegHC): LineSeg

  def lineSeg(hs: HSide): LineSeg = transLineSeg(hs.lineSegHC)

  /** Produces optional data about the HCoord. for example on a world projection it can give the latitude and longitude. */
  def hCoordOptStr(hc: HCoord): Option[String] = None

  /** Set the perspective, The position of the view. the rotation and the scale. */
  def setView(view: Any): Unit

  def transLineSegPairs[A2](inp: LineSegHCPairArr[A2])(implicit ct: ClassTag[A2]): LineSegPairArr[A2] = inp.optMapOnA1(transOptLineSeg(_))

  def sidesOptMap[B, ArrB <: Arr[B]](f: HSide => Option[B])(implicit build: ArrMapBuilder[B, ArrB]): ArrB = gChild.sidesOptMap(f)

  def linksOptMap[B, ArrB <: Arr[B]](f: HSide => Option[B])(implicit build: ArrMapBuilder[B, ArrB]): ArrB = gChild.linksOptMap(f)

  def linkLineSegsOptMap[B, ArrB <: Arr[B]](f: (HSide, LineSeg) => Option[B])(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
    gChild.linksOptMap{hs => f(hs, lineSeg(hs)) }
}