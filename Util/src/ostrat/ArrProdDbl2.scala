/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

/** Base trait for Array[Double] base collections of Products of 2 Doubles. */
trait ArrProdDbl2[A <: ProdDbl2] extends Any with ArrProdDblN[A]
{
  type ThisT <: ArrProdDbl2[A]

  def productSize: Int = 2
  /** Method for creating new elements from 2 Doubles. */
  def elemBuilder(d1: Double, d2: Double): A
  def apply(index: Int): A = elemBuilder(array(2 * index), array(2 * index + 1))
  def getPair(index: Int): (Double, Double) = (array(2 * index), array(2 * index + 1))

  override def unsafeSetElem(index: Int, elem: A): Unit =
  { array(2 * index) = elem._1
    array(2 * index + 1) = elem._2
  }
  def head1: Double = array(0)
  def head2: Double = array(1)

  def foreachPairTail[U](f: (Double, Double) => U): Unit =
  { var count = 1
    while(count < length) { f(array(count * 2), array(count * 2 + 1)); count += 1 }
  }

  def elem1sArray: Array[Double] =
  { val res = new Array[Double](length)
    var count = 0
    while(count < length){ res(count) = array(count * 2); count += 1 }
    res
  }

  def elem2sArray: Array[Double] =
  { val res = new Array[Double](length)
    var count = 0
    while(count < length){ res(count) = array(count * 2 + 1); count += 1 }
    res
  }

  def toArrs: Arr[Arr[Double]] = mapArrSeq(el => Arr(el._1, el._2))
  def foreachArr(f: Arr[Double] => Unit): Unit = foreach(el => f(Arr(el._1, el._2)))
}

trait ArrBuffDbl2[A <: ProdDbl2, M <: ArrProdDbl2[A]] extends Any with ArrBuffDblN[A, M]
{ override def append(newElem: A): Unit = { buffer.append(newElem._1).append(newElem._2); () }
}

trait ProdDbl2sCompanion[T <: ProdDbl2, ST <: ArrProdDbl2[T]] extends ProdDblNsCompanion[T, ST]
{
  implicit val persistImplicit: ArrProdDbl2Persist[T, ST]
  def prodLen: Int = 2
  //implicit val factory: Int => ST = len => persistImplicit.fromArray(new Array[Double](len * 2))
  def apply(length: Int): ST = factory(length)
  def apply(elems: T*): ST =
  {
    val length = elems.length
    val res = factory(length)
    var count: Int = 0

    while (count < length)
    { res.array(count * 2) = elems(count)._1
      res.array(count * 2 + 1) = elems(count)._2
      count += 1
    }
    res
  }

  def doubles(elems: Double*): ST =
  {
    val arrLen: Int = elems.length
    val res = factory(elems.length / 2)
    var count: Int = 0

    while (count < arrLen)
    { res.array(count) = elems(count)
      count += 1
    }
    res
  }

  def fromList(list: List[T]): ST =
  {
    val arrLen: Int = list.length * 2
    val res = factory(list.length)
    var count: Int = 0
    var rem = list

    while (count < arrLen)
    { res.array(count) = rem.head._1
      count += 1
      res.array(count) = rem.head._2
      count += 1
      rem = rem.tail
    }
    res
  }
}


/** Both Persists and Builds ProductD2s collection classes. */
abstract class ArrProdDbl2Persist[A <: ProdDbl2, M <: ArrProdDbl2[A]](typeStr: String) extends ArrProdDblNPersist[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value._1
    buf += value._2
  }

  import pParse._
  override def syntaxDepth = 3
  /** Not sure about this implementation. */
  override def showSemi(thisColl: M): String = thisColl.mapArrSeq(el => el._1.str + ", " + el._2.str).mkString("; ")
  override def showComma(thisColl: M): String = show(thisColl)
  //override def show(thisColl: R): String = typeStr - showSemi(thisColl).enParenth
  override def fromParameterStatements(sts: Refs[Statement]): EMon[M] = ???
  override def fromClauses(clauses: Refs[Clause]): EMon[M] = ???
}