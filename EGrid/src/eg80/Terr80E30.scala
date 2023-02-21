/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import pEarth._, prid._, phex._, WTile._, egrid._

/** The 80 Km grid centred on 30E for 15E to 45E, covers North East Europe. The c or column offset for 30E is 1536 which is 1G0 in base 32. Current y offset is 300 for
 *  the equator. The Old c offset was 400 so a diff of 1136. */
object Terr80E30  extends Long80Terrs
{
  override implicit val grid: EGrid80LongFull = EGrid80.e30(410)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }

    wr(526, sea * 2, taiga * 4, sea * 7)
    wr(524, sea, taiga * 5, sea * 8)
    wr(522, sea, taiga * 8, sea * 5)
    wr(520, taiga * 11, sea * 4)
    gs(518, 1136 + 374, taiga * 12, sea * 2, taiga)
    gs(516, 1136 + 372, taiga * 13, sea * 2, taiga)
    gs(514, 1136 + 370, taiga * 14, sea * 2)
    gs(512, 1136 + 372, taiga * 10, sea * 2, taiga, sea, taiga * 2)
    gs(510, 1136 + 370, taiga * 4, sea, taiga * 6, sea * 3, taiga * 3)
    gs(508, 1136 + 368, taiga * 3, sea * 3, taiga * 5, sea, taiga, sea, taiga * 3)
    gs(506, 1136 + 366, taiga * 4, sea, taiga * 7, sea, taiga * 5)
    gs(504, 1136 + 368, taiga * 2, sea * 2, taiga * 14)
    gs(502, 1136 + 366, taiga * 2, sea * 2, taiga * 14)
    gs(500, 1136 + 364, taiga * 2, sea * 2, taiga * 15)
    gs(498, 1136 + 366, taiga, sea * 2, taiga * 9, Lake, taiga * 6)
    gs(496, 1136 + 364, taiga, sea * 3, taiga * 6, Lake, taiga * 2, Lake, taiga * 6)
    gs(494, 1136 + 362, taiga * 2, sea * 2, taiga * 6, Lake * 2, taiga * 8)
    gs(492, 1136 + 364, taiga * 2, sea * 2, taiga, sea * 4, taiga * 11)
    gs(490, 1136 + 362, taiga * 2, sea * 4, plain * 15)
    gs(488, 1136 + 360, taiga, sea * 4, plain * 16)
    wr(486, plain, sea * 4, plain, sea, plain * 15)
    wr(484, plain , sea, plain, sea, plain, sea, plain * 16)
    wr(482, plain, sea * 3, plain * 18)
    wr(480, plain, sea * 4, plain * 18)
    gs(478, 1136 + 358, sea * 4, plain * 19)
    gs(476, 1136 + 356, sea * 4, plain * 19)
    gs(474, 1136 + 354, sea, plain * 23)
    gs(472, 1136 + 356, plain * 24)
    gs(470, 1136 + 354, plain * 25)
    gs(468, 1136 + 352, plain * 25)
    gs(466, 1136 + 354, plain * 25)
    gs(464, 1136 + 352, plain * 26)
    gs(462, 1136 + 350, hills, plain * 25)
    gs(460, 1136 + 352, hills, plain * 25)
    gs(458, 1136 + 350, hills * 3, mtain * 3, plain * 21)
    gs(456, 1136 + 348, hills, plain, hills * 4, mtain * 2, hills, plain * 18)
    gs(454, 1136 + 350, plain * 7, mtain * 2, hills * 3, plain * 15)
    gs(452, 1136 + 348, mtain, plain * 6, hills, mtain * 2, hills * 3, plain * 15)
    gs(450, 1136 + 346, hills, plain * 5, hills * 3, mtain *2, hills * 3, plain * 7, sea, plain * 6)
    gs(448, 1136 + 348, hills, plain, hills, plain * 3, hills * 3, mtain, hills, plain * 3, sea * 2, plain * 2, sea * 3, plain * 7)
    gs(446, 1136 + 346, hills * 2, plain * 4, mtain * 4, hills * 1, plain * 3, sea * 3, plain * 3, sea, plain * 8)
    wr(444, hills * 3, plain * 3, hills * 4, plain * 4, sea * 3, plain, hills *2, plain, plain * 5, hills, plain * 2)
    wr(442, mtain * 3, hills * 5, plain * 5, sea * 9, mtain * 2, hills * 2, plain * 3)
    wr(440, hills, mtain * 4, hills, mtain, plain * 3, hills * 2, plain, sea * 11, mtain * 3, hills, plain * 2)
    wr(438, sea, mtain * 5, hills, mtain * 2, hills * 4, sea * 13, mtain * 3, plain)
    wr(436, Sea * 2, mtain * 2, hills * 2, mtain * 2, hills * 2, plain * 2, sea * 14, plain, hills, mtain * 2)
    wr(434, hills, sea * 3, hills, mtain, hills * 2, mtain, hills * 2, plain, hills, sea * 4, hills * 4, sea * 6, hills, mtain, hills * 2)
    wr(432, hills, plain, sea * 2, hills, mtain, hills * 6, plain * 2, hills, hills * 7, plain, hills * 8)

    wr(430, hills, plain, sea * 2, hills, mtain, hills, plain, hills * 2, sea, hills, sea, hills * 2, mtain * 5, hills * 3, mtain * 4, hills * 3,
      mtain)

    wr(428, hills, sea, plain, sea, mtain * 2, hills * 2, hills * 2, sea, hills * 3, mtain, hills * 4, desHill * 2, hills * 4, desHill * 2,
      hills * 3, plain)

    wr(426, sea, hills, sea * 3, hills, mtain, plain, hills, sea * 3, hills * 2, mtain, hills * 2, desert, desHill * 7, mtain * 2, hills,
      desHill * 2, mtain, desHill)

    wr(424, hills, sea * 4, hills, mtain, hills * 2, sea * 3, hills * 5, desHill, desert * 2, desHill * 6, mtain * 2, desHill, lake,
      desHill, mtain)

    wr(422, mtain, sea * 5, hills, mtain, hills * 2, sea * 2, hills * 7, desert, desHill, desert, mtain, hills, desHill, mtain, plain * 2,
      desHill, mtain * 2, desHill)

    wr(420, sea * 7, hills * 2, sea * 4, hills, mtain, hills * 2, mtain, hills, plain, desert, hills  * 2, mtain, hills * 2, plain * 2, desert * 4,
      mtain)

    wr(418, sea * 7, hills, sea * 5, hills * 5, mtain, hills * 2, plain * 2, hills, desert * 2, plain, desert * 3, desHill * 2, mtain)
    wr(416, sea * 10, sea * 4, hills, sea, mtain, sea * 2, mtain * 2, sea * 2, hills, plain, desert * 7, desHill)
    wr(414, sea * 9, hills, sea * 10, hills, sea, hills * 2, desert * 8, desHill)
    wr(412, sea * 10, hills * 2, sea * 7, hills, plain, sea * 2, hills, desert * 10)
    wr(410, sea * 11, sea * 12, hills, desHill, desert * 9)//Checked HCens to here

    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res.setSomeInts(Sea, 505,1553)
    res.setSomeInts(Lake, 424,1550)
    res.setSomeInts(Sea, 423,1497,  423,1499,  423,1501, 423,1503,  422,1504,  417,1527,  417,1529,  415,1553,  444, 1562)
    res.setSomeInts(Sea,  422,1472, 429,1521,  429,1523,  431,1529,  432,1530)//Greece
    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res.setMouth3(426, 1550)//Lake Van north
    res.setMouth0(422, 1550)//Lake Van south
    res.setMouth1(422, 1494)//Peloponnese - Greece - west
    res.setVert0In(422, 1498)//Peloponnese - Greece
    res.setVert3In(424, 1500)//Peloponnese - Greece
    res.setVert0In(422, 1502)//Peloponnese - Greece
    res.setVert1In(422, 1502)//Peloponnese - Greece
    res.setMouth0(420, 1504)//Peloponnese - Greece

    res.setMouth1(416, 1524)//Rhodes - Turkey west
    res.setVert0In(416, 1528)//Rhodes - Turkey
    res.setMouth5(416, 1532)//Rhodes - Turkey east

    res.setMouth1(414, 1550)//Cyprus - Turkey west
    res.setMouth4(416, 1556)//Cyprus - Turkey east

    res.setMouth2(430, 1518)
    res.setVert3In(430, 1522)
    res.setMouth4(430, 1526)
    res.setMouth1(430, 1526)
    res.setVert2In(432, 1528)
    res.setMouth3(434, 1530)

    res.setMouth3(446, 1562)//Kerch Straits north
    res.setMouth0(442, 1562)//Kerch Straits south

    res.setCornerIn(422, 1474, 5)//Sicily - Italy north
    res.setCornerIn(422, 1474, 4)//Sicily - Italy
    res.setMouth0Corner(420, 1472)//Sicily - Italy south

    res
  }
}