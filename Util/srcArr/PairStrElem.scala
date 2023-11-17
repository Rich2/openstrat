/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

/** [[PairElem]] where the first component of the pair is a [[String]]. */
class PairStrElem[A2](val a1: String, val a2: A2) extends PairFinalA1Elem[String, A2]

object PairStrElem
{ def apply[A2](a1: String,  a2: A2): PairStrElem[A2] = new PairStrElem[A2](a1, a2)
}

/** An [[Arr]] of [[PairElem]]s where the first component of the pair is a [[String]]. */
class ArrPairStr[A2](val a1Array: Array[String], val a2Array: Array[A2]) extends PairArrFinalA1[String, StrArr, A2, PairStrElem[A2]]
{ override type ThisT = ArrPairStr[A2]
  override def typeStr: String = "StringPairArr"
  override def a1Arr: StrArr = new StrArr(a1Array)
  override def a1Index(index: Int): String = a1Array(index)
  override def setA1Unsafe(index: Int, value: String): Unit = a1Array(index) = value

  override def replaceA1byA2(key: A2, newValue: String): ArrPairStr[A2] =
  { val newA1s = new Array[String](length)
    a1Array.copyToArray(newA1s)
    var i = 0
    while (i < length) { if (key == a2Index(i)) a1Array(i) = newValue; i += 1 }
    new ArrPairStr[A2](newA1s, a2Array)
  }

  override def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): ArrPairStr[A2] =
    new ArrPairStr[A2](new Array[String](length), new Array[A2](length))

  override def apply(index: Int): PairStrElem[A2] = new PairStrElem[A2](a1Array(index), a2Array(index))
  override def setElemUnsafe(i: Int, newElem: PairStrElem[A2]): Unit = { a1Array(i) = newElem.a1; a2Array(i) = newElem.a2 }

  override def fElemStr: PairStrElem[A2] => String = _.toString

  @targetName("append") final def +%(operand: PairStrElem[A2])(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: String, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[String](length + 1)
    a1Array.copyToArray(newA1Array)
    newA1Array(length) = a1
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    new ArrPairStr[A2](newA1Array, newA2Array)
  }

  @targetName("prepend") final def %:(operand: PairStrElem[A2])(implicit ct: ClassTag[A2]): ArrPairStr[A2] = {
    val newA1Array = new Array[String](length + 1)
    newA1Array(0) = operand.a1
    a1Array.copyToArray(newA1Array, 1)
    val newA2Array = new Array[A2](length + 1)
    newA2Array(0) = operand.a2
    a2Array.copyToArray(newA2Array, 1)
    new ArrPairStr[A2](newA1Array, newA2Array)
  }

  /** Returns the first A2 value whose A1 value matches the key parameter or failing that the first. If none fully match will return the one that
   *  matches the most [[Char]]s from the beginning as long that satisfies the minium char number. */
  def findChars(key: String, minChars: Int = 2): Option[A2] =
  { var i = 0
    var acc = 0
    var res: Option[A2] = None
    var continue: Boolean = true
    while(i < length & continue)
    { val poss = a1Index(i)
      if (poss == key) { res = Some(a2Index(i)); continue = false }
      else
      { val charNum = key.compareChars(poss)
        if (charNum > acc & charNum >= minChars) { res = Some(a2Index(i)); acc = charNum }
      }
      i += 1
    }
    res
  }

  /** filters this sequence using a predicate upon the A2 components of the pairs. */
  def filterOnA2(f: A2 => Boolean)(implicit ct: ClassTag[A2]): ThisT =
  { val buff1 = Buffer[String]()
    val buff2 = Buffer[A2]()
    pairForeach { (a1, a2) => if (f(a2)){ buff1.append(a1); buff2.append(a2) } }
    new ArrPairStr[A2](buff1.toArray, buff2.toArray)
  }

  /** filters out elements of this sequence using a predicate upon the A2 components of the pairs. */
  def filterNotOnA2(f: A2 => Boolean)(implicit ct: ClassTag[A2]): ThisT = filterOnA2(a2 => !f(a2))
}

object ArrPairStr
{
  def apply[A2](pairs: (String, A2)*)(implicit ct: ClassTag[A2]): ArrPairStr[A2] =
  { val len = pairs.length
    val a1Array = new Array[String](len)
    val a2Array = new Array[A2](len)
    pairs.iForeach{ (i, p) => a1Array(i) = p._1; a2Array(i) = p._2 }
    new ArrPairStr[A2](a1Array, a2Array)
  }
}

/** Builder for [[Arr]]s of [[PairStrElem]]s via map method. */
class BuilderArrPairStrMap[B2](implicit val b2ClassTag: ClassTag[B2]) extends BuilderArrPairMap[String, StrArr, B2, PairStrElem[B2], ArrPairStr[B2]]
{ override type BuffT = StrPairBuff[B2]
  override type B1BuffT = StringBuff
  override def b1ArrBuilder: BuilderArrMap[String, StrArr] = BuilderArrString
  override def arrFromArrAndArray(b1Arr: StrArr, b2s: Array[B2]): ArrPairStr[B2] = new ArrPairStr[B2](b1Arr.unsafeArray, b2s)

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  override def buffGrow(buff: StrPairBuff[B2], newElem: PairStrElem[B2]): Unit = ???

  /** Creates a new uninitialised [[Arr]] of type ArrB of the given length. */
  override def uninitialised(length: Int): ArrPairStr[B2] = ???

  /** Sets the value in a [[SeqLike]] of type BB. This is usually used in conjunction with uninitialised method. */
  override def indexSet(seqLike: ArrPairStr[B2], index: Int, elem: PairStrElem[B2]): Unit = ???


  /** Constructs a new empty [[BuffSequ]] for the B1 components of the pairs. */
  override def newB1Buff(): StringBuff = ???

  /** Expands / appends the B1 [[BuffSequ]] with a single element of B1. */
  override def b1BuffGrow(buff: StringBuff, newElem: String): Unit = ???

  /** Constructs an [[Arr]] of B from the [[BuffSequ]]s of the B1 and B2 components. */
  override def arrFromBuffs(a1Buff: StringBuff, b2s: ArrayBuffer[B2]): ArrPairStr[B2] = ???

  /** Creates a new empty [[BuffSequ]] with a default capacity of 4 elements. */
  override def newBuff(length: Int): StrPairBuff[B2] = StrPairBuff[B2]()

  /** converts a the buffer type to the target compound class. */
  override def buffToSeqLike(buff: StrPairBuff[B2]): ArrPairStr[B2] = new ArrPairStr[B2](buff.strBuffer.toArray, buff.b2Buffer.toArray)
}

class StrPairBuff[B2](val strBuffer: ArrayBuffer[String], val b2Buffer: ArrayBuffer[B2]) extends BuffPair[String, B2, PairStrElem[B2]]
{ override type ThisT = StrPairBuff[B2]
  override def typeStr: String = "StrPairBuff"
  override def pairGrow(b1: String, b2: B2): Unit = { strBuffer.append(b1); b2Buffer.append(b2) }
  override def grow(newElem: PairStrElem[B2]): Unit = { strBuffer.append(newElem.a1); b2Buffer.append(newElem.a2) }
  override def apply(index: Int): PairStrElem[B2] = PairStrElem[B2](strBuffer(index), b2Buffer(index))
  override def setElemUnsafe(i: Int, newElem: PairStrElem[B2]): Unit = { strBuffer(i) = newElem.a1; b2Buffer(i) == newElem.a2 }
}

object StrPairBuff{
  def apply[B2](): StrPairBuff[B2] = new StrPairBuff[B2](new ArrayBuffer[String](4), new ArrayBuffer[B2](4))
}

object StrStrPairArr
{
  def apply(strings: String *): ArrPairStr[String] =
  { if(strings.length.isOdd) excep("Odd number of Strings for StrStrPaiArr factory apply method.")
    val len = strings.length / 2
    val array1 = new Array[String](len)
    val array2 = new Array[String](len)
    iUntilForeach(0, len){ i =>
      array1(i) = strings(i * 2)
      array2(i) = strings(i * 2 + 1)
    }
    new ArrPairStr(array1, array2)
  }
}