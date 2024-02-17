/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pind
import prid._, phex._, egrid._, eg220._

trait NapScen extends EScenBasic with HSysTurnScen
{
  override def title: String = "AD1783"
  val corps: LayerHcOptSys[Corps]
}

object NapScen1 extends NapScen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid220LongFull = Terr220E0.grid
  override val terrs: LayerHcRefSys[WTile] = Terr220E0.terrs
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = Terr220E0.sTerrs
  override val corners: HCornerLayer = Terr220E0.corners
  override def names: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
  override val corps: LayerHcOptSys[Corps] = LayerHcOptSys()
  corps.setSomeMut(160, 512, Corps(Britain))
  corps.setSomeMut(156, 516, Corps(France))
}

object NapScen2 extends NapScen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid220LongFull = Terr220E0.grid
  override val terrs: LayerHcRefSys[WTile] = Terr220E0.terrs
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = Terr220E0.sTerrs
  override val corners: HCornerLayer = Terr220E0.corners
  override def names: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
  override val corps: LayerHcOptSys[Corps] = LayerHcOptSys()
}
