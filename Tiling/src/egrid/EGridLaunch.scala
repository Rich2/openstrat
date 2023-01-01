/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, eg80._, eg160._, eg320._, prid._, phex._, pParse._

/** object to launch EGrid basic Gui. */
object EGridLaunch extends GuiLaunchMore
{
  override def settingStr: String = "eGrid"

  override def default: (CanvasPlatform => Any, String) =
    (cv => GridWorldGui(cv, EGrid80.scen0, EGrid80.scen0.gridSys.coordCen.view(), false), "JavaFx Eath 80KM Grid")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen",1)
    val isFlat: Boolean = sts.findSettingElse("flat",false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: EScenBasic = num match
    {
      case 0 => EGrid320.scen0
      case 1 => EGrid320.scen1
      case 2 => EGrid320.scen2
      case 3 => EGrid320.scen3
      case 4 => EGrid320.scen4
      case 5 => EGrid320.scen5
      case 6 => EGrid320.scen6
      case 7 => EGrid320.scen7
      case 8 => EGrid320.scen8
      case 9 => EGrid320.scen9
      case 10 => EGrid320.scen10
      case 11 => EGrid320.scen11

      case 12 => Scen320s0e1
      case 13 => Scen320S0E2
      case 14 => Scen320S11E2
      case 15 => Scen320S10E5
      case 16 => Scen320S0E11
      case 17 => Terr320E0.regScen

      case 20 => EGrid160.scen0
      case 31 => EGrid160.scen11

      case 32 => Terr160E0.britScen

      case 40 => EGrid80.scen0
      case 41 => EGrid80.scen1
      case 42 => Scen80s0s1
      case 43 => EGrid80.wFrontScen

      case _ => EGrid80.scen0
    }

    (GridWorldGui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title -- ife(isFlat, "Flat", "Globe") -- "JavaFx")
  }
}