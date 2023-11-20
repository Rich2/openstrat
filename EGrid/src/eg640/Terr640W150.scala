/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 165° west to 135° west, centred on 150° wast. Hex tile scale 640km.  */
object Terr640W150 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w150(108)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(130, ice),
      VRow(129, BendOut(7682, HVUp)),
      TRow(128, Hland(2, 5, tundra)),
      VRow(127, SetSide(7676)),
      TRow(126, SideB(), hillyTundra, taiga),
      TRow(124, hillyTundra, mtain),
      TRow(122, Hland(4, 2, hillyTundra), sea),
    )
  }
  help.run
}