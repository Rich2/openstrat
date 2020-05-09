package ostrat
import reflect.ClassTag

/** Extractor object for empty Refs[A]. Refs[A] is an immutable covariant Array based collection. */
object Arr0
{ /** Extractor for empty Refs[A]. Refs[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Boolean = arr.length == 0
}

/** Extractor object for Refs[A] of length == 1. Refs[A] is an immutable covariant Array based collection. */
object Arr1
{ /** Extractor for Refs[A] of length == 1. Refs[A <: AnyRef] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Option[A] = arr.length match
  { case 1 => Some(arr(0))
    case _ => None
  }
}

/** Extractor object for Refs[A] of length == 2. Refs[A] is an immutable covariant Array based collection. */
object Arr2
{ /** Extractor for Refs[A] of length == 2. Refs[A] is an immutable covariant Array based collection. */
  def unapply[A](refs: Arr[A]): Option[(A, A)] = refs.length match
  { case 2 => Some((refs(0), refs(1)))
    case _ => None
  }
}

/** Extractor object for Refs[A] of length == 3. Refs[A] is an immutable covariant Array based collection. */
object Arr3
{ /** Extractor for Refs[A] of length == 3, Refs[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Option[(A, A, A)] = arr.length match
  { case 3 => Some((arr(0), arr(1), arr(2)))
    case _ => None
  }
}

/** Extractor object for Refs[A] of length == 4. Refs[A] is an immutable covariant Array based collection. */
object Arr4
{ /** Extractor for Refs[A] of length == 4, Refs[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Option[(A, A, A, A)] = arr.length match
  { case 4 => Some((arr(0), arr(1), arr(2), arr(3)))
    case _ => None
  }
}

/** Extractor object for Refs[A] of length == 5. Refs[A] is an immutable covariant Array based collection. */
object Arr5
{ /** Extractor for Refs[A] of length == 5, Refs[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Option[(A, A, A, A, A)] = arr.length match
  { case 5 => Some((arr(0), arr(1), arr(2), arr(3), arr(4)))
    case _ => None
  }
}

/** Extractor object for Refs[A] of length == 6. Refs[A] is an immutable covariant Array based collection. */
object Arr6
{ /** Extractor for Refs[A] of length == 6, Refs[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Option[(A, A, A, A, A, A)] = arr.length match
  { case 6 => Some((arr(0), arr(1), arr(2), arr(3), arr(4), arr(5)))
    case _ => None
  }
}

object arr1Tail
{ def unapply[A](arr: Arr[A])(implicit ct: ClassTag[A]): Option[(A, Arr[A])] = ife(arr.nonEmpty, Some((arr.head, arr.drop1)), None)
}

object arr2Tail
{ def unapply[A](arr: Arr[A])(implicit ct: ClassTag[A]): Option[(A, A, Arr[A])] = ife(arr.nonEmpty, Some((arr(0), arr(1), arr.drop1)), None)
}

object ArrHead
{ /** Extractor for the head of an Arr, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A](arr: Arr[A]): Option[A] = ife(arr.nonEmpty, Some(arr(0)), None)
}

/** Extractor object for the head 2 elements of an Arr, immutable covariant Array based collection. The tail can be any length. */
object ArrHead2
{ /** Extractor for the head 2 elements of an Arr, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A](arr: Arr[A]): Option[(A, A)] = ife(arr.length >= 2, Some((arr(0), arr(1))), None)
}

object ArrHead3
{ /** Extractor for the head of an Arr, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A](arr: Arr[A]): Option[(A, A, A)] = ife(arr.length >= 3, Some((arr(0), arr(1), arr(2))), None)
}

object ArrHead4
{ /** Extractor for the head of an Arr, immutable covariant Array based collection. The tail can be any length. */
  def unapply[A](arr: Arr[A]): Option[(A, A, A, A)] = ife(arr.length >= 4, Some((arr(0), arr(1), arr(2), arr(3))), None)
}

/** Extractor function object for a Good Refs Sequence of length 0. */
case object GoodArr0
{ /** Extractor method for a Good Refs Sequence of length 0. */
  def unapply(eArr: EMon[Arr[_]]): Boolean = eArr match
  { case Good(Arr0()) => true
    case _ => false
  }
}

/** Extractor function object for a Good Refs Sequence of length 1. */
object GoodArr1
{ /** Extractor method for a Good Refs Sequence of length 1. */
  def unapply[A](eArr: EMon[Arr[A]]): Option[A] = eArr match
  { case Good(Arr1(head)) => Some(head)
    case _ => None
  }
}

object GoodArr2
{ def unapply[A](eArr: EMon[Arr[A]]): Option[(A, A)] = eArr.foldErrs (g => if (g.length == 2) Some((g(0), g(1))) else None)(errs => None)
}

object GoodArr3
{ def unapply[A](eArr: EMon[Arr[A]]): Option[(A, A, A)] = eArr match
  { case Good(Arr3(a0, a1, a2)) => Some((a0, a1, a2))
    case _ => None
  }
}

object GoodArr4
{ def unapply[A](arr: EMon[Arr[A]]): Option[(A, A, A, A)] = arr match
  { case Good(Arr4(a0, a1, a2, a3)) => Some((a0, a1, a2, a3))
    case _ => None
  }
}

/** Immutable heapless iterator for Refs. */
class RefsOff[A](val offset0: Int) extends AnyVal with ArrOff[A, Arr[A]]
{ override def apply(index: Int)(implicit refs: Arr[A]) = refs(index + offset0)
  def drop(n: Int): RefsOff[A] = new RefsOff[A](offset0 + n)
  def drop1: RefsOff[A] = new RefsOff(offset0 + 1)
  def drop2: RefsOff[A] = new RefsOff(offset0 + 2)
  def length(implicit refs: Arr[A]): Int = refs.length - offset0
  def span(p: A => Boolean)(implicit refs: Arr[A], ct: ClassTag[A]): (Arr[A], RefsOff[A]) =
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
    (new Arr(newArray), drop(count))
  }
  /** Checks condition against head. Returns false if the collection is empty. */
  def ifHead(f: A => Boolean)(implicit refs: Arr[A]) : Boolean = (refs.length > offset0) & f(refs(offset0))

}

/** Extractor for empty immutable heapless iterator for Refs. */
case object RefsOff0
{ /** Extractor for empty immutable heapless iterator for Refs. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Arr[A]): Boolean = inp.length <= 0
}

/** Extractor object for an immutable heapless iterator for Refs with exactly 1 element. */
object RefsOff1
{ /** Extractor for an immutable heapless iterator for Refs with exactly  1 element. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Arr[A]): Option[A] =
    ife(inp.length == 1, Some(inp(0)), None)
}

/** Extractor object for an immutable heapless iterator for Refs with exactly 2 elements. */
object RefsOff2
{ /** Extractor for an immutable heapless iterator for Refs with exactly 2 elements. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Arr[A]): Option[(A, A)] =
    ife(inp.length == 2, Some((inp(0), inp(1))), None)
}

/** Extractor object for an immutable heapless iterator for Refs with exactly 3 elements. */
object RefsOff3
{ /** Extractor for an immutable heapless iterator for Refs with exactly 3 elements. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Arr[A]): Option[(A, A, A)] =
    ife(inp.length == 3, Some((inp(0), inp(1), inp(2))), None)
}

/** Extractor for immutable heapless iterator for Refs with at least l element. */
object RefsOff1Tail
{ /** Extractor for immutable heapless iterator for Refs with at least l element. */
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Arr[A]): Option[(A, RefsOff[A])] =
    ife(inp.length >= 1, Some(((inp(0)), inp.drop1)), None)
}

object RefsOff2Tail
{
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Arr[A]): Option[(A, A, RefsOff[A])] =
    ife(inp.length >= 2, Some((inp(0), inp(1), inp.drop2)), None)
}

object RefsOffHead
{
  def unapply[A <: AnyRef](inp: RefsOff[A])(implicit refs: Arr[A]): Option[A] =
    ife(inp.length  >= 1, Some(inp(0)), None)
}