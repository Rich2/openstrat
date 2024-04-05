/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 460km. A hex tile area of 183250.975km².
 *  Isle10 57981.753km² => 120974.276km². Ireland
 *  Isle8 35075.382km² => 57981.753km². Sicily no hex available
 *  Isle6 21653.679km² = > 35075.382km². (Sardinia 24090 km²) + (Corsica 8722 km²) = 32812.
 *  Isle3 4473.900km² => 8768.845km² 4473.900km². Balearic Islands 5040km², Corsica no hex available.
 *  Faroes + Orkneys 2389km². */
object Terr460E0 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e0(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(146, SeaIcePerm),
    VRow(145, BendOut(512, HVDn, 6, SeaIceWinter, sea), BendIn(510, HVDR, 6, SeaIceWinter), BendIn(514, HVUp, 13), Bend(516, HVDn, 7, 4, sea, SeaIceWinter)),
    VRow(143, BendOut(510, HVUL, 6, SeaIceWinter), BendIn(512, HVDL, 13, SeaIceWinter)),
    VRow(141, BendIn(512, HVUL, 13, SeaIceWinter)),
    VRow(137, MouthLt(514, HVUp)),
    TRow(136, sea * 2, hillyTaiga),
    VRow(135, MouthRt(506, HVUR), BendIn(512, HVDR, 13), BendOut(514, HVUL), Bend(520, HVDR, 4, 5, SeaIceWinter, sea)),
    TRow(134, sea, mtainDepr, oceanic),
    VRow(133, BendIn(506, HVDR, 13), MouthLt(508, HVUR), BendInLt(512, HVUR, 13, 7), Bend(514, HVDL, 13, 5)),
    TRow(132, mtainOce, hillyOce, oceForest),

    VRow(131, BendIn(504, HVDR, 13), ThreeUp(506, 13, 13, 0), BendOut(508, HVDL), OrigLt(512, HVUR, 7), ThreeUp(514, 13, 0, 13), BendIn(516, HVUp, 13),
      BendIn(518, HVUL)),

    TRow(130, oceanic, hillyOce, sea, oceanic),
    VRow(129, BendIn(504, HVUR, 13), ThreeDown(506, 6, 13, 0), BendOut(508, HVUL, 7), OrigRt(514, HVDn)),
    TRow(128, hillyOce, oceanic, oceanic, hillyOce),
    VRow(127, BendIn(506, HVUR, 13), ThreeDown(508, 13, 6, 0), Bend(510, HVDn, 1, 7), BendIn(512, HVUp, 9), BendIn(514, HVUL, 6)),
    TRow(126, sea, oceanic, oceanic, hillyOce),
    VRow(125, OrigMin(506, HVUR), ThreeUp(508, 13, 13, 0), Mouth(510, HVDR, 5, 7), MouthMin(518, HVUp), MouthRt(520, HVUL), BendIn(522, HVDL, 7)),
    TRow(124, sea, hillyOce, hillyOce, hillyOce, hillyOce),

    VRow(123, OrigLt(504, HVDn, 7), BendIn(512, HVDR, 13), BendIn(514, HVDn, 13), ThreeDown(516, 13, 13, 13), ThreeUp(518, 13, 0, 10), Bend(520, HVDL, 3, 7),
      BendOut(524, HVDL)),

    TRow(122, hillySavannah, sahel, hillySavannah, hillySavannah, hillySavannah),

    VRow(121, BendIn(504, HVUR, 9), BendIn(506, HVDL), BendIn(510, HVDR, 13), ThreeUp(512, 13, 13, 0), Bend(514, HVUp, 13, 3), ThreeUp(516, 13, 8, 13),
      BendIn(518, HVUp, 10), ThreeUp(520, 6, 3, 3), BendIn(522, HVDL, 11), Mouth(524, HVDn,4 ,7)),

    TRow(120, sea, hillyOce, hillySavannah, hillySavannah, hillySavannah),
    VRow(119, OrigRt(504, HVUR, 7), ThreeUp(506, 13, 6, 0), BendIn(508, HVUp, 13), BendIn(510, HVUL, 13), MouthLt(522, HVDn, 7)),
    TRow(118, sea, hillySahel, hillyDeshot, deshot, deshot, sahel),
    VRow(117, BendIn(500, HVDn, 13), BendInRt(502, HVDL, 13, 7)),
    TRow(116, hillyDeshot, deshot * 5),
    VRow(115, BendIn(500, HVUp, 13), BendIn(502, HVUL, 13)),
    TRow(114, deshot * 4, hillyDeshot, deshot),
    TRow(112, deshot * 6),
    TRow(110, deshot * 6),
    TRow(108, savannah, sahel, savannah, sahel, deshot * 2, sahel),
    TRow(106, savannah, savannah * 2, jungle, savannah * 3),
    VRow(105, BendOut(498, HVDL, 7)),
    TRow(104, jungle, hillyJungle * 4, savannah, hillySavannah),
    VRow(103, BendIn(498, HVUR, 13), BendIn(500, HVUp, 13), MouthOld(502, HVUR), MouthOld(520, HVUp)),
    TRow(102, sea * 5, hillyJungle, jungle),
    VRow(101, BendIn(518, HVDR, 13), BendOut(520, HVUL)),
    TRow(100, sea * 5, hillyJungle * 2),
    VRow(99, BendIn(518, HVUR, 13), BendOut(520, HVDL)),
    TRow(98,sea * 5, mtainDepr, hillyJungle),
    VRow(97, BendIn(520, HVUR, 13), BendOut(522, HVDL, 7)),
    TRow(96, sea * 6, hillyJungle),
    VRow(95, BendIn(522, HVUR, 13), MouthRt(524, HVDR)),
    TRow(94, sea * 6, mtainDepr),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(132, "Scotland west", "Scotland east")
    str(130, "Ireland", "England north")
    str(128, "England south west", "England south east")
    str(124, "", "Spain north west")
    str(122, "Portugal")
    str(120, "", "Spain south")
  }
}