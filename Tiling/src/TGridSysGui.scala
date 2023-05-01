/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, pgui._

abstract class TGridSysGui(title: String) extends CmdBarGui(title)
{ /** Tile grid system this gui displays. */
  def gridSys: TGridSys

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit

  def frame: GraphicElems
  def repaint(): Unit = mainRepaint(frame)

  /** The number of pixels displayed per c column coordinate. */
  var pixPerC: Double = 40

  /** Pixels per Tile. The number of pixels from a side of a tile to the opposite side of the tile. */
  def ptScale: Double

  var focus: Vec2 = Vec2(0, 0)

  def tilePScaleStr = s"scale = ${ptScale.str2} pixels per tile"
}