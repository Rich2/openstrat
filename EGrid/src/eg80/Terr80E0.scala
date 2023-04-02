/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, WTile._

/** 80 Km tile width grid ventred on the Greenwich meridian, 0E form 15W to 15E. Covers North West Europe. The c or column offset is 512 which is G0
 *  in base 32. The c offset for North East Europe will be 1536 or 1G0 in base 32. Current y offset is 300 for the equator. The Old c offset was 200 so a diff of 312 */
object Terr80E0 extends Long80Terrs
{ implicit val grid: EGrid80LongFull = EGrid80.e0(416)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(518, sea * 14, taiga),
      TRow(516, sea * 15, taiga),
      TRow(514, sea * 15, taiga),
      TRow(512, sea * 15, taiga),
      TRow(510, sea * 15, taiga * 2),
      TRow(508, Headland(3, 1, Hilly, Tundra), sea * 14, taiga * 2),
      TRow(506, sea * 15, taiga * 3),
      TRow(504, sea * 14, taiga * 4),
      TRow(502, sea * 13, taiga * 5),
      TRow(500, sea * 13, taiga * 6),
      TRow(498, sea * 4, Isle(Hilly), sea * 7, taiga * 7),
      TRow(496, sea * 13, taiga * 7),
      TRow(494, sea * 9, Headland(4, 5, Hilly), sea * 3, taiga * 7),
      TRow(492, sea * 8, Headland(4, 2, Hilly), sea * 4, taiga * 6),
      TRow(490, sea * 14, taiga * 3, taigaHills, taiga * 3),
      TRow(488, sea * 7, Headland(3, 5, Hilly), Isle(Hilly), sea * 6, taiga * 2, sea, Headland(1, 4), taiga * 2),
      TRow(486, sea * 5, Isle(Hilly) * 2, mtain, Headland(2, 1, Hilly), sea * 7, Headland(3, 2, Hilly, Taiga), sea * 2, Headland(1, 4), plain * 2),
      VRow(485, MouthDL(502)),
      TRow(484, sea * 5, Headland(3, 4, Hilly), mtain, forestHills, hills, sea * 9, Headland(4, 5), Headland(1, 4), plain * 2),
      TRow(482, sea * 6, mtain * 2, hills, sea * 8, plain * 2, sea, Headland(1, 4), plain),
      TRow(480, sea * 7, hills * 2, sea * 9, plain * 2, sea, plain * 2),
      VRow(479, MouthUL(550), VertInDL(552)),
      TRow(478, sea * 6, Headland(3, 3, Hilly), plain * 2, Headland(2, 0), sea * 7, plain * 2, Headland(3, 4), plain * 2, sea),
      VRow(477, MouthDn(552)),
      TRow(476, sea * 5, hills, plain, Headland(3, 3, Hilly), hills * 2, Headland(1, 1), sea * 7, plain, Headland(1, 1), Headland(3, 2), sea * 2),
      TRow(474, sea * 4, Headland(2, 5), plain * 2, Headland(3, 1, Hilly), sea, hills, plain, hills, sea * 7, plain * 2, sea * 3),
      VRow(473, MouthUL(492)),
      TRow(472, sea * 3, plain * 3, Headland(2, 1), sea * 2, plain, hills, Headland(2, 1), sea * 6, plain * 6),
      TRow(470, sea * 4, plain * 3, sea, hills * 2, plain * 2, sea * 4, plain * 9),
      TRow(468, sea * 4, plain * 3, sea * 2, hills * 2, plain * 3, sea * 2, plain * 9),
      TRow(466, sea * 3, Headland(3, 3), plain, Headland(2, 2), sea * 2, hills * 2, plain * 3, sea * 2, plain * 4, hills * 2, plain * 4),
      VRow(465, MouthDL(514), MouthDR(502)),
      TRow(464, sea * 9, Headland(2, 5), plain * 3, Headland(4, 0), sea, plain * 2, hills * 7, plain, hills),
      TRow(462, sea * 8, Headland(2, 5, Hilly), hills, Headland(1, 3, Hilly), Headland(1, 3), Headland(2, 2), sea, Headland(1, 5), plain * 3, hills * 8),
      TRow(460, sea * 7, Headland(4, 2, Hilly), sea * 5, Headland(1, 5), plain * 2, hills * 10),
      VRow(459, MouthDL(508), MouthDR(512)),
      TRow(458, sea * 11, Headland(2, 4), Headland(1, 0), plain * 6, hills * 3, plain, hills * 3, plain),
      VRow(457, MouthDR(506)),
      TRow(456, sea * 9, Headland(3, 4), Headland(2, 0), Headland(2, 5), plain * 7, hills * 3, plain * 3, hills * 2),
      VRow(455, MouthDn(502)),
      TRow(454, sea * 9, plain * 10, forestHills * 2, plain * 4, hills, mtain),
      TRow(452, sea * 11, plain * 9, hills * 2, mtain * 6),
      TRow(450, sea * 12, plain * 7, hills, mtain * 8),
      TRow(448, sea * 12, plain * 4, hills * 2, plain, mtain * 9),
      TRow(446, sea * 13, plain * 2, hills * 4, mtain * 3, plain * 2, mtain, plain * 2, hills * 2),
      TRow(444, sea * 13, forest, plain, hills * 5, mtain * 2, hills * 2, plain * 3, sea, plain),
      TRow(442, sea * 12, plain, forest, hills * 3, mtain, hills, mtain * 2, hills, mtain * 3, hills, Headland(1, 1), sea * 2),
      TRow(440, sea * 13, plain * 3, hills, mtain, hills * 3, mtain * 2, sea * 2, hills, mtain, hills, sea * 2),
      TRow(438, sea * 5, hills * 3, mtain * 3, hills, mtain * 2, hills * 4, sea * 2, forestHills * 2, sea * 4, hills * 3, sea),
      TRow(436, sea * 5, hills * 3, plain * 3, desert * 2, hills, mtain * 4, sea * 5, hills, sea * 2, hills * 2, mtain, sea),
      TRow(434, sea * 6, hills * 2, plain, desert, desertHills, plain, hills, plain * 2, hills * 3, sea * 6, hills, sea * 2, Headland(2, 3, Hilly), hills, mtain, hills),
      TRow(432, sea * 6, hills * 3, plain, desert, hills * 3, desert, plain, hills * 2, sea * 7, Headland(3, 5, Hilly), sea * 3, Headland(2, 3, Hilly), hills),
      TRow(430, sea * 5, plain, hills * 5, desert, hills, desertHills, mtain, plain, sea * 8, hills, Headland(2, 1, Hilly), sea * 4, hills),
      TRow(428, sea * 6, hills * 2, plain * 3, desert * 2, hills * 3, sea * 8, hills * 2, sea * 5),
      TRow(426, sea * 6, hills, plain * 3, hills * 2, desert * 2, hills, plain, sea * 3, plain, sea * 5, hills, sea * 6),
      TRow(424, sea * 5, plain * 5, hills * 6, sea * 16),
      TRow(422, sea * 6, plain * 2, hills * 4, desertHills, hills, desertHills, plain, sea * 14, Headland(1, 0, Hilly), Headland(1, 0, Mountains)),
      TRow(420, sea * 7, hills, plain * 3, desertHills, hills * 4, sea * 14, hills * 3),
      TRow(418, sea * 8, plain, hills * 2, mtain * 2, desertHills, sea * 5, hills, mtain, sea, mtain * 2, hills, mtain, hills * 2, sea * 4, Headland(4, 1, Hilly)),
      TRow(416, sea * 9, hills * 2, sea * 6, mtain, hills * 7, mtain, hills * 2, desertHills, sea * 4),
    )
  }
  help.run
}

/** Object for scenarios covering the western front at 80km. */
object WesternFront
{
  def wfGrid: EGrid80LongPart =
  { val array = Array[Int](10, 494, 10, 496, 11, 494, 11, 496, 15, 494, 15, 496, 15, 494, 15, 496, 12, 506, 3, 520)
    new EGrid80LongPart(446, 0, array)
  }

  def wfTerrs: HCenLayer[WTile] = wfGrid.hCenLayerSpawn(Terr80E0.grid, Terr80E0.terrs)
  def wfSTerrs = wfGrid.sideLayerSpawn(Terr80E0.grid, Terr80E0.sTerrs)
  def wfCorners: HCornerLayer = wfGrid.cornerLayerSpawn(Terr80E0.grid, Terr80E0.corners)

  def wFrontScen : EScenBasic = EScenBasic(wfGrid, wfTerrs, wfSTerrs, wfCorners, "Western Front")
}
