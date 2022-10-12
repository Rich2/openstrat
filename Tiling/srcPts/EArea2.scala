/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._

/** A second level area of the Earth. */
abstract class EArea2(val name: String, val cen: LatLong, val terr: WTile) extends GeographicSymbolKey
{
  override def toString = name.appendCommas(terr.toString)

  def aStrs: StringArr = StringArr(name)

  def textScale: Length = 15000.metres

  def colour = terr.colour

  /** A quasi polygon on the earths surface defined in [[LatLong]]s. */
  def polygonLL: PolygonLL

  def display(eg: EarthGuiOld): GraphicElems =
  {
    eg.polyToGlobedArea(polygonLL) match
    {
      case SomeA(d2s) =>
      {
        val v2s: Polygon = d2s.mapPolygon(eg.trans)
        val cenXY: Pt2 = eg.latLongToXY(cen)
        val vis1: GraphicElems = Arr(v2s.fillActive(terr.colour, this))
        val vis2: GraphicElems = Arr(v2s.draw(terr.colour.redOrPink, 2.0))
        val vis3: GraphicElems =
          if (eg.scale < textScale) TextGraphic.lines(aStrs, 10, cenXY, terr.contrast) else Arr()
        (vis1 ++ vis2 ++ vis3)
      }

      case SomeB(curveSegDists) =>
      {
        val cenXY: Pt2 = eg.latLongToXY(cen)
        val curveSegs: ShapeGenOld = ShapeGenOld.deprDataGenMap(curveSegDists)(_.toCurveSeg(eg.trans)) // curveSegDists.dataMap(_.toCurveSeg(eg.trans))
        Arr(PolyCurveParentFull.fill(cenXY, curveSegs, this, terr.colour))
      }

      case NoOptEither => Arr()
    }
  }

  /** Returns a pair of this [[EArea2]] and the [[PolygonM2]] from the given focus and orientation. The polygonM only has points form the side side of
   *  the earth that faces the focus.  */
  def withPolygonM(focus: LatLong, northUp: Boolean): (EArea2, PolygonM2) =
  { val p3s0: PolygonM3 = polygonLL.toMetres3
    val p3s1: PolygonM3 = p3s0.fromLatLongFocus(focus)
    val p3s2: PolygonM3 = ife(northUp, p3s1, p3s1.rotateZ180)
    val p3s3: PolygonM2 = p3s2.earthZPosXYModify
    (this, p3s3)
  }
}

object EArea2
{
  def apply(symName: String, cen: LatLong, terr: WTile, latLongArgs: LatLong*) = new EArea2(symName, cen, terr)
  { val polygonLL = PolygonLL(latLongArgs: _*)
  }

  def apply(symName: String, cen: LatLong, terr: WTile, polygonIn: PolygonLL) = new EArea2(symName, cen, terr)
  { val polygonLL = polygonIn
  }
}