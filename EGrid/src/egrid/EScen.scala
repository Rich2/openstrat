/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._, pEarth._

/** A basic EGrid scenario, containing grid and basic terrain data. */
trait EScenBasic extends HSysScen
{ override def gridSys: EGridSys

  def terrs: HCenLayer[WTile]
  def sTerrs: HSideOptLayer[WSide]
  val corners: HCornerLayer
  def title: String = "EScenBasic"
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
object EScenBasic
{
  def apply(gridSys: EGridSys, terrs: HCenLayer[WTile], sTerrs: HSideOptLayer[WSide], offsets: HCornerLayer, title: String = "EScenBasic"): EScenBasic =
    new EScenWarmImp(gridSys, terrs, sTerrs, offsets, title)

  class EScenWarmImp(val gridSys: EGridSys, override val terrs: HCenLayer[WTile], val sTerrs: HSideOptLayer[WSide], override val corners: HCornerLayer,
                     override val title: String = "EScenWarm") extends EScenBasic
}

trait EScenLongMulti extends EScenBasic
{ override def gridSys: EGridLongMulti
}

/** Contains Earth longitude range grid, [[WTile]] layer and a [[Boolean]] tile side data layer. */
trait LongTerrs
{ /** The grid used. */
  implicit val grid: EGridLongFull

  /** The tile terrain, */
  def terrs: HCenLayer[WTile]

  def sTerrs: HSideOptLayer[WSide]

  def corners: HCornerLayer
}