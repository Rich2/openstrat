/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, WTile._

/** 80 Km tile width grid ventred on the Greenwich meridian, 0E form 15W to 15E. Covers North West Europe. The c or column offset is 512 which is G0
 *  in base 32. The c offset for North East Europe will be 1536 or 1G0 in base 32. Current y offset is 300 for the equator. The Old c offset was 200 so a diff of 312 */
object Terr80E0 extends Long80Terrs
{
  implicit val grid: EGrid80LongFull = EGrid80.e0(416)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.setRowEnd(r, cStart, tileValues :_*); () }

    gs(518, 542, taiga)
    gs(516, 544, taiga)
    gs(514, 542, taiga)
    gs(512, 544, taiga)
    gs(510, 542, taiga * 2)
    gs(508, 540, taiga * 2)
    gs(506, 538, taiga * 3)
    gs(504, 536, taiga * 4)
    gs(502, 530, taiga * 5)
    gs(500, 528, taiga * 6)
    gs(498, 494, hills, sea * 7, taiga * 7)
    gs(496, 528, taiga * 7)
    gs(494, 510, hills, sea * 3, taiga * 7)
    gs(492, 528, taiga * 7)
    gs(490, 530, taiga * 3, sea, taiga * 3)
    wr(488, sea * 8, hills, sea * 6, taiga * 2, sea * 2, taiga * 2)
    wr(486, sea * 7, mtain, hills * 1, sea * 7, taiga, sea * 2, plain * 3)
    gs(484, 492, hills, mtain, hills * 2, sea * 9, plain * 4)
    gs(482, 494, mtain * 2, hills, sea * 8, plain * 2, sea, plain * 2)//Seems like there's an extra plain
    gs(480, 496, hills * 2, sea * 9, plain * 2, sea, plain * 2)
    gs(478, 494, hills, plain * 3, sea * 7, plain * 5, sea)
    gs(476, 488, hills, plain, hills * 3, plain, sea * 7, plain * 3, sea * 2)
    gs(474, 482, plain * 3, sea * 2, hills, plain, hills, sea * 7, plain * 2, sea * 3)
    gs(472, 480, plain * 3, sea * 3, plain, hills, plain, sea * 6, plain * 6)
    gs(470, 482, plain * 3, sea, hills * 2, plain * 2, sea * 4, plain * 9)
    gs(468, 480, plain * 3, sea * 2, hills * 2, plain * 3, sea * 2, plain * 9)
    gs(466, 482, plain, sea * 3, hills * 2, plain * 3, sea * 2, plain * 4, hills * 2, plain * 4)
    gs(464, 500, plain * 5, sea, plain * 2, hills * 7, plain, hills)
    gs(462, 494, hills * 3, sea * 3, plain * 4, hills * 8)
    gs(460, 516, plain * 3, hills * 10)
    gs(458, 506, plain * 8, hills * 3, plain, hills * 3, plain)
    gs(456, 500, plain, sea, plain * 7, hills * 3, plain * 3, hills * 2)
    gs(454, 498, plain * 10, forestHills * 2, plain * 4, hills, mtain)
    gs(452, 504, plain * 9, hills * 2, mtain * 6)
    gs(450, 506, plain * 7, hills, mtain * 8)
    gs(448, 508, plain * 4, hills * 2, plain, mtain * 9)
    gs(446, 510, plain * 2, hills * 4, mtain * 3, plain * 2, mtain, plain * 2, hills * 2)
    wr(444, sea * 13, forest, plain, hills * 5, mtain * 2, hills * 2, plain * 3, sea, plain)
    wr(442, sea * 12, plain, forest, hills * 3, mtain, hills, mtain * 2, hills, mtain * 3, hills, plain, sea * 2)
    wr(440, sea * 13, plain * 3, hills, mtain, hills * 3, mtain * 2, sea * 2, hills, mtain, hills, sea * 2)
    wr(438, sea * 5, hills * 3, mtain * 3, hills, mtain * 2, hills * 4, sea * 2, forestHills * 2, sea * 4, hills * 3, sea)
    wr(436, sea * 5, hills * 3, plain * 3, desert * 2, hills, mtain * 4, sea * 5, hills, sea * 2, hills * 2, mtain, hills)
    wr(434, sea * 6, hills * 2, plain, desert, desertHills, plain, hills, plain * 2, hills * 3, sea * 6, hills, sea * 2, hills * 2, mtain, hills)
    wr(432, sea * 6, hills * 3, plain, desert, hills * 3, desert, plain, hills * 2, sea * 7, hills, sea * 3, hills * 2)
    wr(430, sea * 5, plain, hills * 5, desert, hills, desertHills, mtain, plain, sea * 8, hills * 2, sea * 4, hills)
    wr(428, sea * 6, hills * 2, plain * 3, desert * 2, hills * 3, sea * 8, hills * 2, sea * 5)
    wr(426, sea * 6, hills, plain * 3, hills * 2, desert * 2, hills, plain, sea * 3, plain, sea * 5, hills, sea * 6)
    wr(424, sea * 5, plain * 5, hills * 6, sea * 16)
    wr(422, sea * 6, plain * 2, hills * 4, desertHills, hills, desertHills, plain, sea * 14, hills, mtain)
    wr(420, sea * 7, hills, plain * 3, desertHills, hills * 4, sea * 14, hills * 3)
    wr(418, sea * 8, plain, hills * 2, mtain * 2, desertHills, sea * 5, hills, mtain, sea, mtain * 2, hills, mtain, hills * 2, sea * 4, hills)
    wr(416, sea * 9, hills * 2, sea * 6, mtain, hills * 7, mtain, hills * 2, desertHills, sea * 4)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res.setSomeInts(SCSea, 463,517,  465,499,  465,501,  475,547,  476,546,  476,494,  477,493,  487,503)//British Isles
    res.setSomeInts(SCSea, 477,545,  477,547,  478,544,  478,548,  478,552,  479,545,  479,551,  485,545,  484,546)
    res.setSomeInts(SCSea, 422,576, 433,551)
    res
  }

  override val corners: HCornerLayer =
  { val res: HCornerLayer = grid.newHVertOffsetLayer

    res.setMouth2(488, 500)
    res.setMouth5(486, 506)

    res.setMouth2(478, 490)//North Ireland - Scotland north West
    res.setVert1In(476, 492)//North Ireland - Scotland
    res.setMouth0(474, 494)//North Ireland - Scotland

    res.setMouth1(462, 514)//Straits of Dover north west
    res.setMouth4(464, 520)//Straits of Dover south east

    res.setMouth2(486, 542)//Denmark - Sweden
    res.setVert1In(484, 544)//Denmark - Sweden
    res.setMouth0(482, 546)//Denmark - Sweden

    res.setMouth4(480, 548)//Jutland - Funen north
    res.setVert5In(478, 546)//Funen - Jutland
    res.setVert4In(478, 546)//Funen - Jutland
    res.setTJunction(477, 546)//Funen - Jutland - Zealand

    res.setVert4In(476, 548)//Jutland - Zealand
    res.setMouth5(474, 550)//Jutland - Zealand south
    res.setMouth3(480, 548)//Funen -Zealand north
    res.setVert2In(478, 546)//Funen -Zealand

    res.setMouth2(480, 548)//Zealand - Sweden north
    res.setVert1In(478, 550)//Zealand - Sweden
    res.setMouth0(476, 552)//Zealand - Sweden south

    res.setMouth1(432, 548)//Sardinia - Corsica west
    res.setMouth4(434, 554)//Sardinia - Corsica east

    res.setMouth3Corner(424, 576)//Sicily - Italy north
    res.setCornerIn(422, 574, 1)//Sicily - Italy north
    res.setCornerIn(422, 574, 2)//Sicily - Italy south
    res.setCorner(420, 576, 0, HVUL)//Sicily - Italy south

    res
  }
}

/** Object for scenarios covering the western front at 80km. */
object WesternFront
{
  def wfGrid: EGrid80LongPart =
  { val array = Array[Int](10, 494, 10, 496, 11, 494, 11, 496, 15, 494, 15, 496, 15, 494, 15, 496, 12, 506, 3, 520)
    new EGrid80LongPart(446, 0, array)
  }

  def wfTerrs: HCenLayer[WTile] = wfGrid.hCenLayerSpawn(Terr80E0.grid, Terr80E0.terrs)
  def wfSTerrs = wfGrid.sideOptLayerSpawn(Terr80E0.grid, Terr80E0.sTerrs)
  def wfCorners: HCornerLayer = wfGrid.cornerLayerSpawn(Terr80E0.grid, Terr80E0.corners)

  def wFrontScen : EScenBasic = EScenBasic(wfGrid, wfTerrs, wfSTerrs, wfCorners, "Western Front")
}
