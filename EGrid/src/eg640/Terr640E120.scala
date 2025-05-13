/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 640km [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile area of 354724.005km².
 * [[Isle12]] 183250.975km² => 216506.350km². Sulawesi 186216.16km².
 * [[Isle10]] 125054.068km² => 152766.881km². Java 124413km² + Bali 5780km².
 * [[Isle9]] 100112.536km² => 125054.068km². Luzon 109965km² + Mindoro 10572km² + Catanduanes 1523km² + Marinduque 952km²
 * [[Isle8]] 77942.286km² => 100112.536km². Mindanao 97530km².
 * [[Isle7]] 58543.317km² => 77942.286km². VisayasWest 38353.27km² + Samar-Leyte 21332.41km² = 59685.679km²
 * [[Isle6]] 41915.629km² => 58543.317km². Sumbawa 15414km² + Lambok 4607.68km² + Flores 14731.67km², + others 1500km² + Sumba Island 11243.78km²
 * [[Isle5]] 28059.223km² => 41915.629km². Taiwan 36197km²,
 *   Timor Island 30777km² + Rote Island 1280.10km² + Wetar Island 2651.8km² + Alor Island 2124.93km² + others 600km², Seram Buru 30648km².
 * [[Isle4]] 16974.097km² => 28059.223km². Halmahera 22835km².
 * [[Isle3]] 8660.254km² => 16974.097km². Palawan-12,188.6km². */
object Terr640E120 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e120(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(130, siceWin),
    VertRow(129, OrigMin(4612, HVDR, 3, siceWin)),
    TileRow(128, tundra),
    TileRow(126, hillyTaiga, hillyTaiga),
    TileRow(124, hillyTaiga, hillyTaiga),
    VertRow(123, OrigRt(4612, HVUR, 7, siceWin), BendIn(4614, HVDn, 13, siceWin)),
    TileRow(122, hillyTaiga, hillyTaiga),
    TileRow(120, mtainTaiga, hillyOce, hillyOce),
    VertRow(119, Bend(4616, HVDR, 7, 1)),
    TileRow(118, deshot, steppe, hillyOce),
    VertRow(117, BendIn(4616, HVUL, 8), Bend(4614, HVDR, 10, 7)),
    TileRow(116, hillySahel, hillySavannah, hillyOce),
    VertRow(115, BendIn(4608, HVDR, 13), Orig(4610, HVDL, 5, 4), BendIn(4612, HVDR, 13), BendIn(4614, HVUL, 13)),
    TileRow(114, hillyOce, subtrop, hillyContForest, hillySubForest),
    VertRow(113, BendIn(4608, HVUR, 13), ThreeDown(4610, 13, 0, 6), Bend(4612, HVUL, 3, 5)),
    TileRow(112, subtrop, hillySub),
    VertRow(111, ThreeUp(4610, 2, 11, 4)),
    TileRow(110, hillySavannah * 2, Isle5(hillySubForest)),
    VertRow(109, OrigLt(4602, HVDn), BendOut(4606, HVDR, 7), BendIn(4608, HVUL, 7)),
    TileRow(108, hillyTrop),
    VertRow(107, OrigRt(4602, HVUp), OrigLt(4604, HVUR, 7), BendIn(4606, HVUL, 13)),
    TileRow(106, sea * 2, Isle9(hillyJungle)),
    VertRow(105, BendIn(4598, HVDL, 13), OrigRt(4602, HVDn, 7)),
    TileRow(104, SepB(), jungle, sea, Isle3(mtainJungle), Isle7(hillyJungle)),
    VertRow(103, BendOut(4598, HVUR, 7), ThreeDown(4600, 13, 13, 13), ThreeUp(4602, 0, 13, 13), BendOut(4604, HVUp, 7), BendIn(4606, HVDn), BendIn(4608, HVDL)),
    TileRow(102, jungle, hillyJungle, Isle8(hillyJungle)),

    VertRow(101, ThreeDown(4598, 0, 13, 8), BendOut(4600, HVUL, 7), BendIn(4606, HVDR, 13), BendIn(4608, HVDn, 12), BendIn(4614, HVDR, 12), BendIn(4616, HVDn, 13),
      BendIn(4618, HVDL, 9)),

    TileRow(100, hillyJungle * 2, hillyJungle, Isle4(mtainJungle), hillyJungle),

    VertRow(99, BendIn(4598, HVUR, 13), BendOut(4600, HVDL, 7), BendIn(4606, HVUR, 13), OrigMin(4618, HVUp, 2), BendIn(4612, HVDL, 13), BendIn(4614, HVUR, 8),
      Bend(4616, HVDL, 11, 3)),

    TileRow(98, jungle, sea, hillyJungle, Isle5(mtainJungle), hillyJungle),

    VertRow(97, ThreeDown(4598, 13, 6, 0), BendIn(4600, HVUR, 13), BendIn(4602, HVUp, 13), OrigRt(4604, HVDL, 7), OrigLt(4608, HVDR), BendIn(4610, HVUp, 13),
      BendIn(4612, HVUL, 13), BendIn(4616, HVUR, 13), ThreeDown(4618, 13, 13, 0), OrigRt(4620, HVDL, 7)),

    TileRow(96, hillyJungle * 2, Isle6(mtainJungle), Isle5(mtainJungle)),

    VertRow(95, BendIn(4598, HVUR, 13), BendIn(4600, HVUp, 13), BendOut(4602, HVDn, 7), BendIn(4604, HVUp, 13), BendIn(4606, HVUL, 13), BendIn(4608, HVDR, 13),
      BendIn(4610, HVDn, 13), BendOut(4612, HVUp, 5), OrigLt(4614, HVDL), BendInLt(4620, HVUp, 13, 7)),

    TileRow(94, sea * 2, sahel, savannah * 2),
    VertRow(93, OrigRt(4606, HVUR, 7), BendOut(4608, HVUL, 7)),
    TileRow(92, sea, sahel * 3),
    TileRow(90, sea, sahel, deshot * 2),
    VertRow(89, BendIn(4602, HVDR, 13), OrigRt(4604, HVDL)),
    TileRow(88, savannah * 2, sahel * 2),
    VertRow(87, BendIn(4602, HVUR, 13), BendOut(4604, HVDL, 7), OrigRt(4612, HVDn, 7), OrigRt(4616, HVDn, 7)),
    TileRow(86, sea, hillySavannah, savannah),
    VertRow(85, BendIn(4604, HVUR, 13), BendIn(4606, HVUp, 13), BendOut(4608, HVDn, 7), BendIn(4610, HVUp, 13), BendIn(4612, HVUL, 13)),
    TileRow(72, SeaIcePerm),
    TileRow(70, SeaIcePerm)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(114, "" * 2, "Korea south", "Japan west")
    str(106, "" * 2, "Luzon")
    str(104, "" * 2, "Palawan", "Visayas")
    str(102, "", "Borneo north", "Mindanao")
    str(100, "Borneo west", "Borneo east", "Sulawesi north", "", "New Guinee north west")
    str(98, "Borneo south", "", "Sulawesi south", "", "New Guinee west")
    str(96, "Java west", "Java east", "Sumbawa", "Timor")
  }
}