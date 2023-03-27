/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France. Majorca is still just too small in area at this scale.  */
object Terr220E0 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e0(132)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(178, sea * 4, Headland(2, 4, Hilly, Taiga)),
      TRow(176, sea * 5, taigaHills),
      TRow(174, sea * 5, taigaHills),
      TRow(172, sea * 5, taigaHills, taiga),
      TRow(170, sea * 4, taigaHills, taiga * 2),
      TRow(168, sea * 2, Headland(3, 5, Hilly), sea * 2, Headland(2, 3, Hilly, Taiga), taiga),
      VRow(167, VertInDn(522), VertInDL(524)),
      TRow(166, sea * 2, hills, Headland(2, 1, Hilly), sea * 2, plain, Headland(2, 3)),
      TRow(164, sea, Headland(4, 5), hills, sea * 3, plain, sea),
      TRow(162, sea, plain, sea, plain, sea, Headland(2, 5), plain * 3),
      TRow(160, sea, Headland(4, 2), sea, plain * 2, Head1Land(5), plain, hills, plain),
      VRow(159, VertInUp(512), VertInUL(514)),
      TRow(158, sea * 2, Headland(4, 2, Hilly), Headland(3, 4), plain, hills * 4),
      VRow(157, MouthDR(510)),
      TRow(156, sea * 3, Head1Land(0), plain * 2, hills * 2, plain, hills),
      TRow(154, sea * 4, plain * 2, hills, mtain * 3),
      VRow(153, MouthUp(530)),
      TRow(152, sea * 4, plain, hills, mtain, hills, plain, hills),
      VRow(151, VertInUR(530), MouthDR(532)),
      TRow(150, sea, Headland(3, 4, Hilly), hills * 4, sea * 2, Island(Hilly), hills, sea),
      TRow(148, sea * 2, plain * 4, sea * 2, Headland(4, 4, Hilly), sea, hills),
      TRow(146, sea, plain * 4, sea * 3, Headland(4, 1, Hilly), sea * 2),
      TRow(144, sea * 2, plain * 3, sea * 5, hills),
      TRow(142, sea * 3, Headland(3, 2, Hilly), sea * 2, hills * 4, Headland(3, 0, Hilly), sea),
      TRow(140, sea * 2, Headland(2, 5, Hilly), hills, desertHills * 2, desert * 2, desertHills * 2, sea * 2),
      TRow(138, sea * 2, plain, desert, desertHills, desert * 7),
      TRow(136, sea * 2, mtain * 3, desert * 8),
      TRow(134, sea, desertHills * 2, desert * 10),
      TRow(132, sea, desert * 12)
    )
  }
  help.run
}

object BritReg220
{ def britGrid: EGrid220Long = EGrid220Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = britGrid.hCenLayerSpawn(Terr220E0.grid, Terr220E0.terrs)
  def britSTerrs: HSideLayer[WSide] = britGrid.sideLayerSpawn(Terr220E0.grid, Terr220E0.sTerrs)
  def britCorners: HCornerLayer = britGrid.cornerLayerSpawn(Terr220E0.grid, Terr220E0.corners)

  def regScen: EScenBasic = new EScenBasic
  { override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid220Long = britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideLayer[WSide] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}