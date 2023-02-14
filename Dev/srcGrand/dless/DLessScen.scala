/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import prid._, phex._, egrid._, eg320._, pEarth._

/** Scenario trait for Diceless. */
trait DLessScen extends HSysTurnScen
{ def title: String = "DLessScen"
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide]
  val corners: HCornerLayer
  val armies: HCenOptLayer[Nation]
}

/** The main scenario for Diceless. */
object DLessScen1 extends DLessScen
{
  override def turn: Int = 0

  override implicit val gridSys: EGrid320LongMulti = new EGrid320LongMulti { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGrid320.grids(2, 0, 124)
    override def headGridInt: Int = 0
    override def gridsXSpacing: Double = 40
    override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
    override def adjTilesOfTile(tile: HCen): HCenArr = ???
  }

  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override val armies: HCenOptLayer[Nation] = gridSys.newHCenOptLayer
  armies.unsafeSetSames(Britain, 142,510,  144,508)
  armies.unsafeSetSames(Germany, 142,518)
  armies.unsafeSetSames(France, 138,514)
  armies.unsafeSetSames(Russia, 142,1534,  148,1536)
  armies.unsafeSetSames(Italy, 134, 526)
  armies.unsafeSetSames(Austria, 138, 1526)
  armies.unsafeSetSames(Ottoman, 132, 1532)
  armies.unsafeSetSames(Spain, 132, 508)
}

/** 2nd scenario for Diceless. Might have some use. */
object DLessScen2 extends DLessScen
{ deb("Diceless Scen 2")
  override def turn: Int = 0

  override implicit val gridSys: EGrid320Long = BritReg.britGrid

  override val terrs: HCenLayer[WTile] = BritReg.britTerrs
  override val sTerrs: HSideOptLayer[WSide] = BritReg.britSTerrs
  override val corners: HCornerLayer = gridSys.newHVertOffsetLayer
  override val armies: HCenOptLayer[Nation] = gridSys.newHCenOptLayer
}