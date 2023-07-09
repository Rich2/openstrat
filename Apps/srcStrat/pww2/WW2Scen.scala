/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import prid._, phex._, egrid._, eg320._

/** Scenario for World War 2 grand strategy game. */
trait WW2Scen extends HSysTurnScen
{  ThisScen =>
  def title: String = "WW2Scen"
  override def toString = title
  override implicit val gridSys: EGridSys
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide, WSideSome]
  val corners: HCornerLayer
  def armies: HCenOptLayer[Army]

  def endTurn(orderList: HCenStepPairArr[Army]): WW2Scen =
  {
    val targets: HCenBuffLayer[HCenStep] = gridSys.newHCenArrOfBuff

    orderList.foreach { pair =>
      val optTarget: Option[HCen] = pair.startHC.stepOpt(pair.step)
      optTarget.foreach { target => if (terrs(target).isLand) targets.appendAt(target, pair.a1) }
    }

    val armiesNew: HCenOptLayer[Army] = armies.copy
    targets.foreach { (hc2, buff) => buff.foreachLen1(stCenStep => if (armies.emptyTile(hc2)) armiesNew.moveUnsafe(stCenStep.startHC, hc2)) }

    new WW2Scen
    { override implicit val gridSys: EGridSys = ThisScen.gridSys
      override val terrs: HCenLayer[WTile] = ThisScen.terrs
      override val sTerrs: HSideOptLayer[WSide, WSideSome] = ThisScen.sTerrs
      override val corners: HCornerLayer = ThisScen.corners
      override val armies: HCenOptLayer[Army] = armiesNew

      override def turn: Int = ThisScen.turn + 1
    }
  }
}

/** Initial main scenario for World War 2. Scenario will start March 1 1942. Turns will be 3 months. Segments may initially be a month or 2 weeks. */
object WW2Scen1 extends WW2Scen
{ override def turn: Int = 0

  override implicit val gridSys: EGrid320LongMulti =  Scen320All.gridSys
  override val terrs: HCenLayer[WTile] = Scen320All.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Scen320All.sTerrs
  override val corners: HCornerLayer = Scen320All.corners

  val armies: HCenOptLayer[Army] = HCenOptLayer()
  val polities: RArr[Polity] = RArr(Britain, Soviet, France, Germany, Japan)
  implicit val counters: ArrCounters[Polity] = ArrCounters(polities)
  armies.setFSomesMut(Germany.armyNext, 146,1534,  144,1536,  142,1538,  140,1540,  138,1542,  136,1540)
  armies.setFSomesMut(Soviet.armyNext, 148,1536,  146,1538,  144,1540,  142,1542,  140,1544,  138,1546,  136,1544)
  armies.setFSomesMut(Britain.armyNext, 142,514)
  armies.setFSomesMut(Japan.armyNext, 128,4624)
}

object WW2Scen2 extends WW2Scen
{ override def turn: Int = 0

  override implicit val gridSys: EGrid320LongFull = Terr320E0.grid
  override val terrs: HCenLayer[WTile] = Terr320E0.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Terr320E0.sTerrs
  override val corners: HCornerLayer = Terr320E0.corners
  val armies: HCenOptLayer[Army] = HCenOptLayer()
}