/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 460km. A hex tile area of 354724.005km².
 *  Isle 234173.269km² <= 112236.892km².
 *  Isle10 120974.276km² <= 57981.753km². Ireland
 *  Isle8 57981.753km² <= 35075.382km². Sardinia. Sicily no hex avaliabe
 *  Isle3 8768.845km² <= 4473.900km² Corsica no hex available. */
object Terr460E0 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e0(102)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      VRow(143, Mouth(510, HVUL)),
      TRow(142, SideB()),
      TRow(136, sea * 2, Cape(5, 1, hillyTaiga)),
      VRow(135, Mouth(506, HVUR), BendOut(514, HVUL), BendAll(520, HVDR)),
      TRow(134, sea, Cape(5, 1, mtain), land),
      VRow(133, Mouth(510, HVDR), Mouth(512, HVDn)),
      TRow(132, Cape(4, 3, hilly), sea, Cape(4, 4)),
      VRow(131, BendOut(508, HVDL), Mouth(518, HVDn)),
      TRow(130, Cape(4, 3), hilly, sea, land),
      VRow(129, Mouth(506, HVDR), Mouth(508, HVDn)),
      TRow(128, sea, Cape(1, 3), land, hilly),
      VRow(127, Mouth(508, HVUp), Mouth(510, HVUL)),
      TRow(126, sea, Cape(4, 1), land, hilly),
      VRow(125, Mouth(506, HVDL), Mouth(510, HVDR), Mouth(518, HVUp)),
      TRow(124, sea, Cape(0, 1, hilly), hilly, Cape(2, 2, hilly), Cape(4, 1, hilly)),
      VRow(123, Mouth(506, HVUR), Mouth(514, HVUL), BendOut(524, HVDL)),
      TRow(122, Cape(4, 2, hillySavannah), sahel, sea, Isle8(hillySavannah), Cape(4, 1, hillySavannah)),
      VRow(121, BendIn(506, HVDL), Mouth(524, HVDn)),
      TRow(120, sea, Cape(2, 3, hilly), Cape(5, 2, hillySavannah), Cape(0, 1, hillySavannah), Cape(0, 2, hillySavannah)),
      VRow(119, Mouth(522, HVDn)),
      TRow(118, sea, hillySahel, hillyDesert, desert, desert, sahel),
      VRow(117, SetSide(501), Mouth(504, HVUR)),
      TRow(116, Cape(5, 1, hillyDesert), desert * 5),
      VRow(115, SetSide(501)),
      TRow(114, desert * 4, hillyDesert, desert),
      TRow(112, desert * 6),
      TRow(110, desert * 6),
      TRow(108, savannah, sahel, savannah, sahel, desert * 2, sahel),
      TRow(106, savannah, savannah * 2, jungle, savannah * 3),
      TRow(104, jungle, hillyJungle * 4, savannah, hillySavannah),
      TRow(102, sea * 5, hillyJungle, jungle),
    )
  }
  help.run
}