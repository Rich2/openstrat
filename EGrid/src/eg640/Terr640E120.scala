/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.  */
object Terr640E120 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e120(112)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      TRow(126, hillyTaiga, hillyTaiga),
      TRow(124, hillyTaiga, hillyTaiga),
      VRow(123, Mouth(4614, HVUL)),
      TRow(122, mtain, hillyTaiga),
      TRow(120, mtain, hilly, hilly),
      TRow(118, desert, savannah, hilly),
      VRow(117, BendOut(4614, HVDR)),
      TRow(116, hillySahel, hillySavannah, Cape(1, 2, hilly)),
      TRow(114, hilly, land, sea, Cape(3, 3, hilly)),
      TRow(112, land, hilly, sea * 2),
    )
  }
  help.run
}
