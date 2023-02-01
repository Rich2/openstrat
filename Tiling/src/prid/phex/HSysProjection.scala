/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** Hex grid system graphics projection. */
trait HSysProjection extends TSysProjection
{ type GridT <: HGridSys

  var gChild: HGridSys
  def hCensMap[B, ArrB <: Arr[B]](f: HCen => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB = gChild.map(f)
  def hCenPtMap(f: (HCen, Pt2) => GraphicElem): GraphicElems
  def hCenSizedMap(hexScale: Double = 20)(f: (HCen, Pt2) => GraphicElem): GraphicElems

  /** transforms and filters out non visible [[HSide]]s. */
  def transHSides(inp: HSideArr): LineSegArr

  def transTile(hc: HCen): Option[Polygon]
  def transOptCoord(hc: HCoord): Option[Pt2]

  /** only use for projection's known [[HCoord]]s. */
  def transCoord(hc: HCoord): Pt2

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