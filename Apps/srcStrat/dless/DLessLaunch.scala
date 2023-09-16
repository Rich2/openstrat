/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import pgui._, prid._, phex._, pParse._

case class DLessSettings(view: HGView, counterSet: RArr[Nation], isFlat: Boolean = false)

/** Scenario selector and launcher for DLess. */
object DLessLaunch extends GuiLaunchMore
{
  override def settingStr: String = "dless"

  override def default: (CanvasPlatform => Any, String) =
    (DLessGui(_, DLessGame(DLessScen1, DLessScen1.nations), DLessSettings(DLessScen1.defaultView(), DLessScen1.nations)), "JavaFx Diceless")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: DLessScen = num match
    { case 1 => DLessScen1
      case 2 => DLessScen2
      case _ => DLessScen1
    }

    val view = oview.getElse(scen.gridSys.coordCen.view())
    val game = DLessGame(scen, scen.nations)
    val settings = DLessSettings(view, scen.nations)
    (DLessGui(_, game, settings), scen.title +  " Diceless " + ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}