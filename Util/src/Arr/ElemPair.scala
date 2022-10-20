/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._
import collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

trait ElemPair[A1, A2] extends Any
{ def a1: A1
  def a2: A2
}

trait PairArr[A1, A1Arr <: Arr[A1], A2, A <: ElemPair[A1, A2]] extends Arr[A]
{
  def a1Arr: A1Arr
  def a2Array: Array[A2]
  def a2Arr: RArr[A2] = new RArr[A2](a2Array)
  override final def length: Int = a2Array.length
}

trait PairArrBuilder[B1, ArrB1 <: Arr[B1], B2, B <: ElemPair[B1, B2], ArrB <: Arr[B]] extends ArrBuilder[B, ArrB]
{
  /** Builder for an Arr of the first element of the pair. */
  def b1ArrBuilder: ArrBuilder[B1, ArrB1]

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  def pairArrBuilder(polygonArr: ArrB1, a2s: Array[B2]): ArrB
}

/** An element that pairs a [[SeqSpec]] with a second value. */
trait ElemSeqSpecPair[A1E, A1 <: SeqSpec[A1E], A2] extends ElemPair[A1, A2] with SpecialT
{ def a1: A1
  def a2: A2
}

/** A sequence of [[ElemSeqSpecPair]]s stored in 2 [[Array]]s for efficiency. */
trait SeqSpecPairArr[A1E, A1 <: SeqSpec[A1E], A1Arr <: Arr[A1], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends PairArr[A1, A1Arr, A2, A]
{
  /** Maps the first component of the pairs, dropping the second. */
  def a1Map[B, ArrB <: Arr[B]](f: A1 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = a1Arr.map(f)

  /** Maps the second component of the pairs, dropping the first. */
  def a2Map[B, ArrB <: Arr[B]](f: A2 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = a2Arr.map(f)

  /** Needs rewriting. */
  def pairMap[B, ArrB <: Arr[B]](f: (A1, A2) => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = map(p => f(p.a1, p.a2))
}

/** A buffer of [[ElemSeqSpecPair]]s stored in 2 [[ArrayBuffer]]s for efficiency. */
trait SeqSpecPairBuff[A1E, A1 <: SeqSpec[A1E], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends Sequ[A]
{ def a2Buff: ArrayBuffer[A2]
  override def length: Int = a2Buff.length
}

trait SeqSpecPairArrBuilder[B1E, B1 <: SeqSpec[B1E], ArrB1 <: Arr[B1], B2, B <: ElemSeqSpecPair[B1E, B1, B2], ArrB <: Arr[B]] extends
  PairArrBuilder[B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  def b1Builder: SeqLikeImutBuilder[B1E, B1]
}

trait ElemDblNPair[A1 <: ElemDblN, A2] extends ElemPair[A1, A2]

trait DblNPairArr[A1 <: ElemDblN, ArrA1 <: DblNArr[A1], A2, A <: ElemDblNPair[A1, A2]] extends PairArr[A1, ArrA1, A2, A]
{ type ThisT <: DblNPairArr[A1, ArrA1, A2, A]

  /** The backing Array for the first elements of the pairs. */
  def a1ArrayDbl: Array[Double]
}

/** Helper trait for Companion objects of [[DblNPairArr]] classes. */
trait DblNPairArrCompanion[A1 <: ElemDblN, ArrA1 <: DblNArr[A1]]
{
  /** The number of [[Double]] values that are needed to construct an element of the defining-sequence. */
  def elemNumDbls: Int
}

trait ElemDbl2Pair[A1 <: ElemDbl2, A2] extends ElemDblNPair[A1, A2]
{ def a1Dbl1: Double
  def a1Dbl2: Double
}

trait Dbl2PairArr[A1 <: ElemDbl2, ArrA1 <: Dbl2Arr[A1], A2, A <: ElemDbl2Pair[A1, A2]] extends DblNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Dbl2PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 2 [[Double]]s and a third parameter of type A2. */
  def newPair(dbl1: Double, dbl2: Double, a2: A2): A

  override final def apply(index: Int): A = newPair(a1ArrayDbl(index * 2), a1ArrayDbl(index * 2 + 1), a2Array(index))

  override final def unsafeSetElem(i: Int, value: A): Unit =
  { a1ArrayDbl(i * 2) = value.a1Dbl1;
    a1ArrayDbl(i * 2 + 1) = value.a1Dbl2
    a2Array(i) = value.a2
  }
}

trait Dbl2PairArrCompanion[A1 <: ElemDbl2, ArrA1 <: Dbl2Arr[A1]] extends DblNPairArrCompanion[A1, ArrA1]
{
  override def elemNumDbls: Int = 2

  def seqToArrays[A2](pairs: Seq[ElemDbl2Pair[_, A2]])(implicit ct: ClassTag[A2]): (Array[Double], Array[A2]) =
 {  val dblsArray = new Array[Double](pairs.length * 2)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      dblsArray(i * 2) = p.a1Dbl1
      dblsArray(i * 2 + 1) = p.a1Dbl2
      a2Array(i) = p.a2
      i += 1
    }
    (dblsArray, a2Array)
  }
}