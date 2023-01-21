/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import pgui._, prid._, phex._, pParse._

/** Scenario selector and launcher for Bc305. */
object BcLaunch extends GuiLaunchMore
{
  override def settingStr: String = "bc305"

  override def default: (CanvasPlatform => Any, String) = (BC305Gui(_, BCScen2, BCScen2.defaultView()), "JavaFx BC305")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: BCScen = num match
    { case 2 => BCScen2
      case _ => BCScen2
    }

    (BC305Gui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title +  " BC305 " + ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}