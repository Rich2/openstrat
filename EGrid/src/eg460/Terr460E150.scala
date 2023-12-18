/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 460km.  */
object Terr460E150 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e150(116, 124)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(128, tundra),
//      TRow(126, mtain, hillyTaiga),
      TRow(124, sea, Cape(1, 3, mtain)),
//      VRow(123, Mouth(5630, HVUL), Mouth(5634, HVUR)),
      TRow(122, Cape(5, 4, hilly)),
//      VRow(121, Mouth(5630, HVUp)),
      TRow(120, Cape(2, 2, hilly), sea * 4),

//      TRow(116, Cape(5, 4, hilly), sea * 2),
//      TRow(114, Cape(2, 2, hilly), sea * 3),
//      VRow(99, Mouth(5622, HVDn)),
//      TRow(98, hillyJungle),
    )
  }
  help.run
}