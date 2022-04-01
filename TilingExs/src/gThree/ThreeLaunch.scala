/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import pgui._, pParse._, prid._, phex._

object ThreeLaunch extends GuiLaunchMore
{
  override def settingStr: String = "gThree"

  override def default: (CanvasPlatform => Any, String) = (GThreeGui(_, ThreeScen1, ThreeScen1.defaultView()), "JavaFx Game Three")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)
    val scen: ThreeScen = num match
    { case 1 => ThreeScen1
      case 2 => ThreeScen2
      case 3 => ThreeScen3
      case 4 => ThreeScen4
      case 5 => ThreeScen5
      case 6 => ThreeScen6
      case _ => ThreeScen1
    }
    val oview: EMon[HGridView] = sts.findKeySetting[Int, HGridView](num)
    (GThreeGui(_, scen, oview.getElse(scen.grider.defaultView())), "JavaFx Game Three")
  }
}