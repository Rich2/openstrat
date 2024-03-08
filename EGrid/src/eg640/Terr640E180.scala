/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.  */
object Terr640E180 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e180(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      VRow(127, BendOut(6658, HVUR)),
      TRow(126, hillyTundra, tundra),
      VRow(125, MouthLt(6656, HVUL, 7, SeaIceWinter), ThreeDown(6658, 13, 13, 0, SeaIceWinter), BendIn(6660, HVUL, 13, SeaIceWinter)),
      TRow(124, sea, hillyTundra),
      TRow(122, SepB()),
    )
  }
  help.run
}