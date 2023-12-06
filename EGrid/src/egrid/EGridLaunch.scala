/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, eg80._, eg120._, eg160._, eg220._, eg320._, eg640._, egmega._, eg13._, prid._, phex._, pParse._

/** object to launch EGrid basic Gui. */
object EGridLaunch extends GuiLaunchMore
{
  override def settingStr: String = "eGrid"

  override def default: (CanvasPlatform => Any, String) =
    (cv => EGTerrOnlyGui(cv, EGrid80.scen0, EGrid80.scen0.gridSys.coordCen.view(), false), "JavaFx Eath 80KM Grid")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val scenNum: Int = sts.findSettingElse("scen",1)
    val isFlat: Boolean = sts.findSettingElse("flat",false)
    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](scenNum)
    
    val scen: EScenBasic = scenNum match
    { case 0 => EGrid13.scen0
      case 1 => EGrid13.scen1
      case 2 => EGrid13.scen2
      case 3 => EGrid13.scen3
      case 4 => EGrid13.scen4
      case 5 => EGrid13.scen5
      case 6 => EGrid13.scen6
      case 7 => EGrid13.scen7
      case 8 => EGrid13.scen8
      case 9 => EGrid13.scen9
      case 10 => EGrid13.scen10
      case 11 => EGrid13.scen11
      case 12 => Scen13S0E2
      case 13 => Scen13S11E2
      case 14 => Scen13S2E3
      case 15 => Scen13Americas
      case 16 => Scen13All

      case 20 => EGridMega.scen0
      case 21 => EGridMega.scen1
      case 22 => EGridMega.scen2
      case 23 => EGridMega.scen3
      case 24 => EGridMega.scen4
      case 25 => EGridMega.scen5
      case 26 => EGridMega.scen6
      case 27 => EGridMega.scen7
      case 28 => EGridMega.scen8
      case 29 => EGridMega.scen9
      case 30 => EGridMega.scen10
      case 31 => EGridMega.scen11
      case 32 => ScenMegaS0E2
      case 33 => ScenMegaS11E2
      case 34 => ScenMegaS3E6
      case 35 => ScenMegaAmericas
      case 36 => ScenMegaAll

      case 40 => EGrid640.scen0
      case 41 => EGrid640.scen1
      case 42 => EGrid640.scen2
      case 43 => EGrid640.scen3
      case 44 => EGrid640.scen4
      case 45 => EGrid640.scen5
      case 46 => EGrid640.scen6
      case 47 => EGrid640.scen7
      case 48 => EGrid640.scen8
      case 49 => EGrid640.scen9
      case 50 => EGrid640.scen10
      case 51 => EGrid640.scen11

      case 52 => Scen640S0E1
      case 53 => Scen640ChinaJapan
      case 54 => Scen640S11E2
      case 55 => Scen640S10E5
      case 56 => Scen640All
      case 57 => Scen640Americas

      case 60 => EGrid320.scen0
      case 61 => EGrid320.scen1
      case 62 => EGrid320.scen2
      case 63 => EGrid320.scen3
      case 64 => EGrid320.scen4
      case 65 => EGrid320.scen5
      case 66 => EGrid320.scen6
      case 67 => EGrid320.scen7
      case 68 => EGrid320.scen8
      case 69 => EGrid320.scen9
      case 70 => EGrid320.scen10
      case 71 => EGrid320.scen11

      case 72 => Scen320s0e1
      case 73 => Scen320ChinaJapan
      case 74 => Scen320S11E2
      case 75 => Scen320S10E5
      case 76 => Scen320All
      case 77 => BritReg320.regScen
      case 78 => Scen320Americas
      case 79 => Scen640NCanada

      case 80 => EGrid220.scen0
      case 81 => EGrid220.scen1

      case 90 => EGrid220.scen10

      case 92 => Scen220s0e1

      case 97 => BritReg220.regScen
      case 98 => Scen220Americas

      case 100 => EGrid160.scen0
      case 101 => EGrid160.scen1

      case 104 => EGrid160.scen4
      case 105 => EGrid160.scen5

      case 108 => EGrid160.scen8
      case 109 => EGrid160.scen9
      case 110 => EGrid160.scen10
      case 111 => EGrid160.scen11

      case 112 => Scen160S0E1
      case 113 => Scen160s11e1
      case 114 => Scen160S4E5
      case 115 => Brit160.britScen

      case 120 => EGrid120.scen0
      case 121 => EGrid120.scen1
      case 122 => EGrid120.scen2

      case 132 => Scen120S0E1
      case 133 => Scen120S0E2
      case 134 => Scen120S0E1North

      case 140 => EGrid80.scen0
      case 141 => EGrid80.scen1
      case 152 => Scen80s0s1
      case 153 => WesternFront.wFrontScen
      case _ => Scen320All
    }
    (EGTerrOnlyGui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title --"Experimental" -- ife(isFlat, "Flat", "Globe") -- "JavaFx")
  }
}