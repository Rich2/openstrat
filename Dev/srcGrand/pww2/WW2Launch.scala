/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import prid._, phex._, pParse._, pgui._

/** Object for launching WW2 app. */
object WW2Launch extends GuiLaunchMore
{
  override def settingStr: String = "ww2"

  override def default: (CanvasPlatform => Any, String) = (WW2Gui(_, WW2Scen1, WW2Scen1.defaultView()), "JavaFx WW2")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  {
    val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)
    val scen: WW2Scen = num match
    { case 1 => WW2Scen1
      case 2 => WW2Scen2
      case _ => WW2Scen1
    }
    debvar(scen)
    (WW2Gui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title +  " WW2 " + ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}