/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 165° west to 135° west, centred on 150° wast. Hex tile scale 640km.
 * [[Isle5]] 28059.223km² => 41915.629km². Hawaii 28311 km² */
object Terr640W150 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w150(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(129, BendOut(7682, HVUp)),
      TRow(128, tundra),
      //VRow(127, SetSep(7676)),
      TRow(126, hillyTundra, taiga),
      VRow(125, BendIn(7676, HVUL, 13, SeaIceWinter)),
      TRow(124, hillyTundra, mtain),
      VRow(123, BendOut(7680, HVDR, 7), BendOut(7682, HVDn, 7)),
      TRow(122, hillyTundra, sea),
      TRow(108, Isle5(mtain)),
    )
  }
  help.run
}