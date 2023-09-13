/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri;
import prid.phex._, egrid._, eg13._

object PeriScen1 extends PeriScen
{ override implicit val gridSys: EGridSys = Scen13All.gridSys
  override val terrs: HCenLayer[WTile] = Scen13All.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Scen13All.sTerrs
  override val corners: HCornerLayer = Scen13All.corners
  override val armies: HCenOptLayer[Army] = HCenOptLayer[Army]()
  armies.setSomeMut(110, 514, Army(NBlue, 4))
  armies.setSomeMut(108, 1536, Army(NRed, 1))
  armies.setSomeMut(96, 1540, Army(NViolet, 1))
}
object PeriScen2 extends PeriScenStart
{
  override implicit val gridSys: EGrid13LongFull = EGrid13.e0(100, 104)
  override val nations: RArr[Nation] = RArr(NRed, NBlue, NViolet)
  override val terrs: HCenLayer[WTile] = Terr13E0.terrs.spawn(Terr13E0.grid, gridSys)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Terr13E0.sTerrs.spawn(Terr13E0.grid, gridSys)
  override val corners: HCornerLayer = Terr13E0.corners.spawn(Terr13E0.grid, gridSys)
}
object PeriScen2a extends PeriScenStart
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

  val nations: RArr[Nation] = RArr(NRed, NBlue, NViolet)

//  val armies: HCenOptLayer[Army] = HCenOptLayer[Army]()
//  armies.setSomeMut(108, 1536, Army(NRed, 1))
//  armies.setSomeMut(96, 1540, Army(NViolet, 1))
}

object PeriScen3 extends PeriScen{
  override implicit val gridSys: EGridSys = EGrid13.e30(104, 106)
  override val terrs: HCenLayer[WTile] = Terr13E30.terrs.spawn(Terr13E30.grid, gridSys)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Terr13E30.sTerrs.spawn(Terr13E30.grid, gridSys)
  override val corners: HCornerLayer = Terr13E30.corners.spawn(Terr13E30.grid, gridSys)
  override val armies: HCenOptLayer[Army] = HCenOptLayer[Army]()
}