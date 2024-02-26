/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15 West to 15 East.
 * Isle6 172942.905km² => 243930.488km². (Cuba 105806km²) + (Hispaniola 76479km²) + (Jamaica	11188km²) = 193473km².  */
object Terr13W60 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w60(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, ice),
      TRow(112, hillyTaiga),
      VRow(111, MouthLt(10750, HVDn, 7), BendOut(10754, HVUR, 7), ThreeDown(10756, 12, 12, 13)),
      TRow(110, hillyTempForest),
      VRow(109, MouthLt(10752, HVUL), ThreeDown(10754, 13, 0, 13), ThreeUp(10756, 12, 0, 13)),
      TRow(108, hillyTemp),
      VRow(107, MouthLt(10750, HVUL), BendIn(10752, HVUp, 13), BendIn(10754, HVUL, 13)),
      TRow(106, sea * 2),
      VRow(105, BendIn(10748, HVDn, 10), BendIn(10750, HVDL, 10)),
      VRow(103, Bend(10748, HVUp, 13, 4), ThreeUp(10750, 0, 13, 8), MouthLt(10752, HVDR, 7)),
      TRow(102, hillyTemp, sea),
      VRow(101, MouthRt(10756, HVUL), BendIn(10758, HVDL, 13)),
      TRow(100, jungle, jungle),
      VRow(99, BendIn(10758, HVUR)),
      TRow(98, hillyTemp, temperate),
      //VRow(97, BendOut(10758, HVDR)),
      TRow(96, hillyTemp, temperate),
      TRow(94, mtainOld, temperate),
      VRow(93, BendOut(10754, HVDR, 7), BendIn(10756, HVUL, 12)),
      TRow(92, savannah),
      VRow(91, MouthRt(10750, HVUR), MouthRt(10752, HVDL), BendIn(10754, HVUL, 13)),
      VRow(89, MouthRt(10750, HVDR, 7)),
      VRow(87, SetSep(10750), MouthOld(10756, HVDL, 3, wice)),
      TRow(86, wice),
    )
  }
  help.run
}