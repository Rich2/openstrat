/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** These classes are for use in [[ArrPair]]s. */
trait PairElem[A1, A2] extends Any
{ /** The first component of this pair. */
  def a1: A1

  /** The second component of this pair. */
  def a2: A2
}

/** An [[Arr]] of [[PairElem]]s. These classes allow convenient methods to map and filter on just one component of the pair. They and their associated
 *  [[BuilderArrPairMap]] and [[BuffPair]] classes also allow for efficient storage by using 2 Arrays of the components of the pairs rather than one array of
 *  the pairs. It is particularly designed for efficient mapOnA1 operations, where we want to map over the first part of the pair while leaving the second
 *  component of the pair unchanged. So sub traits and classes specialise on a1 the first component of the pair. There are no filterMap methods. You must map
 *  then filter. */
trait ArrPair[A1, A1Arr <: Arr[A1], A2, A <: PairElem[A1, A2]] extends Arr[A]
{ type ThisT <: ArrPair[A1, A1Arr, A2, A]

  /** Indexes the first component of the pair. */
  def a1Index(index: Int): A1

  /** Indexes the second component of the pair. */
  def a2Index(index: Int): A2 = a2Array(index)

  /** Returns the specialist sequence collection for the A1s. Probably not required most of the time but the method is included for completeness. */
  def a1Arr: A1Arr

  /** The Array for the A2 components of the pairs. Should be rarely reuired by end user. The a2Arr and the a2RArr methods are generally preferred. */
  def a2Array: Array[A2]

  /** Returns an [[RArr]] of the A2s, even if a better more specialist collection exists for the type. Probably not required most of the time but the method is
   * included for completeness. */
  def a2RArr: RArr[A2] = new RArr[A2](a2Array)

  /** Returns the specialist sequence collection for the A2s, as determined by implicit look up. Probably not required most of the time but the method is
   * included for completeness. */
  def a2Arr[A2Arr <: Arr[A2]](implicit build: BuilderArrMap[A2, A2Arr]): A2Arr = a2Array.mapArr(a2 => a2)

  /** Maps the first component of the pairs, dropping the second. */
  def a1Map[B, ArrB <: Arr[B]](f: A1 => B)(implicit builder: BuilderArrMap[B, ArrB]): ArrB = a1Arr.map(f)

  /** Maps the second component of the pairs, dropping the first. */
  def a2Map[B, ArrB <: Arr[B]](f: A2 => B)(implicit builder: BuilderArrMap[B, ArrB]): ArrB = a2Array.mapArr(f)

  def pairForeach(f: (A1, A2) => Unit): Unit =
  { var i = 0
    while (i < length) { f(a1Index(i), a2Index(i)); i += 1 }
  }

  /** Just a map method that avoids unnecessarily constructing the pairs and takes a function from the components to te parameter type B. */
  def pairMap[B, ArrB <: Arr[B]](f: (A1, A2) => B)(implicit builder: BuilderArrMap[B, ArrB]): ArrB =
  { var i = 0
    val res = builder.uninitialised(length)
    while (i < length)
    { val newB = f(a1Index(i), a2Index(i))
      res.setElemUnsafe(i, newB)
      i += 1
    }
    res
  }

  /** Just a flatMap method that avoids unnecessarily constructing the pairs and takes a function from the components to te parameter type ArrB. */
  def pairFlatMap[ArrB <: Arr[?]](f: (A1, A2) => ArrB)(implicit build: BuilderArrFlat[ArrB]): ArrB = {
    val buff = build.newBuff()
    pairForeach { (a1, a2) =>
      val newBs = f(a1, a2)
      build.buffGrowArr(buff, newBs)
    }
    build.buffToSeqLike(buff)
  }

  /** Maps the sequence of pairs to a new sequence of pairs, but leaving the second component of the pairs unchanged. */
  def mapOnA1[B1, ArrB1 <: Arr[B1], B <: PairElem[B1, A2], ArrB <: ArrPair[B1, ArrB1, A2, B]](f: A1 => B1)(implicit
    build: BuilderArrPairMap[B1, ArrB1, A2, B, ArrB]): ArrB =
  { val b1Arr: ArrB1 = a1Arr.map(f)(build.b1ArrBuilder)
    build.arrFromArrAndArray(b1Arr, a2Array)
  }

  /** Maps each A1 to an Arr[B1] combines each of those new B1s with the same old A2 to produce a [[ArrPairFinalA1]] of [[PairFinalA1Elem]][B1, A2]. Then
   * flattens these new [[ArrPairFinalA1]]s to make a single [[ArrPairFinalA1]] */
  def flatMapOnA1[B1, ArrB1 <: Arr[B1], ArrB <: ArrPairFinalA1[B1, ArrB1, A2, ?]](f: A1 => ArrB1)(implicit build: BuilderArrPairFlat[B1, ArrB1, A2, ArrB]):
  ArrB =
  { val buff = build.newBuff()
    pairForeach { (a1, a2) => f(a1).foreach(b1 => buff.pairGrow(b1, a2)) }
    build.buffToSeqLike(buff)
  }

  /** Takes a function from A1 to Option[B1]. The None results are filtered out the B1 values of the sum are paired with their old corresponding A2 values to
   * make the new pairs of type [[PairFinalA1Elem]][B1, A2]. For an [[RPairArr]] return type use the optMapRefOnA1 method. */
  def optMapOnA1[B1, ArrB1 <: Arr[B1], B <: PairFinalA1Elem[B1, A2], ArrB <: ArrPair[B1, ArrB1, A2, B]](f: A1 => Option[B1])(implicit
    build: BuilderArrPairMap[B1, ArrB1, A2, B, ArrB]): ArrB =
  { val buff = build.newBuff()
    pairForeach { (a1, a2) => f(a1).foreach(b1 => buff.pairGrow(b1, a2)) }
    build.buffToSeqLike(buff)
  }

  def optMapRefOnA1[B1, B <: PairFinalA1Elem[B1, A2]](f: A1 => Option[B1])(implicit ct1: ClassTag[B1], ct2: ClassTag[A2]): RPairArr[B1, A2] =
  { val buffer1 = new ArrayBuffer[B1]()
    val buffer2 = new ArrayBuffer[A2]()
    pairForeach { (a1, a2) => f(a1).foreach{b1 => buffer1.append(b1); buffer2.append(a2) } }
    new RPairArr[B1, A2](buffer1.toArray, buffer2.toArray)
  }

  /** filters this sequence using a predicate upon the A1 components of the pairs. */
  def filterOnA1(f: A1 => Boolean)(implicit build: BuilderArrPairMap[A1, A1Arr, A2, A, ThisT]): ThisT =
  { val buff = build.newBuff()
    pairForeach { (a1, a2) => if (f(a1)) buff.pairGrow(a1, a2) }
    build.buffToSeqLike(buff)
  }

  final override def length: Int = a2Array.length
  final override def numElems: Int = a2Array.length

  /** Treats this [[ArrPairFinalA1]] as a [[Map]] with the A2 values as a the key. Will throw an exception if the given A2 value is not found. If you are
   * uncertain whether this pair sequence contains the A2 key, use the safe a2FindA1 method. */
  def a2GetA1(key: A2): A1 = a2FindA1(key) match
  { case Some(a1) => a1
    case None => excep(s"The a2: A2 of value $key was not found")
  }

  /** Treats this [[ArrPairFinalA1]] as a [[Map]] with the A2 values as a the key. Returns None if the key value is absent. If you are certain that this pair
   * sequence contains the A2 key, use the a2GetA1 method. */
  def a2FindA1(key: A2): Option[A1] =
  { var i = 0
    var res: Option[A1] = None
    while (i < length & res == None)
    { if (a2Index(i) == key) res = Some(a1Index(i))
      i += 1
    }
    res
  }

  /** Treats this [[ArrPairFinalA1]] as a [[Map]] with the A1 values as a the key. Will throw an exception if the given A1 value is not found. If you are
   * uncertain whether this pair sequence contains the A1 key value, use the safe a1FindA2 method.*/
  def a1GetA2(key: A1): A2 = a1FindA2(key) match
  { case Some(a1) => a1
    case None => excep(s"The a2: A2 of value $key was not found")
  }

  /** Treats this [[ArrPairFinalA1]] as a [[Map]] with the A1 values as a the key. Returns None if the key value is absent. If you are certain that this pair
   * sequence contains the A1 key, use the a1GetA2 method. */
  def a1FindA2(key: A1): Option[A2] =
  { var i = 0
    var res: Option[A2] = None
    while (i < length & res == None)
    { if (a1Index(i) == key) res = Some(a2Index(i))
      i += 1
    }
    res
  }

  def a2IndexGet(value: A2): Int = a2IndexFind(value).get

  def a2IndexFind(key: A2): Option[Int] =
  { var i = 0
    var res: Option[Int] = None
    while (res == None & i < length) if (key == a2Array(i)) res = Some(i) else i += 1
    res
  }

  def setA1Unsafe(index: Int, value: A1): Unit

  final def setA2Unsafe(index: Int, value: A2): Unit = a2Array(index) = value

  def a2Exists(value: A2): Boolean =
  { var res = false
    var i = 0
    while (!res & i < length) if (value == a2Array(i)) res = true else i += 1
    res
  }

  def strLines: String = mkStr(el => el.a1.toString + "; " + el.a2.toString, "\n")
}

/** An efficient [[BuffSequ]] for [[PairFinalA1Elem]]s where the components are stored in separate buffers. The type parameter B, along with B1 and B2 are used
 * because these [[BuffSequ]]s will normally be used with map(f: A => B) and flatMap(f: A => M[B]) type methods. */
trait BuffPair[B1, B2, B <: PairElem[B1, B2]] extends Any, BuffSequ[B]
{
  /** ArrayBuffer for the B2 components of the pairs. */
    def b2Buffer: ArrayBuffer[B2]

  /** Grows a [[BuffPair]] by adding the elements of the pair to the b1 and b2 buffers. */
  def pairGrow(b1: B1, b2: B2): Unit

  final override def length: Int = b2Buffer.length
  final override def numElems: Int = b2Buffer.length
  override def fElemStr: B => String = _.toString
}

/** Base trait for building [[ArrPair]] objects via map and flatMap methods. */
trait BuilderArrPair[B1, ArrB1 <: Arr[B1], B2, ArrB <: ArrPair[B1, ArrB1, B2, ?]] extends BuilderSeqLike[ArrB]
{ type BuffT <: BuffPair[B1, B2, ?]

  /** The type of the [[BuffSequ]] for accumulating B1s. */
  type B1BuffT <: BuffSequ[B1]

  /** ClassTag for building Arrays and ArrayBuffers of B2s. */
  implicit def b2ClassTag: ClassTag[B2]

  /** Constructs a new empty [[BuffSequ]] for the B1 components of the pairs. */
  def newB1Buff(): B1BuffT

  def newB2Buffer(): ArrayBuffer[B2] = new ArrayBuffer[B2]()

  /** Expands / appends the B1 [[BuffSequ]] with a single element of B1. */
  def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit

  /** Constructs an [[Arr]] of B from the [[BuffSequ]]s of the B1 and B2 components. */
  def arrFromBuffs(b1Buff: B1BuffT, b2Buffer: ArrayBuffer[B2]): ArrB
}

/** Builder for [[ArrPair]] objects via the map f: A => PairB, method. Hence the call site knows the type of the [[PairElem]]s that will make up the final
 * [[Arr]] object. */
trait BuilderArrPairMap[B1, ArrB1 <: Arr[B1], B2, B <: PairElem[B1, B2], ArrB <: ArrPair[B1, ArrB1, B2, B]] extends
  BuilderArrPair[B1, ArrB1, B2, ArrB] with BuilderArrMap[B, ArrB]
{
  type BuffT <: BuffPair[B1, B2, B]

  /** Builder for an Arr of the first element of the pair. */
  def b1ArrBuilder: BuilderArrMap[B1, ArrB1]

  final def b1Uninitialised(length: Int): ArrB1 = b1ArrBuilder.uninitialised(length)

  final def b2Uninitialised(length: Int): Array[B2] = new Array[B2](length)

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. */
  def arrFromArrAndArray(b1Arr: ArrB1, b2s: Array[B2]): ArrB
}

object BuilderArrPairMap extends BuilderArrPairMapPriority2
{ /** Implicit [[BuilderArrPairMap]] type class instances / evidence for [[ArrPairStr]]s. */
  implicit def strArrMapEv[B2](implicit ct: ClassTag[B2]): BuilderArrPairStrMap[B2] = new BuilderArrPairStrMap[B2]
}

trait BuilderArrPairMapPriority2
{ implicit def rArrMapImplicit[B1, B2](implicit ct1: ClassTag[B1], ct2: ClassTag[B2]): RPairArrMapBuilder[B1, B2] = new RPairArrMapBuilder[B1, B2]
}

/** Builder for [[ArrPair]] objects via the flatMap f: A => ArrPairB, method. Hence the call site doesn't know the type of the [[PairElem]]s that will
 *  make up the final [[Arr]] object. */
trait BuilderArrPairFlat[B1, ArrB1 <: Arr[B1], B2, ArrB <: ArrPair[B1, ArrB1, B2, ?]] extends BuilderArrPair[B1, ArrB1, B2, ArrB] with
  BuilderArrFlat[ArrB]