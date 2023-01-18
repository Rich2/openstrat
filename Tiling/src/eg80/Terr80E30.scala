/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import pEarth._, prid._, phex._, WTile._, egrid._

/** The 80 Km grid centred on 30E for 15E to 45E, covers North East Europe. The c or column offset for 30E is 1536 which is 1G0 in base 32. Current y offset is 300 for
 *  the equator. The Old c offset was 400 so a diff of 1136. */
object Terr80E30  extends LongTerrs
{
  override implicit val grid: EGrid80Long = EGrid80.e30(446)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    gs(526, 1136 + 386, taiga * 4, sea * 7)
    gs(524, 1136 + 376, sea, taiga * 5, sea * 8)
    gs(522, 1136 + 374, sea, taiga * 8, sea * 5)
    gs(520, 1136 + 372, taiga * 11, sea * 4)
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
    gs(486, 1136 + 358, plain, sea * 6, plain * 15)
    gs(484, 1136 + 360, plain , sea, plain, sea, plain, sea, plain * 16)
    gs(482, 1136 + 358, plain, sea * 3, plain * 18)
    gs(480, 1136 + 356, plain, sea * 4, plain * 18)
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
    res
  }
  override val sTerrs: HSideOptLayer[WSide] = {
    val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res.setSomeInts(Sea, 505,1553)
      res
  }
  /*override val sTerrsDepr: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts(505,1553)
    res
  }*/

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}