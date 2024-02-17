/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° East to 75°, centred on 60° east. Hex tile scale 1300km. */
object Terr13E60 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e60(86)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      TRow(112, taiga),
      TRow(110, desert),
      VRow(109, Mouth(2558, HVUp)),
      TRow(108, SepB(Lake), desert),
      VRow(107, Mouth(2558, HVDn, 3, Lake), Mouth(2556, HVDR)),
      TRow(106, desert, hilly),
      VRow(105, MouthLt(2556, HVUL), BendIn(2558, HVUp), BendIn(2560, HVDn), BendIn(2562, HVDL, 13)),
      TRow(104, hillyDesert, land),
      VRow(103, BendOut(2556, HVUp), BendIn(2558, HVDn, 12), ThreeDown(2560, 13, 0, 13), ThreeUp(2562, 13, 0, 13), BendAll(2564, HVDL)),
      TRow(102, desert),
      VRow(101, BendOut(2558, HVDR), BendIn(2560, HVUL, 13)),
      VRow(99, BendOut(2556, HVDR, 7), BendIn(2558, HVUL, 13)),
      VRow(97, ThreeUp(2556, 0, 6, 6), BendIn(2558, HVDL)),
      TRow(96, sea * 2),
      VRow(95, BendIn(2556, HVUp), BendIn(2558, HVUL)),
      VRow(87,Mouth(2558, HVUR), Mouth(2560, HVDL, 3, wice), Mouth(2564, HVDR, 3, wice)),
      TRow(86, Cape(0, 1, ice, wice))
    )
  }
  help.run
}