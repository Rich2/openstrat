/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom.*, pglobe.*, egrid.*, WTiles.*, collection.mutable.ArrayBuffer

/** A second level area of the Earth. */
abstract class EarthPoly(val name: String, val cen: LatLong, val terr: WTile) extends GeographicSymbolKey with Coloured
{ override def toString = name.oneLine + ", " + terr.strComma
  def aStrs: StrArr = StrArr(name)
  def textScale: Metres = 15000.metres
  override def colour = terr.colour

  /** A quasi polygon on the earths surface defined in [[LatLong]]s. */
  def polygonLL: PolygonLL

  def places: LocationLLArr = LocationLLArr()

  /** Returns a pair of this [[EarthPoly]] and the [[PolygonM2]] from the given focus and orientation. The polygonM only has points form the side of the earth
   * that faces the focus. */
  def withPolygonM2(focus: LatLongDirn): (EarthPoly, PolygonM2) =
  { val p3s0: PolygonM3 = polygonLL.toMetres3
    val p3s1: PolygonM3 = p3s0.fromLatLongFocus(focus)
    val p3s3: PolygonM2 = p3s1.earthZPosXYModify
    val p3s4 = p3s3.rotate180IfNot(focus.dirn.northUp)
    (this, p3s4)
  }

  def isLake: Boolean = this.isInstanceOf[LakePoly]
}

/** Companion object for the [[EarthPoly]] class. Contains 2 factory apply methods. */
object EarthPoly
{
  def apply(symName: String, cen: LatLong, terr: WTile, latLongArgs: LatLong*) = new EarthPoly(symName, cen, terr)
  { val polygonLL = PolygonLL(latLongArgs*)
  }

  def apply(symName: String, cen: LatLong, terr: WTile, polygonIn: PolygonLL) = new EarthPoly(symName, cen, terr)
  { val polygonLL = polygonIn
  }

  extension (thisArr: RArr[EarthPoly])
  { /** Extension method, filters the [[LakePoly]]s. */
    def lakeFilter: RArr[LakePoly] = thisArr.filter(_.isLake).asInstanceOf[RArr[LakePoly]]
  }
}

/** Common trait for [[IslandPoly]] and [[IslandPolyGroup]]. */
trait IslandPolyLike extends WithKilares
{ /** The name of this geographical entity. */
  def name: String

  /** Optional parent group for this geographical entity. */
  def oGroup: Option[IslandPolyGroup] = None

  def groupings: RArr[IslandPolyGroup] =
  { val acc: ArrayBuffer[IslandPolyGroup] = new ArrayBuffer[IslandPolyGroup]()
    def loop(inp: Option[IslandPolyGroup]): Unit = inp match
    { case Some(eig) => { acc.append(eig); loop(eig.oGroup) }
      case None =>
    }
    loop(oGroup)
    new RArr(acc.toArray)
  }
}

abstract class IslandPoly(name: String, cen: LatLong, terr: WTile) extends EarthPoly(name, cen, terr), IslandPolyLike
{ override def toString = name.oneLine + (area.str0 + ", " + terr.strComma).enParenth

  def strWithGroups: String =
  { val groupStrs: StrArr = groupings.map(g => g.name -- g.area.str0)
    val inner: StrArr = StrArr(area.str0, terr.strComma) ++ groupStrs
    name + inner.mkStr("; ").enParenth    
  }
}

/** Groups [[IslandPoly]]s and subgroups of Islands giving a total area. For tiling purposes this allows an island grouping to be abstracted as a single
 * island. */
abstract class IslandPolyGroup(val name: String) extends IslandPolyLike
{ override def area: Kilares = array.sumBy(_.area)
  def elements: RArr[IslandPolyLike]
  lazy val array: Array[IslandPolyLike] = elements.arrayUnsafe
  override def toString: String = name
}

/** A lake representation with name [[PolygonLL]] and terrain. */
abstract class LakePoly(name: String, cen: LatLong, terr: WTile) extends EarthPoly(name, cen, terr), WithKilares