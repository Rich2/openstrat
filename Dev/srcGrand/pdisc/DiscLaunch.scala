/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pdisc
import pgui._, prid._, phex._, pParse._

/** Scenario selector and launcher for Bc305. */
object DiscLaunch extends GuiLaunchMore
{
  override def settingStr: String = "y1492"

  override def default: (CanvasPlatform => Any, String) = (DiscGui(_, DiscScen1, DiscScen1.defaultView()), "JavaFx AD 1492")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: DiscScen = num match
    { case 1 => DiscScen1
      case 2 => DiscScen2
      case _ => DiscScen1
    }

    (DiscGui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title +  " AD1492 " + ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}