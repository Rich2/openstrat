/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTile._

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
      TRow(128, tundra),
      TRow(126, tundra, taiga),
      TRow(124, hillyTundra, mtain),
//      VRow(123, SetSide(495)),
//      TRow(122, SideB(), desert * 9),
//      VRow(121, SetSide(495)),
//      TRow(120, desert * 5, hillyDesert * 2, desert * 2),
//      TRow(118, desert * 9),
//      TRow(112, desert, plain, desert * 2),
    )
  }
  help.run
}