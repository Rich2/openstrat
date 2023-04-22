/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** Terrain for 30km 150 degrees east. */
object Terr320E120 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e120(124)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = grid.newSideOptLayer[WSide, WSideSome]
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, WSeaIce),
      TRow(162, WSeaIce * 2),
      TRow(160, WSeaIce * 2),
      TRow(158, tundra, hillyTundra, WSeaIce),
      TRow(156, taiga * 2, mtain),
      TRow(154, taiga * 3, hills),
      TRow(152, taiga * 2, mtain, hillyTaiga),
      TRow(150, taiga * 4),
      TRow(148, taiga * 5),
      TRow(146, hillyTaiga, mtain, taiga, hillyTaiga * 2),
      TRow(144, mtain, hillyTaiga * 4),
      TRow(142, hillyTaiga, taiga, hillyTaiga * 2, taiga, hillyTaiga),
      TRow(140, hillyTaiga, plain, hillyTaiga * 2, taiga, hillyTaiga),
      TRow(138, desert * 2, plain, hills, plain, taiga * 2),
      TRow(136, plain * 7),
      TRow(134, plain * 6, sea),
      VRow(133, Mouth(4610, HVUp)),
      TRow(132, plain * 3, Hland(2, 2, Hilly()), Hland(1, 3, Hilly()), hills, sea),
      TRow(130, desert, plain * 3, Hland(3, 0), Hland(2, 4,Hilly()), Hland(2, 1, Hilly()), sea),
      VRow(131, Mouth(4606, HVUL), Mouth(4614, HVUR)),
      TRow(130, desert, plain * 3, Hland(3, 0), Hland(2, 4,Hilly()), Hland(2, 1, Hilly()), sea),
      TRow(128, plain * 3, Hland(1, 2), sea, Hland(3, 2, Hilly()), sea, hills),
      VRow(127, Mouth(4608, HVDL)),
      TRow(126, mtain, hills, plain * 2, Hland(2, 0), sea * 2, hills),
      VRow(125, Mouth(4612, HVDn)),
      TRow(124, hills, mtain, plain, hills * 2),
    )
  }
  help.run
}