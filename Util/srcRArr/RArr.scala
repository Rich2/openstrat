/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** This is a common trait for [[RArr]] and tiling data layer classes in the Tiling module. */
trait RefsSeqLike[+A] extends Any with SeqLike[A]
{ type ThisT <: RefsSeqLike[A]
  def unsafeArray: Array[A] @uncheckedVariance
  def fromArray(array: Array[A] @uncheckedVariance): ThisT
  override final def fElemStr: A @uncheckedVariance => String = _.toString
  override final def setElemUnsafe(i: Int, newElem: A @uncheckedVariance) : Unit = unsafeArray(i) = newElem
}

/** The immutable Array based class for types without there own specialised [[Arr]] collection classes. It inherits the standard foreach, map,
 *  flatMap and fold and their variations' methods from ArrayLike. As it stands in Scala 3.0.2-RC1 the Graphics module will not build for Scala3 for
 *  the Javascript target. */
final class RArr[+A](val unsafeArray: Array[A] @uncheckedVariance) extends AnyVal with Arr[A] with RefsSeqLike[A]
{ type ThisT = RArr[A] @uncheckedVariance
  override def typeStr: String = "Arr"
  override def fromArray(array: Array[A] @uncheckedVariance): RArr[A] = new RArr(array)
  override def length: Int = unsafeArray.length

  def eqs(other: Any): Boolean = other match
  { case a: RArr[_] => unsafeArray.sameElements(a.unsafeArray)
    case _ => false
  }

  override def apply(index: Int): A = unsafeArray(index)

  /** Same map. Maps from this Arr[A] to a new Arr[A]. */
  def smap(f: A => A @uncheckedVariance): RArr[A] =
  { val newArray: Array[A] = unsafeArray.clone()
    iForeach({(i, el) => newArray(i) = f(el) })
    new RArr[A](newArray)
  }

  /** Returns a new shorter Arr with the head element removed. */
  @inline def drop1(implicit ct: ClassTag[A] @uncheckedVariance): RArr[A] = drop(1)

  /** Returns a new shorter Arr with the first 2 head elements removed. */
  @inline def drop2(implicit ct: ClassTag[A] @uncheckedVariance): RArr[A] = drop(2)

  /** Returns a new shorter Arr with the first 3 head elements removed. */
  @inline def drop3(implicit ct: ClassTag[A] @uncheckedVariance): RArr[A] = drop(3)

  def offset(value: Int): ArrOff[A] @uncheckedVariance = new ArrOff[A](value)
  def offset0: ArrOff[A @uncheckedVariance] = offset(0)

  /** Copies the backing Array to the operand Array. */
  def unsafeArrayCopy(operand: Array[A] @uncheckedVariance, offset: Int, copyLength: Int): Unit =
  { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }

  /** Copy's the backing Array[[AnyRef]] to a new Array[AnyRef]. End users should rarely have to use this method. */
  def unsafeSameSize(length: Int)(implicit ct: ClassTag[A] @uncheckedVariance): ThisT = fromArray(new Array[A](length))//.asInstanceOf[Array[A]])

  /** Returns a new shorter Arr with the head elements removed. */
  def drop(n: Int)(implicit ct: ClassTag[A] @uncheckedVariance): RArr[A] =
  { val n2 = n.max0
    val newLen: Int = (length - n2).max0
    val newArray = new Array[A](newLen)
    iUntilForeach(newLen)(i => newArray(i) = unsafeArray(i + n2))
    new RArr(newArray)
  }

  /** Returns a new shorter Arr with the last elements removed. */
  def dropRight(n: Int)(implicit ct: ClassTag[A] @uncheckedVariance): RArr[A] =
  { val n2 = n.max0
    val newLen: Int = (length - n2).max0
    val newArray = new Array[A](newLen)
    iUntilForeach(newLen)(i => newArray(i) = unsafeArray(i))
    new RArr(newArray)
  }

  /** Alias for appendArr. Functionally appends 2nd [[RArr]] collection to dispatching [[RArr]], allows type widening. This operation allows type
   *  widening.*/
  @targetName("appendArr") @inline
  def ++ [AA >: A](op: RArr[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): RArr[AA] =
  { val newArray = new Array[AA](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new RArr(newArray)
  }

  /** append. Functionally concatenates element to dispatching [[RArr]], allows type widening. */
  @targetName("append") @inline
  def +% [AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): RArr[AA] =
  { val newArray = new Array[AA](length + 1)
    unsafeArray.copyToArray(newArray)
    newArray(length) = op
    new RArr(newArray)
  }

  def appends[AA >: A](elems: AA *)(implicit ct: ClassTag[AA]): RArr[AA] = {
    val newArray = new Array[AA](length + elems.length)
    unsafeArray.copyToArray(newArray)
    elems.iForeach{(i, aa) => newArray(length + i) = aa }
    new RArr(newArray)
  }

  /** Alias for prepend. Functionally prepends element to array. Allows type widening. There is no precaternateRefs method, as this would serve no
   *  purpose. The ::: method on Lists is required for performance reasons. */
  @inline def %: [AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA] @uncheckedVariance): RArr[AA] = prepend(op)(ct)
  /** Functionally prepends element to array. Aliased by the +: operator. */
  def prepend[AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): RArr[AA] =
  { val newArray = new Array[AA](length + 1)
    newArray(0) = op
    unsafeArray.copyToArray(newArray, 1)
    new RArr(newArray)
  }

  def removeFirst(f: A => Boolean)(implicit ct: ClassTag[A] @uncheckedVariance): ThisT = indexWhere(f) match {
    case -1 => returnThis
    case n => {
      val newArr = unsafeSameSize(length - 1)
      iUntilForeach(n)(i => newArr.setElemUnsafe(i, apply(i)))
      iUntilForeach(n + 1, length)(i => newArr.setElemUnsafe(i - 1, apply(i)))
      newArr
    }
  }

  /** Returns an empty [[RArr]] if this is empty else returns an [[RArr]] containing only the last element. */
  def lasts(implicit ct: ClassTag[A] @uncheckedVariance): RArr[A] = if(length == 0) RArr[A]() else RArr(last)

  /** Concatenates the elements of the operand [[RArr]], if the condition is true, else returns the original [[RArr]]. The return type is the super type of
   *  the original [[RArr]] and the operand [[RArr]]. The operand is lazy so will only be evaluated if the condition is true. This is similar to the appendsIf
   *  method, but concatsIf allows type widening. */
  def concatArrIf[AA >: A](b: Boolean, newElems: => RArr[AA])(implicit ct: ClassTag[AA]): RArr[AA] =
    ife(b,this ++ newElems, this)

  /** Appends the elements of the operand Refs if the condition is true, else returns the original Refs. The operand is lazy so will only be evaluated
   *  if the condition is true. This is similar to the concatsIf method, but appendsIf does not allow type widening. */
  def appendArrIf(b: Boolean, newElems: => RArr[A] @uncheckedVariance)(implicit ct: ClassTag[A] @uncheckedVariance): RArr[A] =
    ife(b,this ++ newElems, this)

  def concatOption[AA >: A](optElem: Option[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): RArr[AA] =
    optElem.fld(this, this +% _)

  def appendOption(optElem: Option[A]@uncheckedVariance)(implicit ct: ClassTag[A] @uncheckedVariance): RArr[A] =
    optElem.fld(this, this +% _)

  def appendsOption(optElem: Option[RArr[A]]@uncheckedVariance)(implicit @unused ct: ClassTag[A] @uncheckedVariance ): RArr[A] =
    optElem.fld(this, ++ _)

  def concatsOption[AA >: A <: AnyRef](optElems: Option[RArr[AA]])(implicit ct: ClassTag[AA]): RArr[AA] =
    optElems.fld[RArr[AA]](this, this ++ _)

  def setAll(value: A @uncheckedVariance): Unit =
  { var i = 0
    while(i < length){setElemUnsafe(i, value); i += 1}
  }

  def mapToCurlySyntax: String = ???

  /** Takes the first n elements, starting from the second Int parameter that takes default value of 0. */
  def take(n: Int, start: Int = 0)(implicit ct: ClassTag[A] @uncheckedVariance): RArr[A] =
  { val newLen: Int = n - (n + start - length).min0
    val newArray = new Array[A](newLen)
    System.arraycopy(unsafeArray, start, newArray, 0, newLen)
    new RArr[A](newArray)
  }

  /** Takes the first n elements, starting from the second [[Int]] parameter that takes the default value of 0, looping back to the head when it
   *  reaches the end of this sequence. */
  def takeLoop(n: Int, start: Int = 0)(implicit ct: ClassTag[A]@uncheckedVariance): RArr[A] =
  { val newArray = new Array[A](n)
    var i = 0
    while (i < n)
    { newArray(i) = unsafeArray((start + i) %% length)
      i += 1
    }
    new RArr[A](newArray)
  }
}

/** Companion object for the [[RArr]] class contains factory apply method, EqT implicit type class instance and Extension method for Arr[A] where A
 * extends AnyRef. */
object RArr
{ def apply[A](input: A*)(implicit ct: ClassTag[A]): RArr[A] = new RArr(input.toArray)
  implicit def showImplicit[A](implicit evA: ShowT[A]): ShowT[RArr[A]] = ArrShowT[A, RArr[A]](evA)

  implicit def eqTEv[A](implicit evA: EqT[A]): EqT[RArr[A]] = (arr1, arr2) => if (arr1.length != arr2.length) false else
  { var i = 0
    var res = true
    while(i < arr1.length & res) if (evA.eqT(arr1(i), arr2(i))) i += 1 else res = false
    res
  }

  implicit class ArrExtension[A <: AnyRef](thisArr: RArr[A])
  {
    def optFind(f: A => Boolean): Option[A] =
    { var acc: Option[A] = None
      var count = 0
      while (acc == None & count < thisArr.length) if (f(thisArr(count))) acc = Some(thisArr(count)) else count += 1
      acc
    }
  }
}

/** The default Immutable Array based collection builder for the Arr[A] class. */
class RArrAllBuilder[B](implicit ct: ClassTag[B], @unused notB: Not[SpecialT]#L[B] ) extends ArrMapBuilder[B, RArr[B]] with ArrFlatBuilder[RArr[B]]
{ type BuffT = RBuff[B]
  override def uninitialised(length: Int): RArr[B] = new RArr(new Array[B](length))
  override def indexSet(seqLike: RArr[B], index: Int, elem: B): Unit = seqLike.unsafeArray(index) = elem
  override def newBuff(length: Int = 4): RBuff[B] = new RBuff(new ArrayBuffer[B](length))
  override def buffGrow(buff: RBuff[B], newElem: B): Unit = buff.unsafeBuffer.append(newElem)
  override def buffToSeqLike(buff: RBuff[B]): RArr[B] = new RArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: RBuff[B], arr: RArr[B]): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
}

/** R for stored by reference. The default [[Buff]] class for types without their own specialist sequence classes. */
final class RBuff[A](val unsafeBuffer: ArrayBuffer[A]) extends AnyVal with Buff[A]
{ override type ThisT = RBuff[A]
  override def typeStr: String = "AnyBuff"
  override def apply(index: Int): A = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def setElemUnsafe(i: Int, newElem: A): Unit = unsafeBuffer(i) = newElem
  override def fElemStr: A => String = _.toString
  override def grow(newElem: A): Unit = unsafeBuffer.append(newElem)
}