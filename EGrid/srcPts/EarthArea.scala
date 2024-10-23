/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom.*, pglobe.*, egrid.*, WTiles.*, collection.mutable.ArrayBuffer

/** A second level area of the Earth. */
abstract class EarthArea(val name: String, val cen: LatLong, val terr: WTile) extends GeographicSymbolKey with Coloured
{ override def toString = name.oneLine + ", " + terr.strComma
  def aStrs: StrArr = StrArr(name)
  def textScale: Metres = 15000.metres
  override def colour = terr.colour

  /** A quasi polygon on the earths surface defined in [[LatLong]]s. */
  def polygonLL: PolygonLL

  def places: LocationLLArr = LocationLLArr()

  /** Returns a pair of this [[EarthArea]] and the [[PolygonM2]] from the given focus and orientation. The polygonM only has points form the side of the
   *  earth that faces the focus.  */
  def withPolygonM2(focus: LatLongDirn): (EarthArea, PolygonM2) =
  { val p3s0: PolygonM3 = polygonLL.toMetres3
    val p3s1: PolygonM3 = p3s0.fromLatLongFocus(focus)
    val p3s3: PolygonM2 = p3s1.earthZPosXYModify
    val p3s4 = p3s3.rotate180IfNot(focus.dirn.northUp)
    (this, p3s4)
  }
}

/** Companion object for the [[EarthArea]] class. Contains 2 factory apply methods. */
object EarthArea
{
  def apply(symName: String, cen: LatLong, terr: WTile, latLongArgs: LatLong*) = new EarthArea(symName, cen, terr)
  { val polygonLL = PolygonLL(latLongArgs*)
  }

  def apply(symName: String, cen: LatLong, terr: WTile, polygonIn: PolygonLL) = new EarthArea(symName, cen, terr)
  { val polygonLL = polygonIn
  }
}

trait EarthIslandLike
{ 
  def name: String
  
  /** The area of this island or island grouping. */
  def area: KilometresSq

  def oGroup: Option[EarthIslandGroup] = None

  def groupings: RArr[EarthIslandGroup] =
  { val acc: ArrayBuffer[EarthIslandGroup] = new ArrayBuffer[EarthIslandGroup]()
    def loop(inp: Option[EarthIslandGroup]): Unit = inp match
    { case Some(eig) => { acc.append(eig); loop(eig.oGroup) }
      case None =>
    }
    loop(oGroup)
    new RArr(acc.toArray)
  }
}

abstract class EarthAreaIsland(name: String, cen: LatLong, terr: WTile) extends EarthArea(name, cen, terr), EarthIslandLike
{ override def toString = name.oneLine + (area.str0 + ", " + terr.strComma).enParenth

  def strWithGroups: String =
  {
    val groupStrs: StrArr = groupings.map(g => g.name -- g.area.str0)
    if (groupStrs.length > 0) deb(groupStrs(0))
    val inner = StrArr(area.str0, terr.strComma) ++ groupStrs
    val inner2 = inner.mkStr("; ")
    name + inner2.enParenth
  }
}

class EarthIslandGroup(val name: String, val array: Array[EarthIslandLike]) extends EarthIslandLike
{
  def this(name: String, elems: EarthIslandLike*) = this(name, elems.toArray)

  override def area: KilometresSq = array.sumBy(_.area)
  def elements: RArr[EarthIslandLike] = new RArr[EarthIslandLike](array)

  override def toString: String = name
}

object EarthIslandGroup
{
  /** Factory apply method for creating [[EarthIslandGroup]] with repeat parameters. */
  def apply(name: String, elems: EarthIslandLike*): EarthIslandGroup = new EarthIslandGroup(name, elems.toArray)
}