/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pind
import pgui._, prid._, phex._, pParse._

/** Scenario selector and launcher for Y1783. */
object IndRevLaunch extends GuiLaunchMore
{
  override def settingStr: String = "y1783"

  override def default: (CanvasPlatform => Any, String) = (IndRevGui(_, IndRevScen1, IndRevScen1.defaultView()), "JavaFx AD1783")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: IndRevScen = num match
    { case 1 => IndRevScen1
      case 2 => IndRevScen2
      case _ => IndRevScen1
    }

    (IndRevGui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title -- ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}