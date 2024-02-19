/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._

/** A basic EGrid scenario, containing grid and basic terrain data. */
trait EScenBasic extends HSysScen
{ def title: String = "EScenBasic"
  override def gridSys: EGridSys
  def terrs: LayerHcRefSys[WTile]
  def sTerrs: LayerHSOptSys[WSep, WSepSome]
  def corners: HCornerLayer
  def hexNames: LayerHcRefSys[String]
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
object EScenBasic
{
  def apply(gridSys: EGridSys, terrs: LayerHcRefSys[WTile], sTerrs: LayerHSOptSys[WSep, WSepSome], offsets: HCornerLayer, names: LayerHcRefSys[String],
    title: String = "EScenBasic"): EScenBasic = new EScenWarmImp(gridSys, terrs, sTerrs, offsets, names, title)

  class EScenWarmImp(val gridSys: EGridSys, override val terrs: LayerHcRefSys[WTile], val sTerrs: LayerHSOptSys[WSep, WSepSome],
    override val corners: HCornerLayer, val hexNames: LayerHcRefSys[String], override val title: String = "EScenWarm") extends EScenBasic
}

trait EScenLongMulti extends EScenBasic
{ override def gridSys: EGridLongMulti
}

/** Contains Earth longitude range grid, [[WTile]] layer and a [[Boolean]] tile side data layer. */
trait LongTerrs
{ /** The grid used. */
  implicit val grid: EGridLongFull

  /** The tiles terrains. */
  def terrs: LayerHcRefGrid[WTile]

  /** The separators terrain. */
  def sTerrs: LayerHSOptSys[WSep, WSepSome]

  /** The [[HCorner]]s */
  def corners: HCornerLayer

  /** Place names for the tiles. */
  def hexNames: LayerHcRefGrid[String]
}