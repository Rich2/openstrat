/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._

object Grids80s0e1 extends EGrid80LongMulti
{ ThisSys =>

  override def cGridDelta: Double = 40
  override val grids: RArr[EGridLongFull] = RArr(EGrid80.l0b446, EGrid80.l30b446)
  override def headGridInt: Int = 0
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))

  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object Scen80s0s1 extends EScenLongMulti
{ override val gridSys: EGrid80LongMulti = Grids80s0e1
  override val longs: RArr[LongTerrs] = RArr(Terr80E0, Terr80E30)
}