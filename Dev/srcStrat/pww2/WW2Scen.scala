/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import prid._, phex._, egrid._, eg220._, pEarth._

/** Scenario for World War 2 grand strategy game. */
trait WW2Scen extends HSysTurnScen
{  ThisScen =>
  def title: String = "WW2Scen"
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

    val armiesNew: HCenOptLayer[Army] = armies.clone
    targets.foreach { (hc2, buff) => buff.foreachLen1(stCenStep => if (armies.tileNone(hc2)) armiesNew.moveMut(stCenStep.startHC, hc2)) }

    new WW2Scen {
      override implicit val gridSys: EGridSys = ThisScen.gridSys
      override val terrs: HCenLayer[WTile] = ThisScen.terrs
      override val sTerrs: HSideOptLayer[WSide, WSideSome] = ThisScen.sTerrs
      override val corners: HCornerLayer = ThisScen.corners
      override val armies: HCenOptLayer[Army] = armiesNew

      override def turn: Int = ThisScen.turn + 1
    }
  }
}

object WW2Scen
{
  def sa0(layer: HCenOptLayer[Army])(implicit sys: HGridSys): Unit =
  { val polities: RArr[Polity] = RArr(Britain, France, Germany)
    implicit val counters: ArrCounters[Polity] = ArrCounters(polities)
    layer.setFSomesMut(Germany.armyNext, 162,522,  160,520,  158,518,  156,520)
    layer.setFSomesMut(France.armyNext, 156,518, 154,518)
    layer.setFSomesMut(Britain.armyNext, 160,512,  158,514)
  }
}

object WW2Scen1 extends WW2Scen
{ override def turn: Int = 0

  override implicit val gridSys: EGrid220LongFull = Terr220E0.grid
  override val terrs: HCenLayer[WTile] = Terr220E0.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Terr220E0.sTerrs
  override val corners: HCornerLayer = Terr220E0.corners

  val armies: HCenOptLayer[Army] = gridSys.newHCenOptLayer
  WW2Scen.sa0(armies)
}

object WW2Scen2 extends WW2Scen
{ override def turn: Int = 0

  override implicit val gridSys: Grids220S0E1.type = Grids220S0E1
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn

  val armies: HCenOptLayer[Army] = gridSys.newHCenOptLayer
  WW2Scen.sa0(armies)
  //armies.unsafeSetSames(Japan.ar, 136,5624)
}