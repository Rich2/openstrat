/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, reflect.ClassTag

class StrPair[A2](val a1: String, val a2: A2) extends PairNoA1ParamElem[String, A2]

class StrPairArr[A2](val a1Array: Array[String], val a2Array: Array[A2]) extends PairNoA1PramArr[String, StrArr, A2, StrPair[A2]]
{ override type ThisT = StrPairArr[A2]
  override def typeStr: String = "StringPairArr"
  override def a1Arr: StrArr = new StrArr(a1Array)
  override def a1Index(index: Int): String = a1Array(index)
  override def setA1Unsafe(index: Int, value: String): Unit = a1Array(index) = value

  override def replaceA1byA2(key: A2, newValue: String): StrPairArr[A2] =
  { val newA1s = new Array[String](length)
    a1Array.copyToArray(newA1s)
    var i = 0
    while (i < length) {
      if (key == a2Index(i)) a1Array(i) = newValue; i += 1
    }
    new StrPairArr[A2](newA1s, a2Array)
  }

  override def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): StrPairArr[A2] =
    new StrPairArr[A2](new Array[String](length), new Array[A2](length))

  override def apply(index: Int): StrPair[A2] = new StrPair[A2](a1Array(index), a2Array(index))
  override def setElemUnsafe(i: Int, newElem: StrPair[A2]): Unit = { a1Array(i) = newElem.a1; a2Array(i) = newElem.a2 }

  override def fElemStr: StrPair[A2] => String = _.toString

  @targetName("append") final def +%(operand: StrPair[A2])(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: String, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[String](length + 1)
    a1Array.copyToArray(newA1Array)
    newA1Array(length) = a1
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    new StrPairArr[A2](newA1Array, newA2Array)
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
}

object StrPairArr
{
  def apply[A2](pairs: (String, A2)*)(implicit ct: ClassTag[A2]): StrPairArr[A2] =
  { val len = pairs.length
    val a1Array = new Array[String](len)
    val a2Array = new Array[A2](len)
    pairs.iForeach{ (i, p) => a1Array(i) = p._1; a2Array(i) = p._2 }
    new StrPairArr[A2](a1Array, a2Array)
  }
}