/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 460km.  */
object Terr460E180 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e180(104)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      VRow(139, Mouth(6656, HVDL)),
      TRow(138, mtain, Cape(0, 3, mtain)),
      VRow(137, BendIn(6658, HVDR)),
      TRow(136, mtain, Cape(2, 1, hillyTundra)),
      VRow(135, BendOut(6656, HVDR)),
      TRow(134, Cape(2, 2, mtain), sea, Cape(4, 2, hillyTundra)),
      VRow(133, BendOut(6650, HVDR), BendAll(6652, HVDn), BendOut(6662, HVDL)),
      VRow(131, SetSide(6649)),
      TRow(130, SideB()),
    )
  }
  help.run
}