/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° East to 75°, centred on 60° east. Hex tile scale 1300km. */
object Terr13E60 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e60(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(115, BendOut(2560, HVUp, 6, SeaIceWinter), MouthLt(2562, HVUR, 7, SeaIceWinter)),
      TRow(114, tundra),
      TRow(112, taiga),
      TRow(110, desert),
      VRow(109, MouthLt(2558, HVUp, 7, lake)),
      TRow(108, desert),
      VRow(107, MouthRt(2558, HVDn, 7, Lake)),
      TRow(106, desert, hillyOce),
      VRow(105, MouthLt(2556, HVUL), BendIn(2558, HVUp), BendIn(2560, HVDn), BendIn(2562, HVDL, 13)),
      TRow(104, hillyDesert, oceanic),
      VRow(103, BendOut(2556, HVUp), BendIn(2558, HVDn, 12), ThreeDown(2560, 13, 0, 13), ThreeUp(2562, 13, 0, 13), MouthLt(2564, HVDR)),
      TRow(102, desert),
      VRow(101, BendOut(2558, HVDR), BendIn(2560, HVUL, 13), MouthLt(2564, HVUL), BendIn(2566, HVUp)),
      VRow(99, BendOut(2556, HVDR, 7), BendIn(2558, HVUL, 13)),
      VRow(97, ThreeUp(2556, 0, 6, 6), BendIn(2558, HVDL)),
      TRow(96, sea * 2),
      VRow(95, BendIn(2556, HVUp), BendIn(2558, HVUL)),
      TRow(88, SeaIceWinter),
      VRow(87, MouthRt(2562, HVUL, 7, siceWin), BendIn(2564, HVUp, 7, siceWin)),
      TRow(86, ice)
    )
  }
  help.run
}