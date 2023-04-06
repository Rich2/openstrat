/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import prid._, phex._, egrid._, eg80._

trait BCScen extends HSysTurnScen
{ ThisScen =>

  override def title: String = "BC305 scenario"

  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptionalLayer[WSide, WSideSome]
  val corners: HCornerLayer
  val armies: HCenOptLayer[Legion]

  def endTurn(orderList: HCenStepPairArr[Legion]): BCScen = {
    val targets: HCenBuffLayer[HCenStep] = gridSys.newHCenArrOfBuff

    orderList.foreach { pair =>
      val optTarget: Option[HCen] = pair.startHC.stepOpt(pair.step)
      optTarget.foreach { target => if (terrs(target).isLand) targets.appendAt(target, pair.a1) }
    }

    val armiesNew: HCenOptLayer[Legion] = armies.clone
    targets.foreach { (hc2, buff) => buff.foreachLen1(stCenStep => if (armies.tileNone(hc2)) armiesNew.moveMut(stCenStep.startHC, hc2)) }

    new BCScen
    { override implicit def gridSys: HGridSys = ThisScen.gridSys
      override val terrs: HCenLayer[WTile] = ThisScen.terrs
      override val sTerrs: HSideOptionalLayer[WSide, WSideSome] = ThisScen.sTerrs
      override val corners: HCornerLayer = ThisScen.corners
      override val armies: HCenOptLayer[Legion] = armiesNew
      override def turn: Int = ThisScen.turn + 1
    }
  }
}

object BCScen1 extends BCScen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid80LongMulti = EGrid80.multi(2, 0, 418)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptionalLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override val armies: HCenOptLayer[Legion] = gridSys.newHCenOptLayer
  armies.setSomeMut(434,562, Rome.lg(1) )
  armies.setSomeMut(434,566, Rome.lg(2))
  armies.setSomeMut(418,1502, Sparta.lg(1))
}

object BCScen2 extends BCScen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid80LongFull = Terr80E0.grid
  override val terrs: HCenLayer[WTile] = Terr80E0.terrs
  override val sTerrs: HSideOptionalLayer[WSide, WSideSome] = Terr80E0.sTerrs
  override val corners: HCornerLayer = Terr80E0.corners
  override val armies: HCenOptLayer[Legion] = gridSys.newHCenOptLayer
}