/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom;

/** This package is for global geometry. */
package object pglobe
{ /** The position of latitude and longitude [[LatLong]] 0 east and 0 north. */
  val LatLong0: LatLong = LatLong.degs(0, 0)
  implicit def intGlobeToExtensions(thisInt: Int): IntGlobeExtensions = new IntGlobeExtensions(thisInt)
  implicit def doubleGlobeToExtensions(thisDouble: Double): DoubleGlobeExtensions = new DoubleGlobeExtensions(thisDouble)

  /** Method for creating a 2d background or outline for the earth. */
  def earth2DEllipse(scale: LengthMetric): Ellipse = Ellipse(EarthEquatorialRadius / scale, EarthPolarRadius / scale)

  implicit class PolygonMetre3PglobeExtension (thisPoly: PolygonM3)
  {
    /** Method for converting polygons on a globes surface to a 2D flat view. Will probably be replaced. */
    def earthZPosXYModify: PolygonM2Gen = thisPoly.verts.foldLeft(0)((acc, v) => ife(v.zNeg, acc, acc + 1)) match
    { case n if n == thisPoly.numVerts => thisPoly.toXY
      case 0 => PolygonM2Gen.empty
      case _ if thisPoly.numVerts < 2 => PolygonM2Gen.empty
      case _ => thisPoly.earthZPosXYModifyInefficient
    }

    /** Internal method for converting polygons on a globes surface to a 2D flat view. Will probably be replaced. */
    def earthZPosXYModifyInefficient: PolygonM2Gen =
    { val buff = BuffPtM2()
      thisPoly.vertsPrevForEach((prev, v) => (v.zNonNeg) match
        {
          case true if prev.zNeg =>
          { val y: Metres = (prev.y + v.y) / 2
            val ratio = (1 - (y / EarthAvRadius).squared).sqrt
            val x: Metres = ife(v.xNonNeg, EarthAvRadius * ratio, -EarthAvRadius * ratio)
            buff.grow(PtM2(x, y))
            buff.grow(v.xy)
          }

          case false if prev.zNonNeg =>
          { val y: Metres = (prev.y + v.y) / 2
            val ratio: Double = (1 - (y / EarthAvRadius).squared).sqrt //gets cosine value from sine value
            val x: Metres = ife(v.xNonNeg, EarthAvRadius * ratio, -EarthAvRadius * ratio)
            buff.grow(PtM2(x, y))
          }

          case true => buff.grow(v.xy)
          case _ =>
        }
      )
      buff.toPolygon
    }
  }

  implicit class LLTransExtensions[T](val thisT: T)(implicit ev: LLTrans[T])
  { /** Add Longitude, translate to the east extension method. */
    def addLong(longDelta: Longitude): T = ev.fLLTrans(thisT, ll => LatLong(ll.lat, ll.long  + longDelta))

    /** Subtract Longitude, translate to the west extension method. */
    def subLong(longDelta: Longitude): T = ev.fLLTrans(thisT, ll => LatLong(ll.lat, ll.long - longDelta))
  }

  implicit class LatLongGlobeExtensions(val thisPt: PtM3)
  { def fromLatLongFocus(focus: LatLongDirn): PtM3 = thisPt.rotateY(-focus.longVec).rotateX(-focus.latVec)
  }

  implicit class RotateM3GlobeExtensions[T](val thisT: T)(implicit ev: RotateM3T[T])
  { def fromLatLongFocus(focus: LatLongDirn): T = thisT.rotateY(-focus.longVec).rotateX(-focus.latVec)
  }
}