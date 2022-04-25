/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._

object GridsNWNE extends EGrid80MainMulti
{
  val gridMan1: EGrid80MainMan = new EGrid80MainMan
  { override val grid: EGrid80Main = EGrid80Km.l0b446
    override val arrIndex: Int = 0
    override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
  }

  val gridMan2: EGrid80MainMan = new EGrid80MainMan
  { override val grid: EGrid80Main = EGrid80Km.l30b446
    override val arrIndex: Int = gridMan1.numTiles
    override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
  }

  override val gridMans: Arr[EGrid80MainMan] = Arr(gridMan1, gridMan1)

  /** Splits [[HCen]] allocation at 0y100 0r 1024. */
  override def unsafeGetMan(r: Int, c: Int): EGridMan = ife(c < t"100", gridMan1, gridMan2)

  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object ScenNWNE extends EScenBasic(GridsNWNE, EuropeNW80Terr() ++ EuropeNE80Terr())
{

}