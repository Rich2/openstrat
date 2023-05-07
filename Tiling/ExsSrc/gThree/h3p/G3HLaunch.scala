/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree; package h3p
import prid._, phex._, pgui._, pParse._

object G3HLaunch extends GuiLaunchMore
{
  override def settingStr: String = "g3hex"

  override def default: (CanvasPlatform => Any, String) = (G3HGui(_, G3HScen1, G3HScen1.defaultView()), "JavaFx Game Three")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) = {
    val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val scen: G3HScen = num match {
      case 1 => G3HScen1
      case 2 => G3HScen2
      case _ => G3HScen1
    }

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)
    (G3HGui(_, scen, oview.getElse(scen.gridSys.defaultView())), "JavaFx Game Three")
  }
}