/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° east to 75° east, centred on 60° east. Hex tile scale 640km.  */
object Terr640E60 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e60(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      TRow(126, taiga * 2),
      TRow(124, taiga * 2),
      TRow(122, oceanic, oceanic),
      TRow(120, savannah * 3),
      VRow(119, MouthLt(2556, HVUp, 7, Lake)),
      TRow(118, sahel, sahel * 2),
      VRow(117, BendAllOld(2556, HVUR, Lake), BendIn(2558, HVDL, 13, lake)),
      TRow(116, sahel, sahel * 2),
      VRow(115, MouthLt(2556, HVDL, 6, Lake), BendIn(2558, HVUL, 13, lake)),
      TRow(114, hillySahel, hillySahel, Land(Mountains, DesertHot), Land(Mountains, Sahel)),
      TRow(112, hillyDeshot, hillyDeshot, Land(Mountains, DesertHot), savannah),
      VRow(111, MouthLt(2554, HVUL, 7), BendOut(2556, HVUp, 7), BendIn(2558, HVDn, 9), BendIn(2560, HVDL, 13)),
      TRow(110, sahel, sahel, sahel, savannah),
      VRow(109, BendIn(2560, HVUR, 13), BendIn(2562, HVUp, 13), BendOut(2564, HVDn, 7), Bend(2566, HVDL, 5, 1)),
      TRow(108, sahel, sea * 2, savannah),
      VRow(107, BendIn(2566, HVUR, 13), Bend(2568, HVDL, 2, 4)),
      TRow(106, deshot, sea * 3, savannah),
      VRow(105, Bend(2550, HVUp, 4, 6), Bend(2552, HVDn, 10, 4), BendIn(2554, HVUp, 13), MouthRt(2556, HVUR, 7), BendIn(2568, HVUR, 13), Bend(2570, HVDL, 4, 2)),
      TRow(104, deshot),
      VRow(103, BendIn(2570, HVUR), ThreeDown(2572, 6, 11, 0)),
      VRow(101, MouthOld(2550, HVUp), BendIn(2572, HVUR, 9)),
      VRow(97, BendIn(2550, HVDL, 13)),
      VRow(95, BendIn(2550, HVUL, 13)),
    )
  }
  help.run
}