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
    override val rows: RArr[DateRow] = RArr(
      TileRow(146, SeaIcePerm),
      TileRow(144, SeaIceWinter),
      TileRow(142, SeaIceWinter),
      VertRow(141, OrigRt(5632, HVDR, 7, siceWin), OrigLt(5634, HVUL, 7, siceWin)),
      TileRow(140, tundra, SeaIceWinter),
      TileRow(138, tundra, lakesTundra),
      TileRow(136, mtainTundra, hillyTaiga, mtainTundra),
      VertRow(135, OrigLt(5636, HVDn)),
      TileRow(134, mtainTundra * 3),
      VertRow(133, OrigRt(5630, HVDn, 7), BendIn(5634, HVDR, 13), Bend(5636, HVUL, 11, 5), Bend(5638, HVDR, 5, 1, siceWin)),
      TileRow(132, mtainTaiga, sea, hillyTaiga),
      VertRow(131, OrigLt(5628, HVUR, 7), BendIn(5630, HVUL, 13), OrigRt(5634, HVUp), BendLtOut(5638, 6, siceWin), BendIn(5640, HVDL, 10, siceWin)),
      TileRow(130, mtainTaiga, sea * 2, hillyTaiga),
      VertRow(129, OrigRt(5630, HVDn, 7), OrigLt(5638, HVUR, 7, siceWin), BendIn(5640, HVUL, 13, siceWin)),
      TileRow(128, mtainTaiga),
      VertRow(127, BendMin(5628, HVDR), BendIn(5630, HVUL, 7)),
      TileRow(126, mtainTaiga),
      VertRow(125, OrigLt(5626, HVUR, 7), ThreeUp(5628, 0, 6, 9), BendIn(5630, HVDL)),
      TileRow(124, sea, mtainTaiga),
      VertRow(123, OrigRt(5622, HVDL, 7), BendIn(5624, HVDR), BendIn(5626, HVDn), ThreeDown(5628, 6, 0, 13), BendIn(5630, HVUL, 12)),
      TileRow(122, hillyOce),
      VertRow(121, BendOut(5622, HVUp), BendOut(5624, HVUL), BendOut(5626, HVDR), BendIn(5628, HVUL, 13)),
      TileRow(120, hillyOce),
      VertRow(119, BendIn(5626, HVUL, 10)),
      VertRow(117, OrigMin(5620, HVDL), OrigRt(5620, HVDL, 7)),
      VertRow(101, BendIn(5618, HVDL, 13)),
      VertRow(99, OrigMin(5618, HVUp), OrigRt(5622, HVDR), BendOut(5624, HVUp), BendIn(5626, HVDn, 13), BendIn(5628, HVDL, 13)),
      TileRow(98, hillyJungle * 2, sea, Isle4(mtainJungle)),
      VertRow(97, BendOut(5618, HVDL, 7), OrigMin(5628, HVUp, 1)),
      TileRow(96, jungle * 2, mtainJungle, Isle6(mtainJungle), Isle4(hillyJungle), Isle3(mtainJungle)),
      VertRow(95, BendIn(5618, HVUR, 13), OrigRt(5620, HVUL), OrigMin(5626, HVDR, 2), BendOut(5628, HVDL)),
      TileRow(94, sea * 2, mtainJungle, sea * 2, Isle4(mtainJungle)),
      VertRow(93, OrigMax(5622, HVDn), OrigRt(5626, HVUR), ThreeUp(5628, 13, 13, 0), ThreeDown(5630, 13, 0, 13), OrigRt(5632, HVDL, 7)),
      TileRow(92, savannah, savannah, hillySavannah),
      VertRow(91, OrigMax(5622, HVUp), BendOut(5630, HVUR, 7), BendIn(5632, HVDL, 13)),
      TileRow(90, sahel, savannah, hillyJungle),
      VertRow(89, BendOut(5632, HVUR, 7), BendIn(5634, HVDL, 13)),
      TileRow(88, sahel, savannah, hillySubForest, sea * 2, Isle6(mtainJungle)),
      VertRow(87, BendOut(5634, HVUR, 7), BendIn(5636, HVDL, 13)),
      TileRow(86, sahel, savannah * 2, hillySavannah),
      VertRow(85, BendOut(5636, HVUR, 7), BendIn(5638, HVDL, 13)),
      TileRow(84, sahel, savannah, hillySub * 2),
      VertRow(83, BendOut(5636, HVDR, 7), BendIn(5638, HVUL, 13)),
      TileRow(82, hillySavannah, sahel, savannah, hillySub),
      VertRow(81, BendOut(5634, HVDR), BendIn(5636, HVUL, 13)),
      TileRow(80, savannah, hillySavannah, mtainSub),
      VertRow(79, BendIn(5622, HVUR, 8), BendOut(5624, HVDL, 7), BendOut(5632, HVDR), BendIn(5634, HVUL, 10)),
      TileRow(78, oceanic, hillyOce),
      VertRow(77, BendIn(5624, HVUR, 13), BendIn(5626, HVUp, 13), BendOut(5628, HVDn, 7), BendIn(5630, HVUp, 13), BendIn(5632, HVUL, 13)),
      TileRow(76, sea, Isle10(mtainOceForest)),
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