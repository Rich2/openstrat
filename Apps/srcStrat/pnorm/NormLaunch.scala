/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnorm
import pgui._, prid._, phex._, pParse._

/** Scenario selector and launcher for DLess. */
object NormLaunch extends GuiLaunchMore
{
  override def settingStr: String = "normandy"

  override def default: (CanvasPlatform => Any, String) = (NormGui(_, NormScen1, NormScen1.grid.defaultView()), "JavaFx Diceless")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: NormScen = num match
    { case 1 => NormScen1
      case 2 => NormScen1
      case _ => NormScen1
    }

    (NormGui(_, scen, oview.getElse(scen.grid.coordCen.view())), scen.title +  " Diceless " + ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}