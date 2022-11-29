/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, reflect.ClassTag

class StringPair[A2](val a1: String, val a2: A2) extends PairElemRestrict[String, A2]

class StringPairArr[A2](val a1Array: Array[String], val a2Array: Array[A2]) extends PairArrRestrict[String, StrArr, A2, StringPair[A2]]
{ override type ThisT = StringPairArr[A2]
  override def typeStr: String = "StringPairArr"
  override def a1Arr: StrArr = new StrArr(a1Array)
  override def a1Index(index: Int): String = a1Array(index)
  override def unsafeSetA1(index: Int, value: String): Unit = a1Array(index) = value

  override def replaceA1byA2(key: A2, newValue: String): StringPairArr[A2] =
  { val newA1s = new Array[String](length)
    a1Array.copyToArray(newA1s)
    var i = 0
    while (i < length) {
      if (key == a2Index(i)) a1Array(i) = newValue; i += 1
    }
    new StringPairArr[A2](newA1s, a2Array)
  }

  override def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): StringPairArr[A2] =
    new StringPairArr[A2](new Array[String](length), new Array[A2](length))

  override def apply(index: Int): StringPair[A2] = new StringPair[A2](a1Array(index), a2Array(index))
  override def unsafeSetElem(i: Int, newValue: StringPair[A2]): Unit = { a1Array(i) = newValue.a1; a2Array(i) = newValue.a2 }

  override def fElemStr: StringPair[A2] => String = _.toString

  @targetName("append") final def +%(operand: StringPair[A2])(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: String, a2: A2)(implicit ct: ClassTag[A2]): ThisT = {
    val newA1Array = new Array[String](length + 1)
    a1Array.copyToArray(newA1Array)
    newA1Array(length) = a1
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    new StringPairArr[A2](newA1Array, newA2Array)
  }
}

object StringPairArr
{
  def apply[A2](pairs: (String, A2)*)(implicit ct: ClassTag[A2]): StringPairArr[A2] =
  { val len = pairs.length
    val a1Array = new Array[String](len)
    val a2Array = new Array[A2](len)
    pairs.iForeach{ (i, p) => a1Array(i) = p._1; a2Array(i) = p._2 }
    new StringPairArr[A2](a1Array, a2Array)
  }
}