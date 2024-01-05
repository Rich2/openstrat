/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° west to 15° west, centred on 30° wast. Hex tile scale 460km. Maximum Isle area is 120974.276km², which would
 *  include Iceland if it was centred.
 *  Isle3 from 8768.845km² down to 4473.900km² includes the Canaries. */
object Terr460W30 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w30(102)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, ice),
      TRow(144, ice),
      VRow(143, Mouth(11778, HVUL)),
      TRow(142, Cape(1, 2, ice)),
      VRow(141, Mouth(11778, HVDL)),
      TRow(140, ice),
      TRow(138, ice),
      TRow(136, Cape(2, 1, ice), sea, Cape(3, 1, mtain)),
      VRow(135, BendAll(11772, HVDR), Mouth(11778, HVUL), Mouth(11782, HVUR)),
      VRow(133, SetSide(11771)),
      VRow(129, Mouth(11786, HVDR)),
      TRow(116, sea * 5, Isle3(mtain)),
      TRow(112, sea * 5, Cape(5, 1, desert)),
      VRow(111, BendOut(11786, HVUL)),
      TRow(110, sea * 5, Cape(4, 2, sahel)),
      VRow(109, BendOut(11786, HVDL)),
      TRow(108, sea * 6, Cape(4, 1, savannah)),
      VRow(107, BendOut(11788, HVDL)),
      TRow(106, sea * 6, Cape(4, 1, hillySavannah)),
    )
  }
  help.run
}