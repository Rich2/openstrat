/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._

/** 1Mm terrain only scenario for Europe. 2 Grid system for 0°E and 30°E. */
object ScenMegaEurope extends EScenLongMulti
{ override val title: String = "1Mm Europe 0°E - 30°E"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(2, 0, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}

/** 1Mm terrain only scenario for China-Japan. 3 grid system from 90°E to 150°E. */
object ScenMegaChinaJapan extends EScenLongMulti
{ override val title: String = "1Mm China-Japan 90°E - 150°E"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(3, 3, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}

/** 1Mm terrain only scenario for North America. 4 Mm grid system for 150°W, 120°W, 90°W and 60°W */
object ScenMegaNorthAmerica extends EScenLongMulti
{ override val title: String = "1Mm North America 150°W - 60°W"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(4, 7, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}

/** 1Mm terrain only scenario for Africa, Middle East and Europe. 4 grid system for 30°W 0°E, 30°E and 60°E. */
object ScenMegaAfrica extends EScenLongMulti
{ override val title: String = "1Mm Africa 4 grids 30°W - 60°E"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(4, 11, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}

/** 1Mm terrain only scenario for India. 2 Grid system for 60°E and 90°E. */
object ScenMegaIndia extends EScenLongMulti
{ override val title: String = "1Mm India 60°E - 30°E"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(2, 2, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}

/** 1Mm terrain only scenario 4 Mega metre grid system for 90°W 60°W, 30°W and 0°E. */
object ScenMegaAtlantic extends EScenLongMulti
{ override val title: String = "1Mm 4 grids 90°W - 0°E"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(4, 9, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}

/** Just terrain for 3 Mega metre grid system for 150°E 180°E and 150°W. */
object ScenMegaDateLine extends EScenLongMulti
{ override val title: String = "1Mm Date Line 3 grids 150°E - 150°W"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(3, 5, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}

/** 1Mm terrain only scenario for all longitudes grid system. */
object ScenMegaAll extends EScenLongMulti
{ override val title: String = "1Mm all longitude terrain only scenario."
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(12, 0, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}