/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.
 * Isle 234173.269km² <= 112236.892km². Luzon+, southern Philippines
 * [[Isle5]] 28059.223km² => 41915.629km². Taiwan 36197km².
 * Isle3 [[Isle3]] 8660.254km² => 16974.097km². Palawan- (12,188.6 km2). */
object Terr640E120 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e120(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      TRow(126, hillyTaiga, hillyTaiga),
      TRow(124, hillyTaiga, hillyTaiga),
      VRow(123, MouthOld(4614, HVUL)),
      TRow(122, mtainOld, hillyTaiga),
      TRow(120, mtainOld, hilly, hilly),
      TRow(118, desert, savannah, hilly),
      VRow(117, BendOut(4614, HVDR)),
      TRow(116, hillySahel, hillySavannah, hilly),
      TRow(114, hilly, level, sea, hilly),
      TRow(112, level, hilly, sea * 2),
      TRow(110, Land(Hilly, Savannah), hillySavannah, sea, sea),
      TRow(106, sea * 2, Isle10(hillyJungle)),
      VRow(105, BendIn(4598, HVDL, 13)),
      TRow(104, SepB(), jungle, sea, Isle3(mtainJungle), Isle10(hillyJungle)),
      VRow(103, BendOut(4598, HVUR, 7), ThreeDown(4600, 13, 0, 13)),
      TRow(102, sea, hillyJungle),
      VRow(101, ThreeDown(4598, 0, 13, 8)),
      TRow(100, hillyJungle, jungle, hillyJungle, sea, hillyJungle),
      VRow(99, MouthOld(4618, HVDn)),
      TRow(98, hillyJungle, sea, hillyJungle, sea, hillyJungle),
    )
  }
  help.run
}