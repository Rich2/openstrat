/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

/** Base trait for Array[Double] base collections of Products of 4 Doubles. */
trait ArrProdDbl4[A <: ProdDbl4] extends Any with ArrProdDblN[A]
{
  def productSize: Int = 4
  def newElem(d1: Double, d2: Double, d3: Double, d4: Double): A
  def apply(index: Int): A = newElem(array(4 * index), array(4 * index + 1), array(4 * index + 2), array(4 * index + 3))

  final override def unsafeSetElem(index: Int, elem: A): Unit =
  { array(4 * index) = elem._1
    array(4 * index + 1) = elem._2
    array(4 * index + 2) = elem._3
    array(4 * index + 3) = elem._4
  }

  def head1: Double = array(0)
  def head2: Double = array(1)
  def head3: Double = array(2)
  def head4: Double = array(3)

  def toArrs: Arr[Arr[Double]] = mapArrSeq(el => Arr(el._1, el._2, el._3, el._4))
  def foreachArr(f: Arr[Double] => Unit): Unit = foreach(el => f(Arr(el._1, el._2, el._3, el._4)))
}

abstract class ProdDbl4sCompanion[A <: ProdDbl4, M <: ArrProdDbl4[A]] //extends ProductDsBuilder[A, M]
{
  val factory: Int => M
  def apply(length: Int): M = factory(length)

  def apply(elems: A*): M =
  { val length = elems.length
    val res = factory(length)
    var count: Int = 0

    while (count < length)
    { res.array(count * 4) = elems(count)._1
      res.array(count * 4 + 1) = elems(count)._2
      res.array(count * 4 + 2) = elems(count)._3
      res.array(count * 4 + 3) = elems(count)._4
      count += 1
    }
     res
   }

  def doubles(elems: Double*): M =
  { val arrLen: Int = elems.length
    val res = factory(elems.length / 4)
    var count: Int = 0

    while (count < arrLen)
    { res.array(count) = elems(count)
      count += 1
    }
    res
  }

  def fromList(list: List[A]): M =
  { val arrLen: Int = list.length * 4
    val res = factory(list.length)
    var count: Int = 0
    var rem = list

    while (count < arrLen)
    { res.array(count) = rem.head._1
      count += 1
      res.array(count) = rem.head._2
      count += 1
      res.array(count) = rem.head._3
      count += 1
      res.array(count) = rem.head._4
      count += 1
      rem = rem.tail
    }
    res
  }
}

/** Both Persists and Builds ProductD4s Collection classes. */
abstract class ArrHomoDbl4Builder[A <: ProdDbl4, M <: ArrProdDbl4[A]](typeStr: String) extends ArrHomoDblNBuilder[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value._1
    buf += value._2
    buf += value._3
    buf += value._4
  }

  import pParse._
  override def syntaxDepth = 3
  /** Not sure about this implementation. */
  override def showSemi(thisColl: M): String = ??? // thisColl.mapBy2(_ + ", " + _ ).mkString("; ")
  override def showComma(thisColl: M): String = show(thisColl)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[M] = ???
  override def fromClauses(clauses: Arr[Clause]): EMon[M] = ???
}

