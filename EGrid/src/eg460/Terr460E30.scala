/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 460km. A hex tile area of 183250975km².
 *  Isle3 4473.900km² => 8768.845km², includes Crete. */
object Terr460E30 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e30(110)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      VRow(139, Mouth(1536, HVDL)),
      TRow(138, hillyTundra, Cape(0, 3, hillyTundra)),
      VRow(137, Mouth(1534, HVUp), Mouth(1538, HVDL)),
      TRow(136, Cape(2, 1,hillyTaiga), Land(WetLand, Taiga, Forest), taiga),
      VRow(135, BendAll(1532, HVDR)),
      TRow(134, Cape(3, 2, WetLand, Taiga, Forest, Sea), Land(WetLand, Taiga, Forest), taiga),
      VRow(133, Mouth(1536, HVUR)),
      TRow(132, Cape(5, 2), land, taiga),
      VRow(131, Mouth(1530, HVDn)),
      TRow(130, land * 4),
      TRow(128, land * 2, hilly, land),
      TRow(126, land, hilly, savannah, land),
      VRow(125, Mouth(1542, HVUp)),
      TRow(124, Cape(4, 1, mtain), hilly, sea * 2, Cape(4, 1, mtain)),
      VRow(123, BendOut(1528, HVDL), Mouth(1532, HVUp)),
      TRow(122, hillySavannah, Cape(4, 1, hillySavannah), hillySahel, mtain, mtain),
      VRow(121, Mouth(1528, HVDn)),
      TRow(120, SideB(), sea, Isle3(hillySavannah), Cape(3, 2, hillySavannah), hillySavannah, desert),
      VRow(119, Mouth(1526,HVDn),  Mouth(1528, HVDL), BendOut(1532, HVUp), BendOut(1536, HVUp), BendOut(1540, HVDL)),
      TRow(118, sahel, Cape(0, 1, sahel), Cape(0, 1, sahel), Cape(0, 2, sahel), savannah, desert),
      VRow(117, Mouth(1534, HVDL, Scarp), Mouth(1536, HVUR, Scarp), Mouth(1538, HVUp), Mouth(1540, HVDn), Mouth(1542, HVUp), Mouth(1550, HVUp)),
      TRow(116, desert * 2, sahel, desert, desert, desert),
      VRow(115, BendIn(1538, HVUR), ThreeWay(1540), BendIn(1542, HVUL)),
      TRow(114, desert * 3, sahel, Cape(4, 1, hillySahel), desert),
      VRow(113, BendOut(1542, HVDL)),
      TRow(112, desert * 3, hillyDesert, Cape(4, 1, hillyDesert), desert),
//      VRow(111, Mouth(1538, HVUL), Mouth(1546, HVUL)),
//      TRow(110, desert * 2),
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