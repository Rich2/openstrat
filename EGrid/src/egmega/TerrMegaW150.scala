/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 165° west to 135° west, centred on 150° west. Hex tile scale 1 Megametre or 1000km. */
object TerrMegaW150 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w150(82)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, tundra),
      VRow(117, Mouth(7678, HVUp)),
      TRow(116, SepB(), hillyTaiga),
    )
  }
  help.run
}