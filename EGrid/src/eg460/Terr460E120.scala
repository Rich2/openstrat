/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** 460km [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. A hex tile area of 183250.975km².
 *  Isle 120974.276km² down to 57981.753km²
 *  Isle8 35075.382 km² => 57981.753km², includes Taiwan,
 *  Isle4 14495.438km² <= 8768.845km². Palawan 12189 km2.*/
object Terr460E120 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e120(106)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(140, tundra, Cape(0, 1, tundra)),
      TRow(138, hillyTaiga * 2),
      TRow(136, hillyTaiga, taiga, mtain),
      TRow(134, taiga, hillyTaiga * 2),
      TRow(132, mtain * 3),
      TRow(130, mtain * 4),
      TRow(128, mtain * 4),
      TRow(126, desert, sahel, savannah, mtain),
      TRow(124, desert * 2, sahel, hillyForest, mtain),
      VRow(123, Mouth(4608, HVUp), BendOut(4616, HVDR), Mouth(4618, HVUR)),
      TRow(122, mtain, land, Cape(3, 2, hilly), mtain),
      VRow(121, Mouth(4612, HVUR), ThreeWay(4616), BendOut(4618, HVUp), BendOut(4620, HVUL)),
      TRow(120, hillySavannah, land, Cape(2, 1, hilly), Cape(4, 2, hilly), Cape(4, 3, hilly)),
      VRow(119, Mouth(4608, HVDL), Mouth(4616, HVDR)),
      TRow(118, mtain, land * 2, Cape(0, 3), sea, Cape(3, 1, hilly)),
      VRow(117, Mouth(4616, HVUL), Mouth(4620, HVUR)),
      TRow(116, mtain, hilly, mtain),
      TRow(114, mtain * 2, Cape(2, 1, mtain), Isle8(mtain)),
      TRow(112, hillyJungle),
      TRow(110, Cape(2, 1, mtain), sea * 2, Cape(0, 3, hillyJungle)),
      TRow(108, hillyJungle, sea * 2, Cape(4, 2, hillyJungle)),
      TRow(106, sea * 2, Isle4(mtain)),
    )
  }
  help.run
}