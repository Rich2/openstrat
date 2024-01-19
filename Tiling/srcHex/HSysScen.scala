/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, pgui._

/** Graphical User Interface for [[HGridSys]] hex grid systems. */
abstract class HGridSysGui(val title: String) extends CmdBarGui// TGridSysGui(title)
{ def gridSys: HGridSys
  implicit def proj: HSysProjection
  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit

  def frame: GraphicElems
  def repaint(): Unit = mainRepaint(frame)

  /** The number of pixels displayed per c column coordinate. */
  var pixPerC: Double = 40

  /** Pixels per Tile. The number of pixels from a side of a tile to the opposite side of the tile. */
  //def pixPerTile: Double

  def tilePScaleStr = s"scale = ${pixPerTile.str2} pixels per tile"

  final def pixPerTile: Double = proj.pixelsPerTile
}

/** Scenario based on a [[HGrid]] system. */
trait HSysScen
{ implicit def gridSys: HGridSys
  def defaultView(pxScale: Double = 50): HGView = gridSys.defaultView(pxScale)
}

trait HSysTurnScen extends HSysScen with GridTurnScen

trait HGridScen extends HSysScen
{ /** This gives the structure of the hex grid system. It contains no data about the elements of the grid. But it allows the scenario to create and
   * operate on flat arrays of data. */
  implicit override def gridSys: HGrid
}