/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 1300km, a C scale of 375km. A hex tile area of 1.463582932 million km² A minimum Island area of
 *  Isle8 243930.488km² => 463086.787km², British Isles combined 315159 km²
 *  243930.488km², which excludes, Great Britain.
 *  Isle4 70034.730km² => 115771.696km², Iceland 101826km², Ireland 84421km². */
object Terr13E0 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e0(86)
//  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
//  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
//  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(115, BendIn(512, HVDR, 13), BendIn(514, HVDn, 13), BendOut(516, HVUp, 7)),
      TRow(114, hillyTundra),
      VRow(113, ThreeDown(510, 0, 13, 12), BendOut(512, HVUL)),
      TRow(112, hillyTaiga),
      VRow(111, ThreeUp(510, 5, 12, 12), BendIn(512, HVDL, 12)),
      TRow(110, land),
      VRow(109, ThreeDown(510, 12, 6, 0), MouthLt(514, HVUp)),
      TRow(108, hilly),
      VRow(107, BendIn(508, HVDR, 8), ThreeUp(510, 6, 10, 0), BendIn(512, HVUp, 10), BendIn(514, HVUL)),
      TRow(106, savannah, sahel),
      VRow(105, BendAllOld(508, HVUL)),
      TRow(104, desert * 2),
      VRow(103),
      TRow(102, savannah * 2),
      VRow(101, MouthOld(514, HVUp)),
      TRow(100, sea, jungle),
      VRow(99, MouthOld(514, HVDn)),
      TRow(98, sea * 2),
      VRow(97, MouthOld(516, HVUR)),
      TRow(96, sea, Cape(4, 2, desert)),
      VRow(95, MouthOld(516, HVDR)),
      VRow(87, BendOut(512, HVUp, 6, wice), BendOut(516, HVUp, 6, wice)),
      TRow(86, Cape(0, 1, ice, wice))
    )
  }
  help.run

  hexNames.setRow(110, "Middle Western Europe")
}