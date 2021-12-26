/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import pgui._, pParse._

object OneLaunch extends GuiLaunchMore
{
  override def settingStr: String = "gOne"

  override def default: (CanvasPlatform => Any, String) = (GOneGui(_, OneScen1), "JavaFx Game One")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSettingT[Int]("scen")
    val num: Int = oScen.getElse(1)
    val scen: OneScen = num match
    { case 1 => OneScen1
      case 2 => OneScen2
      case 3 => OneScen3
      case 4 => OneScen4
      case _ => OneScen1
    }
    (GOneGui(_, scen), "JavaFx Game One")
  }
}