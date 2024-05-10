/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.
 * [[Isle8]] 77942.286km² => 100112.536km². Hokkaido 83423.84km².
 * [[Isle6]] 41915.629km² => 58543.317km². New Britain 42548.6km². */
object Terr640E150 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e150(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(130, siceWin),
    VRow(129, OrigMin(5632, HVDR, 3, siceWin), BendOut(5634, HVUp, 7, siceWin), BendIn(5636, HVDn, 13, siceWin)),
    TRow(128, tundra),
    TRow(126, mtainTundra, hillyTaiga),
    TRow(124, hillyTaiga, hillyTaiga),

    VRow(123, OrigRt(5628, HVUR, 7, siceWin), BendIn(5630, HVDn, 13, siceWin), ThreeDown(5632, 11, 13, 13), BendMin(5636, HVDR, 3, siceWin),
      OrigRt(5638, HVDL, 7, siceWin)),

    TRow(122, hillyTaiga, hillyTaiga),
    VRow(121, BendOut(5630, HVDR), ThreeUp(5632, 13, 0, 13), OrigMin(5634, HVUL), OrigLt(5636, HVUp, 7, siceWin)),
    TRow(120, taiga),
    VRow(119, Bend(5628, HVDR, 7, 1), ThreeUp(5630, 0, 13, 6), BendIn(5632, HVDL, 13)),
    TRow(118, hillyContForest),
    VRow(117, Bend(5626, HVDR, 10, 7), Bend(5628, HVUL, 8, 3), BendOut(5630, HVDR), BendIn(5632, HVUL, 13)),
    TRow(116, hillyContForest),
    VRow(115, BendOut(5628, HVDR, 7), BendIn(5630, HVUL, 13)),
    TRow(114, hillySub),
    VRow(113, OrigLt(5626, HVUR, 7), BendIn(5628, HVUL, 13)),
    VRow(99, OrigMin(5622, HVUp)),
    TRow(98, hillyJungle * 2, Isle6(hillyJungle)),
    VRow(97, OrigRt(5624, HVDL, 7), ThreeDown(5622, 13, 13, 0), BendOut(5632, HVUR, 7), BendIn(5634, HVDL, 13)),
    TRow(96, jungle, jungle, mtainJungle),

    VRow(95, BendIn(5622, HVUR, 13), BendIn(5624, HVUp, 13), BendOut(5626, HVDn, 7), BendIn(5628, HVUp, 13), BendMax(5630, HVDn), ThreeDown(5632, 13, 0, 13),
      BendIn(5634, HVUL, 13)),

    TRow(94, savannah, hillySavannah),
    VRow(93, Orig(5630, HVUR, 4, 4), ThreeUp(5632, 0, 13, 13), BendIn(5634, HVDL, 13)),
    TRow(92, savannah, hillySavannah),
    VRow(91, BendOut(5634, HVUR, 7), BendIn(5636, HVDL, 13)),
    TRow(90, deshot, savannah, hillySavannah),
    TRow(88, deshot, hillySavannah),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(116, "Honshu north")
    str(114, "Honshu middle")
    str(118, "Hokkaido")
    str(98, "New Guinee\ncentral", "New Guinee NE", "New Britain")
    str(96, "New Guinee SW", "New Guinee SM", "new Guinee SE")
  }
}