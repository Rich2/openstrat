/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** 1300 km [[WTile]] terrain for 15° west to 15° east, centred on 0° east. A hex tile area of 1.463582932 million km²
 *  Isle7 241548.355km² => 321588.046km², British Isles combined 315159km²
 *  Isle4 70034.730km² => 115771.696km², Iceland 101826km², Ireland 84421km². */
object Terr13E0 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e0(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(115, BendIn(512, HVDR, 13), BendIn(514, HVDn, 13), BendOut(516, HVUp, 7)),
      TRow(114, hillyTundra),
      VRow(113, ThreeDown(510, 0, 13, 12), BendOut(512, HVUL)),
      TRow(112, hillyTaiga),
      VRow(111, ThreeUp(510, 5, 12, 12), BendIn(512, HVDL, 12)),
      TRow(110, oceanic),
      VRow(109, ThreeDown(510, 12, 6, 0), MouthLt(514, HVUp)),
      TRow(108, hillyOce),
      VRow(107, BendIn(508, HVDR, 8), ThreeUp(510, 6, 10, 0), BendIn(512, HVUp, 10), BendIn(514, HVUL)),
      TRow(106, savannah, sahel),
      VRow(105, Bend(508, HVUL, 4, 2)),
      TRow(104, desert * 2),
      TRow(102, savannah * 2),
      VRow(101, SourceLt(514, HVDn)),
      TRow(100, sea, jungle),
      VRow(99, BendIn(512, HVDR, 13), BendOut(514, HVUL, 7)),
      TRow(98, sea, hillyJungle),
      VRow(97, BendIn(512, HVUR, 13), BendOut(514, HVDL, 7)),
      TRow(96, sea, sahel),
      VRow(95, BendIn(514, HVUR, 13), BendIn(516, HVDL)),
      TRow(88, siceWin),
      VRow(87, BendMax(510, HVDn, siceWin), BendOut(512, HVUp, 6, siceWin), BendIn(514, HVDn, 12, siceWin), BendMin(516, HVUp, siceWin)),
      TRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(114, "Barents lands")
    str(112, "Europe north east")
    str(110, "Europe Middle Western")
    str(108, "Europe south west")
    str(106, "Morocco", "Algeria")
  }
}