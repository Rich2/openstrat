/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 460km [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. A hex tile area of 183250.975km².
 *  Isle 120974.276km² down to 57981.753km²
 *  [[Isle7]] 30243.569km² => 40265.106km². Sumbawa 15414km² + Flores 16231km² = 32645, Timor Island + Rote Island 32057.1km², Taiwan 36197km².
 *  [[Isle6]] 21653.679km² => 30243.569km². Halmahaera 22835km².
 *  [[Isle5]] 14495.438km² => 21653.679km². Seram 17843km², Samar 13429km² + Leyte 7367.6km² = 20796.6.
 *  [[Isle4]] 8768.845km² => 14495.438km². Buru 12655km². Palawan 12189km², Sumba 11243km², Bali 5780km² + Lambok 4607km² = 9967km².
 *  [[Isle3]] 4473.900km² => 8768.845km². Alor 2724km² + Wetar 2651km² = 5375km². */
object Terr460E120 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e120(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(146, SeaIcePerm),
    TileRow(144, SeaIceWinter),
    TileRow(142, SeaIceWinter),
    TileRow(140, tundra, tundra),
    TileRow(138, hillyTaiga * 2),
    TileRow(136, hillyTaiga, taiga, mtainTaiga),
    TileRow(134, taiga, hillyTaiga * 2),
    TileRow(132, mtainTaiga * 3),
    TileRow(130, mtainTaiga * 4),
    TileRow(128, mtainTaiga * 4),
    TileRow(126, deshot, sahel, savannah, mtainContForest),
    TileRow(124, deshot * 2, sahel, hillyOceForest, mtainContForest),
    VertRow(123, OrigLt(4608, HVDn), BendOut(4616, HVDR), OrigRt(4618, HVDL, 7)),
    TileRow(122, mtainSteppe, oceanic, hillyOce, mtainContForest),

    VertRow(121, Bend(4608, HVUR, 8, 5), ThreeDown(4610, 13, 10, 0), OrigRt(4612, HVDL), BendIn(4614, HVDR, 13), ThreeUp(4616, 0, 13, 6), BendOut(4618, HVUp),
      BendOut(4620, HVUL)),

    TileRow(120, hillySavannah, oceanic, hillyOce, hillyOce, hillyOce),
    VertRow(119, BendMax(4610, HVUR), BendMax(4612, HVDL), OrigRt(4614, HVUp, 7)),
    TileRow(118, mtainSteppe, oceanic * 2, oceanic, sea, hillyOce),
    VertRow(117, OrigLt(4612, HVUp, 7), OrigRt(4616, HVDR), BendIn(4618, HVUp, 13), OrigRt(4620, HVDL, 7)),
    TileRow(116, mtainSubForest, hillyOce, mtainSubForest),
    TileRow(114, mtainSubForest * 3, Isle7(hillyJungle)),
    VertRow(113, BendOut(4606, HVDR), BendIn(4608, HVUL, 7)),
    TileRow(112, hillyJungle * 2),
    VertRow(111, BendMin(4600, HVDR, 4), BendOut(4602, HVDn, 7), BendIn(4604, HVUp, 13), BendIn(4606, HVUL, 13)),
    TileRow(110, mtainJungle, sea * 2, hillyJungle),
    VertRow(109, OrigMin(4598, HVUR), BendIn(4600, HVUL, 13)),
    TileRow(108, hillyJungle, sea * 2, hillyJungle),
    TileRow(106, sea * 2, Isle4(mtainJungle), Isle10(mtainJungle), Isle5(hillyJungle)),
    TileRow(104, sea * 2, mtainJungle, sea, hillyJungle),
    TileRow(102, sea, hillyJungle * 2),
    VertRow(101, BendIn(4606, HVDR), OrigMin(4608, HVDL), ThreeDown(4618, 0, 8, 10), BendIn(4620, HVDn, 13), BendIn(4622, HVDL, 13)),
    TileRow(100, sea, hillyJungle, jungle, mtainJungle * 2, Isle6(mtainJungle), hillyJungle),
    VertRow(99, BendIn(4596, HVDL, 7), OrigRt(4606, HVUp, 7), OrigMin(4622, HVUp)),
    TileRow(98, SepB(), sea, jungle, sea, hillyJungle, Isle4(mtainJungle), Isle5(mtainJungle), mtainJungle),

    VertRow(97, OrigRt(4594, HVUR), ThreeUp(4596, 0, 6, 6), BendOut(4598, HVUp), BendInRt(4600, HVDn, 10, 7), ThreeDown(4602, 13, 12, 0),
      BendInLt(4604, HVDn, 12, 4), ThreeUp(4620, 6, 0, 11), BendOut(4622, HVDL)),

    TileRow(96, hillyJungle * 2, Isle4(hillyJungle), Isle7(mtainJungle), Isle3(mtainJungle)),
    VertRow(95, OrigLt(4594, HVDR, 7), BendIn(4596, HVUp, 13), BendMin(4598, HVDn, 4), OrigRt(4600, HVUL, 7), BendIn(4622, HVUR, 13), OrigRt(4624, HVDR)),
    TileRow(94, sea * 3, Isle4(mtainJungle), Isle7(mtainJungle)),
    VertRow(93, BendIn(4610, HVDR, 13), BendIn(4612, HVDn, 13), BendOut(4614, HVUp), OrigLt(4616, HVDL, 7)),
    TileRow(92, sea * 4, mtainSahel, hillySahel, hillySavannah),
    VertRow(91, BendIn(4608, HVDR, 13), BendOut(4610, HVUL, 7)),
    TileRow(90, sea * 3, sahel, hillySahel, sahel),
    VertRow(89, BendIn(4602, HVDR, 13), OrigLt(4604, HVDL, 7)),
    TileRow(88, sea, hillySahel, deshot * 3, hillySahel),
    VertRow(87, BendIn(4600, HVDR, 13), BendOut(4602, HVUL)),
    TileRow(86, sea, sahel, deshot * 3, hillySahel),
    VertRow(85, BendIn(4600, HVUR, 13), BendMin(4602, HVDL, 2)),
    TileRow(84, sea, savannah * 2, sahel, deshot, sahel),
    TileRow(82, sea * 2, hillySavannah, subtrop, savannah * 2),
    VertRow(81, OrigLt(4602, HVDn, 7), BendMin(4610, HVDR, 2), BendMin(4612, HVDn, 2), BendIn(4614, HVUp, 13), BendOut(4616, HVDn, 7), BendIn(4618, HVDL)),
    TileRow(80, sea, subtrop, savannah),
    VertRow(79, BendIn(4602, HVUR, 13), BendIn(4604, HVUp, 13), BendOut(4606, HVDn, 7), BendIn(4608, HVUp, 13), BendIn(4610, HVUL, 13)),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(114, "" * 3, "Taiwan")
    str(106, "" * 4, "Samar")
    str(96, "Java west", "Java east", "Bali", "Sumbawa", "Alor")
    str(94, "" * 3, "Sumba", "Timor")
  }
}