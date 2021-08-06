/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** The immutable Array based class for types without there own specialised [[ArrBase]] collection classes. It Inherits the standard foreach, map,
 *  flatMap and fold and their variations' methods from ArrayLike. As it stands in Scala 3.0.2-RC1 the Graphics module will not build for Scala3 for
 *  the Javascript target. */
final class Arr[+A](val unsafeArr: Array[A] @uncheckedVariance) extends AnyVal with ArrBase[A]
{ type ThisT = Arr[A]@uncheckedVariance
  override def typeStr: String = "Arr"

  /** Copy's the backing Array[[AnyRef]] to a new Array[AnyRef]. End users should rarely have to use this method. */
  override def unsafeSameSize(length: Int): Arr[A] = new Arr(new Array[AnyRef](length).asInstanceOf[Array[A]])

  override def elemsNum: Int = unsafeArr.length

  override def indexData(index: Int): A = unsafeArr(index)

  def eqs(other: Any): Boolean = other match {
    case a: Arr[_] => unsafeArr.sameElements(a.unsafeArr)
    case _ => false
  }

  /** Not sure why this is called smap. */
  def smap(f: A => A @uncheckedVariance): Arr[A] =
  { val newArray: Array[A] = unsafeArr.clone()
    iForeach{(el, i) => newArray(i) = f(el) }
    new Arr[A](newArray)
  }

  override def fElemStr: A @uncheckedVariance => String = _.toString
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit = unsafeArr(i) = value

  /** Returns a new shorter Arr with the head element removed. */
  @inline def drop1(implicit ct: ClassTag[A] @uncheckedVariance): Arr[A] = drop(1)

  /** Returns a new shorter Arr with the first 2 head elements removed. */
  @inline def drop2(implicit ct: ClassTag[A] @uncheckedVariance): Arr[A] = drop(2)

  /** Returns a new shorter Arr with the first 3 head elements removed. */
  @inline def drop3(implicit ct: ClassTag[A] @uncheckedVariance): Arr[A] = drop(3)

  def offset(value: Int): ArrOff[A] @uncheckedVariance = new ArrOff[A](value)
  def offset0: ArrOff[A @uncheckedVariance] = offset(0)

  /** Copies the backing Array to the operand Array. */
  def unsafeArrayCopy(operand: Array[A] @uncheckedVariance, offset: Int, copyLength: Int): Unit =
  { unsafeArr.copyToArray(unsafeArr, offset, copyLength); () }

  /** Returns a new shorter Arr with the head elements removed. */
  def drop(n: Int)(implicit ct: ClassTag[A] @uncheckedVariance): Arr[A] =
  { val newArray = new Array[A]((elemsNum - 1).atLeast0)
    iUntilForeach(1, elemsNum)(i => newArray(i - 1) = unsafeArr(i))
    new Arr(newArray)
  }

  /** Alias for appendRefs. Functionally appends 2nd Refs collection to dispatching Refs, allows type widening. This operation allows type widening.*/
  @inline def ++ [AA >: A](op: Arr[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] = appendRefs[AA](op)(ct)
  /** Functionally concatenates element to dispatching Refs, allows type widening. Aliased by -+ operator. The '-' character in the operator name
   *  indicates loss of type precision. The ++ appendRefs method is preferred when type widening is not required. */
  def appendRefs [AA >: A](op: Arr[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] =
  { val newArray = new Array[AA](elemsNum + op.elemsNum)
    unsafeArr.copyToArray(newArray)
    op.unsafeArr.copyToArray(newArray, elemsNum)
    new Arr(newArray)
  }

  /** Alias for concat. Functionally concatenates element to dispatching Refs, allows type widening. */
  @inline def +- [AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] = append[AA](op)(ct)
  /** Functionally appends an element to dispatching Refs, allows type widening. Aliased by +- operator. */
  def append[AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] =
  { val newArray = new Array[AA](elemsNum + 1)
    unsafeArr.copyToArray(newArray)
    newArray(elemsNum) = op
    new Arr(newArray)
  }

  /** Alias for prepend. Functionally prepends element to array. Allows type widening. There is no precaternateRefs method, as this would serve no
   *  purpose. The ::: method on Lists is required for performance reasons. */
  @inline def +: [AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA] @uncheckedVariance): Arr[AA] = prepend(op)(ct)
  /** Functionally prepends element to array. Aliased by the +: operator. */
  def prepend[AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] =
  { val newArray = new Array[AA](elemsNum + 1)
    newArray(0) = op
    unsafeArr.copyToArray(newArray, 1)
    new Arr(newArray)
  }

  /** Concatenates the elements of the operands Refs if the condition is true, else returns the original Refs. The return type is the super type of
   *  the original Refs and the operand Ref. The operand is lazy so will only be evaluated if the condition is true. This is similar to the appendsIf
   *  method, but concatsIf allows type widening. */
  def concatRefsIf[AA >: A](b: Boolean, newElems: => Arr[AA])(implicit ct: ClassTag[AA]): Arr[AA] =
    ife(b,this ++ newElems, this)

  /** Appends the elements of the operand Refs if the condition is true, else returns the original Refs. The operand is lazy so will only be evaluated
   *  if the condition is true. This is similar to the concatsIf method, but appendsIf does not allow type widening. */
  def appendRefsIf(b: Boolean, newElems: => Arr[A] @uncheckedVariance)(implicit ct: ClassTag[A] @uncheckedVariance): Arr[A] =
    ife(b,this ++ newElems, this)

  def concatOption[AA >: A](optElem: Option[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] =
    optElem.fld(this, this +- _)

  def appendOption(optElem: Option[A]@uncheckedVariance)(implicit ct: ClassTag[A] @uncheckedVariance): Arr[A] =
    optElem.fld(this, this +- _)

  def appendsOption(optElem: Option[Arr[A]]@uncheckedVariance)(implicit @unused ct: ClassTag[A] @uncheckedVariance ): Arr[A] =
    optElem.fld(this, ++ _)

  def concatsOption[AA >: A <: AnyRef](optElems: Option[Arr[AA]])(implicit ct: ClassTag[AA]): Arr[AA] =
    optElems.fld[Arr[AA]](this, this ++ _)

  /*def optFind(f: A => Boolean): OptRef[A] =
  { var acc: OptRef[A] = NoRef
    var count = 0
    while (acc == NoRef & count < length) if (f(apply(count))) acc = OptRef(apply(count)) else count += 1
    acc
  }*/

  def setAll(value: A @uncheckedVariance): Unit =
  { var i = 0
    while(i < elemsNum){unsafeSetElem(i, value); i += 1}
  }
}

/** Companion object for the Arr class. */
object Arr
{ def apply[A](input: A*)(implicit ct: ClassTag[A]): Arr[A] = new Arr(input.toArray)
  implicit def showImplicit[A <: AnyRef](implicit evA: ShowT[A]): ShowT[Arr[A]] = ArrayLikeShow[A, Arr[A]](evA)

  implicit class ArrExtension[A <: AnyRef](thisArr: Arr[A])
  {
    def optFind(f: A => Boolean): OptRef[A] =
    { var acc: OptRef[A] = NoRef
      var count = 0
      while (acc == NoRef & count < thisArr.elemsNum) if (f(thisArr(count))) acc = OptRef(thisArr(count)) else count += 1
      acc
    }
  }
}

/** The default Immutable Array based collection builder for the Arr[A] class. */
class AnyBuild[B](implicit ct: ClassTag[B], @unused notB: Not[SpecialT]#L[B] ) extends ArrBuilder[B, Arr[B]] with SeqFlatBuilder[Arr[B]]
{ type BuffT = AnyBuff[B]
  override def newArr(length: Int): Arr[B] = new Arr(new Array[B](length))
  override def arrSet(arr: Arr[B], index: Int, value: B): Unit = arr.unsafeArr(index) = value
  override def newBuff(length: Int = 4): AnyBuff[B] = new AnyBuff(new ArrayBuffer[B](length))
  override def buffGrow(buff: AnyBuff[B], value: B): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: AnyBuff[B], arr: Arr[B]): Unit = buff.unsafeBuff.addAll(arr.unsafeArr)
  override def buffToArr(buff: AnyBuff[B]): Arr[B] = new Arr(buff.unsafeBuff.toArray)
}

/** Not sure if this class is necessary now that Arr take Any. */
class AnyBuff[A](val unsafeBuff: ArrayBuffer[A]) extends AnyVal with SeqGen[A]
{ override def indexData(index: Int): A = unsafeBuff(index)
  override def elemsNum: Int = unsafeBuff.length
  override def unsafeSetElem(i: Int, value: A): Unit = unsafeBuff(i) = value
  override def fElemStr: A => String = _.toString
}