/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, WTile._

/** 80 Km tile width grid ventred on the Greenwich meridian, 0E form 15W to 15E. Covers North West Europe. The c or column offset is 512 which is G0
 *  in base 32. The c offset for North East Europe will be 1536 or 1G0 in base 32. Current y offset is 300 for the equator. The Old c offset was 200 so a diff of 312 */
object Terr80E0 extends Long80Terrs
{ implicit val grid: EGrid80LongFull = EGrid80.e0(416)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(518, sea * 14, taiga),
      TRow(516, sea * 15, taiga),
      TRow(514, sea * 15, taiga),
      TRow(512, sea * 15, taiga),
      TRow(510, sea * 15, taiga * 2),
      TRow(508, Hland(3, 1, Hilly(Tundra)), sea * 14, taiga * 2),
      TRow(506, sea * 15, taiga * 3),
      TRow(504, sea * 14, taiga * 4),
      TRow(502, sea * 13, taiga * 5),
      TRow(500, sea * 13, taiga * 6),
      TRow(498, sea * 4, Isle(Hilly()), sea * 7, taiga * 7),
      TRow(496, sea * 13, taiga * 7),
      TRow(494, sea * 9, Hland(4, 5, Hilly()), sea * 3, taiga * 7),
      TRow(492, sea * 8, Hland(4, 2, Hilly()), sea * 4, taiga * 6),
      TRow(490, sea * 14, taiga * 3, hillyTaiga, taiga * 3),
      TRow(488, sea * 7, Hland(3, 5, Hilly()), Isle(Hilly()), sea * 6, taiga * 2, sea, Hland(1, 4), taiga * 2),
      TRow(486, sea * 5, Isle(Hilly()) * 2, mtain, Hland(2, 1, Hilly()), sea * 7, Hland(3, 2, Hilly(Taiga)), sea * 2, Hland(1, 4), plain * 2),
      VRow(485, Mouth(502, HVDL)),
      TRow(484, sea * 5, Hland(3, 4, Hilly()), mtain, hillyForest, hills, sea * 9, Hland(4, 5), Hland(1, 4), plain * 2),
      TRow(482, sea * 6, mtain * 2, hills, sea * 8, plain * 2, sea, Hland(1, 4), plain),
      TRow(480, sea * 7, hills * 2, sea * 9, plain * 2, sea, plain * 2),
      VRow(479, Mouth(550, HVUL), VertIn(552, HVDL)),
      TRow(478, sea * 6, Hland(3, 3, Hilly()), plain * 2, Hland(2, 0), sea * 7, plain * 2, Hland(3, 4), plain * 2, sea),
      VRow(477, Mouth(552, HVDn)),
      TRow(476, sea * 5, hills, plain, Hland(3, 3, Hilly()), hills * 2, Hland(1, 1), sea * 7, plain, Hland(1, 1), Hland(3, 2), sea * 2),
      TRow(474, sea * 4, Hland(2, 5), plain * 2, Hland(3, 1, Hilly()), sea, hills, plain, hills, sea * 7, plain * 2, sea * 3),
      VRow(473, Mouth(492, HVUL)),
      TRow(472, sea * 3, plain * 3, Hland(2, 1), sea * 2, plain, hills, Hland(2, 1), sea * 6, plain * 6),
      TRow(470, sea * 4, plain * 3, sea, hills * 2, plain * 2, sea * 4, plain * 9),
      TRow(468, sea * 4, plain * 3, sea * 2, hills * 2, plain * 3, sea * 2, plain * 9),
      TRow(466, sea * 3, Hland(3, 3), plain, Hland(2, 2), sea * 2, hills * 2, plain * 3, sea * 2, plain * 4, hills * 2, plain * 4),
      VRow(465, Mouth(514, HVDL), Mouth(502, HVDR)),
      TRow(464, sea * 9, Hland(2, 5), plain * 3, Hland(4, 0), sea, plain * 2, hills * 7, plain, hills),
      TRow(462, sea * 8, Hland(2, 5, Hilly()), hills, Hland(1, 3, Hilly()), Hland(1, 3), Hland(2, 2), sea, Hland(1, 5), plain * 3, hills * 8),
      TRow(460, sea * 7, Hland(4, 2, Hilly()), sea * 5, Hland(1, 5), plain * 2, hills * 10),
      VRow(459, Mouth(508, HVDL), Mouth(512, HVDR)),
      TRow(458, sea * 11, Hland(2, 4), Hland(1, 0), plain * 6, hills * 3, plain, hills * 3, plain),
      VRow(457, Mouth(506, HVDR)),
      TRow(456, sea * 9, Hland(3, 4), Hland(2, 0), Hland(2, 5), plain * 7, hills * 3, plain * 3, hills * 2),
      VRow(455, Mouth(502, HVDn)),
      TRow(454, sea * 9, plain * 10, hillyForest * 2, plain * 4, hills, mtain),
      TRow(452, sea * 11, plain * 9, hills * 2, mtain * 6),
      TRow(450, sea * 12, plain * 7, hills, mtain * 8),
      TRow(448, sea * 12, plain * 4, hills * 2, plain, mtain * 9),
      TRow(446, sea * 13, plain * 2, hills * 4, mtain * 3, plain * 2, mtain, plain * 2, hills * 2),
      TRow(444, sea * 13, forest, plain, hills * 5, mtain * 2, hills * 2, plain * 3, sea, plain),
      TRow(442, sea * 12, plain, forest, hills * 3, mtain, hills, mtain * 2, hills, mtain * 3, hills, Hland(1, 1), sea * 2),
      TRow(440, sea * 13, plain * 3, hills, mtain, hills * 3, mtain * 2, sea * 2, hills, mtain, hills, sea * 2),
      TRow(438, sea * 5, hills * 3, mtain * 3, hills, mtain * 2, hills * 4, sea * 2, hillyForest * 2, sea * 4, hills * 3, sea),
      TRow(436, sea * 5, hills * 3, plain * 3, desert * 2, hills, mtain * 4, sea * 5, hills, sea * 2, hills * 2, mtain, sea),
      TRow(434, sea * 6, hills * 2, plain, desert, hillyDesert, plain, hills, plain * 2, hills * 3, sea * 6, hills, sea * 2, Hland(2, 3, Hilly()), hills, mtain, hills),
      TRow(432, sea * 6, hills * 3, plain, desert, hills * 3, desert, plain, hills * 2, sea * 7, Hland(3, 5, Hilly()), sea * 3, Hland(2, 3, Hilly()), hills),
      TRow(430, sea * 5, plain, hills * 5, desert, hills, hillyDesert, mtain, plain, sea * 8, hills, Hland(2, 1, Hilly()), sea * 4, hills),
      TRow(428, sea * 6, hills * 2, plain * 3, desert * 2, hills * 3, sea * 8, hills * 2, sea * 5),
      TRow(426, sea * 6, hills, plain * 3, hills * 2, desert * 2, hills, plain, sea * 3, plain, sea * 5, hills, sea * 6),
      TRow(424, sea * 5, plain * 5, hills * 6, sea * 16),
      TRow(422, sea * 6, plain * 2, hills * 4, hillyDesert, hills, hillyDesert, plain, sea * 14, Hland(1, 0, Hilly()), Hland(1, 0, Mountains())),
      TRow(420, sea * 7, hills, plain * 3, hillyDesert, hills * 4, sea * 14, hills * 3),
      TRow(418, sea * 8, plain, hills * 2, mtain * 2, hillyDesert, sea * 5, hills, mtain, sea, mtain * 2, hills, mtain, hills * 2, sea * 4, Hland(4, 1, Hilly())),
      TRow(416, sea * 9, hills * 2, sea * 6, mtain, hills * 7, mtain, hills * 2, hillyDesert, sea * 4),
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

  def wfTerrs: HCenLayer[WTile] = Terr80E0.terrs.spawn(Terr80E0.grid, wfGrid)
  def wfSTerrs:HSideOptLayer[WSide, WSideSome] = wfGrid.sideOptLayerSpawn(Terr80E0.grid, Terr80E0.sTerrs)
  def wfCorners: HCornerLayer = wfGrid.cornerLayerSpawn(Terr80E0.grid, Terr80E0.corners)
  def wFrontScen : EScenBasic = EScenBasic(wfGrid, wfTerrs, wfSTerrs, wfCorners, "Western Front")
}
