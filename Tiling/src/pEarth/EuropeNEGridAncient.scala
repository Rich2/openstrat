/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth//; package pEurope
import geom._, pglobe._, pGrid._, WTile._, reflect.ClassTag

object EuropeNEGridAncient extends EGridMaker
{
  def apply[TileT <: TileAncient, SideT <: TileSideAncient](implicit fTile: (Int, Int, WTile) => TileT, fSide: (Int, Int, SideTerr) => SideT,
                                                            evTile: ClassTag[TileT], evSide: ClassTag[SideT]): EGrid80KmAncient[TileT, SideT] =
  {
    val grid = new EGFarNorth[TileT, SideT]("EuropeEast", 30.east, xOffset = 400, xTileMin = 314, xTileMax = 486)//{}
    grid.setTilesAll(Ocean)(fTile)
    grid.setSidesAll(SideNone)(fSide)
    import grid.{setRow => gs}
    gs(526, 386, taiga * 4)
    gs(524, 376, sea, taiga * 5)
    gs(522, 374, sea, taiga * 8)
    gs(520, 372, taiga * 11, sea * 4)
    gs(518, 374, taiga * 12, sea * 2, taiga)
    gs(516, 372, taiga * 13, sea * 2, taiga)
    gs(514, 370, taiga * 14, sea * 2)
    gs(512, 372, taiga * 10, sea * 2, taiga, sea, taiga * 2)
    gs(510, 370, taiga * 4, sea, taiga * 6, sea * 3, taiga * 3)
    gs(508, 368, taiga * 3, sea * 3, taiga * 5, sea, taiga, sea, taiga * 3)
    gs(506, 366, taiga * 4, sea, taiga * 7, sea, taiga * 5)
    gs(504, 368, taiga * 2, sea * 2, taiga * 14)
    gs(502, 366, taiga * 2, sea * 2, taiga * 14)
    gs(500, 364, taiga * 2, sea * 2, taiga * 15)
    gs(498, 366, taiga, sea * 2, taiga * 9, Lake, taiga * 6)
    gs(496, 364, taiga, sea * 3, taiga * 6, Lake, taiga * 2, Lake, taiga * 6)
    gs(494, 362, taiga * 2, sea * 2, taiga * 6, Lake * 2, taiga * 8)
    gs(492, 364, taiga * 2, sea * 2, taiga, sea * 4, taiga * 11)
    gs(490, 362, taiga * 2, sea * 4, plain * 15)
    gs(488, 360, taiga, sea * 4, plain * 16)
    gs(486, 358, plain, sea * 6, plain * 15)
    gs(484, 360, plain , sea, plain, sea, plain, sea, plain * 16)
    gs(482, 358, plain, sea * 3, plain * 18)
    gs(480, 356, plain, sea * 4, plain * 18)
    gs(478, 358, sea * 4, plain * 19)
    gs(476, 356, sea * 4, plain * 19)
    gs(474, 354, sea, plain * 23)
    gs(472, 356, plain * 24)
    gs(470, 354, plain * 25)
    gs(468, 352, plain * 25)
    gs(466, 354, plain * 25)
    gs(464, 352, plain * 26)
    gs(462, 350, hills, plain * 25)
    gs(460, 352, hills, plain * 25)
    gs(458, 350, hills * 3, mtain * 3, plain * 21)
    gs(456, 348, hills, plain, hills * 4, mtain * 2, hills, plain * 18)
    gs(454, 350, plain * 7, mtain * 2, hills * 3, plain * 15)
    gs(452, 348, mtain, plain * 6, hills, mtain * 2, hills * 3, plain * 15)
    gs(450, 346, hills, plain * 5, hills * 3, mtain *2, hills * 3, plain * 7, sea, plain * 6)
    gs(448, 348, hills, plain, hills, plain * 3, hills * 3, mtain, hills, plain * 3, sea * 2, plain * 2, sea * 3, plain * 7)
    gs(446, 346, hills * 2, plain * 4, mtain * 4, hills * 1, plain * 3, sea * 3, plain * 3, sea, plain * 8)

    grid
  }
}