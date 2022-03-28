/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, geom.pglobe._

object GridsNWNE extends HGridMulti {
  val gridMan1 = HGridMan(EGrid80Km.l0b446, 0)
  val gridMan2 = HGridMan(EGrid80Km.l30b446, gridMan1.numTiles)

  override def gridMans: Arr[HGridMan] = Arr(gridMan1, gridMan1)

  /** value might not be correct. */
  override def unsafeGetMan(r: Int, c: Int): HGridMan = ife(c <= 0x512, gridMan1, gridMan2)

  override def adjTilesOfTile(tile: HCen): HCens = ???

  override def findStep(startHC: HCen, endHC: HCen): OptRef[HStep] = ???
}