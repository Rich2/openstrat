/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom;

/** This package is for global geometry. */
package object pglobe
{ /** The position of latitude and longitude [[LatLong]] 0 east and 0 north. */
  val LatLong0: LatLong = LatLong.degs(0, 0)

  /** Method for creating a 2d background or outline for the earth. */
  def earth2DEllipse(scale: Length): Ellipse = Ellipse(EarthEquatorialRadius / scale, EarthPolarRadius / scale)

  implicit class PolygonMetre3PglobeExtension (thisPoly: PolygonMetre3)
  {
    def earthZPosXYModify: PolygonMetre = thisPoly.vertsFold(0)((acc, v) => ife(v.zNeg, acc, acc + 1)) match
    { case n if n == thisPoly.vertsNum => thisPoly.toXY
      case 0 | 1 => PolygonMetre.empty
      case _ => thisPoly.earthZPosXYModifyInefficient
    }

    def earthZPosXYModifyInefficient: PolygonMetre =
    { val buff = BuffPtMetre2()
      thisPoly.vertsPrevForEach((prev, v) => (v.zPos) match
        {
          case true if prev.zNeg =>
          { val y: Metres = (prev.y + v.y) / 2
            val ratio = (1 - (y / EarthAvRadius).squared).sqrt
            val x: Metres = ife(v.xPos, EarthAvRadius * ratio, -EarthAvRadius * ratio)
            buff.grow(PtMetre2(x, y))
            buff.grow(v.xy)
          }

          case false if prev.zPos =>
          { val y: Metres = (prev.y + v.y) / 2
            val ratio: Double = (1 - (y / EarthAvRadius).squared).sqrt //gets cosine value from sine value
            val x: Metres = ife(v.xPos, EarthAvRadius * ratio, -EarthAvRadius * ratio)
            buff.grow(PtMetre2(x, y))
          }

          case true => buff.grow(v.xy)
          case _ =>
        }
      )
      buff.toPolygon
    }
  }

  implicit class LLTransExtensions[T](val thisT: T)(implicit ev: LLTrans[T])
  {
    def llLongAdd(longDelta: Longitude): T = ev.fLLTrans(thisT, ll => LatLong(ll.lat, ll.long + longDelta))
  }
}