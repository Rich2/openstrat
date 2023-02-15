/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, eg80._, eg160._, eg220._, eg320._, prid._, phex._, pParse._

/** object to launch EGrid basic Gui. */
object EGridLaunch extends GuiLaunchMore
{
  override def settingStr: String = "eGrid"

  override def default: (CanvasPlatform => Any, String) =
    (cv => GridWorldGui(cv, EGrid80.scen0, EGrid80.scen0.gridSys.coordCen.view(), false), "JavaFx Eath 80KM Grid")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val scenNum: Int = sts.findSettingElse("scen",1)
    val isFlat: Boolean = sts.findSettingElse("flat",false)
    val isExp: Boolean = sts.findSettingElse("exp",false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](scenNum)

    val scen: EScenBasic = scenNum match
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
      case 13 => Scen320ChinaJapan
      case 14 => Scen320S11E2
      case 15 => Scen320S10E5
      case 16 => Scen320S0E11
      case 17 => BritReg220.regScen
      case 18 => Scen320S8E10
      case 19 => ScenNCanada

      case 20 => EGrid220.scen0

      case 30 => EGrid160.scen0
      case 31 => EGrid160.scen1

      case 34 => EGrid160.scen4
      case 35 => EGrid160.scen5

      case 38 => EGrid160.scen8
      case 39 => EGrid160.scen9
      case 40 => EGrid160.scen10
      case 41 => EGrid160.scen11

      case 42 => Scen160S0E1
      case 43 => Scen160s11e1
      case 44 => Scen160S4E5
      case 45 => Brit160.britScen

      case 60 => EGrid80.scen0
      case 61 => EGrid80.scen1
      case 72 => Scen80s0s1
      case 73 => WesternFront.wFrontScen

      case _ => Scen320S0E11
    }
    if (isExp) (ExpWorldGui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title --"Experimental" -- ife(isFlat, "Flat", "Globe") -- "JavaFx")
    else (GridWorldGui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title -- ife(isFlat, "Flat", "Globe") -- "JavaFx")
  }
}