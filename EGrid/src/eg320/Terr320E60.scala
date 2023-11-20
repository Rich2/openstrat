/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° east to 75° east, centred on 60° east. Hex tile scale of 320km. */
object Terr320E60 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e60(118)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, WSeaIce),
      TRow(162, WSeaIce * 2),
      TRow(160, Cape.a(5, 1, tundra, WSeaIce), sea),
      TRow(158, Cape.a(2, 4, tundra), sea, tundra),
      TRow(156, sea * 2, tundra),
      VRow(155, SetSide(2553)),
      TRow(154, SideB(), tundra * 4),
      TRow(152, taiga * 4),
      TRow(150, taiga * 4),
      TRow(148, forest, taiga * 4),
      TRow(146, forest * 2, taiga * 3),
      TRow(144, forest * 5),
      TRow(142, land * 6),
      TRow(140, land * 2, desert * 3, land),
      TRow(138, land, desert * 6),
      VRow(137, Mouth(2552, HVUR, Lake)),
      TRow(136, Cape.a(2, 1, land, Lake), Cape.a(5, 1, desert, Lake), desert * 5),
      TRow(134, Lake, desert * 5, mtain),
      TRow(132, mtain, Lake, desert * 3, mtain * 2),
      TRow(130, mtain, Cape.a(1, 1, mtain, Lake), Cape.a(5, 1, desert, Lake), desert * 3, mtain * 2),
      VRow(129, Mouth(2552, HVDn, Lake)),
      TRow(128, hillyDesert, desert * 5, mtain * 2),
      TRow(126, desert, mtain, desert * 5, land),
      TRow(124, desert, land, mtain, desert * 4, land * 2),
      VRow(123, Mouth(2546, HVUL)),
      TRow(122, Cape.a(1, 1, desert), Cape.a(3, 2, hillyDesert), Cape.a(3, 1, mtain), desert * 3, land, desert * 2),
      TRow(120, desert * 2, sea, Cape.a(0, 2, hillyDesert), sea * 2, hillyDesert, land, desert),
      TRow(118, desert * 4, sea * 3, land * 2),
    )
  }

  help.run
}