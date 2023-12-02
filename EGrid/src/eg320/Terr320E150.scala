/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale of 320km. */
object Terr320E150 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e150(120)
  override val terrs: LayerHcSys[WTile] = LayerHcSys[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, SeaIcePerm),
      TRow(164, SeaIceWinter),
      TRow(162, SeaIceWinter * 2),
      TRow(160, SeaIceWinter * 2),
      TRow(158, Cape(5, 3, tundra, SeaIceWinter), SeaIceWinter * 2),
      TRow(156, tundra * 2, Cape(0, 2, tundra, SeaIceWinter)),
      TRow(154, tundra * 4),
      TRow(152, tundra, taiga * 3),
      TRow(150, taiga * 4),
      TRow(148, taiga * 3, sea, taiga),
      TRow(146, sea * 3, taiga, sea),
      TRow(144, sea * 3, Cape(4, 2, taiga), Cape(1, 2, taiga)),
      TRow(142, taiga * 2, sea * 2, Cape(2, 3, taiga), sea),
      TRow(140, taiga, sea * 5),
      TRow(138, Cape(2, 2, taiga), sea * 6),
      TRow(136, sea, Cape(5, 3, hillyForest), sea * 5),
      TRow(134, Cape(3, 3, hillyForest), Cape(2, 2, hillyForest), sea * 5),
      TRow(132, sea, Cape(0, 2, hilly), sea * 5),
      VRow(131, Mouth(5626, HVDn)),
      TRow(130, hilly, Cape(2, 1, hilly), sea * 6),
      TRow(128, Cape(2, 2, hilly), sea * 7),
    )
  }
  help.run
}
