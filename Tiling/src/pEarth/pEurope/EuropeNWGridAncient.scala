/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, pGrid._, WTile._, reflect.ClassTag

object EuropeNWGridAncient extends EGridMaker
{
  def apply[TileT <: TileAncient, SideT <: TileSideAncient](implicit fTile: (Int, Int, WTile) => TileT, fSide: (Int, Int, SideTerr) => SideT,
                                                            evTile: ClassTag[TileT], evSide: ClassTag[SideT]): EGrid80KmAncient[TileT, SideT] =
  {
    val grid: EGFarNorth[TileT, SideT] = new EGFarNorth[TileT, SideT]("WEurope", 0.east, xOffset = 200, xTileMin = 114, xTileMax = 286)
    grid.setTilesAll(Ocean)(fTile)
    grid.setSidesAll(SideNone)(fSide)
    grid.fSetSide(181, 477, Straitsold)(fSide)
    grid.fSetSide(205, 463, Straitsold)(fSide)
    import grid.{setRow => gs}
    gs(518, 230, taiga)
    gs(516, 232, taiga)
    gs(514, 230, taiga)
    gs(512, 232, taiga)
    gs(510, 230, taiga * 2)
    gs(508, 228, taiga * 3)
    gs(506, 226, taiga * 3)
    gs(504, 224, taiga * 4)
    gs(502, 218, taiga * 6)
    gs(500, 216, taiga * 6)
    gs(498, 182, taiga, sea * 7, taiga * 7)
    gs(496, 216, taiga * 7)
    gs(494, 198, taiga, sea * 3, taiga * 7)
    gs(492, 216, taiga * 7)
    gs(490, 218, taiga * 3, sea, taiga * 3)
    gs(488, 220, taiga * 2, sea * 2, taiga * 3)//, sea * 2)
    gs(486, 186, hills * 2, sea * 10, plain * 3)
    gs(484, 180, hills * 4, sea * 10, plain * 3)
    gs(482, 182, hills * 3, sea * 8, plain * 2, sea, plain * 3)
    gs(480, 184, hills * 2, sea * 9, plain *2, sea, plain * 2)
    gs(478, 182, hills, plain * 3, sea * 7, plain * 2, sea, plain * 2, sea)
    gs(476, 176, plain * 2, sea, hills * 2, sea * 8, plain * 3, sea * 3)
    gs(474, 170, plain * 3, sea * 2, hills, plain, sea * 8, plain * 2, sea * 3)
    gs(472, 168, plain * 3, sea * 3, plain * 2, sea * 7, plain * 6)
    gs(470, 170, plain * 3, sea, hills * 2, plain * 2, sea * 5, plain * 8)
    gs(468, 168, plain * 3, sea, hills * 3, plain * 3, sea * 3, plain * 8)
    gs(466, 170, plain, sea * 3, hills * 2, plain * 3, sea * 2, plain * 4, hills * 2, plain * 4)
    gs(464, 188, plain * 5, sea, plain * 2, hills * 7, plain, hills)
    gs(462, 182, hills * 3, sea * 3, plain * 4, hills * 8)
    gs(460, 204, plain * 3, hills * 10)
    gs(458, 194, plain * 8, hills * 3, plain, hills * 3, plain)
    gs(456, 188, plain, sea, plain * 7, hills * 3, plain * 3, hills * 2)
    gs(454, 186, plain * 10, hills * 2, plain * 4, hills, mtain)
    gs(452, 192, plain * 9, hills * 2, mtain * 6)
    gs(450, 194, plain * 7, hills, mtain * 8)
    gs(448, 196, plain * 4, hills * 2, plain, mtain * 9)
    gs(446, 198, plain * 2, hills * 4, mtain * 3, plain * 2, mtain, plain * 2, hills * 2)

    grid
  }
}