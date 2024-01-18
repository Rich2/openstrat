/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww1
import prid._, phex._, egrid._, eg120._//, pStrat._

/** Scenario for WW1 game. */
trait WW1Scen extends HSysTurnScen
{ ThisScen =>
  def title: String = "WWI scenario."
  val terrs: LayerHcRefSys[WTile]
  val sTerrs: LayerHSOptSys[WSide, WSideSome]
  val corners: HCornerLayer
  val lunits: LayerHcRArr[Lunit]

  def endTurn(orderList: HCenStepPairArr[Lunit]): WW1Scen = ???
  /*{  val targets: HCenBuffLayer[HCenStep] = gridSys.newHCenArrOfBuff

    orderList.foreach { pair =>
      val optTarget: Option[HCen] = pair.startHC.stepOpt(pair.step)
      optTarget.foreach { target => if (terrs(target).isLand) targets.appendAt(target, pair.a1) }
    }

    val armiesNew: LayerHcOptSys[Lunit] = lunits.copy
    targets.foreach { (hc2, buff) => buff.foreachLen1(stCenStep => if (lunits.emptyTile(hc2)) armiesNew.moveUnsafe(stCenStep.startHC, hc2)) }

    new WW1Scen
    { override implicit def gridSys: HGridSys = ThisScen.gridSys
      override val terrs: LayerHcRefSys[WTile] = ThisScen.terrs
      override val sTerrs: LayerHSOptSys[WSide, WSideSome] = ThisScen.sTerrs
      override val corners: HCornerLayer = ThisScen.corners
      override val lunits: LayerHcOptSys[Lunit] = armiesNew
      override def turn: Int = ThisScen.turn + 1
    }
  }*/
}

object WW1Scen1 extends WW1Scen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid120LongFull = Terr120E0.grid
  override val terrs: LayerHcRefSys[WTile] = Terr120E0.terrs
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = Terr120E0.sTerrs
  override val corners: HCornerLayer = Terr120E0.corners
  override val lunits: LayerHcRArr[Lunit] = LayerHcRArr()
  lunits.set1(310, 514, Army(pStrat.Britain, 1))
  lunits.set1(308, 528, Army(pStrat.Germany, 1))
  lunits.set1(306, 526, CavalryCorps(pStrat.Germany, 1))
  lunits.set1(306, 518, Army(pStrat.France, 1))
}

object WW1Scen2 extends WW1Scen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid120LongFull = Terr120E30.grid
  override val terrs: LayerHcRefSys[WTile] = Terr120E30.terrs
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = Terr120E30.sTerrs
  override val corners: HCornerLayer = Terr120E30.corners
  override val lunits: LayerHcRArr[Lunit] = LayerHcRArr()
}