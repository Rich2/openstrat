/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** 640km [[WTile]] terrain terrain for 45° west to 15° west, centred on 30° wast. Hex tile area of 354724.005km²
 * [[Isle3]] 8660.254km² => 16974.097km².
 * Canaries 7,492 km². */
object Terr640W30 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w30(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(131, MouthRt(11780, HVUp)),
      TRow(130, ice),
      VRow(129, BendOut(11778, HVDR), BendIn(11780, HVUL, 13)),
      TRow(128, ice),
      VRow(127, Bend(11776, HVDR, 6, 4), BendIn(11778, HVUL, 9)),
      TRow(126, ice, hillyTundra),
      VRow(125, BendOut(11774, HVDR), ThreeUp(11776, 11, 0, 13), BendIn(11778, HVUp, 8)),
      VRow(123, BendIn(11772, HVUp, 13), BendIn(11774, HVUL, 9)),
      VRow(109, MouthLt(11782, HVUp, 7)),
      TRow(108, sea * 3, desert),
      VRow(107, BendIn(11782, HVUR, 13), BendMin(11784, HVDL)),
      TRow(106, sea * 4, sahel),
     // VRow(105, MouthOld(11784, HVDn)),
      VRow(101, MouthOld(11766, HVDR), MouthLt(11788, HVUL, 7)),
      VRow(99, MouthOld(11770, HVUL)),
      TRow(98, Cape(1, 1, hillySahel)),
      TRow(96, hillySavannah, Cape(2, 1, hillySavannah)),
    )
  }
  help.run
}