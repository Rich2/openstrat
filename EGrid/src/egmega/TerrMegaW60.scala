/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75° west to 45° west, centred on 60° west. Hex tile scale 1 Megametre or 1000km.
 * Isle5 68503.962km² => 102333.079km². Hispaniola 76,192 km². */
object TerrMegaW60 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w60(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, ice),
      TRow(116, mtainOld),
      TRow(114, CapeOld(0, 3, taiga)),
      TRow(112, CapeOld(2, 2,taiga), sea),
      VRow(109, MouthOld(10748, HVUL)),
      TRow(108, SepB(), sea * 2),
      TRow(104, hillyJungle),
      TRow(102, jungle, hillyJungle, sea),
      TRow(100, jungle * 2, jungle),
      TRow(98, jungle * 2, jungle),
      TRow(96, hillySavannah, Land(Plain, Tropical, CivMix), jungle),
      VRow(95, BendAllOld(10760, HVDR)),
      TRow(94, Land(Mountains, Savannah), savannah, CapeOld(2, 1, savannah)),
      VRow(93, BendOut(10758, HVDR)),
      TRow(92, hillySavannah, CapeOld(2, 2, savannah)),
      VRow(91, MouthOld(10754, HVUL)),
      TRow(90, hillySavannah, sea),
      VRow(89, MouthOld(10748, HVUR)),
      TRow(82, siceWin)
    )
  }
  help.run
}