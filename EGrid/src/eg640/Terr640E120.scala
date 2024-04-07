/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.
 * Isle 234173.269km² <= 112236.892km². Luzon+, southern Philippines
 * [[Isle5]] 28059.223km² => 41915.629km². Taiwan 36197km².
 * Isle3 [[Isle3]] 8660.254km² => 16974.097km². Palawan- (12,188.6 km2). */
object Terr640E120 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e120(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(130, siceWin),
    VRow(129, OrigMin(4612, HVDR, 3, siceWin)),
    TRow(128, tundra),
    TRow(126, hillyTaiga, hillyTaiga),
    TRow(124, hillyTaiga, hillyTaiga),
    VRow(123, OrigRt(4612, HVUR, 7, siceWin), BendIn(4614, HVDn, 13, siceWin)),
    TRow(122, hillyTaiga, hillyTaiga),
    TRow(120, mtainTaiga, hillyOce, hillyOce),
    TRow(118, deshot, steppe, hillyOce),
    VRow(117, Bend(4614, HVDR, 10, 7)),
    TRow(116, hillySahel, hillySavannah, hillyOce),
    VRow(115, BendIn(4608, HVDR, 13), Orig(4610, HVDL, 5, 4), BendIn(4612, HVDR, 13), BendIn(4614, HVUL, 13)),
    TRow(114, hillyOce, oceanic, hillyContForest, hillyOce),
    VRow(113, BendIn(4608, HVUR, 13), ThreeDown(4610, 13, 0, 6), Bend(4612, HVUL, 3, 5)),
    TRow(112, oceanic, hillyOce, sea * 2),
    VRow(111, BendIn(4608, HVDR, 11), ThreeUp(4610, 2, 11, 4), BendIn(4612, HVDL, 11)),
    TRow(110, hillySavannah * 2, hillySubForest),
    VRow(109, OrigLt(4602, HVDn), BendOut(4606, HVDR, 7), ThreeUp(4608, 11, 0, 9), BendIn(4610, HVUp, 11), BendIn(4612, HVUL, 11)),
    TRow(108, hillyTrop),
    VRow(107, OrigRt(4602, HVUp), OrigLt(4604, HVUR, 7), BendIn(4606, HVUL, 13)),
    TRow(106, sea * 2, Isle10(hillyJungle)),
    VRow(105, BendIn(4598, HVDL, 13), OrigRt(4602, HVDn, 7)),
    TRow(104, SepB(), jungle, sea, Isle3(mtainJungle), Isle10(hillyJungle)),
    VRow(103, BendOut(4598, HVUR, 7), ThreeDown(4600, 13, 0, 13), BendIn(4602, HVUL, 13)),
    TRow(102, sea, hillyJungle),
    VRow(101, ThreeDown(4598, 0, 13, 8), BendIn(4614, HVDR, 12), BendIn(4616, HVDn, 13), BendIn(4618, HVDL, 9)),
    TRow(100, hillyJungle, jungle, hillyJungle, sea, hillyJungle),
    VRow(99, OrigMin(4618, HVUp, 2)),
    TRow(98, hillyJungle, sea, hillyJungle, sea, hillyJungle),
    )
  }
  help.run
}