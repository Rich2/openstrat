/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri
import pgui._, prid._, phex._, pParse._

/** Scenario selector and launcher for DLess. */
object PeriLaunch extends GuiLaunchMore
{
  override def settingStr: String = "peri"

  override def default: (CanvasPlatform => Any, String) = (PeriGui(_, PeriScen1, PeriScen1.defaultView()), "JavaFx Periculo Fundatusa")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: PeriScen = num match
    { case 1 => PeriScen1
      case 2 => PeriScen2
      case _ => PeriScen1
    }

    (PeriGui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title +  " Periculo Fundatusa " + ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}