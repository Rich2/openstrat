/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import egrid._, pEarth._, prid._, WTile._

/** The new 80 Km grid for North West Europe. The c or column offset is 512 which is G0 in base 32. The c offset for North East Europe will be 1536 or
 * 1G0 in base 32. Current y offset is 300 for the equator. The Old c offset was 200 so a diff of 312 */
object EuropeNW80Terr {
  def apply(): HCenArr[WTile] =
  {
    implicit val grid: HGridIrr = EGrid80Km.l0(446)
    val terrs: HCenArr[WTile] = grid.newTileArr[WTile](sea)
    def gs(yRow: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.setRow(yRow, cStart, tileValues :_*); () }

    //gs(518, 542, taiga)
    //gs(516, 544, taiga)
    //gs(514, 542, taiga)
    //gs(512, 544, taiga)
    //gs(510, 542, taiga * 2)
    /*gs(508, 540, taiga * 3)
    gs(506, 538, taiga * 3)
    gs(504, 536, taiga * 4)
    gs(502, 540, taiga * 6)
    gs(500, 538, taiga * 6)
    gs(498, 494, taiga, sea * 7, taiga * 7)
    gs(496, 528, taiga * 7)
    gs(494, 510, taiga, sea * 3, taiga * 7)
    gs(492, 528, taiga * 7)
    gs(490, 540, taiga * 3, sea, taiga * 3)
    gs(488, 532, taiga * 2, sea * 2, taiga * 3) //, sea * 2)
    gs(486, 498, hills * 2, sea * 10, plain * 3)
    gs(484, 492, hills * 4, sea * 10, plain * 3)
    gs(482, 494, hills * 3, sea * 8, plain * 2, sea, plain * 3)
    gs(480, 496, hills * 2, sea * 9, plain * 2, sea, plain * 2)
    gs(478, 494, hills, plain * 3, sea * 7, plain * 5, sea)
    gs(476, 488, plain * 2, sea, hills * 2, sea * 8, plain * 3, sea * 3)
    gs(474, 482, plain * 3, sea * 2, hills, plain, sea * 8, plain * 2, sea * 3)
    gs(472, 480, plain * 3, sea * 3, plain * 2, sea * 7, plain * 6)
    gs(470, 482, plain * 3, sea, hills * 2, plain * 2, sea * 5, plain * 8)
    gs(468, 480, plain * 3, sea, hills * 3, plain * 3, sea * 3, plain * 8)
    gs(466, 482, plain, sea * 3, hills * 2, plain * 3, sea * 2, plain * 4, hills * 2, plain * 4)
    gs(464, 500, plain * 5, sea, plain * 2, hills * 7, plain, hills)
    gs(462, 494, hills * 3, sea * 3, plain * 4, hills * 8)
    gs(460, 516, plain * 3, hills * 10)
    gs(458, 506, plain * 8, hills * 3, plain, hills * 3, plain)
    gs(456, 500, plain, sea, plain * 7, hills * 3, plain * 3, hills * 2)
    gs(454, 498, plain * 10, hills * 2, plain * 4, hills, mtain)
    gs(452, 504, plain * 9, hills * 2, mtain * 6)
    gs(450, 506, plain * 7, hills, mtain * 8)*/
    //gs(448, 508, plain * 4, hills * 2, plain, mtain * 9)
    //gs(446, 510, plain * 2, hills * 4, mtain * 3, plain * 2, mtain, plain * 2, hills * 2)
    terrs
  }
}
