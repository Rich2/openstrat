/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import prid._, phex._, eg320._, pEarth._, egrid._

/** Scenario trait for Diceless. */
trait DLessScen extends HSysTurnScen
{ ThisScen =>
  def title: String = "DLessScen"
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide]
  val corners: HCornerLayer
  val armies: HCenOptLayer[Army]

  def endTurn(orderList: HCenStepPairArr[Army]): DLessScen =
  { val targets: HCenBuffLayer[HCenStep] = gridSys.newHCenArrOfBuff

    orderList.foreach { pair =>
      val optTarget: Option[HCen] = pair.startHC.stepOpt(pair.step)
      optTarget.foreach { target => if (terrs(target).hasLand) targets.appendAt(target, pair.a1) }
    }

    val armiesNew: HCenOptLayer[Army] = armies.clone
    targets.foreach { (hc2, buff) => buff.foreachLen1(stCenStep => if (armies.tileNone(hc2)) armiesNew.unsafeMove(stCenStep.startHC, hc2)) }

    new DLessScen
    { override implicit def gridSys: HGridSys = ThisScen.gridSys
      override val terrs: HCenLayer[WTile] = ThisScen.terrs
      override val sTerrs: HSideOptLayer[WSide] = ThisScen.sTerrs
      override val corners: HCornerLayer = ThisScen.corners
      override val armies: HCenOptLayer[Army] = armiesNew
      override def turn: Int = ThisScen.turn + 1
    }
  }
}

/** The main scenario for Diceless. */
object DLessScen1 extends DLessScen
{
  override def turn: Int = 0

  override implicit val gridSys: EGrid320LongMulti = EGrid320.multi(2, 0, 124)

  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override val armies: HCenOptLayer[Army] = gridSys.newHCenOptLayer
  armies.unsafeSetSome(142, 510, Britain.ar(1))
  armies.unsafeSetSome(144, 508, Britain.ar(2))
  armies.unsafeSetSome(140, 520, Germany.ar(1))
  armies.unsafeSetSome(144, 1528, Germany.ar(2))
  armies.unsafeSetSome(144, 520, Germany.ar(3))
  armies.unsafeSetSome(142, 1526, Germany.ar(4))
  armies.unsafeSetSome(138, 514, France.ar(1))
  armies.unsafeSetSome(142, 1534, Russia.ar(1))
  armies.unsafeSetSome(148, 1536, Russia.ar(2))
  armies.unsafeSetSome(134, 526, Italy.ar(1))
  armies.unsafeSetSome(136, 1528, Austria.ar(1))
  armies.unsafeSetSome(138, 526, Austria.ar(2))
  armies.unsafeSetSome(132, 1532, Ottoman.ar(1))
  armies.unsafeSetSome(132, 508, Spain.ar(1))
}

/** 2nd scenario for Diceless. Might have some use. */
object DLessScen2 extends DLessScen
{ deb("Diceless Scen 2")
  override def turn: Int = 0

  override implicit val gridSys: EGrid320Long = BritReg.britGrid

  override val terrs: HCenLayer[WTile] = BritReg.britTerrs
  override val sTerrs: HSideOptLayer[WSide] = BritReg.britSTerrs
  override val corners: HCornerLayer = gridSys.newHVertOffsetLayer
  override val armies: HCenOptLayer[Army] = gridSys.newHCenOptLayer
}