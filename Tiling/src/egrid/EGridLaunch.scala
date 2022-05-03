/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, eg80._, eg320._, prid._, phex._, pParse._

/** object to launch EGrid basic Gui. */
object EGridLaunch extends GuiLaunchMore
{
  override def settingStr: String = "eGrid"

  override def default: (CanvasPlatform => Any, String) = (cv => EGridGui(cv, EGrid80Km.scen1, EGrid80Km.scen1.gridSys.coordCen.view()), "JavaFx Eath 80KM Grid")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val oview: EMon[HGridView] = sts.findKeySetting[Int, HGridView](num)

    def egg(scen: EScenBasic): (CanvasPlatform => Any, String) =
      (EGridGui(_, scen, oview.getElse(scen.gridSys.coordCen.view())), "JavaFx EGrid80Km.scen1")

    def gwg(scen: EScenBasic): (CanvasPlatform => Any, String) =
      (GridWorldGui(_, scen, oview.getElse(scen.gridSys.coordCen.view())), "JavaFx EGrid80Km.scen1")

    num match
    { case 0 => gwg(EGrid320Km.scen1)
      case 1 => gwg(EGrid80Km.scen1)
      case 2 => gwg(EGrid80Km.scen2)
      case 3 => gwg(ScenNWNE)

      case 51 => egg(EGrid80Km.scen1)
      case 52 => egg(EGrid80Km.scen2)
      case 61 => egg(EGrid320Km.scen1)
      case _ => egg(EGrid80Km.scen1)
    }
  }
}