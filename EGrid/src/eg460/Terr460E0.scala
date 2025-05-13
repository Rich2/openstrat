/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 460km. A hex tile area of 183250.975km².
 *  Isle10 57981.753km² => 120974.276km². Ireland
 *  Isle8 35075.382km² => 57981.753km². Sicily no hex available
 *  Isle6 21653.679km² = > 35075.382km². (Sardinia 24090 km²) + (Corsica 8722 km²) = 32812.
 *  Isle3 4473.900km² => 8768.845km² 4473.900km². Balearic Islands 5040km², Corsica no hex available.
 *  Faroes + Orkneys 2389km². */
object Terr460E0 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e0(66)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(146, SeaIcePerm),
    VertRow(145, BendOut(512, HVDn, 6, SeaIceWinter), BendIn(510, HVDR, 6, SeaIceWinter), ThreeDown(514, 9, 7, 0, SeaIceWinter, sea, SeaIceWinter)),
    VertRow(143, BendOut(510, HVUL, 6, SeaIceWinter), BendIn(512, HVDL, 13, SeaIceWinter)),
    VertRow(141, BendIn(512, HVUL, 13, SeaIceWinter)),
    VertRow(139, OrigMin(518, HVUp, 5)),
    VertRow(137, OrigLt(514, HVDn, 7)),
    TileRow(136, sea * 2, hillyTaiga),
    VertRow(135, OrigRt(506, HVDL), BendIn(512, HVDR, 13), BendOut(514, HVUL), Bend(520, HVDR, 4, 5, SeaIceWinter, sea)),
    TileRow(134, sea, mtainLakesOceForest, oceanic),
    VertRow(133, BendIn(506, HVDR, 13), OrigLt(508, HVDL), BendInLt(512, HVUR, 13, 7), Bend(514, HVDL, 13, 5)),
    TileRow(132, mtainOce, hillyOce, oceForest),

    VertRow(131, BendIn(504, HVDR, 13), ThreeUp(506, 13, 13, 0), BendOut(508, HVDL), OrigLt(512, HVUR, 7), ThreeUp(514, 13, 0, 13), BendIn(516, HVUp, 13),
      BendIn(518, HVUL)),

    TileRow(130, oceanic, hillyOce, sea, oceanic),
    VertRow(129, BendIn(504, HVUR, 13), ThreeDown(506, 6, 13, 0), BendOut(508, HVUL, 7), OrigRt(514, HVDn)),
    TileRow(128, hillyOce, oceanic, oceanic, hillyOce),
    VertRow(127, BendIn(506, HVUR, 13), ThreeDown(508, 13, 6, 0), Bend(510, HVDn, 1, 7), BendIn(512, HVUp, 9), BendIn(514, HVUL, 6)),
    TileRow(126, sea, oceanic, oceanic, hillyOce),
    VertRow(125, OrigMin(506, HVUR), ThreeUp(508, 13, 13, 0), Orig(510, HVUL, 5, 7), OrigMin(518, HVDn), OrigRt(520, HVDR), BendIn(522, HVDL, 7)),
    TileRow(124, sea, hillyOce, hillyOce, hillyOce, hillyOce),

    VertRow(123, OrigLt(504, HVDn, 7), BendIn(512, HVDR, 13), BendIn(514, HVDn, 13), ThreeDown(516, 13, 13, 13), ThreeUp(518, 13, 0, 10), Bend(520, HVDL, 3, 7),
      BendOut(524, HVDL)),

    TileRow(122, hillySavannah, sahel, hillySavannah, hillySavannah, hillySavannah),

    VertRow(121, BendIn(504, HVUR, 9), BendIn(506, HVDL), BendIn(510, HVDR, 13), ThreeUp(512, 13, 13, 0), Bend(514, HVUp, 13, 3), ThreeUp(516, 13, 8, 13),
      BendIn(518, HVUp, 10), ThreeUp(520, 6, 3, 3), BendIn(522, HVDL, 11), Orig(524, HVUp,4 ,7)),

    TileRow(120, sea, hillyOce, hillySavannah, hillySavannah, hillySavannah),
    VertRow(119, OrigRt(504, HVUR, 7), ThreeUp(506, 13, 6, 0), BendIn(508, HVUp, 13), BendIn(510, HVUL, 13), OrigLt(522, HVUp, 7)),
    TileRow(118, sea, hillySahel, hillyDeshot, deshot, deshot, sahel),
    VertRow(117, BendIn(500, HVDn, 13), BendInRt(502, HVDL, 13, 7)),
    TileRow(116, hillyDeshot, deshot * 5),
    VertRow(115, BendIn(500, HVUp, 13), BendIn(502, HVUL, 13)),
    TileRow(114, deshot * 4, hillyDeshot, deshot),
    TileRow(112, deshot * 6),
    TileRow(110, deshot * 6),
    TileRow(108, savannah, sahel, savannah, sahel, deshot * 2, sahel),
    TileRow(106, savannah, savannah * 2, jungle, savannah * 3),
    VertRow(105, BendOut(498, HVDL, 7)),
    TileRow(104, jungle, hillyJungle * 4, savannah, hillySavannah),
    VertRow(103, BendIn(498, HVUR, 13), BendIn(500, HVUp, 13), OrigLt(502, HVDL), OrigMax(516, HVDn)),
    TileRow(102, sea * 4, jungle, hillyJungle, jungle),
    VertRow(101, BendIn(516, HVUR, 13), BendOut(518, HVDL, 7)),
    TileRow(100, sea * 5, hillyJungle * 2),
    VertRow(99, BendIn(518, HVUR, 13), BendOut(520, HVDL)),
    TileRow(98, sea * 5, mtainJungle, hillyJungle),
    VertRow(97, BendIn(520, HVUR, 13), BendOut(522, HVDL, 7)),
    TileRow(96, sea * 6, hillyJungle),
    VertRow(95, BendIn(522, HVUR, 13), OrigMin(524, HVUL)),
    TileRow(94, sea * 6, hillySavannah),
    VertRow(93, BendIn(522, HVDR, 13), OrigMin(524, HVDL, 2)),
    TileRow(92, sea * 6, hillySavannah),
    VertRow(91, BendIn(520, HVDR, 13), BendMin(522, HVUL, 1)),
    TileRow(90, sea * 5, hillySahel),
    VertRow(89, BendIn(520, HVUR, 13), BendOut(522, HVDL, 7)),
    TileRow(88, sea * 5, deshot),
    VertRow(87, BendIn(522, HVUR, 13), OrigLt(524, HVUL)),
    VertRow(85, OrigLt(522, HVDn, 7)),
    TileRow(84, sea * 5, hillySahel),
    VertRow(83, BendIn(522, HVUR, 13), BendOut(524, HVDL,7)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(132, "Scotland west", "Scotland east")
    str(130, "Ireland", "England north")
    str(128, "England south west", "England south east")
    str(124, "", "Spain north west")
    str(122, "Portugal", "", "Balearics", "Sardina")
    str(120, "", "Spain south")
  }
}