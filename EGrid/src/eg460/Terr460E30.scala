/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 460km. A hex tile area of 183250975km².
 *  Isle3 4473.900km² => 8768.845km². Crete 8450km². */
object Terr460E30 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e30(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      VRow(145, BendIn(1534, HVUp, 13), Bend(1536, HVDn, 7, 4, sea, SeaIceWinter), BendIn(1538, HVDL, 13, SeaIceWinter)),
      TRow(144, mtain),
      VRow(143, MouthLt(1538, HVDn, 7, SeaIceWinter), MouthRt(1540, HVDL, 7)),
      VRow(139, MouthRt(1538, HVUL, 7), ThreeDown(1540, 0, 6, 11, SeaIceWinter, SeaIceWinter, sea)),
      TRow(138, hillyTundra, hillyTundra),
      VRow(137, Mouth(1534, HVUp, 2, 4, SeaIceWinter), MouthRt(1538, HVDL, 7, SeaIceWinter), BendIn(1540, HVUL, 6, SeaIceWinter)),
      TRow(136, hillyTaiga, Land(LandLakes, Boreal, Forest), taiga),
      VRow(135, Bend(1532, HVDR, 4, 5, SeaIceWinter, sea), BendIn(1534, HVUL, 12, SeaIceWinter)),
      TRow(134, Land(LandLakes, Boreal, Forest), Land(LandLakes, Boreal, Forest), taiga),
      VRow(133, BendIn(1530, HVDR), ThreeUp(1532, 6, 6, 0), BendIn(1534, HVUp, 10, SeaIceWinter, sea), MouthRt(1536, HVUR, 6, SeaIceWinter)),
      TRow(132, land, land, taiga),
      VRow(131),
      TRow(130, land * 4),
      TRow(128, land * 2, hilly, land),
      TRow(126, land, hilly, savannah, land),
      VRow(125, SetSep(1527), MouthMax(1536, HVUL), ThreeDown(1538, 0, 13, 13), BendIn(1540, HVDn, 13), BendIn(1542, HVDL, 13)),
      TRow(124, mtain, hilly, hillySavannah, mtain, mtain),
      VRow(123, BendIn(1526, HVUR, 7), BendOut(1528, HVDL), MouthOld(1532, HVUp), MouthLt(1536, HVDL, 7), BendIn(1538, HVUL, 13), BendMax(1542, HVUR), MouthRt(1544, HVDR)),
      TRow(122, hillySavannah, Cape(4, 1, hillySavannah), hillySahel, mtain, mtain),
      VRow(121, Mouth(1528, HVDn, 3, 7), BendIn(1530, HVDR, 13), ThreeUp(1532, 6, 13, 0), BendIn(1534, HVDL, 13)),
      TRow(120, SepB(), sea, hillySavannah, hillySavannah, hillySavannah, desert),

      VRow(119, MouthRt(1528, HVDL, 7), ThreeUp(1530, 13, 11, 0), Bend(1532, HVUp, 13, 7), ThreeUp(1534, 13, 13, 13), Bend(1536, HVUp, 13, 7),
        BendIn(1538, HVDn, 13), Mouth(1540, HVDR, 7, 2)),

      TRow(118, sahel, sahel, sahel, sahel, savannah, desert),
      VRow(117, MouthOld(1534, HVDL, 3, Scarp), MouthOld(1536, HVUR, 3, Scarp), MouthOld(1538, HVUp), MouthRt(1542, HVUp), MouthOld(1550, HVUp)),
      TRow(116, desert * 2, sahel, desert, desert, desert),
      VRow(115, BendIn(1538, HVUR), ThreeDown(1540, 9, 5, 1), BendIn(1542, HVUL)),
      TRow(114, desert * 3, sahel, Cape(4, 1, hillySahel), desert),
      VRow(113, BendOut(1542, HVDL)),
      TRow(112, desert * 3, hillyDesert, Cape(4, 1, hillyDesert), desert),
      VRow(111, Bend(1544, HVDL, 5, 7)),
      TRow(110, desert, hillyDesert, desert * 2, hillySahel, hillySahel),
      VRow(109, BendIn(1544, HVUR, 13), BendOut(1546, HVDL, 7)),
      TRow(108, desert * 4, sahel, hillySahel, Cape(3, 2, hillySahel)),
      VRow(107, BendAllOld(1552, HVUp, sea, 7)),
      TRow(106, savannah * 2, sahel, savannah, mtain, hillySahel, Cape(0, 1, hillySahel)),
      TRow(104, savannah * 5, mtain, hillySahel),
      TRow(102, hillyJungle * 3, hillySavannah, hillySahel * 2, savannah),
      VRow(101, BendOut(1550, HVDR), MouthRt(1552, HVUR)),
      TRow(100, jungle, hillyJungle * 3, hillySavannah * 2, savannah),
      VRow(99, MouthOld(1538, HVUL, 3, lake), MouthOld(1540, HVDR, 3, lake), BendIn(1548, HVDR, 7), BendIn(1550, HVUL, 13)),
      TRow(98, hillyJungle * 3, hillySavannah * 3),
      VRow(97, MouthRt(1536, HVUL, 6, lake), BendIn(1538, HVDL, 6, lake), BendOut(1546, HVDR, 7), BendIn(1548, HVUL, 13)),
      TRow(96, hillyJungle * 4, hillySavannah * 2),
      VRow(95, MouthLt(1538, HVDn, 6, lake), MouthOld(1540, HVUp, 3, lake), BendOut(1546, HVUR), BendIn(1548, HVDL, 13)),
      TRow(94, hillyJungle * 6),
      VRow(93)
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(120, "Ionian Sea", "Crete")
  }
}