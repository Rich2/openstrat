/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, eg80._, eg160._, eg320._, prid._, phex._, pParse._

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

    def gwg(scen: EScenWarm): (CanvasPlatform => Any, String) =
      (GridWorldGui(_, scen, oview.getElse(scen.gridSys.coordCen.view())), "JavaFx " + scen.title)

    num match
    {
      case 0 => gwg(EGrid320.scen0)
      case 100 => egg(Terr320E0.regScen)
      case 1 => gwg(EGrid320.scen1)
      case 101 => egg(EGrid320.scen1)
      case 2 => gwg(EGrid320.scen2)
      case 3 => gwg(EGrid320.scen3)
      case 4 => gwg(EGrid320.scen4)
      case 5 => gwg(EGrid320.scen5)
      case 6 => gwg(EGrid320.scen6)
      case 7 => gwg(EGrid320.scen7)
      case 8 => gwg(EGrid320.scen8)
      case 9 => gwg(EGrid320.scen9)
      case 10 => gwg(EGrid320.scen10)
      case 11 => gwg(EGrid320.scen11)

      case 12 => gwg(Scen320s0e1)
      case 13 => gwg(Scen320S0E2)
      case 14 => gwg(Scen320S11E2)
      case 15 => gwg(Scen320S10E5)
      case 16 => gwg(Scen320S0E11)

      case 20 => gwg(EGrid160.scen0)
      case 21 => gwg(EGrid160.scen11)

      case 32 => gwg(EGrid160.scen0)

      case 40 => gwg(EGrid80.scen0)
      case 140 => egg(EGrid80.scen0)
      case 41 => gwg(EGrid80.scen1)
      case 141 => egg(EGrid80.scen1)
      case 42 => gwg(Scen80s0s1)

      case _ => egg(EGrid80.scen0)
    }
  }
}