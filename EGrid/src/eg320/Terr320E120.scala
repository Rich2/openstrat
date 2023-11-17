/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale of 320km. */
object Terr320E120 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e120(124)
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
      TRow(158, tundra, hillyTundra, WSeaIce),
      TRow(156, taiga * 2, mtain),
      TRow(154, taiga * 3, hills),
      TRow(152, taiga * 2, mtain, hillyTaiga),
      TRow(150, taiga * 4),
      TRow(148, taiga * 5),
      TRow(146, hillyTaiga, mtain, taiga, hillyTaiga * 2),
      TRow(144, mtain, hillyTaiga * 4),
      TRow(142, hillyTaiga, taiga, hillyTaiga * 2, taiga, hillyTaiga),
      TRow(140, hillyTaiga, land, hillyTaiga * 2, taiga, hillyTaiga),
      TRow(138, desert * 2, land, hills, land, taiga * 2),
      TRow(136, land * 7),
      TRow(134, land * 6, sea),
      VRow(133, Mouth(4610, HVUp)),
      TRow(132, land * 3, Hland(2, 2, hills), Hland(1, 3, hills), hills, sea),
      TRow(130, desert, land * 3, Hland(3, 0), Hland(2, 4,hills), Hland(2, 1, hills), sea),
      VRow(131, Mouth(4606, HVUL), Mouth(4614, HVUR)),
      TRow(130, desert, land * 3, Hland(3, 0), Hland(2, 4,hills), Hland(2, 1, hills), sea),
      TRow(128, land * 3, Hland(1, 2), sea, Hland(3, 2, hills), sea, hills),
      VRow(127, Mouth(4608, HVDL)),
      TRow(126, mtain, hills, land * 2, Hland(2, 0), sea * 2, hills),
      VRow(125, Mouth(4612, HVDn)),
      TRow(124, hills, mtain, land, hills * 2),
    )
  }
  help.run
}