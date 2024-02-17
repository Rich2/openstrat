/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.  */
object Terr640E180 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e180(96)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, Cape(0, 2, tundra)),
      VRow(127, BendOut(6658, HVUR)),
      TRow(126, hillyTundra, Cape(1, 3, tundra)),
      VRow(125, MouthOld(6656, HVUL)),
      TRow(124, sea, Cape(3, 3, hillyTundra)),
      TRow(122, SepB()),
    )
  }
  help.run
}