/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 135° west to 115° west, centred on 120° wast. Hex tile scale 640km.  */
object Terr640W120 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w120(106)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(130, ice),
      VRow(129, BendOut(8702, HVUp), Mouth(8704, HVUR)),
      TRow(128, tundra),
      TRow(126, taiga * 2),
      TRow(124, taiga * 2),
      TRow(122, hillyTaiga, taiga),
      TRow(120, sea, hillyTaiga, savannah),
      TRow(118, Cape(4, 2, mtain), mtain, hillySahel),
      TRow(116, sea, hillySavannah, hillyDesert),
      TRow(114, sea * 2, hillySahel, hillyDesert),
      TRow(112, sea * 2, hillySahel, hillySahel),
      TRow(110, sea * 3, Cape(3, 2, hillyDesert)),
      VRow(109, Mouth(8712, HVUR)),
      TRow(108, sea * 3, Cape(3, 3, Land(Mountains, Savannah))),
    )
  }
  help.run
}