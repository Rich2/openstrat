/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° East to 75°, centred on 60° east. Hex tile scale 1300km. */
object Terr13E60 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e60(86)
  override val terrs: LayerHcSys[WTile] = LayerHcSys[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      TRow(112, taiga),
      TRow(110, desert),
      VRow(109, Mouth(2558, HVUp)),
      TRow(108, SideB(Lake), desert),
      VRow(107, Mouth(2558, HVDn, Lake), Mouth(2556, HVDR)),
      TRow(106, desert, hilly),
      VRow(105, Mouth(2556, HVUL), BendAll(2558, HVUp), BendAll(2560, HVDn)),
      TRow(104, Cape(1, 3, hillyDesert), Cape(4, 1)),
      VRow(103, BendAll(2556, HVUp), BendAll(2564, HVDL)),
      TRow(102, Cape(0, 3, desert), sea),
      VRow(101, BendOut(2558, HVDR)),
      TRow(100, SideB(), sea * 2),
      VRow(99, SetSide(2557)),
      TRow(98, SideB(), sea * 2),
      VRow(97, SetSide(2557)),
      TRow(96, SideB(), sea * 2),
      VRow(95, SetSide(2557)),
      VRow(87,Mouth(2558, HVUR), Mouth(2560, HVDL, wice), Mouth(2564, HVDR, wice)),
      TRow(86, Cape(0, 1, ice, wice))
    )
  }
  help.run
}