/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._

/** A basic EGrid scenario, containing grid and basic terrain data. */
trait EScenBasic extends HSysScen
{ def title: String = "EScenBasic"
  override def gridSys: EGridSys
  def terrs: LayerHcRefSys[WTile]
  def sTerrs: LayerHSOptSys[WSep, WSepSome]
  def corners: HCornerLayer
  def names: LayerHcRefSys[String]
}
/*trait EScenBasicEmpty// extends EScenBasic
{/** The grid used. */
  implicit val grid: EGridLongFull
  override lazy val terrs: LayerHcRefSys[WTile] = LayerHcRefSys(grid, Sea)
  override lazy val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome][WTile](grid)
  override lazy val corners: HCornerLayer = HCornerLayer()(grid)
  override lazy val names: LayerHcRefSys[String] = LayerHcRefSys[String](grid, "")
}*/

/** A basic EGrid scenario, containing grid and basic terrain data. */
object EScenBasic
{
  def apply(gridSys: EGridSys, terrs: LayerHcRefSys[WTile], sTerrs: LayerHSOptSys[WSep, WSepSome], offsets: HCornerLayer, names: LayerHcRefSys[String],
    title: String = "EScenBasic"): EScenBasic = new EScenWarmImp(gridSys, terrs, sTerrs, offsets, names, title)

  class EScenWarmImp(val gridSys: EGridSys, override val terrs: LayerHcRefSys[WTile], val sTerrs: LayerHSOptSys[WSep, WSepSome],
    override val corners: HCornerLayer, val names: LayerHcRefSys[String], override val title: String = "EScenWarm") extends EScenBasic
}

trait EScenLongMulti extends EScenBasic
{ override def gridSys: EGridLongMulti
}

/** Contains Earth longitude range grid, [[WTile]] layer and a [[Boolean]] tile side data layer. */
trait LongTerrs
{ /** The grid used. */
  implicit val grid: EGridLongFull

  /** The tile terrain, */
  lazy val terrs: LayerHcRefSys[WTile] = LayerHcRefSys(grid, Sea)

  lazy val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome](grid, WSepNone)
  lazy val corners: HCornerLayer = HCornerLayer()(grid)
  lazy val names: LayerHcRefSys[String] = LayerHcRefSys[String](grid, "")
}