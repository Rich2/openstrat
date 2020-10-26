/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

/** Homogeneous Product4[Double, Double, Double, Double]. These are used in ArrHomoDbl4 Array[Double] based collections. */
trait ProdDbl4 extends Any with Product4[Double, Double, Double, Double] with ProdHomo

/** Base trait for Array[Double] base collections of Products of 4 Doubles. */
trait ArrProdDbl4[A <: ProdDbl4] extends Any with ArrProdDblN[A]
{
  def productSize: Int = 4
  def newElem(d1: Double, d2: Double, d3: Double, d4: Double): A
  def apply(index: Int): A = newElem(arrayUnsafe(4 * index), arrayUnsafe(4 * index + 1), arrayUnsafe(4 * index + 2), arrayUnsafe(4 * index + 3))

  final override def unsafeSetElem(index: Int, elem: A): Unit =
  { arrayUnsafe(4 * index) = elem._1
    arrayUnsafe(4 * index + 1) = elem._2
    arrayUnsafe(4 * index + 2) = elem._3
    arrayUnsafe(4 * index + 3) = elem._4
  }

  def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def head3: Double = arrayUnsafe(2)
  def head4: Double = arrayUnsafe(3)

  //def toArrs: ArrOld[ArrOld[Double]] = mapArrSeq(el => ArrOld(el._1, el._2, el._3, el._4))
  def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el._1, el._2, el._3, el._4)))
}

trait ArrProdDbl4Build[A <: ProdDbl4, ArrT <: ArrProdDbl4[A]] extends ArrProdDblNBuild[A, ArrT]
{ type BuffT <: BuffProdDbl4[A]

  final override def elemSize = 4
  //def newArray(length: Int): Array[Double] = new Array[Double](length * 2)

  override def arrSet(arr: ArrT, index: Int, value: A): Unit =
  { arr.arrayUnsafe(index * 4) = value._1
    arr.arrayUnsafe(index * 4 + 1) = value._2
    arr.arrayUnsafe(index * 4 + 2) = value._3
    arr.arrayUnsafe(index * 4 + 3) = value._4
  }

  override def buffGrow(buff: BuffT, value: A): Unit = ??? //{ buff.append(value._1,) ??? //buff.buffer.append(value)
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
    { res.arrayUnsafe(count * 4) = elems(count)._1
      res.arrayUnsafe(count * 4 + 1) = elems(count)._2
      res.arrayUnsafe(count * 4 + 2) = elems(count)._3
      res.arrayUnsafe(count * 4 + 3) = elems(count)._4
      count += 1
    }
     res
   }

  def doubles(elems: Double*): M =
  { val arrLen: Int = elems.length
    val res = factory(elems.length / 4)
    var count: Int = 0

    while (count < arrLen)
    { res.arrayUnsafe(count) = elems(count)
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
    { res.arrayUnsafe(count) = rem.head._1
      count += 1
      res.arrayUnsafe(count) = rem.head._2
      count += 1
      res.arrayUnsafe(count) = rem.head._3
      count += 1
      res.arrayUnsafe(count) = rem.head._4
      count += 1
      rem = rem.tail
    }
    res
  }
}

/** Both Persists and Builds ProductD4s Collection classes. */
abstract class ArrProdDbl4Persist[A <: ProdDbl4, M <: ArrProdDbl4[A]](typeStr: String) extends ArrProdDblNPersist[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value._1
    buf += value._2
    buf += value._3
    buf += value._4
  }

  override def syntaxDepth = 3
  /** Not sure about this implementation. */
  override def showSemi(thisColl: M): String = ??? // thisColl.mapBy2(_ + ", " + _ ).mkString("; ")
  override def showComma(thisColl: M): String = show(thisColl, 0)
  //override def fromParameterStatements(sts: Refs[Statement]): EMon[M] = ???
 // override def fromClauses(clauses: Refs[Clause]): EMon[M] = ???
}

trait BuffProdDbl4[A <: ProdDbl4] extends Any with BuffProdDblN[A]
{ type ArrT <: ArrProdDbl4[A]
  override def elemSize: Int = 4

  /** Grows the buffer by a single element. */
  override def grow(newElem: A): Unit = { buffer.append(newElem._1).append(newElem._2).append(newElem._3).append(newElem._4); () }

  def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): A
  def apply(index: Int): A = dblsToT(buffer(index * 4), buffer(index * 4 + 1), buffer(index * 4 + 2), buffer(index * 4 + 3))
}



