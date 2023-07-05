/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pEarth._, pEurope._

abstract class DeuNumberedKorps(startDate: MTime, endDate: MTime, val korpsNum: Int) extends Lunit(startDate, endDate)
{ override def desig: String = korpsNum.adjective
  override def levelName: String = "Korps"
  override val polity: MTimeSeries[Polity] = MTimeSeries(Germany)
}

/** 1st German corps of the 3rd Reich. */
object DeuCp1 extends DeuNumberedKorps(MTime(1934, 10), MTime(1945, 5, 8), 1)
{ override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Baltland.konigsberg, (MTime(1939, 8, 1), Polandia.neidenburg))
}

/** 2nd German corps of the 3rd Reich. */
object DeuCp2 extends DeuNumberedKorps(MTime(1935, 4), MTime(1945, 5, 8), 2)
{ override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Baltland.konigsberg, (MTime(1939, 8, 1), Polandia.schlochau))
}

/** 3rd German corps of the 3rd Reich. */
object DeuCp3 extends DeuNumberedKorps(MTime(1935, 4), MTime(1945, 5, 8), 3)
{ override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Polandia.stettin)
}

/** 4th German corps of the 3rd Reich. */
object DeuCp4 extends DeuNumberedKorps(MTime(1935, 4), MTime(1945, 5, 8), 4)
{ override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Germania.dresden.latLong, (MTime(1939, 10), Baltland.konigsberg))
}
