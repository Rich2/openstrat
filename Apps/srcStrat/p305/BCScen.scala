/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import prid._, phex._, egrid._, eg220._

/** Game scario for BC305. */
trait BCScen extends HSysTurnScen
{ ThisScen =>

  override def title: String = "BC305 scenario"

  val terrs: LayerHcRefSys[WTile]
  val sTerrs: LayerHSOptSys[WSep, WSepSome]
  val corners: HCornerLayer
  val armies: LayerHcOptSys[Legion]

  def endTurn(orderList: HCenStepPairArr[Legion]): BCScen =
  { val targets: HCenBuffLayer[HCenStep] = gridSys.newHCenArrOfBuff

    orderList.foreach { pair =>
      val optTarget: Option[HCen] = pair.startHC.stepOpt(pair.step)
      optTarget.foreach { target => if (terrs(target).isLand) targets.appendAt(target, pair.a1) }
    }

    val armiesNew: LayerHcOptSys[Legion] = armies.copy
    targets.foreach { (hc2, buff) => buff.foreachLen1(stCenStep => if (armies.emptyTile(hc2)) armiesNew.moveUnsafe(stCenStep.startHC, hc2)) }

    new BCScen
    { override implicit def gridSys: HGridSys = ThisScen.gridSys
      override val terrs: LayerHcRefSys[WTile] = ThisScen.terrs
      override val sTerrs: LayerHSOptSys[WSep, WSepSome] = ThisScen.sTerrs
      override val corners: HCornerLayer = ThisScen.corners
      override val armies: LayerHcOptSys[Legion] = armiesNew
      override def turn: Int = ThisScen.turn + 1
    }
  }
}

/** Scenario 1 for BC305. This scenario will bedeveloped into the main game. */
object BCScen1 extends BCScen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid220LongMulti =  EGrid220.multi(2, 0, 134, 154)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override val armies: LayerHcOptSys[Legion] = LayerHcOptSys()
  armies.setSomeMut(148,532, Rome.lg(1) )
  armies.setSomeMut(148,1516, Rome.lg(2))
  armies.setSomeMut(144,1524, Sparta.lg(1))
}

object BCScen2 extends BCScen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid220LongFull = Terr220E30.grid
  override val terrs: LayerHcRefSys[WTile] = Terr220E30.terrs
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = Terr220E30.sTerrs
  override val corners: HCornerLayer = Terr220E30.corners
  override val armies: LayerHcOptSys[Legion] = LayerHcOptSys()
}