/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, egrid._, WTiles._

/** A second level area of the Earth. */
abstract class EArea2(val name: String, val cen: LatLong, val terr: WTile) extends GeographicSymbolKey with Coloured
{ override def toString = name.appendCommas(terr.toString)
  def aStrs: StrArr = StrArr(name)
  def textScale: Length = 15000.metres
  override def colour = terr.colour

  /** A quasi polygon on the earths surface defined in [[LatLong]]s. */
  def polygonLL: PolygonLL

  def places: LocationLLArr = LocationLLArr()

  /** Returns a pair of this [[EArea2]] and the [[PolygonM2]] from the given focus and orientation. The polygonM only has points form the side of the
   *  earth that faces the focus.  */
  def withPolygonM2(focus: LatLongDirn): (EArea2, PolygonM2) =
  { val p3s0: PolygonM3 = polygonLL.toMetres3
    val p3s1: PolygonM3 = p3s0.fromLatLongFocus(focus)
    val p3s3: PolygonM2 = p3s1.earthZPosXYModify
    val p3s4 = p3s3.rotate180IfNot(focus.dirn)
    (this, p3s4)
  }
}

/** Companion object for the [[EArea2]] class. Contains 2 factory apply methods. */
object EArea2
{
  def apply(symName: String, cen: LatLong, terr: WTile, latLongArgs: LatLong*) = new EArea2(symName, cen, terr)
  { val polygonLL = PolygonLL(latLongArgs*)
  }

  def apply(symName: String, cen: LatLong, terr: WTile, polygonIn: PolygonLL) = new EArea2(symName, cen, terr)
  { val polygonLL = polygonIn
  }
}

abstract class Area2Island(name: String, cen: LatLong, terr: WTile, area: Area) extends EArea2(name, cen, terr)