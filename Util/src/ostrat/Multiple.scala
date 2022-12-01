/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** The Multiple type class allow you to represent multiple values of type A. Implicit conversion in package object. */
case class Multiple[+A](value: A, num: Int)
{ /** multiply the [[Multiple]] number with the operand */
  def * (operand: Int): Multiple[A] = Multiple(value, num * operand)

  override def toString = "Multiple" + (value.toString + "; " + num.toString).enParenth

  def foreach(f: A => Unit): Unit = num.doTimes(() => f(value))

  def singlesList: List[A] =
  {
    var acc: List[A] = Nil
    var count = 0
    while (count < num)
    { acc ::= value
      count += 1
    }
    acc
  }
  def map[B](f: A => B): Multiple[B] = Multiple[B](f(value), num)

  def flatMap[B](f: A => Multiple[B]) =
  { val res: Multiple[B] = f(value)
    Multiple[B](res.value, res.num * num)
  }

  def toArr[ArrA <: Arr[A]@uncheckedVariance](implicit build: ArrMapBuilder[A, ArrA]@uncheckedVariance): ArrA =
  { val res: ArrA = build.uninitialised(num)
    iUntilForeach(num){i => res.unsafeSetElem(i, value)}
    res
  }
}

/** Companion object for the Multiple[+A] type class. */
object Multiple
{
  implicit def arrMapBuilderEv[A](implicit ct: ClassTag[A]): MultipleArrMapBuilder[A] = new MultipleArrMapBuilder[A]

  implicit def eqImplicit[A](implicit ev: EqT[A]): EqT[Multiple[A]] = (a1, a2) => (a1.num == a2.num) & ev.eqT(a1.value, a2.value)

  implicit def toMultipleImplicit[A](value: A): Multiple[A] = Multiple(value, 1)

  implicit class RefsImplicit[A](thisRefs: RArr[Multiple[A]])
  { def numSingles: Int = thisRefs.sumBy(_.num)
  }

  implicit def seqImplicit[A](thisSeq: Seq[Multiple[A]]): MultipleSeqImplicit[A] = new MultipleSeqImplicit[A](thisSeq)
}

class MultipleArr[A](arrayInt: Array[Int], values: Array[A]) extends Arr[Multiple[A]]
{ type ThisT = MultipleArr[A]
  override def typeStr: String = "MultipleArr"

  def numSingles: Int = this.sumBy(_.num)

  def toSinglesArr[ArrA <: Arr[A]](implicit build: ArrMapBuilder[A, ArrA]): ArrA =
  { val res = build.uninitialised(numSingles)
    var i = 0
    foreach{ m => iUntilForeach(m.num){ _ => res.unsafeSetElem(i, m.value); i += 1 } }
    res
  }

  override def length: Int = arrayInt.length
  override def apply(index: Int): Multiple[A] = new Multiple[A](values(index), arrayInt(index))
  override def unsafeSetElem(i: Int, newElem: Multiple[A]): Unit = { values(i) = newElem.value; arrayInt(i) =newElem.num }
  override def fElemStr: Multiple[A] => String = _.toString
  def unsafeSameSize(length: Int)(implicit ct: ClassTag[A]): ThisT = new MultipleArr[A](new Array[Int](length), new Array[A](length))
}

class MultipleSeqImplicit[A](thisSeq: Seq[Multiple[A]])
{ def numSingles: Int = thisSeq.sumBy (_.num)
  def toSinglesList: List[A] = thisSeq.toList.flatMap (_.singlesList)

  /*def singles[ArrA <: Arr[A]] (implicit build: ArrMapBuilder[A, ArrA] ): ArrA =
  { val len: Int = thisSeq.sumBy (_.num)
    val res = build.uninitialised (len)
    var i = 0
    thisSeq.foreach { m =>
      iUntilForeach (m.num) { j =>
        res.unsafeSetElem (i, m.value)
        i += 1
      }
    }
    res
  }*/

  def iForeachSingle (f: (Int, A) => Unit): Unit = toSinglesList.iForeach (f)
}

class MultipleBuff[A](val numBuffer: ArrayBuffer[Int], val valuesBuffer: ArrayBuffer[A]) extends Buff[Multiple[A]]
{ override type ThisT = MultipleBuff[A]
  override def typeStr: String = "MultipleBuff"
  override def grow(newElem: Multiple[A]): Unit = { numBuffer.append(newElem.num); valuesBuffer.append(newElem.value) }
  override def length: Int = numBuffer.length
  override def apply(index: Int): Multiple[A] = new Multiple[A](valuesBuffer(index), numBuffer(index))
  override def unsafeSetElem(i: Int, newElem: Multiple[A]): Unit = { numBuffer(i) = newElem.num; valuesBuffer(i) = newElem.value }
  override def fElemStr: Multiple[A] => String = _.toString
}

object MultipleBuff
{ def apply[A](initLen: Int = 4): MultipleBuff[A] = new MultipleBuff[A](new ArrayBuffer[Int](initLen), new ArrayBuffer[A](initLen))
}

class MultipleArrMapBuilder[A](implicit ct: ClassTag[A]) extends ArrMapBuilder[Multiple[A], MultipleArr[A]]
{ override type BuffT = MultipleBuff[A]
  override def buffGrow(buff: MultipleBuff[A], newElem: Multiple[A]): Unit = buff.grow(newElem)
  override def uninitialised(length: Int): MultipleArr[A] = new MultipleArr[A](new Array[Int](length), new Array[A](length))
  override def indexSet(seqLike: MultipleArr[A], index: Int, elem: Multiple[A]): Unit = { seqLike.unsafeSetElem(index, elem) }
  override def newBuff(length: Int): MultipleBuff[A] = MultipleBuff(length)
  override def buffToSeqLike(buff: MultipleBuff[A]): MultipleArr[A] = new MultipleArr[A](buff.numBuffer.toArray, buff.valuesBuffer.toArray)
}