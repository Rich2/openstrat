/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** The Multiple type class allow you to represent multiple values of type A. Implicit conversion in package object. */
case class Multiple[+A](value: A, num: Int)
{ /** multiply the [[Multiple]] number with the operand. */
  def * (operand: Int): Multiple[A] = Multiple(value, num * operand)

  override def toString = "Multiple" + (value.toString + "; " + num.toString).enParenth

  def foreach(f: A => Unit): Unit = num.doTimes(() => f(value))

  def map[B](f: A => B): Multiple[B] = Multiple[B](f(value), num)

  def flatMap[B](f: A => Multiple[B]): Multiple[B] =
  { val res: Multiple[B] = f(value)
    Multiple[B](res.value, res.num * num)
  }

  def toArr[ArrA <: Arr[A]@uncheckedVariance](implicit build: BuilderArrMap[A, ArrA]@uncheckedVariance): ArrA =
  { val res: ArrA = build.uninitialised(num)
    iUntilForeach(num){i => res.setElemUnsafe(i, value)}
    res
  }
}

/** Companion object for the [[Multiple]][+A] type class. */
object Multiple
{
  implicit def arrMapBuilderEv[A](implicit ct: ClassTag[A]): MultipleArrMapBuilder[A] = new MultipleArrMapBuilder[A]

  implicit def eqImplicit[A](implicit ev: EqT[A]): EqT[Multiple[A]] = (a1, a2) => (a1.num == a2.num) & ev.eqT(a1.value, a2.value)

  implicit def toMultipleImplicit[A](value: A): Multiple[A] = Multiple(value, 1)

  implicit class RefsImplicit[A](thisRefs: RArr[Multiple[A]])
  { def numSingles: Int = thisRefs.sumBy(_.num)
  }

  implicit def seqImplicit[A](thisSeq: Seq[Multiple[A]]): MultipleSeqImplicit[A] = new MultipleSeqImplicit[A](thisSeq)

  /** [[Show]] type class instance / evidence for [[Multiple]] class. */
  implicit def showEv[A](typeStr: String)(implicit evA: Show[A]): MultipleShow[A] = new MultipleShow[A](typeStr)(evA)

  class MultipleShow[A](val typeStr: String)(implicit val evA: Show[A]) extends Show[Multiple[A]]()
  { override def strT(obj: Multiple[A]): String = show(obj, ShowStandard)
    override def syntaxDepth(obj: Multiple[A]): Int = evA.syntaxDepth(obj.value)

    override def show(obj: Multiple[A], style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = style match
    { case ShowTyped | ShowStdTypedFields => showFullEv.show(obj, style, maxPlaces, minPlaces)
      case _ if obj.num == 1 => evA.show(obj.value, ShowStandard, maxPlaces, minPlaces)
      case _ => evA.show(obj.value, ShowStandard, maxPlaces, minPlaces) + " * " + obj.num
    }
  }

  /** [[Show]] type class instance / evidence for full show of [[Multiple]] class. */
  def showFullEv[A](implicit evA: Show[A]): Show2[A, Int, Multiple[A]] = Show2[A, Int, Multiple[A]]("Multiple", "value", _.value, "num", _.num)
}

class MultipleArr[A](arrayInt: Array[Int], values: Array[A]) extends Arr[Multiple[A]]
{ type ThisT = MultipleArr[A]
  override def typeStr: String = "MultipleArr"

  def numSingles: Int = this.sumBy(_.num)

  def toSinglesArr[ArrA <: Arr[A]](implicit build: BuilderArrMap[A, ArrA]): ArrA =
  { val res = build.uninitialised(numSingles)
    var i = 0
    foreach{ m => iUntilForeach(m.num){ _ => res.setElemUnsafe(i, m.value); i += 1 } }
    res
  }

  override def length: Int = arrayInt.length
  override def apply(index: Int): Multiple[A] = new Multiple[A](values(index), arrayInt(index))
  override def setElemUnsafe(i: Int, newElem: Multiple[A]): Unit = { values(i) = newElem.value; arrayInt(i) =newElem.num }
  override def fElemStr: Multiple[A] => String = _.toString
  def unsafeSameSize(length: Int)(implicit ct: ClassTag[A]): ThisT = new MultipleArr[A](new Array[Int](length), new Array[A](length))
}

class MultipleSeqImplicit[A](thisSeq: Seq[Multiple[A]])
{ /** Extension method. The number of single values of type A in this [[Seq]] of [[Multiple]]s. */
  def numSingles: Int = thisSeq.sumBy (_.num)

  /** Extension method. Converts this [[Seq]] of [[Multiple]]s, to an [[Arr]] of the Single values
   * of type A. The appropriate Arr type is found by implicit look up for type A. */
  def toSinglesArr[ArrA <: Arr[A]](implicit build: BuilderArrMap[A, ArrA]): ArrA =
  { val res = build.uninitialised(numSingles)
    var i = 0
    thisSeq.foreach { m => iUntilForeach(m.num) { _ => res.setElemUnsafe(i, m.value); i += 1 } }
    res
  }

  /** Foreachs over each single value  of this [[Seq]] of [[Multiple]]s with an index. */
  def iForeachSingle(f: (Int, A) => Unit): Unit =
  { var i = 0
    thisSeq.foreach{m => repeat(m.num){ f(i, m.value); i += 1} }
  }
}

class MultipleBuff[A](val numBuffer: ArrayBuffer[Int], val valuesBuffer: ArrayBuffer[A]) extends BuffSequ[Multiple[A]]
{ override type ThisT = MultipleBuff[A]
  override def typeStr: String = "MultipleBuff"
  override def grow(newElem: Multiple[A]): Unit = { numBuffer.append(newElem.num); valuesBuffer.append(newElem.value) }
  override def length: Int = numBuffer.length
  override def apply(index: Int): Multiple[A] = new Multiple[A](valuesBuffer(index), numBuffer(index))
  override def setElemUnsafe(i: Int, newElem: Multiple[A]): Unit = { numBuffer(i) = newElem.num; valuesBuffer(i) = newElem.value }
  override def fElemStr: Multiple[A] => String = _.toString
}

object MultipleBuff
{ def apply[A](initLen: Int = 4): MultipleBuff[A] = new MultipleBuff[A](new ArrayBuffer[Int](initLen), new ArrayBuffer[A](initLen))
}

class MultipleArrMapBuilder[A](implicit ct: ClassTag[A]) extends BuilderArrMap[Multiple[A], MultipleArr[A]]
{ override type BuffT = MultipleBuff[A]
  override def buffGrow(buff: MultipleBuff[A], newElem: Multiple[A]): Unit = buff.grow(newElem)
  override def uninitialised(length: Int): MultipleArr[A] = new MultipleArr[A](new Array[Int](length), new Array[A](length))
  override def indexSet(seqLike: MultipleArr[A], index: Int, elem: Multiple[A]): Unit = { seqLike.setElemUnsafe(index, elem) }
  override def newBuff(length: Int): MultipleBuff[A] = MultipleBuff(length)
  override def buffToSeqLike(buff: MultipleBuff[A]): MultipleArr[A] = new MultipleArr[A](buff.numBuffer.toArray, buff.valuesBuffer.toArray)
}