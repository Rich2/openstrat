/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** 640km scale [[WTile]] terrain for 45° east to 75° east, centred on 60° east. Hex tile scale 640km.
 * [[Isle6]] 41915.629km² => 58543.317km². Severny Island 48904 km². */
object Terr640E60 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e60(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(130, Isle6(hillyTundra, SeaIceWinter)),
    VRow(129, BendOut(2558, HVUp, 7, SeaIceWinter), ThreeUp(2560, 10, 13, 0, SeaIceWinter)),
    TRow(128, tundra),
    TRow(126, taiga * 2),
    TRow(124, taiga * 2),
    TRow(122, continental * 2),
    TRow(120, steppe * 3),
    VRow(119, OrigLt(2556, HVDn, 6, Lake)),
    TRow(118, sahel, sahel * 2),
    VRow(117, BendMin(2556, HVUR, 3, Lake), BendIn(2558, HVDL, 13, lake)),
    TRow(116, sahel, sahel * 2),
    VRow(115, OrigLt(2556, HVUR, 6, Lake), BendIn(2558, HVUL, 13, lake)),
    TRow(114, hillySahel, hillySahel, Land(Mountains, DesertHot), Land(Mountains, Sahel)),
    TRow(112, hillyDeshot, hillyDeshot, Land(Mountains, DesertHot), savannah),
    VRow(111, OrigLt(2554, HVDR, 7), BendOut(2556, HVUp, 7), BendIn(2558, HVDn, 9), BendIn(2560, HVDL, 13)),
    TRow(110, sahel, sahel, sahel, savannah),
    VRow(109, BendIn(2560, HVUR, 13), ThreeDown(2562, 13, 0, 13), BendOut(2564, HVDn, 7), Bend(2566, HVDL, 5, 1)),
    TRow(108, sahel, deshot, sea, savannah),
    VRow(107, OrigLt(2560, HVUR, 7), BendIn(2562, HVUL, 13), BendIn(2566, HVUR, 13), Bend(2568, HVDL, 2, 4)),
    TRow(106, deshot, sea * 3, savannah),
    VRow(105, Bend(2550, HVUp, 4, 6), Bend(2552, HVDn, 10, 4), BendIn(2554, HVUp, 13), OrigRt(2556, HVDL, 7), BendIn(2568, HVUR, 13), Bend(2570, HVDL, 4, 2)),
    TRow(104, deshot),
    VRow(103, BendIn(2570, HVUR), ThreeDown(2572, 6, 11, 0)),
    VRow(101, OrigMin(2550, HVDn), BendIn(2572, HVUR, 9)),
    VRow(97, BendIn(2550, HVDL, 13)),
    VRow(95, BendInRt(2550, HVUL, 13, 7), OrigRt(2554, HVDR, 7), BendIn(2556, HVDL, 13)),
    TRow(94, hillyJungle),
    VRow(93, OrigLt(2556, HVUp, 7)),
    VRow(91, OrigLt(2554, HVDR), BendIn(2556, HVDL, 13)),
    TRow(90, hillySavannah),
    VRow(89, OrigLt(2552, HVDR), BendIn(2554, HVUp, 13), BendIn(2556, HVUL, 13)),
    TRow(72, SeaIcePerm),
    TRow(70, SeaIcePerm)
    )
  }
  help.run
}