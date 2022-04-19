/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgo
import geom._, prid._, psq._, pgui._

case class GoGui(canv: CanvasPlatform, startScen: GoScen) extends SquareMapGui("Go"){
  /** Tile grid this gui displays. */
  override def gridSys: TGridSysFlat = startScen.grid

  /** The number of pixels displayed per c column coordinate. */
  override var cPScale: Double = 20

  def bTurn: PolygonCompound = simpleButton("Turn "){}

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  override def thisTop(): Unit = reTop(bTurn %: navButtons)
  thisTop()
  override def frame: GraphicElems = Arr()
}
