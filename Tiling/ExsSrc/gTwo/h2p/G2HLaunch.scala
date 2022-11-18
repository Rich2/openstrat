/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import pgui._, pParse._, prid._, phex._

object G2HLaunch extends GuiLaunchMore
{
  override def settingStr: String = "g2Hex"

  override def default: (CanvasPlatform => Any, String) = (G2HGui(_, G2HScen1, G2HScen1.defaultView()), "JavaFx Game Hex")

  override def fromStatments(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)
    val scen: G2HScen = num match
    { case 1 => G2HScen1
      case 2 => G2HScen2
      case 3 => G2HScen3
      case 4 => G2HScen4
      case 5 => G2HScen5
      case 6 => G2HScen6
      case 7 => G2HScen7
      case _ => G2HScen1
    }
    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)
    (G2HGui(_, scen, oview.getElse(scen.gridSys.defaultView())), "JavaFx Game Two Hex")
  }
}