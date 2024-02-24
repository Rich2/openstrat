/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** 640km [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile area of 709448.010km² .
 *  [[Isle3]] 8660.254km² => 16974.097km². (Crete 8450km²) + (Rhodes 1401km²) = 9851km²
 *  Below 8660.254km²   */
object Terr640E30 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e30(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      TRow(126, taiga * 2),
      VRow(125, BendOut(1534, HVDR), MouthOld(1536, HVUR)),
      TRow(124, level, taiga),
      VRow(123, MouthOld(1532, HVDR), BendOut(1532, HVUp), BendAllOld(1534, HVUL)),
      TRow(122, level, level),
      TRow(120, level * 3),
      TRow(118, level, Cape(2, 3), level),
      VRow(119, MouthOld(1536, HVUp), MouthOld(1540, HVUp), MouthOld(1544, HVUp, 3, Lake)),
      VRow(117, MouthRt(1534, HVUp), BendAllOld(1544, HVUR, Lake)),
      TRow(116, hilly, hilly, hilly),
      VRow(115, SetSep(1529), BendIn(1532, HVDR, 13), ThreeUp(1534, 0, 13, 6), BendIn(1536, HVDL, 13)),
      TRow(114, sea, mtain, hillySavannah * 2),
      VRow(113, MouthOld(1528, HVDn), BendIn(1532, HVUR, 13), BendIn(1534, HVUp, 13), ThreeUp(1536, 12, 8, 13), Mouth(1538, HVDR, 1, 7)),
      TRow(112, desert, level, desert * 2),
      VRow(111, MouthOld(1538, HVUL), MouthLt(1546, HVUL, 7)),
      TRow(110, desert * 2, Cape(1, 1, desert), desert),
      TRow(108, desert, desert, Cape(1, 1, desert), desert),
      VRow(107, Bend(1542, HVUR, 7, 3), Bend(1544, HVDL, 10, 1)),
      TRow(106, sahel * 3, savannah, hillyDesert),
      VRow(105, BendIn(1544, HVUR), Bend(1546, HVUp, 4, 6), Bend(1548, HVDn, 10, 4)),
      TRow(104, Land(Level, Savannah, Forest), savannah, savannah, hillySavannah, hillySahel),
      TRow(102, jungle * 2, Land(Level, Savannah, Forest), hillySavannah, sahel),
      VRow(101, MouthOld(1546, HVUp)),
      TRow(100, jungle * 2, hillyJungle, hillySavannah, hillySavannah),
      VRow(99, MouthMin(1536, HVUp, lake), BendOut(1544, HVDR), BendIn(1546, HVUL, 13)),
      TRow(98, hillyJungle * 2, savannah, hillySavannah),
      VRow(97, MouthMin(1536, HVDn, lake), BendOut(1544, HVUR, 7), BendIn(1546, HVDL, 13)),
      TRow(96, savannah, jungle * 2, hillySavannah * 2),
      VRow(95, BendIn(1546, HVUL, 13)),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(118, "Balkans", "Crimea")
    str(116, "Greece", "Turkey east")
    str(114, "", "Crete")
  }
}