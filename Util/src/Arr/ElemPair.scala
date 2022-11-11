/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** These classes are for use in [[PairArr]]s. */
trait ElemPair[A1, A2] extends Any
{ /** The first component of this pair. */
  def a1: A1

  /** The second component of this pair. */
  def a2: A2
}

/** An [[Arr]] of pairs [[ElemPair]]. These classes allow convenient methods to map and filter on just one component of the pair. They and their
 *  associated [[PairArrMapBuilder]] and [[PairBuff]] classes also allow for efficient storage by using 2 Arrays of the components of the pairs rather
 *  than one array of the pairs. It is particularly designed for efficient maoOnA1 operations, where we want to map over the first part of the pair
 *  while leaving the second component of the pair unchanged. So sub traits and classes specialise on a1 the first component of the pair. There are no
 *  filterMap methods. You must map then filter. */
trait PairArr[A1, A1Arr <: Arr[A1], A2, A <: ElemPair[A1, A2]] extends Arr[A]
{ type ThisT <: PairArr[A1, A1Arr, A2, A]

  /** Returns the specialist sequence collection for the A1s. Probably not required most of the time but the method is included for completeness.  */
  def a1Arr: A1Arr

  /** The Array for the A2 components of the pairs. */
  def a2Array: Array[A2]

  /** Returns an [[RArr]] of the A2s, even if a better more specialist collection exists for the type. Probably not required most of the time but the
   *  method is included for completeness.  */
  def a2RArr: RArr[A2] = new RArr[A2](a2Array)

  /** Returns the specialist sequence collection for the A2s, as determined by implicit look up. Probably not required most of the time but the method
   *  is included for completeness.  */
  def a2Arr[A2Arr <: Arr[A2]](implicit build: ArrMapBuilder[A2, A2Arr]): A2Arr = a2Array.mapArr(a2 => a2)

  /** Maps the first component of the pairs, dropping the second. */
  def a1Map[B, ArrB <: Arr[B]](f: A1 => B)(implicit builder: ArrMapBuilder[B, ArrB]): ArrB = a1Arr.map(f)

  /** Maps the second component of the pairs, dropping the first. */
  def a2Map[B, ArrB <: Arr[B]](f: A2 => B)(implicit builder: ArrMapBuilder[B, ArrB]): ArrB = a2Array.mapArr(f)

  /** Just a map method that avoids unnecessarily constructing the pairs and takes a function from the components. */
  def pairMap[B, ArrB <: Arr[B]](f: (A1, A2) => B)(implicit builder: ArrMapBuilder[B, ArrB]): ArrB ={
    var i = 0
    val res = builder.uninitialised(length)
    while(i < length)
    { val newB = f(a1Index(i), a2Index(i))
      res.unsafeSetElem(i, newB)
      i += 1
    }
    res
  }

  /** Indexes the first component of the pair. */
  def a1Index(index: Int): A1

  /** Indexes the second component of the pair. */
  def a2Index(index: Int): A2 = a2Array(index)

  /** Maps the sequence of pairs to a new sequence of pairs, but leaving the second component of the pairs unchanged. */
  def mapOnA1[B1, ArrB1 <: Arr[B1], B <: ElemPair[B1, A2], ArrB <: PairArr[B1, ArrB1, A2, B]](f: A1 => B1)(implicit
    build: PairArrMapBuilder[B1, ArrB1, A2, B, ArrB]): ArrB =
  { val b1Arr: ArrB1 = a1Arr.map(f)(build.b1ArrBuilder)
    build.arrFromArrAndArray(b1Arr, a2Array)
  }

  /** Takes a function from A1 to Option[B1]. The None results are filtered out the B1 values of the sum are paired with their old corresponding A2
   * values to make the new pairs of type [[ElemPair]][B1, A2].  */
  def optMapOnA1[B1, ArrB1 <: Arr[B1], B <: ElemPair[B1, A2], ArrB <: PairArr[B1, ArrB1, A2, B]](f: A1 => Option[B1])(implicit
    build: PairArrMapBuilder[B1, ArrB1, A2, B, ArrB], ct: ClassTag[A2]): ArrB =
  { val a1Buff = build.newB1Buff()
    val a2Buff = new ArrayBuffer[A2]()
    foreach{ pair =>
      f(pair.a1).foreach{ poly =>
        build.b1BuffGrow(a1Buff, poly)
        a2Buff.append(pair.a2)
      }
    }
    build.arrFromBuffs(a1Buff, a2Buff)
  }

  /** filters this sequence using a predicate upon the A1 components of the pairs. */
  def filterOnA1(f: A1 => Boolean)(implicit build: PairArrMapBuilder[A1, A1Arr, A2, A, ThisT], ct: ClassTag[A2]): ThisT =
  { val buff1 = build.newB1Buff()
    val buff2 = new ArrayBuffer[A2]()
    var i = 0
    a1Arr.foreach { a1 =>
      if (f(a1)) {
        buff1.grow(a1)
        buff2.append(a2Array(i))
      }
      i += 1
    }
    build.arrFromBuffs(buff1, buff2)
  }

  final override def length: Int = a2Array.length

  def a1ByA2(key: A2): A1 = {
    var i = 0
    var res: Option[A1] = None
    while(i < length & res == None){
      if (a2Index(i) == key) res = Some(a1Index(i))
      i += 1
    }
    res match {
      case Some(a1) => a1
      case None => excep("Not found")
    }
  }
}

/** An efficient [[Buff]] for [[ElemPair]]s where the components are stored in separate buffers. The type parameter B, along with B1 and B2 are used
 * because these [[Buff]]s will normally be used with map(f: A => B) and flatMap(f: A => M[B]) type methods. */
trait PairBuff[B1, B2, B <: ElemPair[B1, B2]] extends Any with Buff[B]
{ /** ArrayBuffer for the B2 components of the pairs. */
  def b2Buffer: ArrayBuffer[B2]

  override def length: Int = b2Buffer.length
  override def fElemStr: B => String = _.toString
}

trait PairArrCommonBuilder[B1, ArrB1 <: Arr[B1], B2, ArrB <: PairArr[B1, ArrB1, B2, _]] extends SeqLikeCommonBuilder[ArrB]
{
  type BuffT <: PairBuff[B1, B2, _]

  /** The type of the [[Buff]] for accumulating B1s. */
  type B1BuffT <: Buff[B1]

  /** ClassTag for building Arrays and ArrayBuffers of B2s. */
  implicit def b2ClassTag: ClassTag[B2]

  /** Constructs a new empty [[Buff]] for the B1 components of the pairs. */
  def newB1Buff(): B1BuffT

  /** Expands / appends the B1 [[Buff]] with a songle element of B1. */
  def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit

  /** Constructs an [[Arr]] of B from the [[Buff]]s of the B1 and B2 components. */
  def arrFromBuffs(a1Buff: B1BuffT, b2s: ArrayBuffer[B2]): ArrB
}

/** Builder for [[ElemPair]]s. As with all builders, we use B as the type parameter, because builders are nearly always used with some kind of map /
 * flatMap methods where B corresponds to the map function f: A => B. */
trait PairArrMapBuilder[B1, ArrB1 <: Arr[B1], B2, B <: ElemPair[B1, B2], ArrB <: PairArr[B1, ArrB1, B2, B]] extends
  PairArrCommonBuilder[B1, ArrB1, B2, ArrB] with ArrMapBuilder[B, ArrB]
{
  type BuffT <: PairBuff[B1, B2, B]

  /** Builder for an Arr of the first element of the pair. */
  def b1ArrBuilder: ArrMapBuilder[B1, ArrB1]

  final def b1Uninitialised(length: Int): ArrB1 = b1ArrBuilder.uninitialised(length)

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. */
  def arrFromArrAndArray(b1Arr: ArrB1, b2s: Array[B2]): ArrB
}

/** [[ArrFlatbuilder]] for [[ElemPair]]s. */
trait PairArrFlatBuilder[B1, ArrB1 <: Arr[B1], B2, ArrB <: PairArr[B1, ArrB1, B2, _]] extends PairArrCommonBuilder[B1, ArrB1, B2, ArrB] with
  ArrFlatBuilder[ArrB]