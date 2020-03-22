package ostrat
import annotation.unchecked.uncheckedVariance
import reflect.ClassTag
import annotation.unused
import scala.collection.mutable.ArrayBuffer

/** The immutable Array based class for reference types. It Inherits the standard foreach, map, flatMap and fold and their variations' methods from
 *  ArrayLike. */
final class Refs[+A <: AnyRef](val array: Array[A] @uncheckedVariance) extends AnyVal with ArrImut[A]
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
  {  array.copyToArray(array, offset, copyLength); ()}

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
}

class RefsBuild[A <: AnyRef](implicit ct: ClassTag[A], @unused notA: Not[ProdHomo]#L[A]) extends ArrBuild[A, Refs[A]] with ArrArrBuild[Refs[A]]
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

/** Extractor object for empty Refs[A <: AnyRef]. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs0
{ /** Extractor for empty Refs[A <: AnyRef]. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Boolean = refs.length == 0
}

/** Extractor object for Refs[A <: AnyRef] of length == 1. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs1
{ /** Extractor for Refs[A <: AnyRef] of length == 1. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[A] = refs.length match
  { case 1 => Some(refs(0))
    case _ => None
  }
}

/** Extractor object for Refs[A <: AnyRef] of length == 2. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs2
{ /** Extractor for Refs[A <: AnyRef] of length == 2. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A)] = refs.length match
  { case 2 => Some((refs(0), refs(1)))
    case _ => None
  }
}

/** Extractor object for Refs[A <: AnyRef] of length == 3. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs3
{ /** Extractor for Refs[A <: AnyRef] of length == 3, Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A)] = refs.length match
  { case 3 => Some((refs(0), refs(1), refs(2)))
    case _ => None
  }
}

/** Extractor object for Refs[A <: AnyRef] of length == 4. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs4
{ /** Extractor for Refs[A <: AnyRef] of length == 4, Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A, A)] = refs.length match
  { case 4 => Some((refs(0), refs(1), refs(2), refs(3)))
    case _ => None
  }
}

/** Extractor object for Refs[A <: AnyRef] of length == 5. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs5
{ /** Extractor for Refs[A <: AnyRef] of length == 5, Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A, A, A)] = refs.length match
  { case 5 => Some((refs(0), refs(1), refs(2), refs(3), refs(4)))
    case _ => None
  }
}

/** Extractor object for Refs[A <: AnyRef] of length == 6. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
object Refs6
{ /** Extractor for Refs[A <: AnyRef] of length == 6, Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A, A, A, A)] = refs.length match
  { case 6 => Some((refs(0), refs(1), refs(2), refs(3), refs(4), refs(5)))
    case _ => None
  }
}


object Refs1Tail
{
  def unapply[A <: AnyRef](refs: Refs[A])(implicit ct: ClassTag[A]): Option[(A, Refs[A])] = refs match
  { case arr if refs.nonEmpty => Some((refs.head, refs.drop1))
    case _ => None
  }
}

object Refs2Tail
{
  def unapply[A <: AnyRef](refs: Refs[A])(implicit ct: ClassTag[A]): Option[(A, A, Refs[A])] = refs match
  { case arr if refs.nonEmpty => Some((refs(0), refs(1), refs.drop1))
    case _ => None
  }
}

object RefsHead
{ /** Extractor for the head of a Refs, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[A] = refs match
  { case refs if refs.length >= 1 => Some(refs(0))
    case _ => None
  }
}

object RefsHead2
{ /** Extractor for the head 2 elements of a Refs, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A)] = refs match
  { case refs if refs.length >= 2 => Some((refs(0), refs(1)))
    case _ => None
  }
}

object RefsHead3
{ /** Extractor for the head of a Refs, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A)] = refs match
  { case refs if refs.length >= 3 => Some((refs(0), refs(1), refs(2)))
    case _ => None
  }
}

object RefsHead4
{ /** Extractor for the head of a Refs, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A <: AnyRef](refs: Refs[A]): Option[(A, A, A, A)] = refs match
  { case refs if refs.length >= 4 => Some((refs(0), refs(1), refs(2), refs(3)))
    case _ => None
  }
}
/** Extractor function object for a Good Refs Sequence of length 0. */
case object GoodRefs0
{ /** Extractor method for a Good Refs Sequence of length 0. */
  def unapply(refs: EMon[Refs[_]]): Boolean = refs match
  { case Good(refs) if refs.length == 0 => true
    case _ => false
  }
}

/** Extractor function object for a Good Refs Sequence of length 1. */
object GoodRefs1
{ /** Extractor method for a Good Refs Sequence of length 1. */
  def unapply[A <: AnyRef](refs: EMon[Refs[A]]): Option[A] = refs match
  { case Good(refs) if refs.length == 1 => Some(refs.head)
    case _ => None
  }
}

object GoodRefs2
{ def unapply[A <: AnyRef](er: EMon[Refs[A]]): Option[(A, A)] = er.foldErrs (g => if (g.length == 2) Some((g(0), g(1))) else None)(errs => None)
}

object GoodRefs3
{ def unapply[A <: AnyRef](refs: EMon[Refs[A]]): Option[(A, A, A)] = refs match
  { case Good(refs) if refs.length == 3 => Some((refs(0), refs(1), refs(2)))
    case _ => None
  }
}

object GoodRefs4
{ def unapply[A <: AnyRef](refs: EMon[Refs[A]]): Option[(A, A, A, A)] = refs match
  { case Good(refs) if refs.length == 4 => Some((refs(0), refs(1),refs(2), refs(3)))
    case _ => None
  }
}

/** Immutable heapless iterator for Refs. */
class RefsOff[A <: AnyRef](val offset0: Int) extends AnyVal with ArrOff[A, Refs[A]]
{ override def apply(index: Int)(implicit refs: Refs[A]) = refs(index + offset0)
  def drop(n: Int): RefsOff[A] = new RefsOff[A](offset0 + n)
  def drop1: RefsOff[A] = new RefsOff(offset0 + 1)
  def drop2: RefsOff[A] = new RefsOff(offset0 + 2)
  def length(implicit refs: Refs[A]): Int = refs.length - offset0
  def span(p: A => Boolean)(implicit refs: Refs[A], ct: ClassTag[A]): (Refs[A], RefsOff[A]) =
  {
    var count = 0
    var continue = true
    while (offset0 + count < refs.length & continue)
    {
      if (p(refs(offset0 + count))) count += 1
      else continue = false
    }
    val newArray: Array[A] = new Array[A](count)
    iUntilForeach(0, count){i =>
      newArray(i) = refs(offset0 + i)}
    (new Refs(newArray), drop(count))
  }
  /** Checks condition against head. Returns false if the collection is empty. */
  def ifHead(f: A => Boolean)(implicit refs: Refs[A]) : Boolean = (refs.length > offset0) & f(refs(offset0))

}

/** Extractor for empty immutable heapless iterator for Refs. */
case object RefsOff0
{ /** Extractor for empty immutable heapless iterator for Refs. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Boolean = inp.length <= 0
}

/** Extractor object for an immutable heapless iterator for Refs with exactly 1 element. */
object RefsOff1
{ /** Extractor for an immutable heapless iterator for Refs with exactly  1 element. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[A] =
    ife(inp.length == 1, Some(inp(0)), None)
}

/** Extractor object for an immutable heapless iterator for Refs with exactly 2 elements. */
object RefsOff2
{ /** Extractor for an immutable heapless iterator for Refs with exactly 2 elements. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[(A, A)] =
    ife(inp.length == 2, Some((inp(0), inp(1))), None)
}

/** Extractor object for an immutable heapless iterator for Refs with exactly 3 elements. */
object RefsOff3
{ /** Extractor for an immutable heapless iterator for Refs with exactly 3 elements. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[(A, A, A)] =
    ife(inp.length == 3, Some((inp(0), inp(1), inp(2))), None)
}

/** Extractor for immutable heapless iterator for Refs with at least l element. */
object RefsOff1Tail
{ /** Extractor for immutable heapless iterator for Refs with at least l element. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[(A, RefsOff[A])] =
    ife(inp.length >= 1, Some(((inp(0)), inp.drop1)), None)
}

object RefsOff2Tail
{
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[(A, A, RefsOff[A])] =
    ife(inp.length >= 2, Some((inp(0), inp(1), inp.drop2)), None)
}

object RefsOffHead
{
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Refs[A]): Option[A] =
    ife(inp.length  >= 1, Some(inp(0)), None)
}

/*
class RefsBuff[A <: AnyRef](val buffer: ArrayBuffer[A]) extends AnyVal with BufferLike[A]
{ override def length: Int = buffer.length
  override def apply(index: Int): A = buffer(index)
}*/
