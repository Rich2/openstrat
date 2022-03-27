/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, geom.pglobe._

object GridsNWNE extends HGridMulti {
  val gridMan1 = HGridMan(EGrid80Km.l0b446)
  val gridMan2 = HGridMan(EGrid80Km.l30b446)

  override def gridMans: Arr[HGridMan] = Arr(gridMan1, gridMan1)

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  override def hCenExists(r: Int, c: Int): Boolean = ???

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   * Array data. */
  override def arrIndex(r: Int, c: Int): Int = ???

  override def adjTilesOfTile(tile: HCen): HCens = ???

  override def findStep(startHC: HCen, endHC: HCen): OptRef[HStep] = ???
}