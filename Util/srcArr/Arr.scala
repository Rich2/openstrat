/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** The immutable Array based class for types without there own specialised [[SeqImut]] collection classes. It Inherits the standard foreach, map,
 *  flatMap and fold and their variations' methods from ArrayLike. As it stands in Scala 3.0.2-RC1 the Graphics module will not build for Scala3 for
 *  the Javascript target. */
final class Arr[+A](val unsafeArray: Array[A] @uncheckedVariance) extends AnyVal with SeqImut[A] with RefsSeqDefImut[A]
{ type ThisT = Arr[A] @uncheckedVariance
  override def typeStr: String = "Arr"
  override def fromArray(array: Array[A] @uncheckedVariance): Arr[A] = new Arr(array)
  override def length: Int = unsafeArray.length

  def eqs(other: Any): Boolean = other match {
    case a: Arr[_] => unsafeArray.sameElements(a.unsafeArray)
    case _ => false
  }

  /** Same map. Maps from this Arr[A] to a new Arr[A]. */
  def smap(f: A => A @uncheckedVariance): Arr[A] =
  { val newArray: Array[A] = unsafeArray.clone()
    iForeach({(i, el) => newArray(i) = f(el) })
    new Arr[A](newArray)
  }

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
  { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }

  /** Returns a new shorter Arr with the head elements removed. */
  def drop(n: Int)(implicit ct: ClassTag[A] @uncheckedVariance): Arr[A] =
  { val n2 = n.max0
    val newLen: Int = (sdLength - n2).max0
    val newArray = new Array[A](newLen)
    iUntilForeach(newLen)(i => newArray(i) = unsafeArray(i + n2))
    new Arr(newArray)
  }

  /** Returns a new shorter Arr with the last elements removed. */
  def dropRight(n: Int)(implicit ct: ClassTag[A] @uncheckedVariance): Arr[A] =
  { val n2 = n.max0
    val newLen: Int = (sdLength - n2).max0
    val newArray = new Array[A](newLen)
    iUntilForeach(newLen)(i => newArray(i) = unsafeArray(i))
    new Arr(newArray)
  }

  /** Alias for appendArr. Functionally appends 2nd [[Arr]] collection to dispatching [[Arr]], allows type widening. This operation allows type
   *  widening.*/
  @inline def ++ [AA >: A](op: Arr[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] = appendArr[AA](op)(ct)
  /** Functionally concatenates element to dispatching [[Arr]], allows type widening. Aliased by -+ operator. The '-' character in the operator name
   *  indicates loss of type precision. The ++ appendArr method is preferred when type widening is not required. */
  def appendArr [AA >: A](op: Arr[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] =
  { val newArray = new Array[AA](sdLength + op.sdLength)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, sdLength)
    new Arr(newArray)
  }

  /** Alias for append. Functionally concatenates element to dispatching [[Arr]], allows type widening. */
  @inline def +% [AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] = append[AA](op)(ct)
  /** Functionally appends an element to dispatching Refs, allows type widening. Aliased by +- operator. */
  def append[AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] =
  { val newArray = new Array[AA](sdLength + 1)
    unsafeArray.copyToArray(newArray)
    newArray(sdLength) = op
    new Arr(newArray)
  }

  /** Alias for prepend. Functionally prepends element to array. Allows type widening. There is no precaternateRefs method, as this would serve no
   *  purpose. The ::: method on Lists is required for performance reasons. */
  @inline def %: [AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA] @uncheckedVariance): Arr[AA] = prepend(op)(ct)
  /** Functionally prepends element to array. Aliased by the +: operator. */
  def prepend[AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] =
  { val newArray = new Array[AA](sdLength + 1)
    newArray(0) = op
    unsafeArray.copyToArray(newArray, 1)
    new Arr(newArray)
  }

  /** Concatenates the elements of the operand [[Arr]], if the condition is true, else returns the original [[Arr]]. The return type is the super type of
   *  the original [[Arr]] and the operand [[Arr]]. The operand is lazy so will only be evaluated if the condition is true. This is similar to the appendsIf
   *  method, but concatsIf allows type widening. */
  def concatArrIf[AA >: A](b: Boolean, newElems: => Arr[AA])(implicit ct: ClassTag[AA]): Arr[AA] =
    ife(b,this ++ newElems, this)

  /** Appends the elements of the operand Refs if the condition is true, else returns the original Refs. The operand is lazy so will only be evaluated
   *  if the condition is true. This is similar to the concatsIf method, but appendsIf does not allow type widening. */
  def appendArrIf(b: Boolean, newElems: => Arr[A] @uncheckedVariance)(implicit ct: ClassTag[A] @uncheckedVariance): Arr[A] =
    ife(b,this ++ newElems, this)

  def concatOption[AA >: A](optElem: Option[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): Arr[AA] =
    optElem.fld(this, this +% _)

  def appendOption(optElem: Option[A]@uncheckedVariance)(implicit ct: ClassTag[A] @uncheckedVariance): Arr[A] =
    optElem.fld(this, this +% _)

  def appendsOption(optElem: Option[Arr[A]]@uncheckedVariance)(implicit @unused ct: ClassTag[A] @uncheckedVariance ): Arr[A] =
    optElem.fld(this, ++ _)

  def concatsOption[AA >: A <: AnyRef](optElems: Option[Arr[AA]])(implicit ct: ClassTag[AA]): Arr[AA] =
    optElems.fld[Arr[AA]](this, this ++ _)

  def setAll(value: A @uncheckedVariance): Unit =
  { var i = 0
    while(i < sdLength){unsafeSetElem(i, value); i += 1}
  }

  def mapToCurlySyntax: String = ???
}

/** Companion object for the [[Arr]] class contains factory apply method, EqT implicit type class instance and Extension method for Arr[A] where A
 * extends AnyRef. */
object Arr
{ def apply[A](input: A*)(implicit ct: ClassTag[A]): Arr[A] = new Arr(input.toArray)
  implicit def showImplicit[A](implicit evA: ShowT[A]): ShowT[Arr[A]] = DataGenShowT[A, Arr[A]](evA)

  implicit def eqTImplcit[A](implicit evA: EqT[A]): EqT[Arr[A]] = (arr1, arr2) => if (arr1.sdLength != arr2.sdLength) false else
  { var i = 0
    var res = true
    while(i < arr1.sdLength & res) if (evA.eqT(arr1(i), arr2(i))) i += 1 else res = false
    res
  }

  implicit class ArrExtension[A <: AnyRef](thisArr: Arr[A])
  {
    def optFind(f: A => Boolean): Option[A] =
    { var acc: Option[A] = None
      var count = 0
      while (acc == None & count < thisArr.sdLength) if (f(thisArr(count))) acc = Some(thisArr(count)) else count += 1
      acc
    }
  }
}

/** The default Immutable Array based collection builder for the Arr[A] class. */
class ArrTBuild[B](implicit ct: ClassTag[B], @unused notB: Not[SpecialT]#L[B] ) extends ArrBuilder[B, Arr[B]] with ArrFlatBuilder[Arr[B]]
{ type BuffT = TBuff[B]
  override def newArr(length: Int): Arr[B] = new Arr(new Array[B](length))
  override def arrSet(arr: Arr[B], index: Int, value: B): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): TBuff[B] = new TBuff(new ArrayBuffer[B](length))
  override def buffGrow(buff: TBuff[B], value: B): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: TBuff[B], arr: Arr[B]): Unit = buff.unsafeBuff.addAll(arr.unsafeArray)
  override def buffToBB(buff: TBuff[B]): Arr[B] = new Arr(buff.unsafeBuff.toArray)
}

/** Not sure if this class is necessary now that Arr takes Any. */
final class TBuff[A](val unsafeBuff: ArrayBuffer[A]) extends AnyVal with Sequ[A]
{ override def typeStr: String = "AnyBuff"
  override def sdIndex(index: Int): A = unsafeBuff(index)
  override def sdLength: Int = unsafeBuff.length
  override def length: Int = unsafeBuff.length
  override def unsafeSetElem(i: Int, value: A): Unit = unsafeBuff(i) = value
  override def fElemStr: A => String = _.toString

  /** The final type of this object. */
  override type ThisT = TBuff[A]

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqDef]] of the this [[SeqImut]]'s final type. */
  override def unsafeSameSize(length: Int): TBuff[A] = ???
}