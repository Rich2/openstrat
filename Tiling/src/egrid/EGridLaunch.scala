/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, eg80._, eg320._, prid._, phex._, pParse._

/** object to launch EGrid basic Gui. */
object EGridLaunch extends GuiLaunchMore
{
  override def settingStr: String = "eGrid"

  override def default: (CanvasPlatform => Any, String) = (cv => EGridFlatGui(cv, EGrid80.scen0, EGrid80.scen0.gridSys.coordCen.view()), "JavaFx Eath 80KM Grid")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    def egg(scen: EScenFlat): (CanvasPlatform => Any, String) =
      (EGridFlatGui(_, scen, oview.getElse(scen.gridSys.coordCen.view())), "JavaFx " + scen.title)

    def gwg(scen: EScenBasic): (CanvasPlatform => Any, String) =
      (GridWorldGui(_, scen, oview.getElse(scen.gridSys.coordCen.view())), "JavaFx " + scen.title)

    num match
    {
      case 0 => gwg(EGrid80.scen0)
      case 100 => egg(EGrid80.scen0)
      case 1 => gwg(EGrid80.scen1)
      case 101 => egg(EGrid80.scen1)
      case 12 => gwg(Scen80s0s1)

      case 20 => gwg(EGrid320.scen0)
      case 201 => egg(Terr320E0.regScen)
      case 21 => gwg(EGrid320.scen1)
      case 211 => egg(EGrid320.scen1)
      case 22 => gwg(EGrid320.scen2)
      case 23 => gwg(EGrid320.scen3)
      case 24 => gwg(EGrid320.scen4)
      case 25 => gwg(EGrid320.scen5)
      case 30 => gwg(EGrid320.scen10)
      case 31 => gwg(EGrid320.scen11)

      case 32 => gwg(Scen320s0e1)
      case 33 => gwg(Scen320S0E2)
      case 34 => gwg(Scen320S11E2)

      case _ => egg(EGrid80.scen0)
    }
  }
}