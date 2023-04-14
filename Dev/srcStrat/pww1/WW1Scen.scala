/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww1
import prid._, phex._, egrid._, eg120._, pEarth._

trait WW1Scen extends HSysTurnScen
{ ThisScen =>
  def title: String = "WWI scenario."
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide, WSideSome]
  val corners: HCornerLayer
  val lunits: HCenOptLayer[Lunit]

  def endTurn(orderList: HCenStepPairArr[Army]): WW1Scen =
  {  val targets: HCenBuffLayer[HCenStep] = gridSys.newHCenArrOfBuff

    orderList.foreach { pair =>
      val optTarget: Option[HCen] = pair.startHC.stepOpt(pair.step)
      optTarget.foreach { target => if (terrs(target).isLand) targets.appendAt(target, pair.a1) }
    }

    val armiesNew: HCenOptLayer[Lunit] = lunits.clone
    targets.foreach { (hc2, buff) => buff.foreachLen1(stCenStep => if (lunits.tileNone(hc2)) armiesNew.moveMut(stCenStep.startHC, hc2)) }

    new WW1Scen
    { override implicit def gridSys: HGridSys = ThisScen.gridSys
      override val terrs: HCenLayer[WTile] = ThisScen.terrs
      override val sTerrs: HSideOptLayer[WSide, WSideSome] = ThisScen.sTerrs
      override val corners: HCornerLayer = ThisScen.corners
      override val lunits: HCenOptLayer[Lunit] = armiesNew
      override def turn: Int = ThisScen.turn + 1
    }
  }
}

object WW1Scen1 extends WW1Scen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid120LongFull = Terr120E0.grid
  override val terrs: HCenLayer[WTile] = Terr120E0.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Terr120E0.sTerrs
  override val corners: HCornerLayer = Terr120E0.corners
  override val lunits: HCenOptLayer[Lunit] = gridSys.newHCenOptLayer[Lunit]
  lunits.setSomeMut(310, 514, Army(Britain, 1))
  lunits.setSomeMut(308, 528, Army(Germany, 1))
  lunits.setSomeMut(306, 526, CavalryCorps(Germany, 1))
  lunits.setSomeMut(306, 518, Army(France, 1))
}

object WW1Scen2 extends WW1Scen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid120LongFull = Terr120E30.grid
  override val terrs: HCenLayer[WTile] = Terr120E30.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Terr120E30.sTerrs
  override val corners: HCornerLayer = Terr120E30.corners
  override val lunits: HCenOptLayer[Lunit] = gridSys.newHCenOptLayer[Lunit]
}