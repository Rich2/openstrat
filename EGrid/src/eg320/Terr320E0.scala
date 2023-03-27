/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object Terr320E0 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e0(124)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(156, sea * 3),
      TRow(154, sea * 4),
      TRow(152, sea * 3, taigaHills),
      TRow(150, sea * 3, taigaHills),
      TRow(148, sea * 3, taigaHills, taiga),
      TRow(146, sea, hills, sea, Head4Land(4), Head3Land(2, Plains, Forest)),
      TRow(144, sea, Headland(4, 5), plain, sea, plain),
      TRow(142, sea, Headland(4, 2), Headland(1, 5), Headland(3, 1), plain * 2),
      TRow(140, sea, Headland(4, 2, Hilly), Headland(2, 5), plain * 3),
      TRow(138, sea * 2, plain * 2, hills, mtain * 2),
      VRow(137, MouthUp(526)),
      TRow(136, sea * 3, plain, hills, mtain, plain),
      VRow(135, VertInUR(526)),
      TRow(134, sea, hills * 3, sea, Island(Hilly), Head1Land(1, Hilly)),
      TRow(132, sea, hills, plain * 2, sea, Island(Hilly), sea),
      TRow(130, sea, Headland(3, 3), Headland(1, 3, Hilly), Headland(2, 2, Hilly), sea * 2, Head3Land(5, Hilly), Island(Hilly)),
      TRow(128, sea, Headland(2, 5, Hilly), Headland(1, 0, Hilly), hills * 3, Headland(1, 1, Hilly), sea),
      TRow(126, sea, Headland(1, 5), mtain, desertHills, desert * 4),
      TRow(124, sea, desertHills, hills, desert * 6),
    )
  }
  help.run
}

object BritReg
{ def britGrid: EGrid320Long = EGrid320Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = britGrid.hCenLayerSpawn(Terr320E0.grid, Terr320E0.terrs)
  def britSTerrs: HSideLayer[WSide] = britGrid.sideLayerSpawn(Terr320E0.grid, Terr320E0.sTerrs)
  def britCorners: HCornerLayer = britGrid.cornerLayerSpawn(Terr320E0.grid, Terr320E0.corners)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid320Long = britGrid

    override val terrs: HCenLayer[WTile] = britTerrs

    override val sTerrs: HSideLayer[WSide] = britSTerrs

    //override val sTerrsDepr: HSideBoolLayer = britSTerrsDepr
    override val corners: HCornerLayer = britCorners
  }
}