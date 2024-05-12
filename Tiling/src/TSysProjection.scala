/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, pgui._

/** Tile system graphical projection. */
trait TSysProjection
{ /** The type of the grid system this projects from. */
  type SysT <: TGridSys

  /** The parent grid system of the scenario, from which the this projection projects from. */
  def parent: SysT

  /** The panel this projection outputs to. */
  def panel: Panel

  def setGChild: Unit

  var pixelsPerC: Double = 80

  /** The number of pixels per tile from side to opposite side. */
  def pixelsPerTile: Double

  def pixTileScaleStr = s"scale = ${pixelsPerTile.str2} pixels per tile"

  /** Gives the projector access to the scenarios tile graphic creation. */
  var getFrame: () => GraphicElems = () => RArr()

  /** Filters the [[GraphicElem]]s away if the tile scale is too small to display the elements satisfactorily.  */
  def ifTileScale(minScale: Double, elems: => GraphicElems): GraphicElems

  var setStatusText: String => Unit = s => {}

  def zoomFactor(button: MouseButton): Double = button match
  { case MiddleButton => 1.4
    case RightButton => 2.0
    case _ => 1.1
  }

  def zoomIn: PolygonCompound = clickButton("+") { b =>
    pixelsPerC *= zoomFactor(b)
    setGChild
    panel.repaint(getFrame())
    setStatusText(pixTileScaleStr)
  }

  def zoomOut: PolygonCompound = clickButton("-") { b =>
    pixelsPerC /= zoomFactor(b)
    setGChild
    panel.repaint(getFrame())
    setStatusText(pixTileScaleStr)
  }

  panel.onScroll = { b =>
    pixelsPerC = ife(b, pixelsPerC * 1.1, pixelsPerC / 1.1)
    setGChild
    panel.repaint(getFrame())
    setStatusText(pixTileScaleStr)
  }

  val buttons: RArr[PolygonCompound]

  /** Projected [[Polygon]]s of the tiles. */
  def tilePolygons: PolygonGenArr

  /** Active projected [[Polygon]]s of the tiles. */
  def tileActives: RArr[PolygonActive]

  /** The visible hex sides. */
  def sideLines: LineSegArr

  /** The visible inner hex sides. */
  def innerSideLines: LineSegArr

  /** The visible outer hex sides. */
  def outerSideLines: LineSegArr

  /** Draws visible hex sides. */
  def sidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = sideLines.draw(lineWidth, colour)

  /** Draws visible inner hex sides. */
  def innerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = innerSideLines.draw(lineWidth, colour)

  /** Draws visible outer hex sides. */
  def outerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = outerSideLines.draw(lineWidth, colour)
}