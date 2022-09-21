/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import pgui._, pParse._, prid._, phex._

object OneLaunch extends GuiLaunchMore
{
  override def settingStr: String = "gOne"

  override def default: (CanvasPlatform => Any, String) = (GOneGui(_, OneScen1, OneScen1.defaultView()), "JavaFx Game One")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)
    
    val scen: OneScen = num match
    { case 1 => OneScen1
      case 2 => OneScen2
      case 3 => OneScen3
      case 4 => OneScen4
      case 5 => OneScen5
      case 6 => OneScen6
      case 7 => OneScen7
      case 8 => OneScen8
      case _ => OneScen1
    }
    
    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)
    (GOneGui(_, scen, oview.getElse(scen.gridSys.defaultView())), "JavaFx Game One")
  }
}