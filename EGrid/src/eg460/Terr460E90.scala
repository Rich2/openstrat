/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 460km.  */
object Terr460E90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e90(114, 130)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(130, tundra),
//      TRow(128, hillyTundra),
//      TRow(126, taiga, Land(Mountains, Taiga, LandFree)),
//      TRow(124, taiga * 2),
      TRow(122, desert * 2, hillyDesert, hillySahel, desert),
      TRow(120, mtain, hillyDesert * 2, mtain * 2),
      TRow(118, mtain, hillySahel * 2, mtain * 3),
      TRow(116, desert, desert, hillyDesert),
      TRow(114, hilly, land, hillySavannah, savannah, hillyJungle, mtain),
//      TRow(112, mtain, Land(Mountains, Desert, LandFree), mtain, hilly),
//      TRow(110, savannah, savannah, Land(Hilly, Savannah, Forest), Land(Mountains, Tropical, Forest)),
//      TRow(108, hillySavannah, sea, Land(Hilly, Tropical), Land(Hilly, Tropical, Forest)),
//      TRow(106, Cape(2, 1, savannah), sea * 2, hillyJungle, hillyJungle),
//      VRow(105, Mouth(3592, HVUL)),
//      TRow(104, Cape(2, 3, savannah), sea * 3, Cape(1, 2, hillyJungle)),
//      TRow(102, sea * 3, Cape(3, 2, hillyJungle)),
//      TRow(100, sea * 4, Cape(0, 2, hillyJungle)),
//      TRow(98, sea * 4, Cape(1, 4, jungle))
    )
  }
  help.run
}