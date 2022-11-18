/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package sp1
import pgui._, pParse._, prid._, psq._

object G1SLaunch extends GuiLaunchMore
{
  override def settingStr: String = "g2Sq"

  override def default: (CanvasPlatform => Any, String) = (G1SGui(_, S1Scen1, S1Scen1.defaultView()), "JavaFx Game Two")
  override def fromStatments(sts: RArr[Statement]): (CanvasPlatform => Any, String) = {
    val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val scen: G1SScen = num match {
      case 1 => S1Scen1
      case 2 => S1Scen2
      case _ => S1Scen1
    }
    val oview: EMon[SqGridView] = sts.findKeySetting[Int, SqGridView](num)
    (G1SGui(_, scen, oview.getElse(scen.gSys.defaultView())), "JavaFx Game Two")
  }
}