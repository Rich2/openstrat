/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._, pEarth._

/** These scenarios require a [[WTile]] layer but not an [[EGridSys]]. */
trait EScenFlat extends HSysScen
{ def terrs: HCenLayer[WTile]
  def sTerrs: HSideBoolLayer
  def title: String = "EScenFlat"
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
trait EScenBasic extends EScenFlat
{ override def gridSys: EGridSys
  override def title: String = "EScenWarm"
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
object EScenBasic
{
  def apply(gridSys: EGridSys, terrs: HCenLayer[WTile], sTerrs: HSideBoolLayer, title: String = "EScenWarm"): EScenBasic = new EScenWarmImp(gridSys, terrs, sTerrs, title)

  class EScenWarmImp(val gridSys: EGridSys, override val terrs: HCenLayer[WTile], val sTerrs: HSideBoolLayer, override val title: String = "EScenWarm") extends EScenBasic
}

trait EScenLongMulti extends EScenBasic{
  override def gridSys: EGridLongMulti
  def longs: Arr[LongTerrs]
  override final lazy val terrs: HCenLayer[WTile] = longs.tailfold(longs(0).terrs)(_ ++ _.terrs)
  override final lazy val sTerrs: HSideBoolLayer = gridSys.sideBoolsFromGrids(longs.map(_.sTerrs))
}

/** Contains Earth longitude range grid, [[WTile]] layer and a [[Boolean]] tile side data layer. */
trait LongTerrs
{ implicit val grid: EGridLongFull
  def terrs: HCenLayer[WTile]
  def sTerrs: HSideBoolLayer
}