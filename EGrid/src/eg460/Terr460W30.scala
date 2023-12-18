/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° west to 15° west, centred on 30° wast. Hex tile scale 460km.  */
object Terr460W30 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w30(112)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(136, Cape(2, 1, ice), sea, Cape(3, 1, mtain)),
//      TRow(130, ice),
//      TRow(128, Cape(2, 1, ice)),
//      TRow(126, ice, Cape(3, 4, hillyTundra)),
//      VRow(121, Mouth(11780, HVDn)),
//      VRow(109, Mouth(11784, HVUR)),
//      TRow(108, sea * 3, Cape(4, 2, desert)),
//      VRow(107, BendOut(11784, HVDL)),
//      TRow(106, sea * 4, sahel),
//      VRow(105, Mouth(11784, HVDn)),
//      VRow(101, Mouth(11766, HVDR)),
//      VRow(99, Mouth(11770, HVUL)),
//      TRow(98, Cape(1, 1, hillySahel)),
//      TRow(96, hillySavannah, Cape(2, 1, hillySavannah)),
    )
  }
  help.run
}