/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 640km. */
object Terr640E30 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e30(112)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      TRow(126, taiga * 2),
      TRow(124, plain, taiga),
      TRow(122, plain, plain),
      TRow(120, plain * 3),
      TRow(118, plain, Hland(3, 2), plain),
      VRow(119, Mouth(1536, HVUp), Mouth(1540, HVUp), Mouth(1544, HVUp, Lake)),
      VRow(117, BendAll(1544, HVUR, Lake)),
      TRow(116, hills, hills, hills),
      TRow(114, sea, sea, sea, hills),
      TRow(112, desert, plain, desert * 2),
    )
  }
  help.run
}