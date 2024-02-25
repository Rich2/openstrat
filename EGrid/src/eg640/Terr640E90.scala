/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** 640km [[WTile]] terrain terrain for 75° east to 105° east, centred on 90° east. A hex tile area of 354724.005km².
 *  [[Isle7]] 58543.317km² => 77942.286km². Sri Lanka 65610km². */
object Terr640E90 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e90(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(130, tundra),
      TRow(128, hillyTundra),
      TRow(126, taiga, Land(Mountains, Boreal, LandFree)),
      TRow(124, taiga * 2),
      TRow(122, forest, mtainOld),
      TRow(120, savannah, sahel, sahel),
      TRow(118, sahel, hillyDesert, hillyDesert),
      TRow(116, desert, desert, hillyDesert),
      TRow(114, Land(Mountains, Desert, LandFree), hillyDesert, hillyDesert, mtainOld),
      TRow(112, mtainOld, Land(Mountains, Desert, LandFree), mtainOld, hilly),
      TRow(110, savannah, savannah, Land(Hilly, Savannah, Forest), Land(Mountains, Tropical, Forest)),
      TRow(108, hillySavannah, sea, Land(Hilly, Tropical), Land(Hilly, Tropical, Forest)),
      TRow(106, CapeOld(2, 1, savannah), sea * 2, hillyJungle, hillyJungle),
      VRow(105, MouthOld(3592, HVUL)),
      TRow(104, CapeOld(2, 3, savannah), sea * 3, CapeOld(1, 2, hillyJungle)),
      TRow(102, sea * 3, CapeOld(3, 2, hillyJungle)),
      TRow(100, sea * 4, CapeOld(0, 2, hillyJungle)),
      TRow(98, sea * 4, CapeOld(1, 4, jungle))
    )
  }
  help.run
}