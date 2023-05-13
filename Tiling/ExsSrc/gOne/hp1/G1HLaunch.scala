/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package hp1
import pgui._, pParse._, prid._, phex._

object G1HLaunch extends GuiLaunchMore
{
  override def settingStr: String = "g1Hex"

  override def default: (CanvasPlatform => Any, String) = (G1HGui(_, G1HScen1, G1HScen1.defaultView()), "JavaFx Game One Hex")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)
    
    val scen: G1HScen = num match
    { case 1 => G1HScen1
      case 2 => G1HScen2
      case 3 => G1HScen3
      case 4 => G1HScen4
     // case 5 => G1HScen5
    //  case 6 => G1HScen6
      case 7 => G1HScen7
      case 8 => G1HScen8
      case 9 => G1HScen9
      case 10 => G1HScen10
      case _ => G1HScen1
    }

    val oSetts: EMon[AssignMemExpr] = sts.findIntSettingExpr(num)
    val sts2: EMon[RArr[Statement]] = oSetts.map(_.toStatements)
    val pl = sts2.findSettingIdentifierArr("players")
    debvar(pl)
    val view: HGView = sts2.findTypeElse(scen.gridSys.defaultView())
    (G1HGui(_, scen, view), "JavaFx Game One")
  }
}