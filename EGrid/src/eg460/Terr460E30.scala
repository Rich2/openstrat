/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 460km. A hex tile area of 709448.010km² . A minimum island area of
 *  118241.335km² */
object Terr460E30 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e30(116)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(138,hillyTundra, Cape(1, 2, hillyTundra)),
      VRow(137, Mouth(1534, HVUp), Mouth(1538, HVDL)),
      TRow(136, Cape(2, 1,hillyTaiga), taiga, taiga),
      TRow(134, Cape(3, 2, taiga), taiga, taiga),
      TRow(132, Cape(4, 3), land, taiga),
      TRow(130, land * 4),
      TRow(128, land * 2, hilly, land),
      TRow(126, land, hilly, savannah, land),
      VRow(125, Mouth(1542, HVUp)),
      TRow(124, Cape(4, 1, mtain), hilly, sea * 2, Cape(4, 1, mtain)),
      VRow(123, BendOut(1528, HVDL), Mouth(1532, HVUp)),
      TRow(122, Cape(4, 1, hillySavannah), Cape(4, 1, hillySavannah), hillySahel, mtain, mtain),
      TRow(120, SideB(), sea * 2, Cape(3, 2, hillySavannah), hillySavannah, desert),
      VRow(119, Mouth(1526,HVDn),  Mouth(1528, HVDL), BendOut(1532, HVUp), BendOut(1536, HVUp), BendOut(1540, HVDL)),
      TRow(118, sahel, Cape(0, 1, sahel), Cape(0, 1, sahel), Cape(0, 2, sahel), savannah, desert),
//      VRow(119, Mouth(1536, HVUp), Mouth(1540, HVUp), Mouth(1544, HVUp, Lake)),
      VRow(117, Mouth(1534, HVDL, Scarp), Mouth(1536, HVUR, Scarp), Mouth(1538, HVUp), Mouth(1540, HVDn), Mouth(1542, HVUp)),
      TRow(116, desert * 2, sahel, Cape(2, 3, desert), desert, desert),
//      VRow(115, SetSide(1529), Mouth(1536, HVUp)),
//      TRow(114, sea, sea, Cape(4, 1, hilly), hilly),
//      VRow(113, Mouth(1528, HVDn), Mouth(1534, HVDL), Mouth(1538, HVDR)),
//      TRow(112, desert, Cape(0, 1), desert * 2),
//      VRow(111, Mouth(1538, HVUL), Mouth(1546, HVUL)),
//      TRow(110, desert * 2, Cape(1, 1, desert), Cape(4, 1, desert)),
//      TRow(108, desert, desert, Cape(1, 1, desert), Cape(4, 1, desert)),
//      TRow(106, sahel * 3, Cape(1, 1, savannah), Cape(3, 2, hillyDesert)),
//      TRow(104, Land(Level, Savannah, Forest), savannah, savannah, hillySavannah, hillySahel),
//      TRow(102, jungle * 2, Land(Level, Savannah, Forest), hillySavannah, sahel),
//      VRow(101, Mouth(1546, HVUp)),
//      TRow(100, jungle * 2, hillyJungle, hillySavannah, Cape(2, 1, hillySavannah)),
//      VRow(99, Mouth(1536, HVUp, lake), BendOut(1544, HVDR)),
//      TRow(98, hillyJungle * 2, savannah, hillySavannah),
//      VRow(97, Mouth(1536, HVDn, lake)),
//      TRow(96, savannah, jungle * 2, hillySavannah),
    )
  }
  help.run
}