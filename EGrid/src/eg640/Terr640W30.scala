/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° west to 15° west, centred on 30° wast. Hex tile scale 640km.  */
object Terr640W30 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w30(112)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(130, ice),
      TRow(128, Cape(2, 1, ice)),
      TRow(126, ice, Cape(3, 4, hillyTundra)),

//      TRow(124, sea, hillyDesert, hills, desert * 6),
//      VRow(123, SetSide(495)),
//      TRow(122, SideB(), desert * 9),
//      VRow(121, SetSide(495)),
//      TRow(120, desert * 5, hillyDesert * 2, desert * 2),
//      TRow(118, desert * 9),
    )
  }
  help.run
}