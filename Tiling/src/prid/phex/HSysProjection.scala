/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** Hex grid system graphics projection. */
trait HSysProjection extends TSysProjection
{ type GridT <: HGridSys

  var gChild: HGridSys
  def hCenMap(f: (Pt2, HCen) => GraphicElem): GraphicElems
  def hCenSizedMap(hexScale: Double = 20)(f: (Pt2, HCen) => GraphicElem): GraphicElems

  //def hcPolyArrTrans(inp: PolygonHCArr)

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
}