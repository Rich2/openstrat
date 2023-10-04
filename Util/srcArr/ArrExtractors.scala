/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, annotation.unchecked.uncheckedVariance

/** Extractor object for empty [[Arr]][A]. Arr[A] is an immutable covariant Array based collection. */
object Arr0
{ /** Extractor for empty [[Arr]][A]. Arr[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Boolean = arr.length == 0
}

/** Extractor object for [[Arr]][A] of length == 1. Arr[A] is an immutable covariant Array based collection. */
object Arr1
{ /** Extractor for [[Arr]][A] of length == 1. Arr[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Option[A] = arr.length match
  { case 1 => Some(arr(0))
    case _ => None
  }
}

/** Extractor object for [[Arr]][A] of length == 2. Arr[A] is an immutable covariant Array based collection. */
object Arr2
{ /** Extractor for [[Arr]][A] of length == 2. Arr[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Option[(A, A)] = ife(arr.length == 2, Some((arr(0), arr(1))), None)
}

/** Extractor object for [[Arr]][A] of length == 3. Arr[A] is an immutable covariant Array based collection. */
object Arr3
{ /** Extractor for [[Arr]][A] of length == 3, Arr[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Option[(A, A, A)] = arr.length match
  { case 3 => Some((arr(0), arr(1), arr(2)))
    case _ => None
  }
}

/** Extractor object for [[Arr]][A] of length == 4. Arr[A] is an immutable covariant Array based collection. */
object Arr4
{ /** Extractor for [[Arr]][A] of length == 4, Arr[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Option[(A, A, A, A)] = arr.length match
  { case 4 => Some((arr(0), arr(1), arr(2), arr(3)))
    case _ => None
  }
}

/** Extractor object for [[Arr]][A] of length == 5. Arr[A] is an immutable covariant Array based collection. */
object Arr5
{ /** Extractor for [[Arr]][A] of length == 5, Arr[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Option[(A, A, A, A, A)] = arr.length match
  { case 5 => Some((arr(0), arr(1), arr(2), arr(3), arr(4)))
    case _ => None
  }
}

/** Extractor object for [[Arr]][A] of length == 6. Arr[A] is an immutable covariant Array based collection. */
object Arr6
{ /** Extractor for [[Arr]][A] of length == 6, Arr[A] is an immutable covariant Array based collection. */
  def unapply[A](arr: Arr[A]): Option[(A, A, A, A, A, A)] = arr.length match
  { case 6 => Some((arr(0), arr(1), arr(2), arr(3), arr(4), arr(5)))
    case _ => None
  }
}

object Arr1Tail
{ /** Extractor method for Arr of length >= 1. Optionally Returns a [[Tuple2]] of the first element and the tail. */
  def unapply[A](arr: RArr[A])(implicit ct: ClassTag[A]): Option[(A, RArr[A])] = ife(arr.nonEmpty, Some((arr.head, arr.drop1)), None)

  def unapply[A, ArrT <: ArrNoParam[A]{type ThisT = ArrT}](arr: ArrT): Option[(A, ArrT)] = ife(arr.nonEmpty, Some((arr.head, arr.tail)), None)
}

object Arr2Tail
{ /** Extractor method for Arr of length >= 2. Optionally Returns a [[Tuple3]] of the first 2 elements and the tail. */
  def unapply[A](arr: RArr[A])(implicit ct: ClassTag[A]): Option[(A, A, RArr[A])] = ife(arr.length >= 2, Some((arr(0), arr(1), arr.drop(2))), None)

  def unapply[A, ArrT <: ArrNoParam[A]{type ThisT = ArrT}](arr: ArrT): Option[(A, A, ArrT)] =
    ife(arr.length >= 2, Some((arr(0), arr(1), arr.drop(2))), None)
}

object Arr3Tail
{ /** Extractor method for Arr of length >= 3. Optionally Returns a [[Tuple4]] of the first 3 elements and the tail. */
  def unapply[A](arr: RArr[A])(implicit ct: ClassTag[A]): Option[(A, A, A, RArr[A])] =
    ife(arr.length >= 3, Some((arr(0), arr(1), arr(2), arr.drop1)), None)

  def unapply[A, ArrT <: ArrNoParam[A] {type ThisT = ArrT}](arr: ArrT): Option[(A, A, A, ArrT)] =
    ife(arr.length >= 2, Some((arr(0), arr(1), arr(2), arr.drop(3))), None)
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

/** Extractor function object for a Good Arr Sequence of length 0. */
case object GoodArr0
{ /** Extractor method for a Good Arr Sequence of length 0. */
  def unapply(eArr: EMon[Arr[_]]): Boolean = eArr match
  { case Good(Arr0()) => true
    case _ => false
  }
}

/** Extractor function object for a Good Arr Sequence of length 1. */
object GoodArr1
{ /** Extractor method for a Good [[Arr]] Sequence of length 1. */
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

/** Immutable heapless iterator for Arr. */
class ArrOff[A](val offset0: Int) extends AnyVal with ArrBaseOff[A, RArr[A]]
{ override def apply(index: Int)(implicit arr: RArr[A]) = arr(index + offset0)
  def drop(n: Int): ArrOff[A] = new ArrOff[A](offset0 + n)
  def drop1: ArrOff[A] = new ArrOff(offset0 + 1)
  def drop2: ArrOff[A] = new ArrOff(offset0 + 2)
  def length(implicit arr: RArr[A]): Int = arr.length - offset0
  def str: String = "ArrOff offset = " + offset0.toString

  def span(p: A => Boolean)(implicit arr: RArr[A], ct: ClassTag[A]): (RArr[A], ArrOff[A]) =
  { var count = 0
    var continue = true

    while (offset0 + count < arr.length & continue)
    { if (p(arr(offset0 + count))) count += 1
      else continue = false
    }
    val newArray: Array[A] = new Array[A](count)
    iUntilForeach(count){i =>
      newArray(i) = arr(offset0 + i)}
    (new RArr(newArray), drop(count))
  }
  /** Checks condition against head. Returns false if the collection is empty. */
  def ifHead(f: A => Boolean)(implicit arr: RArr[A]): Boolean = (arr.length > offset0) & f(arr(offset0))

  /** Folds over the existence of a head on this [[ArrOff]] iterator */
  def headFold[B](emptyValue: => B)(f: (A, ArrOff[A]) => B)(implicit arr: RArr[A]): B =
    if (arr.length > offset0) f(arr(offset0), drop1) else emptyValue

  /** Partitions this [[ArrOff]] into an [[RArr]] and an [[ArrOff]] with the first part taking all the elements while they are of type AA. */
  def partitionT[AA <: A@uncheckedVariance](implicit ct1: ClassTag[AA]@uncheckedVariance,  arr: RArr[A]): (RArr[AA], ArrOff[A]) =
  { val buff = Buffer[AA]()
    var continue = true
    var i = 0
    while (i < length && continue) apply(i) match {
      case el if el.isInstanceOf[AA] => {
        buff.append(el.asInstanceOf[AA])
        i += 1
      }
      case _ => continue = false
    }
    (new RArr(buff.toArray), drop(i))
  }
}

/** Extractor for empty immutable heapless iterator for Arr. */
case object ArrOff0
{ /** Extractor for empty immutable heapless iterator for Arr. */
  def unapply[A](inp: ArrOff[A])(implicit arr: RArr[A]): Boolean = inp.length <= 0
}

/** Extractor object for an immutable heapless iterator for Arr with exactly 1 element. */
object ArrOff1
{ /** Extractor for an immutable heapless iterator for Arr with exactly  1 element. */
  def unapply[A](inp: ArrOff[A])(implicit arr: RArr[A]): Option[A] =
    ife(inp.length == 1, Some(inp(0)), None)
}

/** Extractor object for an immutable heapless iterator for Arr with exactly 2 elements. */
object ArrOff2
{ /** Extractor for an immutable heapless iterator for Arr with exactly 2 elements. */
  def unapply[A](inp: ArrOff[A])(implicit arr: RArr[A]): Option[(A, A)] =
    ife(inp.length == 2, Some((inp(0), inp(1))), None)
}

/** Extractor object for an immutable heapless iterator for Arr with exactly 3 elements. */
object ArrOff3
{ /** Extractor for an immutable heapless iterator for Arr with exactly 3 elements. */
  def unapply[A](inp: ArrOff[A])(implicit arr: RArr[A]): Option[(A, A, A)] =
    ife(inp.length == 3, Some((inp(0), inp(1), inp(2))), None)
}

/** Extractor for immutable heapless iterator for Arr with at least l element. */
object ArrOff1Tail
{ /** Extractor for immutable heapless iterator for Arr with at least l element. */
  def unapply[A](inp: ArrOff[A])(implicit arr: RArr[A]): Option[(A, ArrOff[A])] =
    ife(inp.length >= 1, Some(((inp(0)), inp.drop1)), None)
}

object ArrOff2Tail
{
  def unapply[A](inp: ArrOff[A])(implicit arr: RArr[A]): Option[(A, A, ArrOff[A])] =
    ife(inp.length >= 2, Some((inp(0), inp(1), inp.drop2)), None)
}

object ArrOffHead
{
  def unapply[A](inp: ArrOff[A])(implicit arr: RArr[A]): Option[A] =
    ife(inp.length  >= 1, Some(inp(0)), None)
}