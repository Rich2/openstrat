/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo
import pgui._, pParse._, prid._, psq._

object TwoLaunch extends GuiLaunchMore
{
  override def settingStr: String = "gTwo"

  override def default: (CanvasPlatform => Any, String) = (GTwoGui(_, TwoScen1, TwoScen1.defaultView()), "JavaFx Game Two")
  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) = {
    val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val scen: TwoScen = num match {
      case 1 => TwoScen1
      case 2 => TwoScen2
      case _ => TwoScen1
    }
    val oview: EMon[SqGridView] = sts.findKeySetting[Int, SqGridView](num)
    (GTwoGui(_, scen, oview.getElse(scen.gSys.defaultView())), "JavaFx Game Two")
  }
}