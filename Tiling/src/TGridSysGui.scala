/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, pgui._

abstract class TGridSysGui(val title: String) extends CmdBarGui
{ /** Tile grid system this gui displays. */
  def gridSys: TGridSys

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit

  def frame: GraphicElems
  def repaint(): Unit = mainRepaint(frame)

  /** The number of pixels displayed per c column coordinate. */
  var pixPerC: Double = 40

  /** Pixels per Tile. The number of pixels from a side of a tile to the opposite side of the tile. */
  def pixPerTile: Double

  var focus: Vec2 = Vec2(0, 0)

  def tilePScaleStr = s"scale = ${pixPerTile.str2} pixels per tile"
}