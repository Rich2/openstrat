/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/* A polygon using distances measured in metres rather than scalars. */
final class PolygonMetre(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl2s[PtMetre2] with PolygonDbl2s[PtMetre2]
{ type ThisT = PolygonMetre
  type SideT = LineSegMetre
  def unsafeFromArray(array: Array[Double]): PolygonMetre = new PolygonMetre(array)
  override def typeStr: String = "PolygonMs"
  override def dataElem(d1: Double, d2: Double): PtMetre2 = new PtMetre2(d1, d2)
  override def fElemStr: PtMetre2 => String = _.str

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  @inline override def vert(index: Int): PtMetre2 = indexData(index)

  /** Performs the side effecting function on the [[PtMetre2]] value of each vertex. */
  override def vertsForeach[U](f: PtMetre2 => U): Unit =
  { var count = 0
    while (count < vertsNum)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsIForeach[U](f: (Int, PtMetre2) => Any): Unit =
  { var count = 0
    vertsForeach{ v =>
      f(count, v)
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: ArrBase[B]](f: PtMetre2 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB =
  { val res = builder.newArr(vertsNum)
    var count = 0
    vertsForeach{ v =>
      builder.arrSet(res, count, f(v))
      count += 1
    }
    res
  }

  override def vertsFold[B](init: B)(f: (B, PtMetre2) => B): B =
  { var res = init
    vertsForeach(v => res = f(res, v))
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
  override def vertsPrevForEach[U](f: (PtMetre2, PtMetre2) => U): Unit = ???

  override def sidesForeach[U](f: LineSegMetre => U): Unit = ???
}

/** The companion object for PolygonDist. Provides an implicit builder. */
object PolygonMetre extends DataDbl2sCompanion[PtMetre2, PolygonMetre]
{ override def fromArrayDbl(array: Array[Double]): PolygonMetre = new PolygonMetre(array)

  implicit val persistImplicit: DataDbl2sPersist[PtMetre2, PolygonMetre] = new DataDbl2sPersist[PtMetre2, PolygonMetre]("PolygonMs")
  { override def fromArray(value: Array[Double]): PolygonMetre = new PolygonMetre(value)
  }
}

/* A polygon using distances measured in metres rather than scalars. */
final class PolygonKMs(val arrayUnsafe: Array[Double]) extends AnyVal