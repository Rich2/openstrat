/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww1
import pgui._, prid._, phex._, pParse._

/** Scenario selector and launcher for Ww1. */
object WW1Launch extends GuiLaunchMore
{
  override def settingStr: String = "ww1"

  override def default: (CanvasPlatform => Any, String) = (WW1Gui(_, WW1Scen1, WW1Scen1.defaultView()), "JavaFx WW1")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: WW1Scen = num match
    { case 1 => WW1Scen1
      case 2 => WW1Scen2
      case _ => WW1Scen1
    }

    (WW1Gui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title +  " WW1 " + ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}