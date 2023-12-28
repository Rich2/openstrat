/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 460km.  */
object Terr460E90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e90(106)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(142, SideB(), hillyTundra),
      VRow(143, SetSide(3583)),
      TRow(140, tundra, hillyTundra),
      TRow(138, taiga, mtain),
      TRow(136, taiga, hillyTaiga, mtain),
      TRow(134, taiga, mtain, taiga),
      TRow(132, taiga * 2, hillyTaiga),
      TRow(130, savannah * 2, mtain, hillySavannah),
      TRow(128, mtain * 4),
      TRow(126, sahel * 2, mtain, hillyDesert),
      TRow(124, hillySahel, mtain, hillySahel * 3),
      TRow(122, desert * 2, hillyDesert, hillySahel, desert),
      TRow(120, mtain, hillyDesert * 2, mtain * 2),
      TRow(118, mtain, hillySahel * 2, mtain * 3),
      TRow(116, desert, desert, hillyDesert, mtain * 3),
      TRow(114, hilly, land, hillySavannah, savannah, hillyJungle, mtain),
      TRow(112, hillySavannah * 2, Cape(3, 1, Land(Level, Tropical)), hillySavannah, mtain * 2),
      TRow(110, savannah, hillySavannah, sea * 2, hillyJungle, mtain),
      TRow(108, sahel, sea * 4, Cape(4, 1, mtain), hillySavannah),
    )
  }
  help.run
}