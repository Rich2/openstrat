/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import prid._, phex._, pgui._, pParse._

object ThreeLaunch extends GuiLaunchMore
{
  override def settingStr: String = "gThree"

  override def default: (CanvasPlatform => Any, String) = (GThreeGui(_, ThreeScen1, ThreeScen1.defaultView()), "JavaFx Game Three")

  override def fromStatments(sts: RArr[Statement]): (CanvasPlatform => Any, String) = {
    val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val scen: ThreeScen = num match {
      case 1 => ThreeScen1
      case 2 => ThreeScen2
      case _ => ThreeScen1
    }

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)
    (GThreeGui(_, scen, oview.getElse(scen.gridSys.defaultView())), "JavaFx Game Three")
  }
}