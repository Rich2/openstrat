/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnap
import pgui._, prid._, phex._, pParse._

/** Scenario selector and launcher for Bc305. */
object NapLaunch extends GuiLaunchMore
{
  override def settingStr: String = "ad1783"

  override def default: (CanvasPlatform => Any, String) = (NapGui(_, NapScen1, NapScen1.defaultView()), "JavaFx AD1783")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: NapScen = num match
    { case 1 => NapScen1
      case 2 => NapScen2
      case _ => NapScen1
    }

    (NapGui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title -- ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}