/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain terrain for 75° west to 45° west, centred on 60° wast. Hex tile scale 460km.
 * [[Isle10]] 64603.127km² => 78919.609km². Hispaniola 76192km².
 * [[Isle4]] 8768.845km² => 14495.438km². Falkland Islands 12173km². */
object Terr460W60 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w60(66)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(147, BendOut(10752, HVUp, 7, SeaIcePerm)),
    TileRow(146, ice),
    TileRow(144, ice),
    VertRow(143, Orig(10750, HVDR, 5, 1, SeaIcePerm), BendIn(10752, HVDL, 13, SeaIcePerm, siceWin)),
    TileRow(142, ice),
    VertRow(141, BendIn(10750, HVDR, 13, siceWin)),//Special needed BendOut(10752, HVUL, 7, siceWin)),
    TileRow(140, mtainTundra, ice),
    VertRow(139, BendIn(10748, HVDL, 8, siceWin), BendIn(10750, HVUR, 13, siceWin), BendMax(10752, HVDL, siceWin)),
    TileRow(138, hillyTundra, mtainDepr),
    VertRow(137, Bend(10752, HVUR, 13, 3, siceWin), Bend(10754, HVDL, 4, 2, siceWin, sea)),
    TileRow(136, hillyTundra, siceWin, mtainTundra),
    VertRow(135, BendIn(10754, HVUR, 10), BendMin(10756, HVDL)),
    TileRow(134, sea * 2, mtainTundra),

    VertRow(133, OrigMin(10752, HVDR), BendIn(10754, HVDL, 13), BendIn(10756, HVUR, 12, siceWin, sea), BendIn(10758, HVUp, 6, SeaIceWinter),
      BendIn(10760, HVUL, 6, SeaIceWinter)),

    TileRow(132, lakesTaiga, mtainTundra),
    VertRow(131, BendOut(10754, HVUR), BendIn(10756, HVDL, 13)),
    TileRow(130, lakesTaiga * 2, hillyTaiga),
    VertRow(129, BendMin(10754, HVDR), ThreeUp(10756, 0, 13, 6)),
    TileRow(128, mtainDepr, mtainDepr, hillyTaiga),
    VertRow(127, OrigMax(10750, HVDR), ThreeDown(10752, 13, 0, 6), BendIn(10754, HVUL, 13)),
    TileRow(126, hillyTaiga, hillyTaiga, mtainDepr),
    VertRow(125, BendIn(10746, HVDR, 8), OrigMin(10748, HVDL), OrigMin(10750, HVDn), BendIn(10752, HVUR, 13), BendIn(10754, HVUp, 13), OrigRt(10756, HVDL, 7)),
    TileRow(124, hillyOceForest, Land(PlainLakes, Boreal, Forest)),
    VertRow(123, OrigMin(10744, HVDn, 4), BendIn(10746, HVUR, 10), BendIn(10748, HVUp, 13), BendIn(10750, HVUL, 13)),
    VertRow(121, OrigMin(10742, HVUR, 2), BendIn(10744, HVUL, 9)),
    VertRow(113, BendIn(10740, HVDn, 13), BendIn(10742, HVDL, 13)),
    VertRow(111, ThreeDown(10740, 6, 6, 12), ThreeUp(10742, 0, 6, 6), BendIn(10744, HVDL)),
    TileRow(110, hillyJungle),
    VertRow(109, BendIn(10740, HVUR), BendIn(10742, HVUp), BendIn(10744, HVUL)),
    VertRow(107, OrigRt(10742, HVDR), BendOut(10744, HVUp, 7), BendIn(10746, HVDn, 13), BendOut(10748, HVUp, 7), BendIn(10750, HVDn, 13), OrigMin(10752, HVUL)),
    TileRow(106, hillySavannah * 3),
    TileRow(104, mtainJungle, savannah, hillyJungle * 2),
    TileRow(102, jungle, hillyJungle * 2, jungle, hillyJungle),
    VertRow(101, OrigRt(10762, HVUR), BendIn(10764, HVDn, 13), BendIn(10766, HVDL, 13)),
    TileRow(100, jungle * 3, hillyJungle, mtainJungle, hillyJungle, jungle),
    VertRow(99, OrigMin(10766, HVUp)),
    TileRow(98, jungle * 4, hillyJungle * 2, jungle),
    TileRow(96, jungle * 3, hillyJungle * 3, hillySavannah),
    TileRow(94, jungle * 2, hillyJungle, hillySavannah * 2, hillyJungle, hillySavannah),
    TileRow(92, mtainSahel, mtainSavannah, jungle, hillySavannah * 2, savannah, hillyJungle),
    TileRow(90, hillyDeshot, mtainSahel, jungle * 2, hillySavannah * 2),
    VertRow(89, BendIn(10740, HVUR, 13), BendOut(10742, HVDL,7)),
    TileRow(88, hillySahel, hillySavannah, savannah, hillySavannah, savannah, mtainSub),
    VertRow(87, BendIn(10740, HVDR, 13), BendOut(10742, HVUL, 7)),
    TileRow(86, hillySahel, mtainSahel, savannah, subtrop, mtainSub * 2),
    VertRow(85, BendIn(10740 , HVUR, 13), BendOut(10742, HVDL, 7)),
    TileRow(84, mtainSahel, savannah * 2, subtrop, hillySub),
    VertRow(83, BendIn(10740, HVDR, 13), BendMin(10742, HVUL, 1)),
    TileRow(82, mtainSavannah, savannah * 2, subtrop * 2),
    VertRow(81, BendIn(10740, HVUR, 13), BendOut(10742, HVDL, 7), BendOut(10758, HVDR, 7), BendIn(10760, HVUL, 13)),
    TileRow(80, hillySteppe, steppe, subtrop, hillySub),
    VertRow(79, BendOut(10742, HVUL, 7), OrigLt(10756, HVUR, 7), BendIn(10758, HVUL, 13)),
    TileRow(78, hillySahel, sahel),
    VertRow(77, BendOut(10750, HVDR), OrigRt(10752, HVDL, 7)),
    TileRow(76, hillyOce, hillySteppe),
    VertRow(75, OrigLt(10750, HVUp, 7)),
    TileRow(74, hillySteppe),
    VertRow(73, BendInLt(10750, HVDR, 12, 7)),
    TileRow(72, hillySteppe, Isle4(hillyBoreal)),
    VertRow(71, BendMin(10744, HVDL, 5), OrigLt(10748, HVUR), BendIn(10750, HVUL, 13)),
    TileRow(70, hillyOce),
    VertRow(69, BendIn(10744, HVUR), OrigRt(10746, HVUL)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(110, "Hispaniola")
    str(72, "", "Falklands")
  }
}