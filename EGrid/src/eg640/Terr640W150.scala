/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 165° west to 135° west, centred on 150° wast. Hex tile scale 640km.  */
object Terr640W150 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w150(96)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(130, ice),
      VRow(129, BendOut(7682, HVUp)),
      TRow(128, Cape(5, 2, tundra)),
      VRow(127, SetSide(7676)),
      TRow(126, SepB(), hillyTundra, taiga),
      TRow(124, hillyTundra, mtain),
      TRow(122, Cape(2, 4, hillyTundra), sea),
    )
  }
  help.run
}