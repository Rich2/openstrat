/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom.*, collection.mutable.ArrayBuffer

/** A polygon with the vertices defined by hex tile coordinates  [[HCoord]]s. */
class PolygonHC(val arrayUnsafe: Array[Int]) extends AnyVal, HCoordSeqSpec, PolygonLikeInt2[HCoord], ArrayIntBacked
{ override type ThisT = PolygonHC
  override type SideT = LineSegHC
  override def typeStr: String = "PolygonHC"
  override def fromArray(array: Array[Int]): PolygonHC = new PolygonHC(array)
  def vertNum: Int = arrayUnsafe.length / 2
  override def verts: HCoordArr = new HCoordArr(arrayUnsafe)

  /** Performs the side effecting function on the [[HCoord]] value of each vertex. */
  override def vertsForeach[U](f: HCoord => U): Unit =
  { var count = 0
    while (count < numVerts)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: HCoord => B)(implicit builder: BuilderMapArr[B, ArrB]): ArrB =
  { val res = builder.uninitialised(numVerts)
    var count = 0
    vertsForeach{ v =>
      builder.indexSet(res, count, f(v))
      count += 1
    }
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The previous
   * vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the foreach based
   * convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B signature. */
  override def vertsPrevForEach[U](f: (HCoord, HCoord) => U): Unit = ???

  /** This applies the index value in a circular manner. So the 6th index of a Hexagon is applied at vertex 0, 7 at 1 and -1 at 5. */
  def circularIndex(inp: Int): Int = inp %% vertNum

  def toPolygon(f: HCoord => Pt2): Polygon = new PolygonGen(toPolygonArray(f))
  /*{
    val res = PolygonGen.uninitialised(sdLength)
    dataIForeach((i, hv) => res.unsafeSetElem(i, f(hv)))
    res
  }*/

  def toPolygonArray(f: HCoord => Pt2): Array[Double] =
  { val res = new Array[Double](numElems * 2)
    iForeach{ (i, hv) =>
      val newVal = f(hv)
      res(i * 2) = newVal.dbl1
      res(i * 2 + 1) = newVal.dbl2
    }
    res
  }

  def combine(operand: PolygonHC): Option[PolygonHC] =
  {
    var starts: Option[(Int, Int)] = None
    val a = elem(0)
    ???
  }

  @inline override def side(index: Int): LineSegHC = LineSegHC(vert(index), vert(index + 1))
  override def sides: LineSegHCArr = new LineSegHCArr(arrayForSides)

  override def sidesForeach[U](f: LineSegHC => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }
}

/** Companion object for the polygon whose vertices are defined by hex tile coordinates [[PolygonHC]] trait. */
object PolygonHC extends CompanionSlInt2[HCoord, PolygonHC]
{ override def fromArray(array: Array[Int]): PolygonHC = new PolygonHC(array)

  implicit val arrBuildImplicit: BuilderMapArr[PolygonHC, PolygonHCArr] = new BuilderMapArr[PolygonHC, PolygonHCArr]
  { override type BuffT = PolygonHCBuff
    override def newBuff(length: Int): PolygonHCBuff = PolygonHCBuff(length)
    override def uninitialised(length: Int): PolygonHCArr = new PolygonHCArr(new Array[Array[Int]](length))
    override def indexSet(seqLike: PolygonHCArr, index: Int, newElem: PolygonHC): Unit = seqLike.arrayOfArraysUnsafe(index) = newElem.arrayUnsafe
    override def buffGrow(buff: PolygonHCBuff, newElem: PolygonHC): Unit = buff.unsafeBuffer.append(newElem.arrayUnsafe)
    override def buffToSeqLike(buff: PolygonHCBuff): PolygonHCArr = new PolygonHCArr(buff.unsafeBuffer.toArray)
  }
}

/** Speialised [[Arr]] class for [[PolygonHC]]s. */
class PolygonHCArr(val arrayOfArraysUnsafe:Array[Array[Int]]) extends ArrArrayInt[PolygonHC]
{ override type ThisT = PolygonHCArr
  override def typeStr: String = "PolygonHCArr"
  override def fElemStr: PolygonHC => String = _.toString
  override def fromArrayArray(array: Array[Array[Int]]): PolygonHCArr = new PolygonHCArr(array)
  override def elemFromArray(array: Array[Int]): PolygonHC = new PolygonHC(array)
}

class PolygonHCBuff(val unsafeBuffer: ArrayBuffer[Array[Int]]) extends AnyVal, ArrayIntBuff[PolygonHC]
{ override type ThisT = PolygonHCBuff
  override def typeStr: String = "PolygonHCBuff"
  override def setElemUnsafe(index: Int, newElem: PolygonHC): Unit = unsafeBuffer(index) = newElem.arrayUnsafe
  override def fElemStr: PolygonHC => String = _.toString
  override def fromArrayInt(array: Array[Int]): PolygonHC = new PolygonHC(array)
}

object PolygonHCBuff
{ def apply(initLen: Int = 4): PolygonHCBuff = new PolygonHCBuff(new ArrayBuffer[Array[Int]](initLen))
}
