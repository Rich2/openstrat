/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._, pEarth._

/** A basic EGrid scenario, containing grid and basic terrain data. */
trait EScenBasic extends HSysScen
{ override def gridSys: EGridSys

  def terrs: HCenLayer[WTile]
  def sTerrs: HSideOptLayer[WSide]
  def sTerrsDepr: HSideBoolLayer
  val corners: HCornerLayer
  def title: String = "EScenWarm"
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
object EScenBasic
{
  def apply(gridSys: EGridSys, terrs: HCenLayer[WTile], sTerrs: HSideOptLayer[WSide], sTerrsDepr: HSideBoolLayer, offsets: HCornerLayer, title: String = "EScenBasic"): EScenBasic =
    new EScenWarmImp(gridSys, terrs, sTerrs, sTerrsDepr, offsets, title)

  class EScenWarmImp(val gridSys: EGridSys, override val terrs: HCenLayer[WTile], val sTerrs: HSideOptLayer[WSide], val sTerrsDepr: HSideBoolLayer, override val corners: HCornerLayer,
                     override val title: String = "EScenWarm") extends EScenBasic
}

trait EScenLongMulti extends EScenBasic
{
  override def gridSys: EGridLongMulti
}

trait EScenLongMultiDepr extends EScenLongMulti
{
  def longs: RArr[LongTerrs]
  override lazy val terrs: HCenLayer[WTile] = longs.map(_.terrs).combine// longs.tailfold(longs(0).terrs)(_ ++ _.terrs)
  override lazy val sTerrsDepr: HSideBoolLayer = gridSys.sideBoolsFromGrids(longs.map(_.sTerrsDepr))

  override def sTerrs: HSideOptLayer[WSide] = ???
}

/** Contains Earth longitude range grid, [[WTile]] layer and a [[Boolean]] tile side data layer. */
trait LongTerrs
{ /** The grid used. */
  implicit val grid: EGridLongFull

  /** The tile terrain, */
  def terrs: HCenLayer[WTile]

  def sTerrs: HSideOptLayer[WSide]

  /** The straits. */
  def sTerrsDepr: HSideBoolLayer

  def corners: HCornerLayer
}