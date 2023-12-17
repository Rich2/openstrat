/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.  */
object Terr640E120 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e120(96)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
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
      TRow(116, hillySahel, hillySavannah, Cape(2, 1, hilly)),
      TRow(114, hilly, land, sea, Cape(3, 3, hilly)),
      TRow(112, land, hilly, sea * 2),
      TRow(110, Land(Hilly, Savannah), hillySavannah, sea, sea),
      TRow(104, SideB(), Cape(1, 3, jungle)),
      TRow(102, sea, Cape(0, 2, hillyJungle)),
      TRow(100, Cape(3, 4, hillyJungle), jungle, Cape(4, 3, hillyJungle), sea, Cape(4, 4, hillyJungle)),
      VRow(99, Mouth(4618, HVDn)),
      TRow(98, Cape(4, 4, hillyJungle), sea, Cape(1, 4, hillyJungle), sea, Cape(3, 2, hillyJungle)),
    )
  }
  help.run
}