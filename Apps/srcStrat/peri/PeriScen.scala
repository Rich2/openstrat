/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri;
import prid.phex._, egrid._, eg13._

trait Nation extends Coloured

object NoNation extends Nation
{
  override def colour: Colour = Colour.White
}

case class Army(nation: Nation, num: Int)

trait PeriScen extends HSysScen
{ def title: String = "DLessScen"
  override implicit val gridSys: EGridSys
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide, WSideSome]
  val corners: HCornerLayer
  val armies: HCenOptLayer[Army]
}
object PeriScen1 extends PeriScen
{ override implicit val gridSys: EGridSys = Scen13All.gridSys
  override val terrs: HCenLayer[WTile] = Scen13All.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Scen13All.sTerrs
  override val corners: HCornerLayer = Scen13All.corners
  override val armies: HCenOptLayer[Army] = HCenOptLayer[Army]()
}

object PeriScen2 extends PeriScen
{ override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(3, 0, 94)
  val ft3 = fullTerrs.take(3)
  override val terrs: HCenLayer[WTile] = iToMap(0, 2) { i =>
    val ft: Long13Terrs = ft3(i)
    ft.terrs.spawn(ft.grid, gridSys.grids(i))
  }.combine

  override val sTerrs: HSideOptLayer[WSide, WSideSome] =
  { val arr = iToMap(0, 2) { i =>
      val ft: Long13Terrs = fullTerrs(i)
      (ft.grid, ft.sTerrs)
    }
    gridSys.sidesOptFromPairsSpawn(arr)
  }

  override val corners: HCornerLayer = iToMap(0, 2) { i =>
    val ft: Long13Terrs = fullTerrs(i)
    ft.corners.spawn(ft.grid, gridSys.grids(i))
  }.combine
   
  override val armies: HCenOptLayer[Army] = HCenOptLayer[Army]()
}