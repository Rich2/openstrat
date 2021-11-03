/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi Polygon specified in 3D metre points. This is not a proper polygon as the points do not have to lie within the same plane. I'm not
 *  sure how useful this class will prove. It has been created for the intermediary step of converting from [[LatLongs]]s to [[PolygonMetre]]s on world
 *  maps. */
final class PolygonMetre3(val arrayUnsafe: Array[Double]) extends AnyVal with PolygonDbl3s[PtMetre3]
{ override type ThisT = PolygonMetre3
  override def dataElem(d1: Double, d2: Double, d3: Double): PtMetre3 = new PtMetre3(d1, d2, d3)
  override def unsafeFromArray(array: Array[Double]): PolygonMetre3 = new PolygonMetre3(array)
  override def typeStr: String = "PolygonMetre3"
  override def fElemStr: PtMetre3 => String = _.toString
  def xyPlane: PolygonMetre = map(_.xy)

  /** All vertices have a non negative Z component. */
  def zNonNeg: Boolean = vertsForAll(_.zMetres >= 0)

  override def vert(index: Int): PtMetre3 = indexData(index)

  /** Performs the side effecting function on the [[PtMetre3]] value of each vertex. */
  override def vertsForeach[U](f: PtMetre3 => U): Unit =
  { var count = 0
    while (count < vertsNum)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsIForeach[U](f: (PtMetre3, Int) => U): Unit =
  { var count = 0
    vertsForeach{ v =>
      f(v, count)
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: ArrBase[B]](f: PtMetre3 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB =
  { val res = builder.newArr(vertsNum)
    var count = 0
    vertsForeach{ v =>
      builder.arrSet(res, count, f(v))
      count += 1
    }
    res
  }

  override def vertsFold[B](init: B)(f: (B, PtMetre3) => B): B =
  { var res = init
    vertsForeach(v => res = f(res, v))
    res
  }
}

/** Companion object for PolygonM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object PolygonMetre3 extends DataDbl3sCompanion[PtMetre3, PolygonMetre3]
{ override def fromArrayDbl(array: Array[Double]): PolygonMetre3 = new PolygonMetre3(array)

  implicit val persistImplicit: DataDbl3sPersist[PtMetre3, PolygonMetre3] = new DataDbl3sPersist[PtMetre3, PolygonMetre3]("PolygonMs3")
  { override def fromArray(value: Array[Double]): PolygonMetre3 = new PolygonMetre3(value)
  }
}