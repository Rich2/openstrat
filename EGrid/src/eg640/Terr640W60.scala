/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° west to 45° west, centred on 60° wast. Hex tile scale 640km.  */
object Terr640W60 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w60(108)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(130, tundra),
      TRow(128, tundra),
      VRow(127, SetSide(10748)),
      TRow(126, SideB(), Hland(2, 2, tundra), Hland(4, 3, hillyTundra)),
      VRow(123, Mouth(10748, HVDR)),
      TRow(124, Hland(4, 5, Mountains(Tundra)), sea),
      TRow(122, taiga, sea),
      TRow(120, taiga, taiga),
      VRow(119, Mouth(10750, HVUL), Mouth(10752, HVDR)),
      TRow(118, Hland(2, 2, taiga), sea),
      VRow(117, Mouth(10748, HVUL)),
    )
  }
  help.run
}