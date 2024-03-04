/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pdiscov
import pgui._, prid._, phex._, pParse._

/** Scenario selector and launcher for AD1492. */
object DiscovLaunch extends GuiLaunchMore
{
  override def settingStr: String = "discov"

  override def default: (CanvasPlatform => Any, String) = (DiscovGui(_, DiscovScen1, DiscovScen1.defaultView()), "JavaFx AD 1492")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: DiscovScen = num match
    { case 1 => DiscovScen1
      case 2 => DiscovScen2
      case _ => DiscovScen1
    }

    (DiscovGui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title +  " AD1492 " + ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}