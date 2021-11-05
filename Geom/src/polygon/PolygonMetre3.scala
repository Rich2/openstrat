/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi Polygon specified in 3D metre points. This is not a proper polygon as the points do not have to lie within the same plane. I'm not
 *  sure how useful this class will prove. It has been created for the intermediary step of converting from [[LatLongs]]s to [[PolygonMetre]]s on world
 *  maps. */
final class PolygonMetre3(val arrayUnsafe: Array[Double]) extends AnyVal with PolygonDbl3s[PtMetre3]
{ override type ThisT = PolygonMetre3
  override type SideT = LineSegMetre3
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

  override def vertsIForeach[U](f: (Int, PtMetre3) => Any): Unit =
  { var count = 0
    vertsForeach{ v =>
      f(count, v)
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

  /** This method does nothing if the vertNum < 2. Foreach [[PtMetre3]] vertex applies the side effecting function to the previous [[PtMetre3]] vertex
   *  with each vertex. The previous vertex to the first vertex is the last vertex of this [[PolygonMetre3]]. Note the function signature (previous,
   *  vertex) => U follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'
   *  (accumulator, element) => B signature. */
  override def vertsPrevForEach[U](f: (PtMetre3, PtMetre3) => U): Unit = if (vertsNum >= 2)
  { f(dataLast, vert(0))
    var i = 2
    while (i <= vertsNum) f(vert(i - 1), vert(i))
  }

  def modifyToZPositive: PolygonMetre3 = vertsFold(0)((acc, v) => ife(v.zNeg, acc, acc + 1)) match {
    case n if n == vertsNum => this
    case 0 | 1 => PolygonMetre3.empty
    case _ => this
  }

  override def sidesForeach[U](f: LineSegMetre3 => U): Unit = ??? //if (vertsNum >= 2)
}

/** Companion object for PolygonM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object PolygonMetre3 extends DataDbl3sCompanion[PtMetre3, PolygonMetre3]
{ override def fromArrayDbl(array: Array[Double]): PolygonMetre3 = new PolygonMetre3(array)

  implicit val persistImplicit: DataDbl3sPersist[PtMetre3, PolygonMetre3] = new DataDbl3sPersist[PtMetre3, PolygonMetre3]("PolygonMs3")
  { override def fromArray(value: Array[Double]): PolygonMetre3 = new PolygonMetre3(value)
  }
}