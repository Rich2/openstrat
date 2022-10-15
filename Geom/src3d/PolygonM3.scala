/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

/** A quasi Polygon specified in 3D metre points. This is not a proper polygon as the points do not have to lie within the same plane. I'm not
 *  sure how useful this class will prove. It has been created for the intermediary step of converting from [[LatLongs]]s to [[PolygonM2]]s on world
 *  maps. */
final class PolygonM3(val unsafeArray: Array[Double]) extends AnyVal with PolygonLike[PtM3] with PolygonDbl3s[PtM3]
{ override type ThisT = PolygonM3
  override type SideT = LineSegM3
  override def dataElem(d1: Double, d2: Double, d3: Double): PtM3 = new PtM3(d1, d2, d3)
  override def unsafeFromArray(array: Array[Double]): PolygonM3 = new PolygonM3(array)
  override def typeStr: String = "PolygonMetre3"
  override def fElemStr: PtM3 => String = _.toString
  def xyPlane: PolygonM2 = map(_.xy)

  /** All vertices have a non negative Z component. */
  def zNonNeg: Boolean = vertsForAll(_.zMetres >= 0)

  override def vert(index: Int): PtM3 = ssIndex(index)

  /** Performs the side effecting function on the [[PtM3]] value of each vertex.  */
  override def vertsForeach[U](f: PtM3 => U): Unit =
  { var count = 0
    while (count < vertsNum)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsIForeach[U](f: (Int, PtM3) => Any): Unit =
  { var count = 0
    vertsForeach{ v =>
      f(count, v)
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: PtM3 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB =
  { val res = builder.newArr(vertsNum)
    var count = 0
    vertsForeach{ v =>
      builder.arrSet(res, count, f(v))
      count += 1
    }
    res
  }

  override def vertsFold[B](init: B)(f: (B, PtM3) => B): B =
  { var res = init
    vertsForeach(v => res = f(res, v))
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach [[PtM3]] vertex applies the side effecting function to the previous [[PtM3]] vertex
   *  with each vertex. The previous vertex to the first vertex is the last vertex of this [[PolygonM3]]. Note the function signature (previous,
   *  vertex) => U follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'
   *  (accumulator, element) => B signature. */
  override def vertsPrevForEach[U](f: (PtM3, PtM3) => U): Unit = if (vertsNum >= 2)
  { f(ssLast, vert(0))
    var i = 2
    while (i <= vertsNum){
      f(vert(i - 2), vert(i - 1))
      i += 1
    }
  }

  def toXY: PolygonM2 = map(_.xy)

  override def sidesForeach[U](f: LineSegM3 => U): Unit = ??? //if (vertsNum >= 2)
}

/** Companion object for [[PolygonM3]]. Contains apply factory method fromArrayDbl and Persist Implicit. */
object PolygonM3 extends Dbl3SeqLikeCompanion[PtM3, PolygonM3]
{ override def fromArray(array: Array[Double]): PolygonM3 = new PolygonM3(array)

  implicit val arrBuildImplicit: ArrBuilder[PolygonM3, PolygonM3Arr] = new ArrBuilder[PolygonM3, PolygonM3Arr] {
    override type BuffT = PolygonM3Buff

    override def newBuff(length: Int): PolygonM3Buff = PolygonM3Buff(length)

    override def newArr(length: Int): PolygonM3Arr = new PolygonM3Arr(new Array[Array[Double]](length))

    override def arrSet(arr: PolygonM3Arr, index: Int, value: PolygonM3): Unit = arr.unsafeArrayOfArrays(index) = value.unsafeArray

    override def buffGrow(buff: PolygonM3Buff, value: PolygonM3): Unit = buff.unsafeBuff.append(value.unsafeArray)

    override def buffGrowArr(buff: PolygonM3Buff, arr: PolygonM3Arr): Unit = arr.foreach(p => buff.unsafeBuff.append(p.unsafeArray))

    override def buffToBB(buff: PolygonM3Buff): PolygonM3Arr = new PolygonM3Arr(buff.unsafeBuff.toArray)
  }



  implicit val persistImplicit: Dbl3SeqDefPersist[PtM3, PolygonM3] = new Dbl3SeqDefPersist[PtM3, PolygonM3]("PolygonMs3")
  { override def fromArray(value: Array[Double]): PolygonM3 = new PolygonM3(value)
  }

  implicit val rotateM3TImplicit: RotateM3T[PolygonM3] = new RotateM3T[PolygonM3] {
    override def rotateXT(obj: PolygonM3, angle: AngleVec): PolygonM3 = obj.map(pt => pt.rotateX(angle))
    override def rotateYT(obj: PolygonM3, angle: AngleVec): PolygonM3 = obj.map(pt => pt.rotateY(angle))
    override def rotateZT(obj: PolygonM3, angle: AngleVec): PolygonM3 = obj.map(pt => pt.rotateZ(angle))
    override def rotateZ180T(obj: PolygonM3): PolygonM3 = obj.map(pt => pt.rotateZ180)
  }
}
class PolygonM3Arr(val unsafeArrayOfArrays:Array[Array[Double]]) extends ArrayDblArr[PolygonM3]
{ override type ThisT = PolygonM3Arr
  override def typeStr: String = "PolygonM3Arr"
  override def fElemStr: PolygonM3 => String = _.toString
  override def apply(index: Int): PolygonM3 = new PolygonM3(unsafeArrayOfArrays(index))
  override def unsafeFromArrayArray(array: Array[Array[Double]]): PolygonM3Arr = new PolygonM3Arr(array)
}

class PolygonM3Buff(val unsafeBuff: ArrayBuffer[Array[Double]]) extends AnyVal with ArrayDblBuff[PolygonM3]
{ override type ThisT = PolygonM3Buff
  override def typeStr: String = "PolygonM3Buff"
  override def unsafeSetElem(i: Int, value: PolygonM3): Unit = unsafeBuff(i) = value.unsafeArray
  override def fElemStr: PolygonM3 => String = _.toString
  override def apply(index: Int): PolygonM3 = new PolygonM3(unsafeBuff(index))
}

object PolygonM3Buff
{ def apply(initLen: Int = 4): PolygonM3Buff = new PolygonM3Buff(new ArrayBuffer[Array[Double]](initLen))
}