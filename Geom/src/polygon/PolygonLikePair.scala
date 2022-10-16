/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, reflect.ClassTag

trait PolygonLikePair[A1V, A1 <: PolygonLike[A1V], A2] extends ElemSeqLikePair[A1V, A1, A2]
{ //def a1: A1
}

trait PolygonLikePairArr[A1V, A1 <: PolygonLike[A1V], A1Arr <: Arr[A1], A2, A <: PolygonLikePair[A1V, A1, A2]] extends
  SeqLikePairArr[A1V, A1, A1Arr, A2, A]
{
  /** Maps this to a new [PolygonLikePairArr]] by mapping [[PolygonLike]]s to new [[PolygonLike]]s of type B1 leaving the second parts of the pairs
   * unchanged. */
  def polygonMapToPair[B1V <: ElemValueN, B1 <: PolygonLike[B1V], ArrB1 <: Arr[B1], B <: PolygonLikePair[B1V, B1, A2],
    ArrB <: PolygonLikePairArr[B1V, B1, ArrB1, A2, B]](f: A1V => B1V)(implicit build: PolygonLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]): ArrB =
  { val polygons = a1Arr.map(p => p.map[B1V, B1](f)(build.b1Builder))(build.b1ArrBuilder)
    build.pairArrBuilder(polygons, a2Array)
  }
}

trait PolygonLikePairArrBuilder[B1V, B1 <: PolygonLike[B1V], ArrB1 <: Arr[B1], B2, B <: PolygonLikePair[B1V, B1, B2],
  ArrB <: PolygonLikePairArr[B1V, B1, ArrB1, B2, B]] extends SeqSpecPairArrBuilder[B1V, B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1, in this case a [[PolygonLike]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[PolygonLikePairArr]]. */
  override def b1Builder: PolygonLikeBuilder[B1V, B1]
}

trait PolygonDblsPair[A1V <: ElemDblN, A1 <: PolygonDblN[A1V], A2] extends PolygonLikePair[A1V, A1, A2]
{
  def unsafeArray: Array[Double]
}

trait PolygonDblsPairArr[A1V <: ElemDblN, A1 <: PolygonDblN[A1V], ArrA1 <: Arr[A1], A2, A <: PolygonDblsPair[A1V, A1, A2]] extends
  PolygonLikePairArr[A1V, A1, ArrA1, A2, A]
{
  type ThisT <: PolygonDblsPairArr[A1V, A1, ArrA1, A2, A]
  def arrayArrayDbl: Array[Array[Double]]
  def fromArrays(array1: Array[Array[Double]], array2: Array[A2]): ThisT// = ???
  def a1Buff: ArrayDblBuff[A1]// = ???
  //f a1Arr: ArrA1 //= fromArrays(arrayArrayDbl)
  def filterOn1(f: A1 => Boolean)(implicit ct: ClassTag[A2]): ThisT = {
    val buff1 = a1Buff//PolygonM3Buff()
    val buff2 = new ArrayBuffer[A2]()
    var i = 0
    a1Arr.foreach { a1 =>
      if (f(a1)) {
        buff1.grow(a1)
        buff2.append(a2Array(i))
      }
      i += 1
    }
    //new PolygonM3PairArr[A2](buff1.arrayArrayDbl, buff2.toArray)
    ???
  }
}

trait PolygonDblsLikePairArrBuilder[B1V <: ElemDblN, B1 <: PolygonDblN[B1V], ArrB1 <: Arr[B1], A2, B <: PolygonDblsPair[B1V, B1, A2],
  ArrB <: PolygonDblsPairArr[B1V, B1, ArrB1, A2, B]] extends PolygonLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]
{
 // override def newArr(newPolygonArr: Arr[PB], a2Arr: Arr[A2]): ArrB = ???// fromArrayArrayDbl(newPolygonArr.arrayArrayDbl, a2Arr)
  def fromArrayArrayDbl(arrayArrayDbl: Array[Array[Double]], a2Arr: RArr[A2]): ArrB
}