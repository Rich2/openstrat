/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, eg80._, eg320._, prid._, phex._, pParse._

/** object to launch EGrid basic Gui. */
object EGridBasicLaunch extends GuiLaunchMore
{
  override def settingStr: String = "eGrid"

  override def default: (CanvasPlatform => Any, String) = (cv => EGridGui(cv, EGrid80Km.scen1, EGrid80Km.scen1.eGrid.coordCen.view()), "JavaFx Eath 80KM Grid")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val scen = num match
    { case 1 => EGrid80Km.scen1
      case 2 => EGrid320Km.scen1
      case _ => EGrid80Km.scen1
    }

    val oview = sts.findSettingOrUniqueT[HGridView]("view")
    (EGridGui(_, scen, oview.getElse(scen.eGrid.coordCen.view())), "JavaFx EGrid80Km.scen1")
  }
}