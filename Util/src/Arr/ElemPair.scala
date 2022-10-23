/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** These classes are for use in [[PairArr]]s. */
trait ElemPair[A1, A2] extends Any
{ def a1: A1
  def a2: A2
}

/** An [[Arr]] of pairs [[ElemPair]]. These classees allow convenient methods to map and filter on just one component of the pair. They and their
 *  associated [[PairArrBuilder]] and [[PairBuff]] classes also allow for efficient storage by using 2 Arrays of the components of the pairs rather
 *  than one array of the pairs. It is particularly designed for efficient maoOnA1 operations, where we want to map over the first part of the pair
 *  while leaving the second component of the oair unchanged. So sub traits and classes specialise on a1 the first component of the pair. There are no
 *  filterMap methods. You must map then filter. */
trait PairArr[A1, A1Arr <: Arr[A1], A2, A <: ElemPair[A1, A2]] extends Arr[A]
{ def a1Arr: A1Arr
  def a2Array: Array[A2]
  def a2Arr: RArr[A2] = new RArr[A2](a2Array)
  override final def length: Int = a2Array.length

  /** Maps the first component of the pairs, dropping the second. */
  def a1Map[B, ArrB <: Arr[B]](f: A1 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = a1Arr.map(f)

  /** Maps the second component of the pairs, dropping the first. */
  def a2Map[B, ArrB <: Arr[B]](f: A2 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = a2Arr.map(f)

  /** Needs rewriting. */
  def pairMap[B, ArrB <: Arr[B]](f: (A1, A2) => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = map(p => f(p.a1, p.a2))

  def mapOnA1[B1, ArrB1 <: Arr[B1], B <: ElemPair[B1, A2], ArrB <: PairArr[B1, ArrB1, A2, B]](f: A1 => B1)(implicit
    build: PairArrBuilder[B1, ArrB1, A2, B, ArrB]): ArrB =
  { val b1Arr: ArrB1 = a1Arr.map(f)(build.b1ArrBuilder)
    build.pairArrBuilder(b1Arr, a2Array)
  }
}

trait PairBuff[A1, A2, A <: ElemPair[A1, A2]] extends Any with Buff[A]
{ def a2Buffer: ArrayBuffer[A2]
  override def length: Int = a2Buffer.length
  override def fElemStr: A => String = _.toString
}

/** Builder for [[ElemPair]]s. As with all builders, we use B as the type parameter, because builders are nearly always used with some kind of map /
 * flatMap methods where B corresponds to the map function f: A => B. */
trait PairArrBuilder[B1, ArrB1 <: Arr[B1], B2, B <: ElemPair[B1, B2], ArrB <: Arr[B]] extends ArrBuilder[B, ArrB]
{ type BuffT <: PairBuff[B1, B2, B]

  /** Builder for an Arr of the first element of the pair. */
  def b1ArrBuilder: ArrBuilder[B1, ArrB1]

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  def pairArrBuilder(b1Arr: ArrB1, b2s: Array[B2]): ArrB
}

trait ElemIntNPair[A1 <: ElemIntN, A2] extends ElemPair[A1, A2]

trait IntNPairArr[A1 <: ElemIntN, ArrA1 <: IntNArr[A1], A2, A <: ElemIntNPair[A1, A2]] extends PairArr[A1, ArrA1, A2, A]
{ type ThisT <: IntNPairArr[A1, ArrA1, A2, A]

  /** The backing Array for the first elements of the pairs. */
  def a1ArrayInt: Array[Int]

  def newFromArrays(a1Array: Array[Int], a2Array: Array[A2]): ThisT

  def filterOnA1(f: A1 => Boolean)(implicit ct: ClassTag[A2]): ThisT =
  { val a1Buffer = new ArrayBuffer[Int]()
    val a2Buffer = new ArrayBuffer[A2]()
    foreach{ p =>
      if (f(p.a1))
      { p.a1.intForeach(a1Buffer.append(_))
        a2Buffer.append(p.a2)
      }
    }
    newFromArrays(a1Buffer.toArray, a2Buffer.toArray)
  }
}

trait IntNPairBuff[A1 <: ElemIntN, A2, A <: ElemIntNPair[A1, A2]] extends PairBuff[A1, A2, A]
{ def a1IntBuffer: ArrayBuffer[Int]
  override final def grows(newElems: Arr[A]): Unit = newElems.foreach(grow)
}

trait IntNPairArrBuilder[B1 <: ElemIntN, ArrB1 <: IntNArr[B1], B2, B <: ElemIntNPair[B1, B2], ArrB <: IntNPairArr[B1, ArrB1, B2, B]] extends
  PairArrBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: IntNPairBuff[B1, B2, B]
  implicit def b2CT: ClassTag[B2]
  def a1IntNum: Int
  def buffFromBuffers(a1Buffer: ArrayBuffer[Double], a2Buffer: ArrayBuffer[B2]): BuffT
  def arrFromArrays(a1ArrayInt: Array[Double], a2Array: Array[B2]): ArrB
  final override def arrUninitialised(length: Int): ArrB = arrFromArrays(new Array[Double](length * a1IntNum), new Array[B2](length))
  final override def newBuff(length: Int): BuffT = buffFromBuffers(new ArrayBuffer[Double](length * a1IntNum), new ArrayBuffer[B2](length))
  inline final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)
}

/** Helper trait for Companion objects of [[IntNPairArr]] classes. */
trait IntNPairArrCompanion[A1 <: ElemIntN, ArrA1 <: IntNArr[A1]]
{
  /** The number of [[Double]] values that are needed to construct an element of the defining-sequence. */
  def elemNumInts: Int
}