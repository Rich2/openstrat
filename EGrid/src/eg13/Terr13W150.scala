/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75 East to 105 East. 1300km per hex tile.
 * Isle3 35732.005km² => 70034.730km².
 * Isle below min 35732.005km². Hawaii 28311km².  */
object Terr13W150 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w150(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      VRow(113, BendIn(7678, HVDL, 6, SeaIceWinter)),
      TRow(112, hillyTundra),
      VRow(111, BendIn(7678, HVUL, 13, SeaIceWinter)),
      VRow(109, MouthLt(7682, HVUp, 7)),
      TRow(92, SepB(), sea),
      VRow(87, MouthOld(7684, HVDL, 3, siceWin)),
      TRow(86, siceWin)
    )
  }
  help.run
}