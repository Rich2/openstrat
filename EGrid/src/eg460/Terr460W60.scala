/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° west to 45° west, centred on 60° wast. Hex tile scale 460km.
 * [[Isle10]] 64603.127km² => 78919.609km². Hispaniola 76192km².
 * [[Isle4]] 8768.845km² => 14495.438km². Falkland Islands 12173km². */
object Terr460W60 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w60(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(147, BendOut(10752, HVUp, 7, SeaIcePerm)),
    TRow(146, ice),
    TRow(144, ice),
    VRow(143, Orig(10750, HVDR, 5, 1, SeaIcePerm), BendIn(10752, HVDL, 13, SeaIcePerm, siceWin)),
    TRow(142, ice),
    VRow(141, BendIn(10750, HVDR, 13, siceWin)),//Special needed BendOut(10752, HVUL, 7, siceWin)),
    TRow(140, mtainTundra, ice),
    VRow(139, BendIn(10748, HVDL, 8, siceWin), BendIn(10750, HVUR, 13, siceWin), BendMax(10752, HVDL, siceWin)),
    TRow(138, hillyTundra, mtainDepr),
    VRow(137, Bend(10752, HVUR, 13, 3, siceWin), Bend(10754, HVDL, 4, 2, siceWin, sea)),
    TRow(136, hillyTundra, siceWin, mtainTundra),
    VRow(135, BendIn(10754, HVUR, 10), BendMin(10756, HVDL)),
    TRow(134, sea * 2, mtainTundra),

    VRow(133, OrigMin(10752, HVDR), BendIn(10754, HVDL, 13), BendIn(10756, HVUR, 12, siceWin, sea), BendIn(10758, HVUp, 6, SeaIceWinter),
      BendIn(10760, HVUL, 6, SeaIceWinter)),

    TRow(132, lakesTaiga, mtainTundra),
    VRow(131, BendOut(10754, HVUR), BendIn(10756, HVDL, 13)),
    TRow(130, lakesTaiga * 2, hillyTaiga),
    VRow(129, BendMin(10754, HVDR), ThreeUp(10756, 0, 13, 6)),
    TRow(128, mtainDepr, mtainDepr, hillyTaiga),
    VRow(127, OrigMax(10750, HVDR), ThreeDown(10752, 13, 0, 6), BendIn(10754, HVUL, 13)),
    TRow(126, hillyTaiga, hillyTaiga, mtainDepr),
    VRow(125, BendIn(10746, HVDR, 8), OrigMin(10748, HVDL), OrigMin(10750, HVDn), BendIn(10752, HVUR, 13), BendIn(10754, HVUp, 13), OrigRt(10756, HVDL, 7)),
    TRow(124, hillyOceForest, Land(PlainLakes, Boreal, Forest)),
    VRow(123, OrigMin(10744, HVDn, 4), BendIn(10746, HVUR, 10), BendIn(10748, HVUp, 13), BendIn(10750, HVUL, 13)),
    VRow(121, OrigMin(10742, HVUR, 2), BendIn(10744, HVUL, 9)),
    VRow(113, BendIn(10740, HVDn, 13), BendIn(10742, HVDL, 13)),
    VRow(111, ThreeDown(10740, 6, 6, 12), ThreeUp(10742, 0, 6, 6), BendIn(10744, HVDL)),
    TRow(110, hillyJungle),
    VRow(109, BendIn(10740, HVUR), BendIn(10742, HVUp), BendIn(10744, HVUL)),

    VRow(107, OrigRt(10742, HVDR), BendOut(10744, HVUp, 7), BendIn(10746, HVDn, 13), BendOut(10748, HVUp, 7), BendIn(10750, HVDn, 13),
      OrigMin(10752, HVUL)),

    TRow(106, hillySavannah * 3),
    TRow(104, mtainJungle, savannah, hillyJungle * 2),
    TRow(102, jungle, hillyJungle * 2, jungle, hillyJungle),
    VRow(101, OrigRt(10762, HVUR), BendIn(10764, HVDn, 13), BendIn(10766, HVDL, 13)),
    TRow(100, jungle * 3, hillyJungle, mtainJungle, hillyJungle, jungle),
    VRow(99, OrigMin(10766, HVUp)),
    TRow(98, jungle * 4, hillyJungle * 2, jungle),
    TRow(96, jungle * 3, hillyJungle * 3, hillySavannah),
    TRow(94, jungle * 2, hillyJungle, hillySavannah * 2, hillyJungle, hillySavannah),
    TRow(92, mtainSahel, mtainSavannah, jungle, hillySavannah * 2, savannah, hillyJungle),
    TRow(90, hillyDeshot, mtainSahel, jungle * 2, hillySavannah * 2),
    VRow(89, BendIn(10740, HVUR, 13), BendOut(10742, HVDL,7)),
    TRow(88, hillySahel, hillySavannah, savannah, hillySavannah, savannah, mtainSub),
    VRow(87, BendIn(10740, HVDR, 13), BendOut(10742, HVUL, 7)),
    TRow(86, hillySahel, mtainSahel, savannah, subtrop, mtainSub * 2),
    VRow(85, BendIn(10740 , HVUR, 13), BendOut(10742, HVDL, 7)),
    TRow(84, mtainSahel, savannah * 2, subtrop, hillySub),
    VRow(83, BendIn(10740, HVDR, 13), BendMin(10742, HVUL, 1)),
    TRow(82, mtainSavannah, savannah * 2, subtrop * 2),
    VRow(81, BendIn(10740, HVUR, 13), BendOut(10742, HVDL, 7), BendOut(10758, HVDR, 7), BendIn(10760, HVUL, 13)),
    TRow(80, hillySteppe, steppe, subtrop, hillySub),
    VRow(79, BendOut(10742, HVUL, 7), OrigLt(10756, HVUR, 7), BendIn(10758, HVUL, 13)),
    TRow(78, hillySahel, sahel),
    VRow(77, BendOut(10750, HVDR), OrigRt(10752, HVDL, 7)),
    TRow(76, hillyOce, hillySteppe),
    TRow(74, hillySteppe),
    VRow(73, BendInLt(10750, HVDR, 13, 7)),
    TRow(72, hillySteppe, Isle4(hillyBoreal)),
    VRow(71, OrigMin(10744, HVUL, 5), OrigLt(10748, HVUR), BendIn(10750, HVUL, 13)),
    TRow(70, hillyOce),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(110, "Hispaniola")
  }
}