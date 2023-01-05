/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._, pEarth._

/** A basic EGrid scenario, containing grid and basic terrain data. */
trait EScenBasic extends HSysScen
{ override def gridSys: EGridSys

  def terrs: HCenLayer[WTile]
  def sTerrs: HSideBoolLayer
  def title: String = "EScenWarm"
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
object EScenBasic
{
  def apply(gridSys: EGridSys, terrs: HCenLayer[WTile], sTerrs: HSideBoolLayer, title: String = "EScenBasic"): EScenBasic =
    new EScenWarmImp(gridSys, terrs, sTerrs, title)

  class EScenWarmImp(val gridSys: EGridSys, override val terrs: HCenLayer[WTile], val sTerrs: HSideBoolLayer,
    override val title: String = "EScenWarm") extends EScenBasic
}

trait EScenLongMulti extends EScenBasic
{ override def gridSys: EGridLongMulti
  def longs: RArr[LongTerrs]
  override lazy val terrs: HCenLayer[WTile] = longs.map(_.terrs).combine// longs.tailfold(longs(0).terrs)(_ ++ _.terrs)
  override lazy val sTerrs: HSideBoolLayer = gridSys.sideBoolsFromGrids(longs.map(_.sTerrs))
}

/** Contains Earth longitude range grid, [[WTile]] layer and a [[Boolean]] tile side data layer. */
trait LongTerrs
{ implicit val grid: EGridLongFull
  def terrs: HCenLayer[WTile]
  def sTerrs: HSideBoolLayer
}