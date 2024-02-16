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
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, hillyTundra),
      VRow(113, ThreeDown(510, 0, 13, 12), BendOut(512, HVUL)),
      TRow(112, hillyTaiga),
      VRow(111),
      TRow(110, land),
      VRow(109, Mouth(512, HVUR)),
      TRow(108, Cape(3, 3, hilly)),
      TRow(106, Cape(5, 2, savannah), Cape(0, 1, sahel)),
      VRow(105, BendAll(508, HVUL)),
      TRow(104, desert * 2),
      VRow(103, Mouth(508, HVDR)),
      TRow(102, savannah * 2),
      VRow(101, Mouth(514, HVUp)),
      TRow(100, sea, jungle),
      VRow(99, Mouth(514, HVDn)),
      TRow(98, sea * 2),
      VRow(97, Mouth(516, HVUR)),
      TRow(96, sea, Cape(4, 2, desert)),
      VRow(95, Mouth(516, HVDR)),
      VRow(87, BendOut(512, HVUp, 6, wice), BendOut(516, HVUp, 6, wice)),
      TRow(86, Cape(0, 1, ice, wice))
    )
  }
  help.run
}