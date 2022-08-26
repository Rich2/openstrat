/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, pgui._

/** Hex grid system graphics projection. */
trait HSysProjection
{ type GridT <: HGridSys
  def gridSys: GridT
  var getFrame: () => GraphicElems = () => Arr()
  var setStatusText: String => Unit = s => {}
  val buttons: Arr[PolygonCompound]
  def tiles: PolygonArr
  def tileActives: Arr[PolygonActive]
  var gChild: HGridSys
  def hCenMap(f: (Pt2, HCen) => GraphicElem): GraphicElems = ???

  //def hcPolyArrTrans(inp: PolygonHCArr)

  /** The visible hex sides. */
  def sides: LineSegArr

  /** The visible inner hex sides. */
  def innerSides: LineSegArr

  /** The visible outer hex sides. */
  def outerSides: LineSegArr

  /** transforms and filters out non visible [[HSide]]s. */
  def transHSides(inp: HSideArr): LineSegArr

  def transTile(hc: HCen): Option[Polygon]
  def transCoord(hc: HCoord): Option[Pt2]
  def transLineSeg(seg: LineSegHC): Option[LineSeg]

  /** Produces optional data about the HCoord. for example on a world projection it can give the latitude and longitude. */
  def hCoordOptStr(hc: HCoord): Option[String] = None

  /** Draws visible hex sides. */
  def sidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = sides.draw(lineWidth, colour)

  /** Draws visible inner hex sides. */
  def innerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = innerSides.draw(lineWidth, colour)

  /** Draws visible outer hex sides. */
  def outerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = outerSides.draw(lineWidth, colour)

  def panel: Panel

  /** Set the perpective, The position of the view. the rotation and the scale. */
  def setView(view: Any): Unit
}