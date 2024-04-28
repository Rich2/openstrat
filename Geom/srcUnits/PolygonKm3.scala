/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

/** A quasi Polygon specified in 3D metre points. This is not a proper polygon as the points do not have to lie within the same plane. I'm not
 *  sure how useful this class will prove. It has been created for the intermediary step of converting from [[LatLongs]]s to [[PolygonM2]]s on world
 *  maps. */
final class PolygonKm3(val arrayUnsafe: Array[Double]) extends AnyVal with PolygonLength3[PtKm3]
{ override type ThisT = PolygonKm3
  override type SideT = LineSegKm3
  override def ssElem(d1: Double, d2: Double, d3: Double): PtKm3 = new PtKm3(d1, d2, d3)
  override def fromArray(array: Array[Double]): PolygonKm3 = new PolygonKm3(array)
  override def typeStr: String = "PolygonMetre3"
  override def fElemStr: PtKm3 => String = _.toString
  def xyPlane: PolygonM2 = map(_.xy)

  /** All vertices have a non negative Z component. */
  def zAllNonNeg: Boolean = vertsForAll(_.zMetresNum >= 0)

  def zAllNeg: Boolean = vertsForAll(_.zMetresNum < 0)
  override def verts: PtKm3Arr = new PtKm3Arr(arrayUnsafe)

  /** Performs the side effecting function on the [[PtKm3]] value of each vertex.  */
  override def vertsForeach[U](f: PtKm3 => U): Unit =
  { var count = 0
    while (count < numVerts)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: PtKm3 => B)(implicit builder: BuilderArrMap[B, ArrB]): ArrB =
  { val res = builder.uninitialised(numVerts)
    var count = 0
    vertsForeach{ v =>
      builder.indexSet(res, count, f(v))
      count += 1
    }
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach [[PtKm3]] vertex applies the side effecting function to the previous [[PtKm3]] vertex
   *  with each vertex. The previous vertex to the first vertex is the last vertex of this [[PolygonKm3]]. Note the function signature (previous,
   *  vertex) => U follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'
   *  (accumulator, element) => B signature. */
  override def vertsPrevForEach[U](f: (PtKm3, PtKm3) => U): Unit = if (numVerts >= 2)
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

  override def sidesForeach[U](f: LineSegKm3 => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }

  @inline override def side(index: Int): LineSegKm3 = LineSegKm3(vert(index), vert(index + 1))
  override def sides: LineSegKm3Arr = new LineSegKm3Arr(arrayForSides)
}

/** Companion object for [[PolygonKm3]]. Contains apply factory method fromArrayDbl and Persist Implicit. */
object PolygonKm3 extends CompanionSeqLikeDbl3[PtKm3, PolygonKm3]
{ override def fromArray(array: Array[Double]): PolygonKm3 = new PolygonKm3(array)

  implicit val arrBuildImplicit: BuilderArrMap[PolygonKm3, PolygonKm3Arr] = new BuilderArrMap[PolygonKm3, PolygonKm3Arr] {
    override type BuffT = PolygonKm3Buff
    override def newBuff(length: Int): PolygonKm3Buff = PolygonKm3Buff(length)
    override def uninitialised(length: Int): PolygonKm3Arr = new PolygonKm3Arr(new Array[Array[Double]](length))
    override def indexSet(seqLike: PolygonKm3Arr, index: Int, newElem: PolygonKm3): Unit = seqLike.unsafeArrayOfArrays(index) = newElem.arrayUnsafe
    override def buffGrow(buff: PolygonKm3Buff, newElem: PolygonKm3): Unit = buff.unsafeBuffer.append(newElem.arrayUnsafe)
    override def buffToSeqLike(buff: PolygonKm3Buff): PolygonKm3Arr = new PolygonKm3Arr(buff.unsafeBuffer.toArray)
  }

  /*implicit val rotateKm3TImplicit: RotateKm3T[PolygonKm3] = new RotateKm3T[PolygonKm3] {
    override def rotateXT(obj: PolygonKm3, angle: AngleVec): PolygonKm3 = obj.map(pt => pt.rotateX(angle))
    override def rotateYT(obj: PolygonKm3, angle: AngleVec): PolygonKm3 = obj.map(pt => pt.rotateY(angle))
    override def rotateZT(obj: PolygonKm3, angle: AngleVec): PolygonKm3 = obj.map(pt => pt.rotateZ(angle))
    override def rotateZ180T(obj: PolygonKm3): PolygonKm3 = obj.map(pt => pt.rotateZ180)
  }*/

  /** Both [[Show]] and [[Unshow]] type class instances / evidence for [[PolygonKm3]]. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtKm3, PolygonKm3] = ???// PersistSeqSpecBoth[PtKm3, PolygonKm3]("PolygonKm3")
}

/** Specialised [[Arr]] class for [[PolygonKm3]]s. Polygon in a 3D space measured in metres. */
class PolygonKm3Arr(val unsafeArrayOfArrays:Array[Array[Double]]) extends ArrArrayDbl[PolygonKm3]
{ override type ThisT = PolygonKm3Arr
  override def typeStr: String = "PolygonKm3Arr"
  override def fElemStr: PolygonKm3 => String = _.toString
  override def apply(index: Int): PolygonKm3 = new PolygonKm3(unsafeArrayOfArrays(index))
  override def unsafeFromArrayArray(array: Array[Array[Double]]): PolygonKm3Arr = new PolygonKm3Arr(array)
}

/** Specialised [[BuffSequ]] class for [[PolygonKm3]]s. Polygon in a 3D space measured in metres. */
class PolygonKm3Buff(val unsafeBuffer: ArrayBuffer[Array[Double]]) extends AnyVal with BuffArrayDbl[PolygonKm3]
{ override type ThisT = PolygonKm3Buff
  override def typeStr: String = "PolygonKm3Buff"
  override def fElemStr: PolygonKm3 => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonKm3 = new PolygonKm3(array)
}

object PolygonKm3Buff
{ def apply(initLen: Int = 4): PolygonKm3Buff = new PolygonKm3Buff(new ArrayBuffer[Array[Double]](initLen))
}

/** Speccialised effeicnet class for pairs where the first ocmponent of the pair is a [[PolygonKm3]], a polygon in £d space poits specified in metre
 * scales. */
class PolygonKm3Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDblNPair[PtKm3, PolygonKm3, A2] with SpecialT {
  override def a1: PolygonKm3 = new PolygonKm3(a1ArrayDbl)
}

object PolygonKm3Pair
{ def apply[A2](poly: PolygonKm3, a2: A2): PolygonKm3Pair[A2] = new PolygonKm3Pair[A2](poly.arrayUnsafe, a2)
}

final class PolygonKm3PairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDblNPairArr[PtKm3, PolygonKm3, PolygonKm3Arr, A2, PolygonKm3Pair[A2]]
{ override type ThisT = PolygonKm3PairArr[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonKm3Pair[A2]): Unit = { a1ArrayDbls(i) = newElem.a1ArrayDbl; a2Array(i) = newElem.a2 }
  override def fElemStr: PolygonKm3Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonKm3PairArray"
  override def apply(index: Int): PolygonKm3Pair[A2] = new PolygonKm3Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonKm3Arr = new PolygonKm3Arr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonKm3PairArr[A2] = new PolygonKm3PairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonKm3 = new PolygonKm3(array)
}

final class PolygonKm3PairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[PtKm3, PolygonKm3, PolygonKm3Arr, A2, PolygonKm3Pair[A2], PolygonKm3PairArr[A2]]
{ override type BuffT = PolygonKm3PairBuff[A2]
  override type B1BuffT = PolygonKm3Buff
  override def uninitialised(length: Int): PolygonKm3PairArr[A2] = new PolygonKm3PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonKm3PairArr[A2], index: Int, newElem: PolygonKm3Pair[A2]): Unit =
  { seqLike.a1ArrayDbls(index) = newElem.a1ArrayDbl ; seqLike.a2Array(index) = newElem.a2 }

  override def newBuff(length: Int): PolygonKm3PairBuff[A2] = new PolygonKm3PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToSeqLike(buff: PolygonKm3PairBuff[A2]): PolygonKm3PairArr[A2] = new PolygonKm3PairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)

  override def b1Builder: PolygonLikeMapBuilder[PtKm3, PolygonKm3] = PtKm3.polygonBuildImplicit
  override def b1ArrBuilder: BuilderArrMap[PolygonKm3, PolygonKm3Arr] = PolygonKm3.arrBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonKm3Arr, b2s: Array[A2]): PolygonKm3PairArr[A2] = new PolygonKm3PairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)
  override def newB1Buff(): PolygonKm3Buff = PolygonKm3Buff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonKm3PairArr[A2] = new PolygonKm3PairArr[A2](arrayArrayDbl, a2Array)
}

class PolygonKm3PairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends
  SeqLikeDblNPairBuff[PtKm3, PolygonKm3, A2, PolygonKm3Pair[A2]]
{ override type ThisT = PolygonKm3PairBuff[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonKm3Pair[A2]): Unit = { b1Buffer(i) = newElem.a1ArrayDbl; b2Buffer(i) = newElem.a2 }
  override def fElemStr: PolygonKm3Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonKm3PairBuff"
  override def apply(index: Int): PolygonKm3Pair[A2] = new PolygonKm3Pair[A2](b1Buffer(index), b2Buffer(index))
}