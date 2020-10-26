package ostrat

import scala.collection.mutable.ArrayBuffer

/** Homogeneous Product5[Double, Double, Double, Double, Double]. These are used in ArrHmoDbl5 Array[Double] based collections. */
trait ProdDbl5 extends Any with Product5[Double, Double, Double, Double, Double] with ProdHomo

/** Base trait for Array[Double] base collections of Products of 4 Doubles. */
trait ArrProdDbl5[A <: ProdDbl5] extends Any with ArrProdDblN[A]
{
  def productSize: Int = 5
  def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A
  def apply(index: Int): A = newElem(arrayUnsafe(5 * index), arrayUnsafe(5 * index + 1), arrayUnsafe(5 * index + 2), arrayUnsafe(5 * index + 3), arrayUnsafe(5 * index + 4))

  final override def unsafeSetElem(index: Int, elem: A): Unit =
  { arrayUnsafe(5 * index) = elem._1
    arrayUnsafe(5 * index + 1) = elem._2
    arrayUnsafe(5 * index + 2) = elem._3
    arrayUnsafe(5 * index + 3) = elem._4
    arrayUnsafe(5 * index + 4) = elem._5
  }

  def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def head3: Double = arrayUnsafe(2)
  def head4: Double = arrayUnsafe(3)
  def head5: Double = arrayUnsafe(4)

  //def toArrs: ArrOld[ArrOld[Double]] = mapArrSeq(el => ArrOld(el._1, el._2, el._3, el._4, el._5))
  def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el._1, el._2, el._3, el._4, el._5)))
}

abstract class ProdDbl5sCompanion[A <: ProdDbl5, M <: ArrProdDbl5[A]] //extends ProductDsBuilder[A, M]
{
  val factory: Int => M
  def apply(length: Int): M = factory(length)

  def apply(elems: A*): M =
  { val length = elems.length
    val res = factory(length)
    var count: Int = 0

    while (count < length)
    { res.arrayUnsafe(count * 5) = elems(count)._1
      res.arrayUnsafe(count * 5 + 1) = elems(count)._2
      res.arrayUnsafe(count * 5 + 2) = elems(count)._3
      res.arrayUnsafe(count * 5 + 3) = elems(count)._4
      res.arrayUnsafe(count * 5 + 4) = elems(count)._5
      count += 1
    }
    res
  }

  def doubles(elems: Double*): M =
  { val arrLen: Int = elems.length
    val res = factory(elems.length / 5)
    var count: Int = 0

    while (count < arrLen)
    { res.arrayUnsafe(count) = elems(count)
      count += 1
    }
    res
  }

  def fromList(list: List[A]): M =
  { val arrLen: Int = list.length * 5
    val res = factory(list.length)
    var count: Int = 0
    var rem = list

    while (count < arrLen)
    { res.arrayUnsafe(count) = rem.head._1
      count += 1
      res.arrayUnsafe(count) = rem.head._2
      count += 1
      res.arrayUnsafe(count) = rem.head._3
      count += 1
      res.arrayUnsafe(count) = rem.head._4
      count += 1
      res.arrayUnsafe(count) = rem.head._5
      count += 1
      rem = rem.tail
    }
    res
  }
}

/** Both Persists and Builds ProductD4s Collection classes. */
abstract class ArrHomoDbl5Builder[A <: ProdDbl5, M <: ArrProdDbl5[A]](typeStr: String) extends ArrProdDblNPersist[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value._1
    buf += value._2
    buf += value._3
    buf += value._4
    buf += value._5
  }

  override def syntaxDepth = 3
  /** Not sure about this implementation. */
  override def showSemi(thisColl: M): String = ??? // thisColl.mapBy2(_ + ", " + _ ).mkString("; ")
  override def showComma(thisColl: M): String = show(thisColl, 0)
  //override def fromParameterStatements(sts: Refs[Statement]): EMon[M] = ???
  //override def fromClauses(clauses: Refs[Clause]): EMon[M] = ???
}
