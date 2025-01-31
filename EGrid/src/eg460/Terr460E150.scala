/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 460km.
 * [[Isle10]] 64603.127km² => 78919.609km². Tasmania 68401km²
 * [[Isle7]] 30243.569km² => 40265.106km². New Britain 35144km².
 * [[Isle6]] 21653.679km² => 30243.569km². New Caledonia 18353km².
 * [[Isle4]] 8768.845km² => 14495.438km². Solomon south east 12999km², Bougainville 9518km², New Ireland 8990km².
 * [[Isle3]] 4473.900km² => 8768.845km². Solomon middle 6379km². */
object Terr460E150 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e150(66)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, SeaIceWinter),
      TRow(142, SeaIceWinter),
      VRow(141, OrigRt(5632, HVDR, 7, siceWin), OrigLt(5634, HVUL, 7, siceWin)),
      TRow(140, tundra, SeaIceWinter),
      TRow(138, tundra, lakesTundra),
      TRow(136, mtainTundra, hillyTaiga, mtainTundra),
      VRow(135, OrigLt(5636, HVDn)),
      TRow(134, mtainTundra * 3),
      VRow(133, OrigRt(5630, HVDn, 7), BendIn(5634, HVDR, 13), Bend(5636, HVUL, 11, 5), Bend(5638, HVDR, 5, 1, siceWin)),
      TRow(132, mtainTaiga, sea, hillyTaiga),
      VRow(131, OrigLt(5628, HVUR, 7), BendIn(5630, HVUL, 13), OrigRt(5634, HVUp), BendLtOut(5638, 6, siceWin), BendIn(5640, HVDL, 10, siceWin)),
      TRow(130, mtainTaiga, sea * 2, hillyTaiga),
      VRow(129, OrigRt(5630, HVDn, 7), OrigLt(5638, HVUR, 7, siceWin), BendIn(5640, HVUL, 13, siceWin)),
      TRow(128, mtainTaiga),
      VRow(127, BendMin(5628, HVDR), BendIn(5630, HVUL, 7)),
      TRow(126, mtainTaiga),
      VRow(125, OrigLt(5626, HVUR, 7), ThreeUp(5628, 0, 6, 9), BendIn(5630, HVDL)),
      TRow(124, sea, mtainTaiga),
      VRow(123, OrigRt(5622, HVDL, 7), BendIn(5624, HVDR), BendIn(5626, HVDn), ThreeDown(5628, 6, 0, 13), BendIn(5630, HVUL, 12)),
      TRow(122, hillyOce),
      VRow(121, BendOut(5622, HVUp), BendOut(5624, HVUL), BendOut(5626, HVDR), BendIn(5628, HVUL, 13)),
      TRow(120, hillyOce),
      VRow(119, BendIn(5626, HVUL, 10)),
      VRow(117, OrigMin(5620, HVDL), OrigRt(5620, HVDL, 7)),
      VRow(101, BendIn(5618, HVDL, 13)),
      VRow(99, OrigMin(5618, HVUp), OrigRt(5622, HVDR), BendOut(5624, HVUp), BendIn(5626, HVDn, 13), BendIn(5628, HVDL, 13)),
      TRow(98, hillyJungle * 2, sea, Isle4(mtainJungle)),
      VRow(97, BendOut(5618, HVDL, 7), OrigMin(5628, HVUp, 1)),
      TRow(96, jungle * 2, mtainJungle, Isle6(mtainJungle), Isle4(hillyJungle), Isle3(mtainJungle)),
      VRow(95, BendIn(5618, HVUR, 13), OrigRt(5620, HVUL), OrigMin(5626, HVDR, 2), BendOut(5628, HVDL)),
      TRow(94, sea * 2, mtainJungle, sea * 2, Isle4(mtainJungle)),
      VRow(93, OrigMax(5622, HVDn), OrigRt(5626, HVUR), ThreeUp(5628, 13, 13, 0), ThreeDown(5630, 13, 0, 13), OrigRt(5632, HVDL, 7)),
      TRow(92, savannah, savannah, hillySavannah),
      VRow(91, OrigMax(5622, HVUp), BendOut(5630, HVUR, 7), BendIn(5632, HVDL, 13)),
      TRow(90, sahel, savannah, hillyJungle),
      VRow(89, BendOut(5632, HVUR, 7), BendIn(5634, HVDL, 13)),
      TRow(88, sahel, savannah, hillySubForest, sea * 2, Isle6(mtainJungle)),
      VRow(87, BendOut(5634, HVUR, 7), BendIn(5636, HVDL, 13)),
      TRow(86, sahel, savannah * 2, hillySavannah),
      VRow(85, BendOut(5636, HVUR, 7), BendIn(5638, HVDL, 13)),
      TRow(84, sahel, savannah, hillySub * 2),
      VRow(83, BendOut(5636, HVDR, 7), BendIn(5638, HVUL, 13)),
      TRow(82, hillySavannah, sahel, savannah, hillySub),
      VRow(81, BendOut(5634, HVDR), BendIn(5636, HVUL, 13)),
      TRow(80, savannah, hillySavannah, mtainSub),
      VRow(79, BendIn(5622, HVUR, 8), BendOut(5624, HVDL, 7), BendOut(5632, HVDR), BendIn(5634, HVUL, 10)),
      TRow(78, oceanic, hillyOce),
      VRow(77, BendIn(5624, HVUR, 13), BendIn(5626, HVUp, 13), BendOut(5628, HVDn, 7), BendIn(5630, HVUp, 13), BendIn(5632, HVUL, 13)),
      TRow(76, sea, Isle10(mtainOceForest)),
    )
  }
  help.run

  { import hexNames.{setRow => str}
    str(98, "" * 3, "New Ireland")
    str(96, "" * 3, "New Britain", "Bougainville", "Solomon middle")
    str(94, "" * 5, "Solomon south-east")
    str(88, "" * 5, "New Caldeonia")
    str(76, "", "Tasmania")
  }
}