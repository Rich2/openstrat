/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

/** Homogeneous Product4[Int, Int, Int, Int] with Stringer. These are used in ArrHomoInt4 Array[Int] based collections. */
trait ProdInt4 extends Any with Product4[Int, Int, Int, Int] with ProdHomo

trait ArrProdInt4[A <: ProdInt4] extends Any with ArrProdIntN[A]
{
  override def productSize: Int = 4
  def newElem(i1: Int, i2: Int, i3: Int, i4: Int): A
  def apply(index: Int): A = newElem(arrayUnsafe(4 * index), arrayUnsafe(4 * index + 1), arrayUnsafe(4 * index + 2), arrayUnsafe(4 * index + 3))
  override def unsafeSetElem(index: Int, elem: A): Unit =
  { arrayUnsafe(4 * index) = elem._1;
    arrayUnsafe(4 * index + 1) = elem._2
    arrayUnsafe(4 * index + 2) = elem._3
    arrayUnsafe(4 * index + 3) = elem._4
  }

  def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
  def head3: Int = arrayUnsafe(2)
  def head4: Int = arrayUnsafe(3)
}

trait ProdInt4Buff[A <: ProdInt4, M <: ArrProdInt4[A]] extends Any with BuffProdIntN[A]
{ override def elemSize: Int = 4
  override def grow(newElem: A): Unit = { buffer.append(newElem._1).append(newElem._2).append(newElem._3).append(newElem._4); ()}
  def intsToT(i1: Int, i2: Int, i3: Int, i4: Int): A
  def apply(index: Int): A = intsToT(buffer(index * 4), buffer(index * 4 + 1), buffer(index * 4 + 2), buffer(index * 4 + 3))
}

abstract class ProdInt4sCompanion[A <: ProdInt4, M <: ArrProdInt4[A]]
{ val factory: Int => M
  def buff(initialSize: Int): ProdInt4Buff[A, M]

  def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 4
    val res = factory(elems.length)
    var count: Int = 0
    while (count < arrLen)
    {
      res.arrayUnsafe(count) = elems(count / 2)._1
      count += 1
      res.arrayUnsafe(count) = elems(count / 2)._2
      count += 1
      res.arrayUnsafe(count) = elems(count / 2)._3
      count += 1
      res.arrayUnsafe(count) = elems(count / 2)._4
      count += 1
    }
    res
  }
}



abstract class ProductI4sBuilder[A <: ProdInt4, M <: ArrProdInt4[A]](typeStr: String) extends ProductIntsBuilder[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value._1
    buf += value._2
    buf += value._3
    buf += value._4
  }

  override def syntaxDepth = 3
  /** Not sure about this implementation. */
  override def showSemi(thisColl: M): String = ??? //thisColl.mapBy2(_ + ", " + _ ).mkString("; ")
  override def showComma(thisColl: M): String = show(thisColl)
 // override def fromParameterStatements(sts: Refs[Statement]): EMon[M] = ???
 // override def fromClauses(clauses: Refs[Clause]): EMon[M] = ???
}