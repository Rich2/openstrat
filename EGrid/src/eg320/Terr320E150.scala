/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale of 320km. */
object Terr320E150 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e150(120)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, WSeaIce),
      TRow(162, WSeaIce * 2),
      TRow(160, WSeaIce * 2),
      TRow(158, Hland(3, 5, Level(Tundra), WSeaIce), WSeaIce * 2),
      TRow(156, tundra * 2, Hland(2, 0, Level(Tundra), WSeaIce)),
      TRow(154, tundra * 4),
      TRow(152, tundra, taiga * 3),
      TRow(150, taiga * 4),
      TRow(148, taiga * 3, sea, taiga),
      TRow(146, sea * 3, taiga, sea),
      TRow(144, sea * 3, Hland(2, 4, Level(Taiga)), Hland(2, 1, Level(Taiga))),
      TRow(142, taiga * 2, sea * 2, Hland(3, 2, Level(Taiga)), sea),
      TRow(140, taiga, sea * 5),
      TRow(138, Hland(2, 2, Level(Taiga)), sea * 6),
      TRow(136, sea, Hland(3, 5, Hilly(Forest)), sea * 5),
      TRow(134, Hland(3, 3, Hilly(Forest)), Hland(2, 2, Hilly(Forest)), sea * 5),
      TRow(132, sea, Hland(2, 0, Hilly()), sea * 5),
      VRow(131, Mouth(5626, HVDn)),
      TRow(130, hills, Hland(1, 2, Hilly()), sea * 6),
      TRow(128, Hland(2, 2, Hilly()), sea * 7),
    )
  }
  help.run
}
