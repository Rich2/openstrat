/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import pgui._, prid._, phex._, pParse._

/** Scenario selector and launcher for DLess. */
object DLessLaunch extends GuiLaunchMore
{
  override def settingStr: String = "dless"

  override def default: (CanvasPlatform => Any, String) = (DLessGui(_, DLessScen1, DLessScen1.defaultView()), "JavaFx Diceless")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: DLessScen = num match
    { case 1 => DLessScen1
      case _ => DLessScen1
    }

    (DLessGui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title +  " Diceless " + ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}