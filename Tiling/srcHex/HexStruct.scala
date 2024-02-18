/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

trait HexStruct extends TCenStruct
{
  /** The number of [[HCorner]]s in this [[HGridSys]], 6 for each [[HCen]]. */
  final def numCorners: Int = numTiles * 6

  /** The conversion factor for c column tile grid coordinates. 1.0 / sqrt(3). */
  final override def yRatio: Double = 3.sqrt

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   * Array SeqDef data. */
  def layerArrayIndex(r: Int, c: Int): Int

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   * data. */
  @inline final def layerArrayIndex(hc: HCen): Int = layerArrayIndex(hc.r, hc.c)

  /** Boolean. True if the [[HCen]] hex centre exists in this hex grid. */
  final def hCenExists(hc: HCen): Boolean = hCenExists(hc.r, hc.c)

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def hCenExists(r: Int, c:Int): Boolean

  /** If the given [[HCen]] exists within this [[HGridSys]], perform the side effecting function. */
  def hCenExistsIfDo(hc: HCen)(proc: => Unit): Unit = if(hCenExists(hc)) proc

  /** If the given [[HCen]] exists within this [[HGridSys]], perform the side effecting function. */
  def hCenExistsIfDo(r: Int, c: Int)(proc: => Unit): Unit = if(hCenExists(r, c)) proc

  /** Boolean. True if the [[HCen]] hex centre exists in this hex grid. */
  final def hCoordExists(hc: HCoord): Boolean = hCoordExists(hc.r, hc.c)

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def hCoordExists(r: Int, c:Int): Boolean

}