/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, eg80._, eg320._, prid._, phex._, pParse._

/** object to launch EGrid basic Gui. */
object EGridLaunch extends GuiLaunchMore
{
  override def settingStr: String = "eGrid"

  override def default: (CanvasPlatform => Any, String) = (cv => EGridGui(cv, EGrid80Km.scen1, EGrid80Km.scen1.eGrid.coordCen.view()), "JavaFx Eath 80KM Grid")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val oview: EMon[HGridView] = sts.findKeySetting[Int, HGridView](num)

    def egg(scen: EScenBasic) = (EGridGui(_, scen, oview.getElse(scen.eGrid.coordCen.view())), "JavaFx EGrid80Km.scen1")

    num match
    { case 0 => (GridWorldGui(_, eg80.EGrid80Km.l0b446, oview.getElse(HGridView(0, 0, 50))), "")
      case 1 => egg(EGrid80Km.scen1)
      case 2 => egg(EGrid80Km.scen2)
      case 11 => egg(EGrid320Km.scen1)
      case _ => egg(EGrid80Km.scen1)
    }
  }
}