/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A quasi Polygon specified in 3D metre points. This is not a proper polygon as the points do not have to lie within the same plane. I'm not
 *  sure how useful this class will prove. It has been created for the intermediary step of converting from [[LatLongs]]s to [[PolygonM2]]s on world
 *  maps. */
final class PolygonM3(val arrayUnsafe: Array[Double]) extends AnyVal with PolygonLikeDbl3[PtM3]
{ override type ThisT = PolygonM3
  override type SideT = LineSegM3
  override def ssElem(d1: Double, d2: Double, d3: Double): PtM3 = new PtM3(d1, d2, d3)
  override def fromArray(array: Array[Double]): PolygonM3 = new PolygonM3(array)
  override def typeStr: String = "PolygonMetre3"
  override def fElemStr: PtM3 => String = _.toString
  def xyPlane: PolygonM2 = map(_.xy)

  /** All vertices have a non negative Z component. */
  def zAllNonNeg: Boolean = vertsForAll(_.zMetres >= 0)

  def zAllNeg: Boolean = vertsForAll(_.zMetres < 0)
  override def verts: PtM3Arr = new PtM3Arr(arrayUnsafe)

  /** Performs the side effecting function on the [[PtM3]] value of each vertex.  */
  override def vertsForeach[U](f: PtM3 => U): Unit =
  { var count = 0
    while (count < numVerts)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: PtM3 => B)(implicit builder: BuilderArrMap[B, ArrB]): ArrB =
  { val res = builder.uninitialised(numVerts)
    var count = 0
    vertsForeach{ v =>
      builder.indexSet(res, count, f(v))
      count += 1
    }
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach [[PtM3]] vertex applies the side effecting function to the previous [[PtM3]] vertex
   *  with each vertex. The previous vertex to the first vertex is the last vertex of this [[PolygonM3]]. Note the function signature (previous,
   *  vertex) => U follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'
   *  (accumulator, element) => B signature. */
  override def vertsPrevForEach[U](f: (PtM3, PtM3) => U): Unit = if (numVerts >= 2)
  { f(ssLast, vert(0))
    var i = 2
    while (i <= numVerts){
      f(vert(i - 2), vert(i - 1))
      i += 1
    }
  }

  /** Returns the X component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  def vertX(index: Int): Double = arrayUnsafe(index * 3)

  /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. For maximum efficiency
   * override the implementation in sub classes. */
  def vertY(index: Int): Double = arrayUnsafe(index * 3 + 1)

  /** Returns the Z component of the vertex of the given number. Will throw an exception if the vertex index is out of range. For maximum efficiency
   * override the implementation in sub classes. */
  def vertZ(index: Int): Double = arrayUnsafe(index * 3 + 2)

  def toXY: PolygonM2 = map(_.xy)

  override def sidesForeach[U](f: LineSegM3 => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }

  @inline override def side(index: Int): LineSegM3 = LineSegM3(vert(index), vert(index + 1))
  override def sides: LineSegM3Arr = new LineSegM3Arr(arrayForSides)
}

/** Companion object for [[PolygonM3]]. Contains apply factory method fromArrayDbl and Persist Implicit. */
object PolygonM3 extends CompanionSeqLikeDbl3[PtM3, PolygonM3]
{ override def fromArray(array: Array[Double]): PolygonM3 = new PolygonM3(array)

  implicit val arrBuildImplicit: BuilderArrMap[PolygonM3, PolygonM3Arr] = new BuilderArrMap[PolygonM3, PolygonM3Arr] {
    override type BuffT = PolygonM3Buff
    override def newBuff(length: Int): PolygonM3Buff = PolygonM3Buff(length)
    override def uninitialised(length: Int): PolygonM3Arr = new PolygonM3Arr(new Array[Array[Double]](length))
    override def indexSet(seqLike: PolygonM3Arr, index: Int, newElem: PolygonM3): Unit = seqLike.unsafeArrayOfArrays(index) = newElem.arrayUnsafe
    override def buffGrow(buff: PolygonM3Buff, newElem: PolygonM3): Unit = buff.unsafeBuffer.append(newElem.arrayUnsafe)
    override def buffToSeqLike(buff: PolygonM3Buff): PolygonM3Arr = new PolygonM3Arr(buff.unsafeBuffer.toArray)
  }


  implicit val rotateM3TImplicit: RotateM3T[PolygonM3] = new RotateM3T[PolygonM3] {
    override def rotateXT(obj: PolygonM3, angle: AngleVec): PolygonM3 = obj.map(pt => pt.rotateX(angle))
    override def rotateYT(obj: PolygonM3, angle: AngleVec): PolygonM3 = obj.map(pt => pt.rotateY(angle))
    override def rotateZT(obj: PolygonM3, angle: AngleVec): PolygonM3 = obj.map(pt => pt.rotateZ(angle))
    override def rotateZ180T(obj: PolygonM3): PolygonM3 = obj.map(pt => pt.rotateZ180)
  }

  /** Both [[Show]] and [[Unshow]] type class instances / evidence for [[PolygonM3]]. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtM3, PolygonM3] = PersistSeqSpecBoth[PtM3, PolygonM3]("PolygonM3")
}

/** Specialised [[Arr]] class for [[PolygonM3]]s. Polygon in a 3D space measured in metres. */
class PolygonM3Arr(val unsafeArrayOfArrays:Array[Array[Double]]) extends ArrArrayDbl[PolygonM3]
{ override type ThisT = PolygonM3Arr
  override def typeStr: String = "PolygonM3Arr"
  override def fElemStr: PolygonM3 => String = _.toString
  override def apply(index: Int): PolygonM3 = new PolygonM3(unsafeArrayOfArrays(index))
  override def unsafeFromArrayArray(array: Array[Array[Double]]): PolygonM3Arr = new PolygonM3Arr(array)
}

/** Specialised [[BuffSequ]] class for [[PolygonM3]]s. Polygon in a 3D space measured in metres. */
class PolygonM3Buff(val unsafeBuffer: ArrayBuffer[Array[Double]]) extends AnyVal with BuffArrayDbl[PolygonM3]
{ override type ThisT = PolygonM3Buff
  override def typeStr: String = "PolygonM3Buff"
  override def fElemStr: PolygonM3 => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonM3 = new PolygonM3(array)
}

object PolygonM3Buff
{ def apply(initLen: Int = 4): PolygonM3Buff = new PolygonM3Buff(new ArrayBuffer[Array[Double]](initLen))
}