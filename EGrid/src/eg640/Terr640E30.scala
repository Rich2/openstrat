/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 640km. A hex tile area of 709448.010km² . A minimum island area of
 *  118241.335km² */
object Terr640E30 extends Long640Terrs {
  override implicit val grid: EGrid640LongFull = EGrid640.e30(96)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners) {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, Cape(5, 3, tundra)),
      TRow(126, taiga * 2),
      VRow(125, BendOut(1534, HVDR), MouthOld(1536, HVUR)),
      TRow(124, land, taiga),
      VRow(123, MouthOld(1532, HVDR), BendOut(1532, HVUp), BendAllOld(1534, HVUL)),
      TRow(122, land, land),
      TRow(120, land * 3),
      TRow(118, land, Cape(2, 3), land),
      VRow(119, MouthOld(1536, HVUp), MouthOld(1540, HVUp), MouthOld(1544, HVUp, 3, Lake)),
      VRow(117, BendAllOld(1544, HVUR, Lake)),
      TRow(116, hilly, hilly, hilly),
      VRow(115, SetSep(1529), MouthOld(1536, HVUp)),
      TRow(114, sea, sea, Cape(4, 1, hilly), hilly),
      VRow(113, MouthOld(1528, HVDn), MouthOld(1534, HVDL), MouthOld(1538, HVDR)),
      TRow(112, desert, Cape(0, 1), desert * 2),
      VRow(111, MouthOld(1538, HVUL), MouthOld(1546, HVUL)),
      TRow(110, desert * 2, Cape(1, 1, desert), Cape(4, 1, desert)),
      TRow(108, desert, desert, Cape(1, 1, desert), Cape(4, 1, desert)),
      TRow(106, sahel * 3, Cape(1, 1, savannah), Cape(3, 2, hillyDesert)),
      TRow(104, Land(Level, Savannah, Forest), savannah, savannah, hillySavannah, hillySahel),
      TRow(102, jungle * 2, Land(Level, Savannah, Forest), hillySavannah, sahel),
      VRow(101, MouthOld(1546, HVUp)),
      TRow(100, jungle * 2, hillyJungle, hillySavannah, Cape(2, 1, hillySavannah)),
      VRow(99, MouthOld(1536, HVUp, 3, lake), BendOut(1544, HVDR)),
      TRow(98, hillyJungle * 2, savannah, hillySavannah),
      VRow(97, MouthOld(1536, HVDn, 3, lake)),
      TRow(96, savannah, jungle * 2, hillySavannah),
    )
  }
  help.run
}