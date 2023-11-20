/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.  */
object Terr640E150 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e150(112)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      TRow(126, mtain, hillyTaiga),
      TRow(124, hillyTaiga, hillyTaiga),
      TRow(122, sea, Hland(4, 2, hillyTaiga)),
      TRow(120, taiga, sea * 2),
//      TRow(118, plain, Hland(3, 2), plain),
//      VRow(119, Mouth(1536, HVUp), Mouth(1540, HVUp)),
//      TRow(116, hills, hills, hills),
//      TRow(114, sea, sea, sea, hills),
//      TRow(112, desert, plain, desert * 2),
    )
  }
  help.run
}