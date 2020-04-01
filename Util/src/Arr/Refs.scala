package ostrat
import annotation.unchecked.uncheckedVariance
import reflect.ClassTag
import annotation.unused
import scala.collection.mutable.ArrayBuffer

/** The immutable Array based class for reference types. It Inherits the standard foreach, map, flatMap and fold and their variations' methods from
 *  ArrayLike. */
final class Refs[+A <: AnyRef](val array: Array[A] @uncheckedVariance) extends AnyVal with Arr[A]
{ type ThisT = Refs[A] @uncheckedVariance
  override def unsafeNew(length: Int): Refs[A] = new Refs(new Array[AnyRef](length).asInstanceOf[Array[A]])
  override def length: Int = array.length
  override def apply(index: Int): A = array(index)
  override def toString: String = "Refs" + elemsStr
  def elemsStr: String =  array.toStrsCommaParenth()
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit = array(i) = value
  @inline def drop1(implicit ct: ClassTag[A] @uncheckedVariance): Refs[A] = drop(1)
  def offset(value: Int): RefsOff[A] @uncheckedVariance = new RefsOff[A](value)
  def offset0: RefsOff[A @uncheckedVariance] = offset(0)

  override def unsafeArrayCopy(operand: Array[A] @uncheckedVariance, offset: Int, copyLength: Int): Unit =
  { array.copyToArray(array, offset, copyLength); () }

  def drop(n: Int)(implicit ct: ClassTag[A] @uncheckedVariance): Refs[A] =
  { val newArray = new Array[A]((length - 1).min0)
    iUntilForeach(1, length)(i => newArray(i - 1) = array(i))
    new Refs(newArray)
  }

  /** Alias for appendRefs. Functionally appends 2nd Refs collection to dispatching Refs, allows type widening. This operation allows type widening.*/
  @inline def ++ [AA >: A <: AnyRef](op: Refs[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): Refs[AA] = appendRefs[AA](op)(ct)
  /** Functionally concatenates element to dispatching Refs, allows type widening. Aliased by -+ operator. The '-' character in the operator name
   *  indicates loss of type precision. The ++ appendRefs method is preferred when type widening is not required. */
  def appendRefs [AA >: A <: AnyRef](op: Refs[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): Refs[AA] =
  { val newArray = new Array[AA](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Refs(newArray)
  }

  /** Alias for concat. Functionally concatenates element to dispatching Refs, allows type widening. */
  @inline def +- [AA >: A <: AnyRef](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): Refs[AA] = append[AA](op)(ct)
  /** Functionally appends an element to dispatching Refs, allows type widening. Aliased by +- operator. */
  def append[AA >: A <: AnyRef](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): Refs[AA] =
  { val newArray = new Array[AA](length + 1)
    array.copyToArray(newArray)
    newArray(length) = op
    new Refs(newArray)
  }

  /** Alias for prepend. Functionally prepends element to array. Allows type widening. There is no precaternateRefs method, as this would serve no
   *  purpose. The ::: method on Lists is required for performance reasons. */
  @inline def +: [AA >: A <: AnyRef](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA] @uncheckedVariance): Refs[AA] = prepend(op)(ct)
  /** Functionally prepends element to array. Aliased by the +: operator. */
  def prepend[AA >: A <: AnyRef](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): Refs[AA] =
  { val newArray = new Array[AA](length + 1)
    newArray(0) = op
    array.copyToArray(newArray, 1)
    new Refs(newArray)
  }

  /** Concatenates the elements of the operands Refs if the condition is true, else returns the original Refs. The return type is the super type of the
   * original Refs and the operand Ref. The operand is lazy so will only be evaluated if the condition is true. This is similar to the appendsIf
   * method, but concatsIf allows type widening. */
  def concatRefsIf[AA >: A <: AnyRef](b: Boolean, newElems: => Refs[AA])(implicit ct: ClassTag[AA]): Refs[AA] = ife(b,this ++ newElems, this)

  /** Appends the elements of the operand Refs if the condition is true, else returns the original Refs. The operand is lazy so will only be evaluated
   *  if the condition is true. This is similar to the concatsIf method, but appendsIf does not allow type widening. */
  def appendRefsIf(b: Boolean, newElems: => Refs[A] @uncheckedVariance)(implicit ct: ClassTag[A] @uncheckedVariance): Refs[A] =
    ife(b,this ++ newElems, this)

  def concatOption[AA >: A <: AnyRef](optElem: Option[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): Refs[AA] =
    optElem.fld(this, this +- _)

  def appendOption(optElem: Option[A]@uncheckedVariance)(implicit ct: ClassTag[A] @uncheckedVariance): Refs[A] =
    optElem.fld(this, +- _)

  def appendsOption(optElem: Option[Refs[A]]@uncheckedVariance)(implicit ct: ClassTag[A] @uncheckedVariance): Refs[A] =
    optElem.fld(this, ++ _)

  def concatsOption[AA >: A <: AnyRef](optElems: Option[Refs[AA]])(implicit ct: ClassTag[AA]): Refs[AA] =
    optElems.fld[Refs[AA]](this, this ++ _)

  def optFind(f: A => Boolean): OptRef[A] =
  { var acc: OptRef[A] = NoRef
    var count = 0
    while (acc == NoRef & count < length) if (f(apply(count))) acc = OptRef(apply(count)) else count += 1
    acc
  }

  def setAll(value: A @uncheckedVariance): Unit =
  { var i = 0
    while(i < length){unsafeSetElem(i, value); i += 1}
  }
}

class RefsBuild[A <: AnyRef](implicit ct: ClassTag[A], @unused notA: Not[ProdHomo]#L[A]) extends ArrBuild[A, Refs[A]] with ArrFlatBuild[Refs[A]]
{ type BuffT = RefBuff[A]
  override def imutNew(length: Int): Refs[A] = new Refs(new Array[A](length))
  override def imutSet(arr: Refs[A], index: Int, value: A): Unit = arr.array(index) = value
  override def buffNew(length: Int = 4): RefBuff[A] = new RefBuff(new ArrayBuffer[A](length))
  override def buffGrow(buff: RefBuff[A], value: A): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: RefBuff[A], arr: Refs[A]): Unit = buff.unsafeBuff.addAll(arr.array)
  override def buffToArr(buff: RefBuff[A]): Refs[A] = new Refs(buff.unsafeBuff.toArray)
}

object Refs
{ def apply[A <: AnyRef](input: A*)(implicit ct: ClassTag[A]): Refs[A] = new Refs(input.toArray)
  implicit def showImplicit[A <: AnyRef](implicit evA: Show[A]): Show[Refs[A]] = ArrayLikeShow[A, Refs[A]](evA)
}

class RefBuff[A <: AnyRef](val unsafeBuff: ArrayBuffer[A]) extends AnyVal with ArrayLike[A]
{ override def apply(index: Int): A = unsafeBuff(index)
  override def length: Int = unsafeBuff.length
}