/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** These classes are for use in [[PairArrRestrict]]s. */
trait PairElem[A1, A2] extends Any
{ /** The first component of this pair. */
  def a1: A1

  /** The second component of this pair. */
  def a2: A2
}



/** An [[Arr]] of pairs [[PairElem]]. These classes allow convenient methods to map and filter on just one component of the pair. They and their
 *  associated [[PairArrMapBuilder]] and [[PairBuff]] classes also allow for efficient storage by using 2 Arrays of the components of the pairs rather
 *  than one array of the pairs. It is particularly designed for efficient maoOnA1 operations, where we want to map over the first part of the pair
 *  while leaving the second component of the pair unchanged. So sub traits and classes specialise on a1 the first component of the pair. There are no
 *  filterMap methods. You must map then filter. */
trait PairArr[A1, A1Arr <: Arr[A1], A2, A <: PairElem[A1, A2]] extends Arr[A]
{ type ThisT <: PairArr[A1, A1Arr, A2, A]

  /** Indexes the first component of the pair. */
  def a1Index(index: Int): A1

  /** Indexes the second component of the pair. */
  def a2Index(index: Int): A2 = a2Array(index)

  /** Returns the specialist sequence collection for the A1s. Probably not required most of the time but the method is included for completeness. */
  def a1Arr: A1Arr

  /** The Array for the A2 components of the pairs. Should be rarely reuired by end user. The a2Arr and the a2RArr methods are generally preferred. */
  def a2Array: Array[A2]

  /** Returns an [[RArr]] of the A2s, even if a better more specialist collection exists for the type. Probably not required most of the time but the
   * method is included for completeness. */
  def a2RArr: RArr[A2] = new RArr[A2](a2Array)

  /** Returns the specialist sequence collection for the A2s, as determined by implicit look up. Probably not required most of the time but the method
   * is included for completeness. */
  def a2Arr[A2Arr <: Arr[A2]](implicit build: ArrMapBuilder[A2, A2Arr]): A2Arr = a2Array.mapArr(a2 => a2)

  /** Maps the first component of the pairs, dropping the second. */
  def a1Map[B, ArrB <: Arr[B]](f: A1 => B)(implicit builder: ArrMapBuilder[B, ArrB]): ArrB = a1Arr.map(f)

  /** Maps the second component of the pairs, dropping the first. */
  def a2Map[B, ArrB <: Arr[B]](f: A2 => B)(implicit builder: ArrMapBuilder[B, ArrB]): ArrB = a2Array.mapArr(f)

  def pairForeach(f: (A1, A2) => Unit): Unit = {
    var i = 0
    while (i < length) {
      f(a1Index(i), a2Index(i)); i += 1
    }
  }


  /** Just a map method that avoids unnecessarily constructing the pairs and takes a function from the components to te parameter type B. */
  def pairMap[B, ArrB <: Arr[B]](f: (A1, A2) => B)(implicit builder: ArrMapBuilder[B, ArrB]): ArrB = {
    var i = 0
    val res = builder.uninitialised(length)
    while (i < length) {
      val newB = f(a1Index(i), a2Index(i))
      res.unsafeSetElem(i, newB)
      i += 1
    }
    res
  }

  /** Just a flatMap method that avoids unnecessarily constructing the pairs and takes a function from the components to te parameter type ArrB. */
  def pairFlatMap[ArrB <: Arr[_]](f: (A1, A2) => ArrB)(implicit build: ArrFlatBuilder[ArrB]): ArrB = {
    val buff = build.newBuff()
    pairForeach { (a1, a2) =>
      val newBs = f(a1, a2)
      build.buffGrowArr(buff, newBs)
    }
    build.buffToSeqLike(buff)
  }

  /** Maps the sequence of pairs to a new sequence of pairs, but leaving the second component of the pairs unchanged. */
  def mapOnA1[B1, ArrB1 <: Arr[B1], B <: PairElem[B1, A2], ArrB <: PairArr[B1, ArrB1, A2, B]](f: A1 => B1)(implicit
    build: PairArrMapBuilder[B1, ArrB1, A2, B, ArrB]): ArrB =
  { val b1Arr: ArrB1 = a1Arr.map(f)(build.b1ArrBuilder)
    build.arrFromArrAndArray(b1Arr, a2Array)
  }

  /** Maps each A1 to an Arr[B1] combines each of those new B1s with the same old A2 to produce a [[PairArrRestrict]] of [[PairElem]][B1, A2]. Then flattens
   * these new [[PairArrRestrict]]s to make a single [[PairArrRestrict]] */
  def flatMapOnA1[B1, ArrB1 <: Arr[B1], ArrB <: PairArrRestrict[B1, ArrB1, A2, _]](f: A1 => ArrB1)(implicit
                                                                                                   build: PairArrFlatBuilder[B1, ArrB1, A2, ArrB]): ArrB = {
    val buff = build.newBuff()
    pairForeach { (a1, a2) => f(a1).foreach(b1 => buff.pairGrow(b1, a2)) }
    build.buffToSeqLike(buff)
  }

  /** Takes a function from A1 to Option[B1]. The None results are filtered out the B1 values of the sum are paired with their old corresponding A2
   * values to make the new pairs of type [[PairElem]][B1, A2]. */
  def optMapOnA1[B1, ArrB1 <: Arr[B1], B <: PairElem[B1, A2], ArrB <: PairArr[B1, ArrB1, A2, B]](f: A1 => Option[B1])(implicit
    build: PairArrMapBuilder[B1, ArrB1, A2, B, ArrB]): ArrB =
  { val buff = build.newBuff()
    pairForeach { (a1, a2) => f(a1).foreach(b1 => buff.pairGrow(b1, a2)) }
    build.buffToSeqLike(buff)
  }

  /** filters this sequence using a predicate upon the A1 components of the pairs. */
  def filterOnA1(f: A1 => Boolean)(implicit build: PairArrMapBuilder[A1, A1Arr, A2, A, ThisT]): ThisT = {
    val buff = build.newBuff()
    pairForeach { (a1, a2) => if (f(a1)) buff.pairGrow(a1, a2) }
    build.buffToSeqLike(buff)
  }

  final override def length: Int = a2Array.length

  /** Treats this [[PairArrRestrict]] as a [[Map]] with the A2 values as a the key. Will throw an exception if the given A2 value is not found. */
  def a1FromA2Key(key: A2): A1 = {
    var i = 0
    var res: Option[A1] = None
    while (i < length & res == None) {
      if (a2Index(i) == key) res = Some(a1Index(i))
      i += 1
    }
    res match {
      case Some(a1) => a1
      case None => excep(s"The a2: A2 of value $key was not found")
    }
  }

  def getA2Index(key: A2): Int = findA2Index(key).get

  def findA2Index(key: A2): Option[Int] = {
    var i = 0
    var res: Option[Int] = None
    while (res == None & i < length) if (key == a2Array(i)) res = Some(i) else i += 1
    res
  }

  def unsafeSetA1(index: Int, value: A1): Unit

  def a2Exists(key: A2): Boolean = {
    var res = false
    var i = 0
    while (!res & i < length) if (key == a2Array(i)) res = true else i += 1
    res
  }
}

trait PairArrRestrict[A1, A1Arr <: Arr[A1], A2, A <: PairElem[A1, A2]] extends PairArr[A1, A1Arr, A2, A]
{ type ThisT <: PairArrRestrict[A1, A1Arr, A2, A]

  /** Returns a copy of this [[PairArrRestrict]] where the A1 component is replaced for any pairs where the A2 value matches the given parameter. this method
   * treats the [[PairArrRestrict]] as a Scala [[Map]] class with the A2s as the keys and the A1s as the values. */
  def replaceA1byA2(key: A2, newValue: A1): ThisT

  def replaceA1byA2OrAppend(key: A2, newValue: A1)(implicit classTag: ClassTag[A2]): ThisT =
    if (a2Exists(key)) replaceA1byA2(key, newValue) else appendPair(newValue, key)

  /** Returns a new uninitialised [[PairArrRestrict]] of the same final type. */
  def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): ThisT

  @targetName("append") def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT

  def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT
}

/** An efficient [[Buff]] for [[PairElem]]s where the components are stored in separate buffers. The type parameter B, along with B1 and B2 are used
 * because these [[Buff]]s will normally be used with map(f: A => B) and flatMap(f: A => M[B]) type methods. */
trait PairBuff[B1, B2, B <: PairElem[B1, B2]] extends Any with Buff[B]
{ /** ArrayBuffer for the B2 components of the pairs. */
  def b2Buffer: ArrayBuffer[B2]

  /** Grows a [[PairBuff]] by adding the elements of the pair to the b1 and b2 buffers. */
  def pairGrow(b1: B1, b2: B2): Unit

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

  def newB2Buffer(): ArrayBuffer[B2] = new ArrayBuffer[B2]()

  /** Expands / appends the B1 [[Buff]] with a songle element of B1. */
  def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit

  /** Constructs an [[Arr]] of B from the [[Buff]]s of the B1 and B2 components. */
  def arrFromBuffs(a1Buff: B1BuffT, b2s: ArrayBuffer[B2]): ArrB
}

/** Builder for [[PairElem]]s. As with all builders, we use B as the type parameter, because builders are nearly always used with some kind of map /
 * flatMap methods where B corresponds to the map function f: A => B. */
trait PairArrMapBuilder[B1, ArrB1 <: Arr[B1], B2, B <: PairElem[B1, B2], ArrB <: PairArr[B1, ArrB1, B2, B]] extends
  PairArrCommonBuilder[B1, ArrB1, B2, ArrB] with ArrMapBuilder[B, ArrB]
{
  type BuffT <: PairBuff[B1, B2, B]

  /** Builder for an Arr of the first element of the pair. */
  def b1ArrBuilder: ArrMapBuilder[B1, ArrB1]

  final def b1Uninitialised(length: Int): ArrB1 = b1ArrBuilder.uninitialised(length)

  final def b2Uninitialised(length: Int): Array[B2] = new Array[B2](length)

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. */
  def arrFromArrAndArray(b1Arr: ArrB1, b2s: Array[B2]): ArrB
}

object PairArrMapBuilder{
  implicit def rArrMapImplicit[B1, B2](implicit ct1: ClassTag[B1], ct2: ClassTag[B2]): RPairArrMapBuilder[B1, B2] = new RPairArrMapBuilder[B1, B2]
}

/** [[ArrFlatbuilder]] for [[PairElem]]s. */
trait PairArrFlatBuilder[B1, ArrB1 <: Arr[B1], B2, ArrB <: PairArr[B1, ArrB1, B2, _]] extends PairArrCommonBuilder[B1, ArrB1, B2, ArrB] with
  ArrFlatBuilder[ArrB]