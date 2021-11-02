/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, pCanv._

abstract class TileMapGui(title: String) extends CmdBarGui(title)
{
  /** The number of pixels / 2 displayed per row height. */
  var yScale: Double

  /** The number of pixels from a side of a tile to the opposite tile. */
  def tileScale: Double

  def scaleStr = s"scale = ${tileScale.str2} pixels per tile"
  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit
  def frame: GraphicElems
  def repaint(): Unit = mainRepaint(frame)

  def zoomIn: PolygonCompound = clickButton("+"){_ =>
    yScale *= 1.1
    repaint()
    statusText = scaleStr
    thisTop()
  }

  def zoomOut: PolygonCompound = clickButton("-"){_ =>
    yScale /= 1.1
    repaint()
    statusText = scaleStr
    thisTop()
  }
}

abstract class HexMapGui(title: String) extends TileMapGui(title)
{
  override def tileScale: Double = yScale * 4 / Sqrt3
}

abstract class SquareMapGui(title: String) extends TileMapGui(title)
{
  override def tileScale: Double = yScale * 2
}