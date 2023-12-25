/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 165° west to 135° west, centred on 150° wast. Hex tile scale 460km.  */
object Terr460W150 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w150(112)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(138, SideB(), hillyTundra, mtain),
      TRow(136, hillyTaiga * 2, mtain),
      TRow(134, hillyTaiga, Cape(3, 1, mtain), mtain),
      VRow(133, BendOut(7674, HVDL), BendAll(7678, HVDR), BendOut(7680, HVDn), Mouth(7684, HVUR)),
      TRow(132, Cape(2, 3, hillyTundra)),
      VRow(129, BendOut(7690, HVDL)),
    )
  }
  help.run
}