/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 640km. A hex tile area of 709448.010km² . A minimum island area of
 *  118241.335km² */
object Terr640E30 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e30(108)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      TRow(126, taiga * 2),
      TRow(124, land, taiga),
      TRow(122, land, land),
      TRow(120, land * 3),
      TRow(118, land, Cape(2, 3), land),
      VRow(119, Mouth(1536, HVUp), Mouth(1540, HVUp), Mouth(1544, HVUp, Lake)),
      VRow(117, BendAll(1544, HVUR, Lake)),
      TRow(116, hilly, hilly, hilly),
      VRow(115, SetSide(1529), Mouth(1536, HVUp)),
      TRow(114, sea, sea, Cape(4, 1, hilly), hilly),
      VRow(113, Mouth(1528, HVDn), Mouth(1534, HVDL), Mouth(1538, HVDR)),
      TRow(112, desert, Cape(0, 1), desert * 2),
      VRow(111, Mouth(1538, HVUL), Mouth(1546, HVUL)),
      TRow(110, desert * 2, Cape(1, 1, desert), Cape(4, 1, desert)),
      TRow(108, desert, desert, Cape(1, 1, desert), Cape(4, 1, desert)),
    )
  }
  help.run
}