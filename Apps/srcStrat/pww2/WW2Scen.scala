/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import prid._, phex._, egrid._, eg460._, pStrat._

/** Scenario for World War 2 grand strategy game. */
trait WW2Scen extends HSysTurnScen
{  ThisScen =>
  def title: String = "WW2Scen"
  override def toString = title
  override implicit val gridSys: EGridSys
  val terrs: LayerHcRefSys[WTile]
  val sTerrs: LayerHSOptSys[WSep, WSepSome]
  val corners: HCornerLayer
  def lunitSts: LayerHcRArr[Lunit]

  def endTurn(orderList: HCenStepPairArr[BrArmyDesigNum]): WW2Scen = ???
  /*{
    val targets: HCenBuffLayer[HCenStep] = gridSys.newHCenArrOfBuff

    orderList.foreach { pair =>
      val optTarget: Option[HCen] = pair.startHC.stepOpt(pair.step)
      optTarget.foreach { target => if (terrs(target).isLand) targets.appendAt(target, pair.a1) }
    }

    val armiesNew: LayerHcOptSys[Army] = armies.copy
    targets.foreach { (hc2, buff) => buff.foreachLen1(stCenStep => if (armies.emptyTile(hc2)) armiesNew.moveUnsafe(stCenStep.startHC, hc2)) }

    new WW2Scen
    { override implicit val gridSys: EGridSys = ThisScen.gridSys
      override val terrs: LayerHcRefSys[WTile] = ThisScen.terrs
      override val sTerrs: LayerHSOptSys[WSide, WSideSome] = ThisScen.sTerrs
      override val corners: HCornerLayer = ThisScen.corners
      override val armies: HCenRArrLayer[LunitSt] =  armiesNew

      override def turn: Int = ThisScen.turn + 1
    }
  }*/
}

/** Initial main scenario for World War 2. Scenario will start March 1 1942. Turns will be 3 months. Segments may initially be a month or 2 weeks. */
object WW2Scen1 extends WW2Scen
{ override def turn: Int = 0

  override implicit val gridSys: EGrid460LongMulti = Scen460All.gridSys
  override val terrs: LayerHcRefSys[WTile] = Scen460All.terrs
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = Scen460All.sTerrs
  override val corners: HCornerLayer = Scen460All.corners

  val lunitSts: LayerHcRArr[Lunit] = LayerHcRArr[Lunit]()
  val polities: RArr[Polity] = RArr(Britain, Soviet, France, Germany, Japan)
  val AfKorps0 = Lunit(AfricaKorps, PzArmeeUnNum("Africa"))
  val de1Armee0: DeArmee = DeArmee.num(DeArmee1, 1, 2)
  val de7Armee0 = DeArmee.num(DeArmee7, 7, 2)
  val de15Armee0 = DeArmee.num(DeArmee15, 15, 2)
  val Br8Army0 = BrArmy.num(BrArmy8, 8, 2)
  lunitSts.set1(118, 1538, Br8Army0)
//  lunitSts.set1(128, 512, LunitSt(BrCorps5))
  lunitSts.set1(126, 510, de1Armee0)
  lunitSts.set1(126, 514, de7Armee0)
  lunitSts.set1(128, 516, de15Armee0)
  lunitSts.set1(118, 1534, AfKorps0)
  //implicit val counters: ArrCounters[Polity] = ArrCounters(polities)
}

object WW2Scen2 extends WW2Scen
{ override def turn: Int = 0

  override implicit val gridSys = Scen460Europe.gridSys
  override val terrs: LayerHcRefSys[WTile] = Scen460Europe.terrs
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = Scen460Europe.sTerrs
  override val corners: HCornerLayer = Scen460Europe.corners
  val lunitSts: LayerHcRArr[Lunit] = LayerHcRArr()
}